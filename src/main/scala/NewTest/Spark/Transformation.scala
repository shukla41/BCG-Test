package NewTest.Spark

import org.apache.spark.sql.SparkSession

object Transformation {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession =
      SparkSession
        .builder()
        .appName("AppName")
        .config("spark.master", "local")
        .getOrCreate()


    val rawdata= spark.read.textFile("/Users/shuvamoymondal/Desktop/Records.csv")
    val header= rawdata.first()
    val data = rawdata.filter(p=> p!=header)

    val rawData= spark.sparkContext.textFile("/Users/shuvamoymondal/Desktop/employee.txt")
    val header1= rawData.first()
    val data1 = rawData.filter(p=> p!=header)

    //map function
    data1.map(_.split(",")).map(v=> (v(0),v(1))).take(5)



    import spark.implicits._
    val m_df=data.map(_.split(",")).map(p=> (p(0),p(1),p(3),p(4),p(5),p(6))).toDF("col1","col2","col3","col4","col5","col6")

    m_df.show(2)

    //flatmap example
    val rdd = spark.sparkContext.parallelize(Seq("Roses are red", "Violets are blue"))
    rdd.flatMap(_.split(" ")).collect()

    val rdd1 = spark.sparkContext.parallelize(Seq(1,2,3,4))

    def myfn(x: Int): Option[Int] = if (x <= 2) Some(x * 10) else None

    rdd1.flatMap(myfn).collect

    //mappartition example:

    val rdd2 =  spark.sparkContext.parallelize(
                   List("yellow",   "red",
                       "blue",     "cyan",
                      "black"
                    ),
                    3
                 )


    val parallel = spark.sparkContext.parallelize(1 to 9, 3)
    parallel.mapPartitions( x => List(x.next).iterator).collect

   val parallel1 = spark.sparkContext.parallelize(1 to 9)

    parallel1.mapPartitions( x => List(x.next).iterator).collect


    def myfunc(inputs: Iterator[String]) : Iterator[Int] = {
      var results = List[Int]()
      results = List(inputs.size) // results :: =(inputs.size)
      results.iterator
    }

    parallel.mapPartitionsWithIndex( (index: Int, it: Iterator[Int]) =>
      it.toList.map(x => index + ", "+x).iterator).collect

    //pair RDD
    data1.map(x=> (x.split(",")(1),x))

    // ReduceBykey
    data1.map(s => (s, 1)).reduceByKey((a,b)=> a+b).take(10)
    data1.map(_.split(",")).map(v=> (v(0),v(1).toInt)).reduceByKey(_ + _).take(10)
    data1.map(_.split(",")).map(v=> ((v(0),v(1)),v(1).toInt)).reduceByKey((a,b)=> a+b).take(10)



    // groupbyKey
    val inputrdd = spark.sparkContext.parallelize(Seq(
                    ("key1", 1),
                      ("key2", 2),
                      ("key1", 3)))


    val grouped2 = inputrdd.groupBy{ x =>
                            if((x._2 % 2) == 0) {
                              "evennumbers"
                              } else {
                               "oddnumbers"
                            }
                      }


    //sortByKey
    val rdd4 = spark.sparkContext.parallelize(Seq(
                      ("math",    55),
                      ("math",    56),
                      ("english", 57),
                      ("english", 58),
                      ("science", 59),
                      ("science", 54)))

     rdd4.sortByKey().collect


    //cogroup
    val rdd5 = spark.sparkContext.parallelize(Seq(
      ("key1", 1),
      ("key2", 2),
      ("key1", 3)))

    val rdd6 = spark.sparkContext.parallelize(Seq(
      ("key1", 5),
      ("key2", 4)))

    val grouped = rdd5.cogroup(rdd6)
    // Iterate through each value in key
    // and increment the value by '1'

    grouped.map(x=> x._2).map(p=> p._2).map(p=> p.toList).map(p=> p(0)).take(10)


    val updated = grouped.map{x =>
          {
               val key = x._1
               //println("Key -> " + key)
               val value = x._2
               val itl1 = value._1
               val itl2 = value._2
               val res1 = itl1.map{ x =>
                    {
                         //println("It1 : Key -> " + key + ", Val -> " + (x + 1))
                         x + 1
                      }
                 }
               val res2 = itl2.map{ x =>
                    {
                         //println("It2 : Key -> " + key + ", Val -> " + (x + 1))
                         x + 1
                      }
                 }
               //println("End")
               (key,  (res1, res2))
            }
       }

    //combineBykey

    val rdd8 = spark.sparkContext.parallelize(Seq(
                          ("maths", 50), ("english", 60),
                          ("english", 65),
                          ("physics", 66), ("physics", 61), ("physics", 87)),
                          1)

    val reduced = rdd8.combineByKey(
           (mark) => {
             println(s"Create combiner -> ${mark}")
             (mark, 1)
           },
         (acc: (Int, Int), v) => {
             println(s"""Merge value : (${acc._1} + ${v}, ${acc._2} + 1)""")
             (acc._1 + v, acc._2 + 1)
           },
         (acc1: (Int, Int), acc2: (Int, Int)) => {
             println(s"""Merge Combiner : (${acc1._1} + ${acc2._1}, ${acc1._2} + ${acc2._2})""")
             (acc1._1 + acc2._1, acc1._2 + acc2._2)
           }
     )

    //substractByKey

    val rdd9 = spark.sparkContext.parallelize(Seq(
      ("key1", 1),
      ("key2", 2),
      ("key1", 3)))

    val rdd10 = spark.sparkContext.parallelize(Seq(
      ("key3", 5),
      ("key2", 4)))

    rdd9.subtractByKey(rdd10).take(10)





  }
}
