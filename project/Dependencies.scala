import sbt._

object Versions {
  val configV     = "1.3.3"
  val enumeratumV = "1.5.13"
  val sparkV      = "2.4.0"
  val scalaTestV  = "3.0.5"
}

object Dependencies {
  import Versions._

  lazy val allDeps: Seq[ModuleID] = spark ++ other ++ test

  private lazy val spark = Seq(
    "org.apache.spark" %% "spark-core" % sparkV % Provided,
    "org.apache.spark" %% "spark-sql"  % sparkV % Provided
  )

  private lazy val other = Seq(
    "com.typesafe" % "config"      % configV,
    "com.beachape" %% "enumeratum" % enumeratumV
  )

  private lazy val test = Seq(
    "org.scalatest" %% "scalatest" % scalaTestV % "test"
  )
}
