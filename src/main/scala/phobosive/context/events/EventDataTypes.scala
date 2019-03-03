package phobosive.context.events

import java.time.Instant

case class IsPublic(value: Boolean) extends AnyVal
case class Payload(value: String)   extends AnyVal

case class RepoId(value: Int)      extends AnyVal
case class RepoName(value: String) extends AnyVal
case class RepoUrl(value: String)  extends AnyVal

case class Repo(id: RepoId, name: RepoName, url: RepoUrl)
case class RepoPlain(id: Long, name: String, url: String)

case class ActorId(value: Int)       extends AnyVal
case class ActorLogin(value: String) extends AnyVal
case class GravatarId(value: String) extends AnyVal
case class AvatarUrl(value: String)  extends AnyVal
case class ActorUrl(value: String)   extends AnyVal

case class Actor(id: ActorId, login: ActorLogin, gravatarId: GravatarId, avatarUrl: AvatarUrl, url: ActorUrl)
case class ActorPlain(id: Long, login: String, gravatar_id: String, avatar_url: String, url: String)

case class OrgId(value: Int)       extends AnyVal
case class OrgLogin(value: String) extends AnyVal
case class OrgUrl(value: String)   extends AnyVal

case class Org(id: OrgId, login: OrgLogin, gravatar_id: GravatarId, avatar_url: AvatarUrl, url: OrgUrl)
case class OrgPlain(id: Long, login: String, gravatar_id: String, avatar_url: String, url: String)

case class CreatedAt(value: java.sql.Timestamp) extends AnyVal
object CreatedAt {
  def now = CreatedAt(java.sql.Timestamp.from(Instant.now()))
}

case class EventId(value: String) extends AnyVal
case class Other(value: String)   extends AnyVal

case class EventType(value: String) extends AnyVal

//sealed trait EventType extends EnumEntry
//object EventType extends Enum[EventType] {
//  case object CheckRunEvent                     extends EventType
//  case object CheckSuiteEvent                   extends EventType
//  case object CommitCommentEvent                extends EventType
//  case object ContentReferenceEvent             extends EventType
//  case object CreateEvent                       extends EventType
//  case object DeleteEvent                       extends EventType
//  case object DeploymentEvent                   extends EventType
//  case object DeploymentStatusEvent             extends EventType
//  case object DownloadEvent                     extends EventType
//  case object FollowEvent                       extends EventType
//  case object ForkEvent                         extends EventType
//  case object ForkApplyEvent                    extends EventType
//  case object GitHubAppAuthorizationEvent       extends EventType
//  case object GistEvent                         extends EventType
//  case object GollumEvent                       extends EventType
//  case object InstallationEvent                 extends EventType
//  case object InstallationRepositoriesEvent     extends EventType
//  case object IssueCommentEvent                 extends EventType
//  case object IssuesEvent                       extends EventType
//  case object LabelEvent                        extends EventType
//  case object MarketplacePurchaseEvent          extends EventType
//  case object MemberEvent                       extends EventType
//  case object MembershipEvent                   extends EventType
//  case object MilestoneEvent                    extends EventType
//  case object OrganizationEvent                 extends EventType
//  case object OrgBlockEvent                     extends EventType
//  case object PageBuildEvent                    extends EventType
//  case object ProjectCardEvent                  extends EventType
//  case object ProjectColumnEvent                extends EventType
//  case object ProjectEvent                      extends EventType
//  case object PublicEvent                       extends EventType
//  case object PullRequestEvent                  extends EventType
//  case object PullRequestReviewEvent            extends EventType
//  case object PullRequestReviewCommentEvent     extends EventType
//  case object PushEvent                         extends EventType
//  case object ReleaseEvent                      extends EventType
//  case object RepositoryEvent                   extends EventType
//  case object RepositoryImportEvent             extends EventType
//  case object RepositoryVulnerabilityAlertEvent extends EventType
//  case object SecurityAdvisoryEvent             extends EventType
//  case object StatusEvent                       extends EventType
//  case object TeamEvent                         extends EventType
//  case object TeamAddEvent                      extends EventType
//  case object WatchEvent                        extends EventType
//
//  val values = findValues
//}

case class Event(
  eventType: EventType,
  isPublic: IsPublic,
  payload: Payload,
  repo: Repo,
  actor: Actor,
  org: Org,
  createdAt: CreatedAt,
  id: EventId,
  other: Other
)

case class PayloadPlain(action: String)

case class EventPlain(
  event_type: String,
  is_public: Boolean,
  payload: PayloadPlain,
  repo: RepoPlain,
  actor: ActorPlain,
  org: OrgPlain,
  created_at: java.sql.Timestamp,
  id: String /*,
  other: String*/
)
