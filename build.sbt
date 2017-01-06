lazy val root = (project in file(".")).
  settings(
    name := "sbt-jflex-scala",
    version := "0.1.0-SNAPSHOT",
    organization := "com.micchon",
    sbtPlugin := true,
    scalacOptions := Seq("-deprecation", "-unchecked"),
    libraryDependencies += "edu.umass.cs.iesl" % "jflex-scala" % "1.6.1",
    publishMavenStyle := true
  ).settings(publishSettings: _*)

lazy val publishSettings = Seq(
  crossPaths := false,
  autoScalaLibrary := false,
  publishTo := Some(Resolver.file("file", file("repo")))
)
