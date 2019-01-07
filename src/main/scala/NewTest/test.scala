package NewTest

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window



/*
1,aa,300
2,aa,500
3,aa,400
4,bb,300
5,bb,500
6,bb,200
*/


object test {

  case class p (
                     id : String,
                     gr: String,
                     sal: String
               )

  def main(args: Array[String]): Unit = {


    val spark: SparkSession =
      SparkSession
        .builder()
        .appName("AppName")
        .config("spark.master", "local")
        .getOrCreate()


    import spark.sqlContext.implicits._
    val v = spark.sparkContext.textFile("/Users/shuvamoymondal/p1.txt").map(p => p.split(",")).map(x => p(x(0), x(1), x(2))).toDF()

    print(v.rdd.countByValue())

   val b= Window.partitionBy(col("gr")).orderBy(col("id").desc,col("sal").desc)

    val r = row_number().over(b)

    val df= v.select(v("*"), r as "rank")
    df.show()

    v.join(df,v("id")===df("id")).filter(df("rank") ===1).select(v("*")).show()




  }


}
