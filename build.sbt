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
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (version.value.trim.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  publishArtifact in (Compile, packageDoc) := false,
  publishArtifact in packageDoc := false,
  sources in (Compile, doc) := Seq.empty,
  licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT")),
  pomExtra :=
    <url>https://github.com/3tty0n/sbt-jflex-scala</url>
    <developers>
      <developer>
        <id>3tty0n</id>
        <name>Yusuke Izawa</name>
        <url>https://github.com/3tty0n</url>
      </developer>
    </developers>
    <scm>
      <url>git@github.com:3tty0n/sbt-jflex-scala.git</url>
      <connection>scm:git@github.com:3tty0n/sbt-jflex-scala.git</connection>
    </scm>
)
