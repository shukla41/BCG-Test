package NewTest

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql._

object TestSpark {
  def main(args: Array[String]): Unit = {


    val spark: SparkSession =
      SparkSession
        .builder()
        .appName("AppName")
        .config("spark.master", "local")
        .getOrCreate()


    import spark.sqlContext.implicits._

    val df_CategoricalDataset_1=spark.read.option("header","true").csv("/Users/shuvamoymondal/Downloads/odi-cricket-matches-19712017/CategoricalDataset.csv")

     val df_ContinousDataset_2=spark.read.option("header","true").csv("/Users/shuvamoymondal/Downloads/odi-cricket-matches-19712017/ContinousDataset.csv")

     val df_LabelledDataset_3=spark.read.option("header","true").csv("/Users/shuvamoymondal/Downloads/odi-cricket-matches-19712017/LabelledDataset.csv")

     val originalDataset_4=spark.read.option("header","true").csv("/Users/shuvamoymondal/Downloads/odi-cricket-matches-19712017/originalDataset.csv")




    val  df_IND_total_Win_perntge= originalDataset_4.filter($"Winner" === "India").count.toFloat/originalDataset_4.filter(($"Team 1" === "India" || $"Team 2" === "India")).count * 100
    val df_IND_total_Loss_perntg=originalDataset_4.filter(($"Team 1" === "India" || $"Team 2" === "India") && (!$"Winner".isin("India","tied","no result") )).count.toFloat/(originalDataset_4.filter(($"Team 1" === "India" || $"Team 2" === "India")).count) * 100
    val  df_IND_total_tied_perntge= originalDataset_4.filter(($"Winner" === "tied") && ($"Team 1" === "India" || $"Team 2" === "India")).count.toFloat/(originalDataset_4.filter(($"Team 1" === "India" || $"Team 2" === "India") && ($"Winner" !== "no result" )).count) * 100


    val df_stg=df_ContinousDataset_2.filter(((df_ContinousDataset_2("Team 1") === "India") ) && ( (df_ContinousDataset_2("Host_Country") !== "India") && (df_ContinousDataset_2("Venue_Team1") === "Away")))
    val stg_tie= originalDataset_4.filter(($"Winner" === "tied") && ($"Team 1" === "India" || $"Team 2" === "India")).filter(!originalDataset_4("Ground").isin("Bengaluru","Indore"))
    val total_away_match=df_stg.join((stg_tie,df_stg("Scorecard") === stg_tie("Scorecard"),"outer") && (df_stg("Scorecard") === stg_tie("Scorecard")) ).count
    val df_IND_Away_Win_Pertg=df_ContinousDataset_2.filter(((df_ContinousDataset_2("Team 1") === "India")) && ((df_ContinousDataset_2("Winner") === "India" ) && (df_ContinousDataset_2("Host_Country") !== "India") && (df_ContinousDataset_2("Venue_Team1") === "Away"))).count.toFloat/total_away_match * 100
    val df_IND_Away_loss_Pertg=df_ContinousDataset_2.filter(((df_ContinousDataset_2("Team 1") === "India")) && ((df_ContinousDataset_2("Winner") !== "India" ) && (df_ContinousDataset_2("Host_Country") !== "India") && (df_ContinousDataset_2("Venue_Team1") === "Away"))).count.toFloat/total_away_match * 100


  }

  }
