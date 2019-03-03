package phobosive.context.processing
import org.apache.spark.sql.SparkSession
import org.scalatest.{FlatSpec, Matchers}

class ProcessingServiceSpec extends FlatSpec with Matchers {

  import ProcessingService._
  import phobosive.fixture.EventFixture._

  implicit val session = SparkSession
    .builder()
    .appName("spark_test")
    .master("local[4]")
    .config("spark.ui.enabled", "false")
    .getOrCreate()

  import org.apache.spark.sql.functions._
  import session.implicits._

  behavior of "ProcessingService"

  val processingService = new ProcessingService()

  it should "return empty result for repository aggregation when empty event dataset" in {
    //given
    val emptyDataset = Seq(testEventPlain()).toDS().filter(_ => false)

    //when
    val aggregatedRepositories = processingService.aggregateUserData(emptyDataset)

    //then
    aggregatedRepositories.count() shouldEqual 0
  }

  it should "count expected amount of starred events for repo" in {
    //given
    //todo.. find way for determining the star event...
    val expectedStarredEvents = Seq(
      testEventPlain()
    ).toDS()

    //when
    val aggregatedRepositories = processingService.aggregateRepositoryData(expectedStarredEvents)

    //then
    val row  = aggregatedRepositories.filter(col("repoId") === 1L)
    val row2 = aggregatedRepositories.filter(col("repoId") === 2L)

    //todo create custom matcher and just pass row with expected count for each column and row count
    row.count() shouldEqual 1
    row.first().getAs[Int](starredColName) shouldEqual 1
    row.first().getAs[Int](prColName) shouldEqual 0
    row.first().getAs[Int](forkedColName) shouldEqual 0
    row.first().getAs[Int](issuesColName) shouldEqual 0
    row2.count() shouldEqual 1
    row2.first().getAs[Int](starredColName) shouldEqual 1
    row2.first().getAs[Int](prColName) shouldEqual 0
    row2.first().getAs[Int](forkedColName) shouldEqual 0
    row2.first().getAs[Int](issuesColName) shouldEqual 0
  }

  it should "count expected amount of forked events for repo" in {
    //given
    val expectedForkEvents = Seq(
      testEventPlain(eventType = "ForkEvent", repo = testRepoPlain(id = 1L)),
      testEventPlain(eventType = "ForkEvent", repo = testRepoPlain(id = 2L)),
      testEventPlain(eventType = "ForkEvent", repo = testRepoPlain(id = 1L))
    ).toDS()

    //when
    val aggregatedRepositories = processingService.aggregateRepositoryData(expectedForkEvents)

    //then
    val row  = aggregatedRepositories.filter(col("repoId") === 1L)
    val row2 = aggregatedRepositories.filter(col("repoId") === 2L)

    row.count() shouldEqual 1
    row.first().getAs[Int](forkedColName) shouldEqual 2
    row.first().getAs[Int](prColName) shouldEqual 0
    row.first().getAs[Int](starredColName) shouldEqual 0
    row.first().getAs[Int](issuesColName) shouldEqual 0
    row2.count() shouldEqual 1
    row2.first().getAs[Int](forkedColName) shouldEqual 1
    row2.first().getAs[Int](prColName) shouldEqual 0
    row2.first().getAs[Int](starredColName) shouldEqual 0
    row2.first().getAs[Int](issuesColName) shouldEqual 0
  }

  it should "count expected amount of created issues for repo" in {
    //given
    val expectedIssuesEvents = Seq(
      testEventPlain(eventType = "IssuesEvent", repo = testRepoPlain(id = 1L), payload = testPayloadPlain()),
      testEventPlain(eventType = "IssuesEvent", repo = testRepoPlain(id = 2L), payload = testPayloadPlain(action = "edited")),
      testEventPlain(eventType = "IssuesEvent", repo = testRepoPlain(id = 1L), payload = testPayloadPlain())
    ).toDS()

    //when
    val aggregatedRepositories = processingService.aggregateRepositoryData(expectedIssuesEvents)

    //then
    val row  = aggregatedRepositories.filter(col("repoId") === 1L)
    val row2 = aggregatedRepositories.filter(col("repoId") === 2L)

    row.count() shouldEqual 1
    row.first().getAs[Int](issuesColName) shouldEqual 2
    row.first().getAs[Int](prColName) shouldEqual 0
    row.first().getAs[Int](starredColName) shouldEqual 0
    row.first().getAs[Int](forkedColName) shouldEqual 0
    row2.count() shouldEqual 1
    row2.first().getAs[Int](issuesColName) shouldEqual 0
    row2.first().getAs[Int](prColName) shouldEqual 0
    row2.first().getAs[Int](starredColName) shouldEqual 0
    row2.first().getAs[Int](forkedColName) shouldEqual 0
  }

