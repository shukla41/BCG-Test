package NewTest

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

import scopt.OptionParser

/*
1,aa,300
2,aa,500
3,aa,400
4,bb,300
5,bb,500
6,bb,200
*/


object test {

  case class Config(input: String = null,
                    output: String = null)

  def main(args: Array[String]): Unit = {


    val spark: SparkSession =
      SparkSession
        .builder()
        .appName("AppName")
        .config("spark.master", "local")
        .getOrCreate()

    println("hello Scopt")
    val parser = new scopt.OptionParser[Config]("scopt") {
      head("scopt", "3.x")
      opt[String]('f', "input") required() action { (x, c) =>
        c.copy(input = x)
      } text ("input is the input path")
      opt[String]('o', "output") required() action { (x, c) =>
        c.copy(output = x)
      } text ("output is the output path")
    }
    // parser.parse returns Option[C]
    parser.parse(args, Config()) map { config =>
      // do stuff
      val input = config.input
      val output = config.output
      println("input=" + input)
      println("output=" + output)
     // val rdd = spark.sparkContext.textFile(input)
      //rdd.saveAsTextFile(output)

    } getOrElse {
      // arguments are bad, usage message will have been displayed
    }

  }
}
