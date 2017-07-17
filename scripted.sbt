// https://github.com/sbt/sbt/issues/3325
ScriptedPlugin.scriptedSettings.filterNot(_.key.key == libraryDependencies.key)
libraryDependencies ++= {
  CrossVersion.binarySbtVersion(scriptedSbt.value) match {
    case "0.13" =>
      Seq(
        "org.scala-sbt" % "scripted-sbt" % scriptedSbt.value % scriptedConf.toString,
        "org.scala-sbt" % "sbt-launch" % scriptedSbt.value % scriptedLaunchConf.toString
      )
    case _ =>
      Seq(
        "org.scala-sbt" %% "scripted-sbt" % scriptedSbt.value % scriptedConf.toString,
        "org.scala-sbt" % "sbt-launch" % scriptedSbt.value % scriptedLaunchConf.toString
      )
  }
}
scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
}
scriptedBufferLog := false
