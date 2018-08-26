
package com.streamsets.spark

import com.streamsets.pipeline.api.Record
import com.streamsets.pipeline.spark.api.SparkTransformer
import com.streamsets.pipeline.spark.api.TransformResult
import org.apache.spark.api.java.JavaPairRDD
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import java.io.Serializable
import java.util

class App extends SparkTransformer with Serializable {
  var emptyRDD: JavaRDD[(Record, String)] = _

  override def init(javaSparkContextInstance: JavaSparkContext, params: util.List[String]): Unit = {
    // Create an empty JavaPairRDD to return as 'errors'
    emptyRDD = javaSparkContextInstance.emptyRDD
  }

  override def transform(recordRDD: JavaRDD[Record]): TransformResult = {
    val rdd = recordRDD.rdd

    val errors = emptyRDD

    // Apply a function to the incoming records
    val result = rdd.map((record)=> record)
    //result.saveAsTextFile("hdfs://localhost:9000/output/oo")
    // return result
    new TransformResult(result.toJavaRDD(), new JavaPairRDD[Record, String](errors))
  }
}
