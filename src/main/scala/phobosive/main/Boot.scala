package phobosive.main

import phobosive.main.config.JobConfig
import phobosive.main.layer.{BaseLayer, ServiceLayer}
import org.apache.spark.sql.SparkSession

trait Setup extends ServiceLayer with BaseLayer {}

object Boot extends Setup {
  implicit val sparkSession: SparkSession = SparkSession.builder.appName("GHArchive-aggregate-job").getOrCreate()

  def main(args: Array[String]) {

    //todo scopt
    val jobConfig = JobConfig(args(0), args(1), args(2))

    val eventData                  = ioService.read(jobConfig.inputPath).cache()
    val (repositoryData, userData) = processingService.aggregateData(eventData)
    ioService.dump(repositoryData, jobConfig.repositoryOutputFilePath)
    ioService.dump(userData, jobConfig.userOutputFilePath)

    sparkSession.stop()
  }
}
