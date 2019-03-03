name := "project"
version := "0.1"
scalaVersion := "2.11.12"

scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-unchecked",
  "-feature",
  "-encoding",
  "utf8",
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
)

libraryDependencies ++= Dependencies.allDeps