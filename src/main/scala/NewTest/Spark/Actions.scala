package NewTest.Spark

import org.apache.spark.sql.SparkSession

object Actions {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession =
      SparkSession
        .builder()
        .appName("AppName")
        .config("spark.master", "local")
        .getOrCreate()

       //aggregate key

    val donutBasket1: Set[String] = Set("Plain Donut", "Strawberry Donut")
    val donutLengthAccumulator: (Int, String) => Int = (accumulator, donutName) => accumulator + donutName.length
    val totalLength = donutBasket1.aggregate(0)(donutLengthAccumulator, _ + _)

    val donutBasket2: Set[(String, Double, Int)] = Set(("Plain Donut", 1.50, 10), ("Strawberry Donut", 2.0, 10))
    val totalCostAccumulator: (Double, Double, Int) => Double = (accumulator, price, quantity) => accumulator + (price * quantity)
    val totalCost = donutBasket2.aggregate(0.0)((accumulator: Double, tuple: (String, Double, Int)) => totalCostAccumulator(accumulator, tuple._2, tuple._3), _ + _)


    // top and takeOrdered function
    val myrdd1 = spark.sparkContext.parallelize(List(5,7,9,13,51,89,12))
    myrdd1.top(4)
    myrdd1.takeOrdered(8)

    //collectAsMap and countByKey


    val rdd = spark.sparkContext.parallelize(Seq(
                      ("math",    55),
                      ("math",    56),
                      ("english", 57),
                      ("english", 58),
                      ("science", 59),
                      ("science", 54)))

    rdd.collectAsMap()  //
    rdd.countByKey()
    rdd.countByValue()

    //sample
    val rawdata= spark.read.textFile("/Users/shuvamoymondal/Desktop/Records.csv")
    val header= rawdata.first()
    val data = rawdata.filter(p=> p!=header)
    data.sample(true, 0.5)

    val rawData1= spark.sparkContext.textFile("/Users/shuvamoymondal/Desktop/employee.txt")
    val header1= rawData1.first()
    val data1 = rawData1.filter(p=> p!=header1)

    val rawData2= spark.sparkContext.textFile("/Users/shuvamoymondal/Desktop/depy.txt")
    val header2= rawData2.first()
    val data2 = rawData2.filter(p=> p!=header2)



    //min/max/sum/varianece
    val min= data1.map(p=> p.split(",")).map(p=> p(3).toDouble).min()
    val max= data1.map(p=> p.split(",")).map(p=> p(3).toDouble).max()
    val sum= data1.map(p=> p.split(",")).map(p=> p(3).toDouble).sum()
    val stv= data1.map(p=> p.split(",")).map(p=> p(3).toDouble).stdev()
    val mean= data1.map(p=> p.split(",")).map(p=> p(3).toDouble).mean()
    val variance= data1.map(p=> p.split(",")).map(p=> p(3).toDouble).variance()

   // Join in RDD
    val empdept=data1.map(p=> p.split(",")).map(p=> (p(1),p))
    val deptid=data2.map(p=> p.split(",")).map(p=> (p(0),p))

    val eqjon=empdept.join(deptid).collect()

    // left outer join and extract data from Some and None
    val leftrdd=empdept.leftOuterJoin(deptid).map(p=> (p._1.toInt,p._2._2))
    leftrdd.saveAsTextFile("/test/test.txt")
    leftrdd.map {case (a,c: Option[Array[String]]) => (a,c.getOrElse(0))}.take(100)

  }

}
