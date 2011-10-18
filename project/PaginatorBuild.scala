import sbt._
import Keys._

object PaginatorBuild extends Build
{
  lazy val core = Project("core", file("core")) settings(
    organization:= "com.github.ornicar",
    name := "paginator-core",
    version := "1.1",
    scalaVersion := "2.9.1",
    libraryDependencies ++= Seq(
      "org.scala-tools.testing" %% "scalacheck" % "1.9",
      "org.scala-tools.testing" % "test-interface" % "0.5",
      "org.scalatest" % "scalatest_2.9.0" % "1.6.1"
    )
  )

  lazy val salatAdapter = Project("salat-adapter", file("salat-adapter")) dependsOn(core) settings(
    organization:= "com.github.ornicar",
    name := "paginator-salat-adapter",
    version := "1.1",
    scalaVersion := "2.9.1",
    libraryDependencies ++= Seq(
      "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
      "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT"
    ),
    resolvers ++= Seq(
      "repo.novus rels" at "http://repo.novus.com/releases/",
      "repo.novus snaps" at "http://repo.novus.com/snapshots/"
    )
  )

  // append -deprecation to the options passed to the Scala compiler
  scalacOptions += "-deprecation"

  scalacOptions += "-unchecked"
}
