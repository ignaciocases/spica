import sbt._
import sbt.Keys._

import org.scalastyle.sbt.ScalastylePlugin


object SpicaBuild extends Build {

  lazy val spica = Project(
    id = "Spica",
    base = file("."),
    settings = Project.defaultSettings ++ ScalastylePlugin.Settings ++ Seq(
      name := "Spica",
      organization := "spica",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.3",
      // add other settings here
      libraryDependencies ++= Seq(
        "org.spire-math" %% "spire" % "0.7.1",
        "org.scalatest" % "scalatest_2.10" % "2.0" % "test" withSources() withJavadoc(),
        "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" withSources() withJavadoc()
      )
    )
  )
}
