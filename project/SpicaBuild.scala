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
      scalaVersion := "2.11.1",
      resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      // add other settings here
      libraryDependencies ++= Seq(
        "org.spire-math" %% "spire" % "0.7.5",
        "org.scalanlp" % "breeze_2.11" % "0.8.1",
        "org.scalanlp" %% "breeze-natives" % "0.8.1",
        // "com.squants"  %% "squants"  % "0.2.1-SNAPSHOT",
        "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test" withSources() withJavadoc(),
        "org.scalacheck" %% "scalacheck" % "1.11.4" % "test" withSources() withJavadoc()
      )
    )
  )
}