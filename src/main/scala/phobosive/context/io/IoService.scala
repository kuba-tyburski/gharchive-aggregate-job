package phobosive.context.io
import phobosive.context.events.EventPlain
import org.apache.spark.sql.{Dataset, SparkSession}

class IoService(implicit session: SparkSession) {
  def read(inputPath: String): Dataset[EventPlain] = {
    import session.implicits._

    val jsonData = session.sparkContext.textFile(inputPath).toDS()
    session.sqlContext.read.json(jsonData).withColumnRenamed("public", "is_public").withColumnRenamed("type", "event_type").as[EventPlain]
  }

  def dump[T](data: Dataset[T], outputPath: String): Unit =
    data.repartition(1).write.option("compression", "gzip").parquet(outputPath) //repartition(1) - shuffle to one node all results so we have one output file

}
