lazy val root = (project in file(".")).
  settings(
    name := "sbt-jflex-scala",
    organization := "com.github.3tty0n",
    sbtPlugin := true,
    crossSbtVersions := Vector("0.13.16", "1.0.3"),
    scalacOptions := Seq("-deprecation", "-unchecked"),
    libraryDependencies += "edu.umass.cs.iesl" % "jflex-scala" % "1.6.1"
  ).settings(
    publishSettings
  )

lazy val publishSettings = Seq(
  publishMavenStyle := true,
  publishTo in ThisBuild := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishArtifact in Test := false,
  pomIncludeRepository := { _ =>
    false
  },
  sonatypeProfileName := "com.github.3tty0n",
  licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("https://github.com/3tty0n")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/3tty0n/gatling-thrift"),
      "scm:git@github.com:3tty0n/gatling-thrift.git"
    )
  ),
  developers := List(
    Developer(id = "3tty0n", name = "Yusuke Izawa", email = "yuizalp@gmail.com", url = url("https://github.com/3tty0n"))
  )
)
