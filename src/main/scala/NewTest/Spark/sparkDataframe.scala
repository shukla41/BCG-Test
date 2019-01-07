package NewTest.Spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.expressions.Window

object sparkDataframe {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession =
      SparkSession
        .builder()
        .appName("AppName")
        .config("spark.master", "local")
        .getOrCreate()



    val data2=spark.read.option("header", "true")
      .option("inferSchema", "true")
      .csv("/Users/shuvamoymondal/Desktop/sample1.csv")


    // casting

    val data=spark.read.option("header", "true")
      .option("inferSchema", "true")
      .option("dateFormat","yyyy-MM-dd HH:mm:ss")
      .csv("/Users/shuvamoymondal/Desktop/sample.csv")
      .toDF("id", "creation_date", "closed_date", "deletion_date", "score", "owner_userid", "answer_count")

    val data1 = data.select(
      data.col("id").cast("integer"),
      data.col("creation_date").cast("timestamp"),
      data.col("closed_date").cast("timestamp"),
      data.col("deletion_date").cast("date"),
      data.col("score").cast("integer"),
      data.col("owner_userid").cast("integer"),
      data.col("answer_count").cast("integer")
    )

    data1.printSchema()
    //data1.show()

    //Join in dataframe
    //data1.join(data2,"id").show()
    //data1.join(data2,data1("id")=== data2("id"),"inner").show()
    // data1.join(data2,data1("id")=== data2("id"),"fullouter").show()

    // Function example
    // avg function

    val avg_data=data1.select(avg(data1("score")))
    //avg_data.show

    val sum_data=data1.select(sum(data1("score")))
    //sum_data.show()

    //union
    //data.union(data2).show(2)
    //data.intersect(data2).show()

    //with columns
    val newDf = data1.withColumn("new_col", when(data1("owner_userid").isNull, 0).otherwise(1))
    //newDf.show()

    // Window functions

    val empDF = spark.createDataFrame(Seq(
      (7369, "SMITH", "CLERK", 7902, "17-Dec-80", 800, 20, 10),
      (7499, "ALLEN", "SALESMAN", 7698, "20-Feb-81", 1600, 300, 30),
      (7521, "WARD", "SALESMAN", 7698, "22-Feb-81", 1250, 500, 30),
      (7566, "JONES", "MANAGER", 7839, "2-Apr-81", 2975, 0, 20),
      (7654, "MARTIN", "SALESMAN", 7698, "28-Sep-81", 1250, 1400, 30),
      (7698, "BLAKE", "MANAGER", 7839, "1-May-81", 2850, 0, 30),
      (7782, "CLARK", "MANAGER", 7839, "9-Jun-81", 2450, 0, 10),
      (7788, "SCOTT", "ANALYST", 7566, "19-Apr-87", 3000, 0, 20),
      (7839, "KING", "PRESIDENT", 0, "17-Nov-81", 5000, 0, 10),
      (7844, "TURNER", "SALESMAN", 7698, "8-Sep-81", 1500, 0, 30),
      (7876, "ADAMS", "CLERK", 7788, "23-May-87", 1100, 0, 20)
    )).toDF("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno")



    val partitionWindow = Window.partitionBy(col("deptno")).orderBy(col("sal").desc)
    val rankTest = rank().over(partitionWindow)
    val rankTest1 = dense_rank().over(partitionWindow)

    //running total
    val sumTest = sum(col("sal")).over(partitionWindow)
    //lead function
    val leadTest = lead(col("sal"), 1, 1).over(partitionWindow)
    val firstValTest = first(col("sal")).over(partitionWindow)

    //empDF.select(col("*"), firstValTest as "firstValTest").show

    //string function
    val student = Seq((" warren buffet", 1.50, "2018-04-17"), ("tim cook ", 2.0, "2018-04-01"), ("sundar pichai", 2.50, "2018-04-02"))

    val df = spark
      .createDataFrame(student)
      .toDF("Name", "Price", "Purchase Date")

    import spark.sqlContext.implicits._

    df
      .withColumn("Length", length(col("Name")))
      .withColumn("Trim", trim(col("Name")))
      .withColumn("LTrim", ltrim(col("Name")))
      .withColumn("RTrim", rtrim(col("Name")))
      .withColumn("Reverse", reverse(col("Name")))
      .withColumn("Substring", substring(col("Name"), 0, 5))
      .withColumn("IsNull", isnull(col("Name")))
      .withColumn("Concat", concat_ws(" - ", col("Name"), $"Price"))
      .withColumn("InitCap", initcap(col("Name")))
      .show()


    // drop null
    val student1 = Seq(("tim cook", 1.50), (null.asInstanceOf[String], 2.0), ("thomas cook", 2.50))
    val dfWithNull = spark
      .createDataFrame(student1)
      .toDF("Name", "Price")

    dfWithNull.show()

    // UDF function example

    def prefixUdf(s: String): String = s"udf_$s"
    val appendtags = udf[String, String](prefixUdf)
   // empDF.select(col("ename"), appendtags(col("ename"))).show()

    val lower: String => String = _.toLowerCase()
    val lowerUDF = udf(lower)
    //empDF.select(col("ename"), lowerUDF(col("ename"))).show()

    val donuts = Seq(("plain donut", 1.50), ("vanilla donut", 2.0), ("glazed donut", 2.50))
    val df_2 = spark.createDataFrame(donuts).toDF("Donut Name", "Price")


    val stockMinMax: String => Seq[Int] = (donutName: String) => donutName match {
      case "plain donut"    => Seq(100, 500)
      case "vanilla donut"  => Seq(200, 400)
      case "glazed donut"   => Seq(300, 600)
      case _                => Seq(150, 150)
    }


    val udfStockMinMax = udf(stockMinMax)
    val df10 = df_2.withColumn("Stock Min Max", udfStockMinMax($"Donut Name"))
    df10.show()


    /// How to read nested json

    val json=spark.read.option("multiLine", true).option("inferSchema", true).json("/Users/shuvamoymondal/Desktop/nested.json")
    //json.show(false)
    val df3 = json.select(explode($"stackoverflow") as "stackoverflow_tags")

    df3.printSchema()
    //df3.show(false)

    df3.select(
      $"stackoverflow_tags.tag.id" as "id",
      $"stackoverflow_tags.tag.author" as "author",
      $"stackoverflow_tags.tag.name" as "tag_name",
      $"stackoverflow_tags.tag.frameworks.id" as "frameworks_id",
      $"stackoverflow_tags.tag.frameworks.name" as "frameworks_name"
    ).show()


   // rename column
    val df_rname = df10.withColumnRenamed("Stock Min Max", "Stock")
    df_rname.show(false)

    //how to save a dataframe as parquet format




  }

}
