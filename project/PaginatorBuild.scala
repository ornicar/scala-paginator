import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "com.github.ornicar"
  val buildScalaVersion = "2.9.1"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    scalaVersion := buildScalaVersion,
    shellPrompt := ShellPrompt.buildShellPrompt,
    scalacOptions := Seq("-deprecation", "-unchecked"),
    exportJars := true)
}

object Resolvers {
  val novus = "repo.novus snaps" at "http://repo.novus.com/snapshots/"
  val typesafe = "typesafe.com" at "http://repo.typesafe.com/typesafe/releases/"
  val iliaz = "iliaz.com" at "http://scala.iliaz.com/"
  val iliazPublish = Some(Resolver.sftp(
    "iliaz",
    "scala.iliaz.com"
  ) as ("scala_iliaz_com", Path.userHome / ".ssh" / "id_rsa"))
}

object Dependencies {
  val scalacheck = "org.scala-tools.testing" %% "scalacheck" % "1.9"
  val test = "org.scala-tools.testing" % "test-interface" % "0.5"
  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1"

  val casbah = "com.mongodb.casbah" %% "casbah" % "2.1.5-1"
  val salat = "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT"
}

object PaginatorBuild extends Build
{
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  lazy val core = Project("core", file("core"),
    settings = buildSettings ++ Seq(
      name := "paginator-core",
      version := "1.3",
      resolvers := Seq(),
      publishTo := iliazPublish,
      libraryDependencies ++= Seq(scalacheck, test, scalatest)))

  lazy val salatAdapter = Project("salat-adapter", file("salat-adapter"),
    settings = buildSettings ++ Seq(
      name := "paginator-salat-adapter",
      version := "1.2",
      resolvers := Seq(novus),
      publishTo := iliazPublish,
      libraryDependencies ++= Seq(casbah, salat))) dependsOn core
}

object ShellPrompt {

  val buildShellPrompt = {
    (state: State) =>
      {
        val currProject = Project.extract(state).currentProject.id
        "paginator:%s> ".format(currProject)
      }
  }
}