  it should "count expected amount of created PRs for repo" in {
    //given
    val expectedPREvents = Seq(
      testEventPlain(eventType = "PullRequestEvent", repo = testRepoPlain(id = 1L), payload = testPayloadPlain()),
      testEventPlain(eventType = "PullRequestEvent", repo = testRepoPlain(id = 2L), payload = testPayloadPlain(action = "edited")),
      testEventPlain(eventType = "PullRequestEvent", repo = testRepoPlain(id = 1L), payload = testPayloadPlain())
    ).toDS()

    //when
    val aggregatedRepositories = processingService.aggregateRepositoryData(expectedPREvents)

    //then
    val row  = aggregatedRepositories.filter(col("repoId") === 1L)
    val row2 = aggregatedRepositories.filter(col("repoId") === 2L)

    row.count() shouldEqual 1
    row.first().getAs[Int](prColName) shouldEqual 2
    row.first().getAs[Int](issuesColName) shouldEqual 0
    row.first().getAs[Int](starredColName) shouldEqual 0
    row.first().getAs[Int](forkedColName) shouldEqual 0
    row2.count() shouldEqual 1
    row2.first().getAs[Int](prColName) shouldEqual 0
    row2.first().getAs[Int](issuesColName) shouldEqual 0
    row2.first().getAs[Int](starredColName) shouldEqual 0
    row2.first().getAs[Int](forkedColName) shouldEqual 0
  }

  it should "return empty result for user aggregation when empty event dataset" in {
    //given
    val emptyDataset = Seq(testEventPlain()).toDS().filter(_ => false)

    //when
    val aggregatedUsers = processingService.aggregateUserData(emptyDataset)

    //then
    aggregatedUsers.count() shouldEqual 0
  }

  it should "count expected amount of starred project by user" in {
    //given
    //todo.. find way for determining the star event...
    val expectedStarredEvents = Seq(
      testEventPlain(actor = testActorPlain(id = 1L)),
      testEventPlain(actor = testActorPlain(id = 2L))
    ).toDS()

    //when
    val aggregatedUsers = processingService.aggregateUserData(expectedStarredEvents)

    //then
    val row  = aggregatedUsers.filter(col("userId") === 1L)
    val row2 = aggregatedUsers.filter(col("userId") === 2L)

    row.count() shouldEqual 1
    row.first().getAs[Int](starredColName) shouldEqual 1
    row.first().getAs[Int](prColName) shouldEqual 0
    row.first().getAs[Int](issuesColName) shouldEqual 0
    row2.count() shouldEqual 1
    row2.first().getAs[Int](starredColName) shouldEqual 1
    row2.first().getAs[Int](prColName) shouldEqual 0
    row2.first().getAs[Int](issuesColName) shouldEqual 0
  }

  it should "count expected amount of created issues by user" in {
    //given
    val expectedIssuesEvents = Seq(
      testEventPlain(actor = testActorPlain(id = 1L), eventType = "IssuesEvent", repo = testRepoPlain(id = 1L), payload = testPayloadPlain()),
      testEventPlain(actor = testActorPlain(id = 2L), eventType = "IssuesEvent", repo = testRepoPlain(id = 2L), payload = testPayloadPlain(action = "edited")),
      testEventPlain(actor = testActorPlain(id = 1L), eventType = "IssuesEvent", repo = testRepoPlain(id = 1L), payload = testPayloadPlain())
    ).toDS()

    //when
    val aggregatedUsers = processingService.aggregateUserData(expectedIssuesEvents)

    //then
    val row  = aggregatedUsers.filter(col("userId") === 1L)
    val row2 = aggregatedUsers.filter(col("userId") === 2L)

    row.count() shouldEqual 1
    row.first().getAs[Int](issuesColName) shouldEqual 2
    row.first().getAs[Int](prColName) shouldEqual 0
    row.first().getAs[Int](starredColName) shouldEqual 0
    row2.count() shouldEqual 1
    row2.first().getAs[Int](issuesColName) shouldEqual 0
    row2.first().getAs[Int](prColName) shouldEqual 0
    row2.first().getAs[Int](starredColName) shouldEqual 0
  }

  it should "count expected amount of created PRs by user" in {
    //given
    val expectedPREvents = Seq(
      testEventPlain(eventType = "PullRequestEvent", repo = testRepoPlain(id = 1L), payload = testPayloadPlain(), actor = testActorPlain(id = 1L)),
      testEventPlain(
        eventType = "PullRequestEvent",
        repo = testRepoPlain(id = 2L),
        payload = testPayloadPlain(action = "edited"),
        actor = testActorPlain(id = 2L)),
      testEventPlain(eventType = "PullRequestEvent", repo = testRepoPlain(id = 1L), payload = testPayloadPlain(), actor = testActorPlain(id = 1L))
    ).toDS()

    //when
    val aggregatedUsers = processingService.aggregateUserData(expectedPREvents)

    //then
    val row  = aggregatedUsers.filter(col("userId") === 1L)
    val row2 = aggregatedUsers.filter(col("userId") === 2L)

    row.count() shouldEqual 1
    row.first().getAs[Int](prColName) shouldEqual 2
    row.first().getAs[Int](issuesColName) shouldEqual 0
    row.first().getAs[Int](starredColName) shouldEqual 0
    row2.count() shouldEqual 1
    row2.first().getAs[Int](prColName) shouldEqual 0
    row2.first().getAs[Int](issuesColName) shouldEqual 0
    row2.first().getAs[Int](starredColName) shouldEqual 0
  }
}
