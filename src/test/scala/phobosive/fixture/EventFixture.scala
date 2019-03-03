package phobosive.fixture
import phobosive.context.events._

object EventFixture {
  def testEvent(
    eventType: EventType = EventType("CreateEvent"),
    isPublic: IsPublic = IsPublic(true),
    payload: Payload = Payload(""),
    repo: Repo = testRepo(),
    actor: Actor = testActor(),
    org: Org = testOrg(),
    createdAt: CreatedAt = CreatedAt.now,
    id: EventId = EventId("0"),
    other: Other = Other("other")
  ) =
    Event(
      eventType,
      isPublic,
      payload,
      repo,
      actor,
      org,
      createdAt,
      id,
      other
    )
  def testEventPlain(
    eventType: String = "CreateEvent",
    isPublic: Boolean = true,
    payload: PayloadPlain = testPayloadPlain(),
    repo: RepoPlain = testRepoPlain(),
    actor: ActorPlain = testActorPlain(),
    org: OrgPlain = testOrgPlain(),
    createdAt: CreatedAt = CreatedAt.now,
    id: String = "0" /*,
    other: Other = Other("other")*/
  ) =
    EventPlain(
      eventType,
      isPublic,
      payload,
      repo,
      actor,
      org,
      createdAt.value,
      id /*,
      other*/
    )

  def testRepo(
    id: RepoId = RepoId(0),
    name: RepoName = RepoName("repoName"),
    url: RepoUrl = RepoUrl("repoUrl")
  ) =
    Repo(id, name, url)

  def testActor(
    id: ActorId = ActorId(0),
    login: ActorLogin = ActorLogin("actorLogin"),
    gravatarId: GravatarId = GravatarId("actorGravatarId"),
    avatarUrl: AvatarUrl = AvatarUrl("actorAvatarUrl"),
    url: ActorUrl = ActorUrl("actorUrl")
  ) =
    Actor(id, login, gravatarId, avatarUrl, url)

  def testOrg(
    id: OrgId = OrgId(0),
    login: OrgLogin = OrgLogin("orgLogin"),
    gravatarId: GravatarId = GravatarId("orgGravatarId"),
    avatarUrl: AvatarUrl = AvatarUrl("orgAvatarUrl"),
    url: OrgUrl = OrgUrl("orgUrl")
  ) =
    Org(id, login, gravatarId, avatarUrl, url)

  def testPayloadPlain(
    action: String = "opened"
  ) =
    PayloadPlain(action)

  def testRepoPlain(
    id: Long = 0L,
    name: String = "repoName",
    url: String = "repoUrl"
  ) =
    RepoPlain(id, name, url)

  def testActorPlain(
    id: Long = 0L,
    login: String = "actorLogin",
    gravatarId: String = "actorGravatarId",
    avatarUrl: String = "actorAvatarUrl",
    url: String = "actorUrl"
  ) =
    ActorPlain(id, login, gravatarId, avatarUrl, url)

  def testOrgPlain(
    id: Long = 0L,
    login: String = "orgLogin",
    gravatarId: String = "orgGravatarId",
    avatarUrl: String = "orgAvatarUrl",
    url: String = "orgUrl"
  ) =
    OrgPlain(id, login, gravatarId, avatarUrl, url)
}
