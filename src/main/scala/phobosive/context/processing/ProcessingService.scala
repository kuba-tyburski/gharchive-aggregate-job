package phobosive.context.processing

import phobosive.context.events.EventPlain
import org.apache.spark.sql.{DataFrame, Dataset}
import org.apache.spark.sql.functions._

class ProcessingService {
  import ProcessingService._

  def aggregateRepositoryData(eventData: Dataset[EventPlain]) = {
    val groupedReposByDay =
      eventData.groupBy(date_trunc("day", col("created_at")).alias("date"), col("repo.id").alias("repoId"), col("repo.name").alias("repoName"))
    groupedReposByDay
      .agg(
        starred,
        forked,
        issues,
        prs
      )
  }

  def aggregateUserData(eventData: Dataset[EventPlain]) = {
    val groupedUsersByDay = eventData.groupBy(date_trunc("day", col("created_at")).alias("date"), col("actor.id").alias("userId"), col("actor.login"))
    groupedUsersByDay
      .agg(
        starred,
        issues,
        prs
      )
  }

  def aggregateData(eventData: Dataset[EventPlain]) = (aggregateRepositoryData(eventData), aggregateUserData(eventData))

  private val starred = sum(when(col("event_type") === "FollowEvent", 1).otherwise(0)).alias(starredColName) //todo find event telling when user starred repo
  private val forked  = sum(when(col("event_type") === "ForkEvent", 1).otherwise(0)).alias(forkedColName)
  private val issues  = sum(when(col("event_type") === "IssuesEvent" && col("payload.action") === "opened", 1).otherwise(0)).alias(issuesColName)
  private val prs     = sum(when(col("event_type") === "PullRequestEvent" && col("payload.action") === "opened", 1).otherwise(0)).alias(prColName)
}

object ProcessingService {
  val starredColName = "starred"
  val forkedColName  = "forked"
  val issuesColName  = "issues"
  val prColName      = "pr"
}
