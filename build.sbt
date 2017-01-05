lazy val root = (project in file(".")).
  settings(
    name := "sbt-jflex-plugin",
    version := "0.1.0-SNAPSHOT",
    organization := "com.micchon",
    sbtPlugin := true,
    scalacOptions := Seq("-deprecation", "-unchecked"),
    libraryDependencies += "edu.umass.cs.iesl" % "jflex-scala" % "1.6.1"
  )
