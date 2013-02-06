import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "com.github.ornicar"
  val buildScalaVersion = "2.10.0"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    scalaVersion := buildScalaVersion,
    shellPrompt := ShellPrompt.buildShellPrompt,
    scalacOptions := Seq("-deprecation", "-unchecked"),
    exportJars := true)
}

object Resolvers {
  val novus = "repo.novus" at "http://repo.novus.com/snapshots/"
  val sonatype = "sonatype" at "http://oss.sonatype.org/content/repositories/releases"
  val sonatypeSnapshots = "sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
  val typesafe = "typesafe.com" at "http://repo.typesafe.com/typesafe/releases/"
  val iliaz = "iliaz.com" at "http://scala.iliaz.com/"
  val iliazPublish = Some(Resolver.sftp(
    "iliaz",
    "scala.iliaz.com"
  ) as ("scala_iliaz_com", Path.userHome / ".ssh" / "id_rsa"))
}

object Dependencies {
  val scalacompiler = "org.scala-lang" % "scala-compiler" % "2.10.0" % "optional"
  val scalacheck = "org.scalacheck" %% "scalacheck" % "1.10.0" % "test"
  val test = "org.scala-tools.testing" % "test-interface" % "0.5" % "test"
  val scalatest = "org.scalatest" %% "scalatest" % "1.9" % "test"

  val salat = "com.novus" %% "salat-core" % "1.9.2-SNAPSHOT"

  val jdubext = "com.github.ornicar" %% "jdubext" % "1.1"
}

object PaginatorBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  lazy val core = Project("core", file("core"),
    settings = buildSettings ++ Seq(
      name := "paginator-core",
      version := "1.6",
      publishTo := iliazPublish,
      libraryDependencies := Seq(scalacompiler, scalacheck, test, scalatest)))

  lazy val salatAdapter = Project("salat-adapter", file("salat-adapter"),
    settings = buildSettings ++ Seq(
      name := "paginator-salat-adapter",
      version := "1.5",
      resolvers := Seq(novus, sonatypeSnapshots),
      publishTo := iliazPublish,
      libraryDependencies ++= Seq(salat))) dependsOn core

  lazy val jdubextAdapter = Project("jdubext-adapter", file("jdubext-adapter"),
    settings = buildSettings ++ Seq(
      name := "paginator-jdubext-adapter",
      version := "1.0",
      resolvers := Seq(iliaz),
      publishTo := iliazPublish,
      libraryDependencies ++= Seq(jdubext))) dependsOn core
}

object ShellPrompt {

  val buildShellPrompt = {
    (state: State) ⇒
      {
        val currProject = Project.extract(state).currentProject.id
        "paginator:%s> ".format(currProject)
      }
  }
}
