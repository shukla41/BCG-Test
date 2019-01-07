package NewTest.Spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.broadcast


case class Employee(name:String, age:Int, depId: String)
case class Department(id: String, name: String)


object BroadCast {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession =
      SparkSession
        .builder()
        .appName("AppName")
        .config("spark.master", "local")
        .getOrCreate()

    val employeesRDD = spark.sparkContext.parallelize(Seq(
      Employee("Shuva", 33, "IT"),
      Employee("Mathur", 45, "IT"),
      Employee("Pit", 26, "MKT"),
      Employee("Jone", 34, "MKT"),
      Employee("Sarah", 29, "IT"),
      Employee("Shiva", 21, "Intern")
    ))

    val departmentsRDD = spark.sparkContext.parallelize(Seq(
      Department("IT", "IT  Department"),
      Department("MKT", "Marketing Department"),
      Department("FIN", "Finance & Controlling")
    ))

    import spark.implicits._

    val employeesDF = employeesRDD.toDF
    val departmentsDF = departmentsRDD.toDF

    val tmpDepartments = broadcast(departmentsDF.as("departments"))

    employeesDF.join(broadcast(tmpDepartments),
      $"depId" === $"id",  // join by employees.depID == departments.id
      "inner").show()



    /////////////////////////broadcast variable use //////////////////////////

    val rddTmpDepartment = spark.sparkContext.broadcast(
      departmentsRDD.keyBy{ d => (d.id) }  // turn to pair RDD
        .collectAsMap()                  // collect as Map
    )

    employeesRDD.map( emp =>
      if(rddTmpDepartment.value.get(emp.depId) != None){
        (emp.name, emp.age, emp.depId,
          rddTmpDepartment.value.get(emp.depId).get.id,
          rddTmpDepartment.value.get(emp.depId).get.name)
      } else {
        None
      }
    ).collect().foreach(println)


  }

}
