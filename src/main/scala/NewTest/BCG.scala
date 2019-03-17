package NewTest

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object BCG {

  def main(args: Array[String]): Unit = {


    val spark: SparkSession =
      SparkSession
        .builder()
        .appName("AppName")
        .config("spark.master", "local")
        .getOrCreate()


    import spark.sqlContext.implicits._
    val df_CategoricalDataset_1 = spark.read.option("header", "true").csv("/Users/shuvamoymondal/Downloads/odi-cricket-matches-19712017/CategoricalDataset.csv")
    val df_ContinousDataset_2 = spark.read.option("header", "true").csv("/Users/shuvamoymondal/Downloads/odi-cricket-matches-19712017/ContinousDataset.csv")
    val df_LabelledDataset_3 = spark.read.option("header", "true").csv("/Users/shuvamoymondal/Downloads/odi-cricket-matches-19712017/LabelledDataset.csv")
    val originalDataset_4 = spark.read.option("header", "true").csv("/Users/shuvamoymondal/Downloads/odi-cricket-matches-19712017/originalDataset.csv")

    // Solution 1
    val V_IND_Total_Match = originalDataset_4.filter(($"Team 1" === "India" || $"Team 2" === "India")).count

    val df_IND_total_Win_perntge = originalDataset_4.filter($"Winner" === "India").count.toFloat / V_IND_Total_Match * 100
    val df_IND_total_Loss_perntg = originalDataset_4.filter(($"Team 1" === "India" || $"Team 2" === "India") && (!$"Winner".isin("India", "tied", "no result"))).count.toFloat / V_IND_Total_Match * 100
    val df_IND_total_tied_perntge = originalDataset_4.filter(($"Winner" === "tied") && ($"Team 1" === "India" || $"Team 2" === "India")).count.toFloat / V_IND_Total_Match * 100
    val df_IND_total_no_result_perntge = originalDataset_4.filter(($"Winner" === "no result") && ($"Team 1" === "India" || $"Team 2" === "India")).count.toFloat / V_IND_Total_Match * 100

    val sol1 =
      s"""Total Win Percentage: $df_IND_total_Win_perntge
         ||Total Loss Percentage: $df_IND_total_Loss_perntg
         ||Total Tied Percentage: $df_IND_total_tied_perntge
         ||Total No Result Percentage: $df_IND_total_no_result_perntge """.stripMargin

    println(sol1)


    // solution 2
    val HCG_dstnct_DF = df_ContinousDataset_2.select("Host_Country","Ground").distinct().cache()
    val Ind_total_Match_DF = originalDataset_4.filter(($"Team 1" === "India" || $"Team 2" === "India"))
    val combine_DF=HCG_dstnct_DF.join(Ind_total_Match_DF,HCG_dstnct_DF("Ground")===Ind_total_Match_DF("Ground"),"right_outer").drop(HCG_dstnct_DF("Ground"))

    //Total home match
    val Ind_total_Home_Match=combine_DF.filter(combine_DF("Host_Country") === "India").count
    //Total away match
    val Ind_total_away_Match=combine_DF.filter(combine_DF("Host_Country") !== "India").count

    //Total Home percentage
    val Home_percentage=combine_DF.filter(col("Host_Country")==="India").select(when(combine_DF("Winner") === "India","India_Win_at_home").as("status")).groupBy("status").agg((count("status")/Ind_total_Home_Match * 100).alias("percentage")).union(combine_DF.filter(col("Host_Country")==="India").select(when(!combine_DF("Winner").isin("India","no result"),"India_lost_at_home").as("status")).groupBy("status").agg((count("status")/Ind_total_Home_Match * 100).alias("percentage"))).union(combine_DF.filter(col("Host_Country")==="India").select(when(combine_DF("Winner") === "no result","Tie").as("status")).groupBy("status").agg((count("status")/Ind_total_Home_Match * 100).alias("percentage"))).filter(col("status").isNotNull)
    //Total Away percentage
    val Away_Percentage=combine_DF.filter(col("Host_Country")!=="India").select(when(combine_DF("Winner") === "India","India_Win_at_away").as("status")).groupBy("status").agg((count("status")/Ind_total_away_Match * 100).alias("percentage")).union(combine_DF.filter(col("Host_Country")!=="India").select(when(!combine_DF("Winner").isin("India","no result"),"India_lost_at_away").as("status")).groupBy("status").agg((count("status")/Ind_total_away_Match * 100).alias("percentage"))).union(combine_DF.filter(col("Host_Country")!=="India").filter(col("Host_Country").isNotNull).select(when(combine_DF("Winner")==="no result","Tie").as("status")).groupBy("status").agg((count("status")/Ind_total_away_Match * 100).alias("percentage"))).filter(col("status").isNotNull).show

    // solution 3

    val df_played_against = combine_DF.withColumn("team",when(col("Team 1") === "India", col("Team 2")).otherwise(col("Team 1"))).groupBy("team").agg(count("team").alias("played_against"))

    // solution 4

    val number_of_home_win_with_each_team= combine_DF.filter((col("Winner")=== "India") && (col("Team 1")==="India")).join(df_played_against,combine_DF("Team 2")===df_played_against("team")).groupBy("Team 2").agg(count("Team 2").alias("number_of_home_win"))

    val number_of_away_win_with_each_team=combine_DF.filter((col("Winner")=== "India") && (col("Team 2")==="India")).join(df_played_against,combine_DF("Team 1")===df_played_against("team")).groupBy("Team 1").agg(count("Team 1").alias("number_of_away_win"))

    val number_of_home_lost_with_each_team= combine_DF.filter((!col("Winner").isin("India","no result")) && (col("Team 1")==="India")).join(df_played_against,combine_DF("Team 2")===df_played_against("team")).groupBy("Team 2").agg(count("Team 2").alias("number_of_home_lost"))

    val number_of_away_loss_with_each_team=combine_DF.filter((!col("Winner").isin("India","no result")) && (col("Team 2")==="India")).join(df_played_against,combine_DF("Team 1")===df_played_against("team")).groupBy("Team 1").agg(count("Team 1").alias("number_of_away_lost"))

    val rslt =
      s"""Total Win  home with each team: $number_of_home_win_with_each_team.show
         ||Total win away with each team: $number_of_away_win_with_each_team.show
         ||Total lost home with each team: $number_of_home_lost_with_each_team.show
         ||Total lost away with each team: $number_of_away_loss_with_each_team.show """.stripMargin

    println(rslt)

  }
}
