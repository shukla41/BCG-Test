package NewTest

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.broadcast

object App1 {

  def main(args: Array[String]): Unit = {

      val spark: SparkSession =
      SparkSession
        .builder()
        .appName("AppName")
        .config("spark.master", "local")
        .getOrCreate()


    case class sales (
                       Region : String,
                      Country: String,
                      Item_Type: String,
                      Sales_Channel: String,
                       Order_Priority: String,
                      Order_Date: String,
                      Order_ID: String,
                      Ship_Date: String,
                      Units_Sold: String,
                      Unit_Price: String,
                       Unit_Cost: String,
                       Total_Revenue: String,
                       Total_Cost: String,
                       Total_Profit: String
                     )


    case class sales_dt (
                       Region1 : String,
                       Country1: String,
                       Item_Type1: String,
                       Sales_Channel1: String,
                       Order_Priority1: String,
                       Order_Date1: String,
                       Order_ID1: String,
                       Ship_Date1: String,
                       Units_Sold1: String,
                       Unit_Price1: String,
                       Unit_Cost1: String,
                       Total_Revenue1: String,
                       Total_Cost1: String,
                       Total_Profit1: String
                     )


    case class sales_db (
                          Region2 : String,
                          Country2: String,
                          Item_Type2: String,
                          Sales_Channel2: String,
                          Order_Priority2: String,
                          Order_Date2: String,
                          Order_ID2: String,
                          Ship_Date2: String,
                          Units_Sold2: String,
                          Unit_Price2: String,
                          Unit_Cost2: String,
                          Total_Revenue2: String,
                          Total_Cost2: String,
                          Total_Profit2: String
                        )



    case class sales_db1 (
                          Region3 : String,
                          Country3: String,
                          Item_Type3: String,
                          Sales_Channel3: String,
                          Order_Priority3: String,
                          Order_Date3: String,
                          Order_ID3: String,
                          Ship_Date3: String,
                          Units_Sold3: String,
                          Unit_Price3: String,
                          Unit_Cost3: String,
                          Total_Revenue3: String,
                          Total_Cost3: String,
                          Total_Profit3: String
                        )


    case class sales_db4 (
                           Region4 : String,
                           Country4: String,
                           Item_Type4: String,
                           Sales_Channel4: String,
                           Order_Priority4: String,
                           Order_Date4: String,
                           Order_ID4: String,
                           Ship_Date4: String,
                           Units_Sold4: String,
                           Unit_Price4: String,
                           Unit_Cost4: String,
                           Total_Revenue4: String,
                           Total_Cost4: String,
                           Total_Profit4: String
                         )


    case class sales_db5 (
                           Region5 : String,
                           Country5: String,
                           Item_Type5: String,
                           Sales_Channel5: String,
                           Order_Priority5: String,
                           Order_Date5: String,
                           Order_ID5: String,
                           Ship_Date5: String,
                           Units_Sold5: String,
                           Unit_Price5: String,
                           Unit_Cost5: String,
                           Total_Revenue5: String,
                           Total_Cost5: String,
                           Total_Profit5: String
                         )

    case class sales_db6 (
                           Region6 : String,
                           Country6: String,
                           Item_Type6: String,
                           Sales_Channel6: String,
                           Order_Priority6: String,
                           Order_Date6: String,
                           Order_ID6: String,
                           Ship_Date6: String,
                           Units_Sold6: String,
                           Unit_Price6: String,
                           Unit_Cost6: String,
                           Total_Revenue6: String,
                           Total_Cost6: String,
                           Total_Profit6: String
                         )


    import spark.sqlContext.implicits._

    val p=spark.sparkContext.textFile("/Users/shuvamoymondal/Desktop/Records.csv")
    val header=p.first()
    val data=p.filter(o=> o !=header)
  //  println(data.header)
    println(header)

    val df = data.map(o=> o.split(",")).map(p=> sales(p(0),p(1),p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13))).toDF()
    val df1 = data.map(o=> o.split(",")).map(p=> sales_dt(p(0),p(1),p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13))).toDF()
    val df2 = data.map(o=> o.split(",")).map(p=> sales_db(p(0),p(1),p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13))).toDF()
    val df3 = data.map(o=> o.split(",")).map(p=> sales_db1(p(0),p(1),p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13))).toDF()
    val df7 = data.map(o=> o.split(",")).map(p=> sales_db4(p(0),p(1),p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13))).toDF()
    val df8 = data.map(o=> o.split(",")).map(p=> sales_db5(p(0),p(1),p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13))).toDF()
    val df11 = data.map(o=> o.split(",")).map(p=> sales_db6 (p(0),p(1),p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9),p(10),p(11),p(12),p(13))).toDF()


    val df4=df.join(df1,df("Order_ID")===df1("Order_ID1"))
    val df5=df4.join(df2,df4("Order_ID")===df2("Order_ID2"))
    val df6=df5.join(df3,df5("Order_ID")===df3("Order_ID3"))
    val df9=df6.join(df7,df6("Order_ID")===df7("Order_ID4"))
    val df10=df9.join(df8,df9("Order_ID")===df8("Order_ID5"))
    val df12=df10.join(df11,df10("Order_ID")===df11("Order_ID6"))

    df12.createOrReplaceTempView("test")
    val v=spark.sql("select Order_ID,Order_Date, row_number() over(partition by Order_ID order by Order_Date desc) r from test ")
    v.cache
    val b=df12.join(broadcast(v) ,v("Order_ID") === df12("Order_ID") && v("Order_Date") === df12("Order_Date")).filter(v("r") ===1)


    def benchmark(name: String)(f: => Unit) {
      val startTime = System.nanoTime
      f
      val endTime = System.nanoTime
      println(s"Time taken in $name: " + (endTime - startTime).toDouble / 1000000000 + " seconds")
    }


    benchmark("Spark 2.0 (sum of a billion)") {
      spark.range(1000L * 1000 * 1000).selectExpr("sum(id)")
    }

  }

}
