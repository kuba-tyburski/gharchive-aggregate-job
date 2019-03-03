package phobosive.context.io
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.reflect.io.File

class IoServiceSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  import phobosive.fixture.EventFixture._
  import IoServiceSpec._

  implicit val session = SparkSession
    .builder()
    .appName("spark_test")
    .master("local[4]")
    .config("spark.ui.enabled", "false")
    .getOrCreate()

  import session.implicits._

  behavior of "IoService"

  val ioService = new IoService()

  it should "read input file and return dataset" in {
    //given
    val datasetPath = this.getClass.getResource("/2018-01-01-0.json").getPath

    //when
    val dataset = ioService.read(datasetPath)

    //then
    dataset.count() shouldEqual 10
  }

  it should "dump dataset to new parquet file" in {
    //given
    val dataset = Seq(testEventPlain()).toDS()

    //when
    ioService.dump(dataset, expectedPath)

    //then
    val dataFile = File(expectedPath)
    dataFile.exists shouldEqual true
  }

  override protected def afterAll(): Unit = {
    val dataFile = File(expectedPath)

    //clean up
    dataFile.deleteRecursively()
  }
}

object IoServiceSpec {
  val expectedPath: String = this.getClass.getResource("/").getPath + "/datasetOutput"
}
