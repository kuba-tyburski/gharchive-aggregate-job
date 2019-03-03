package phobosive.main.layer

import org.apache.spark.sql.SparkSession

trait BaseLayer {
  implicit val sparkSession: SparkSession
}
