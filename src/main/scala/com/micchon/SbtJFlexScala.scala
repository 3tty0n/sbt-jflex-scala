package com.micchon

import sbt._
import Keys._

import jflex.{ Options, Main }

object SbtJFlexScala extends Plugin {

  lazy val jflexSourceDirectory = settingKey[File]("jflex-source-directory")
  lazy val jflexOutputDirectory = settingKey[File]("jflex-output-directory")
  lazy val jflexSources = taskKey[Seq[File]]("jflex-sources")
  lazy val jflexGenerate = taskKey[Unit]("jflex-generate")

  override lazy val settings = Seq(
    jflexSourceDirectory := sourceDirectory.value / "main" / "flex",
    jflexOutputDirectory := sourceDirectory.value / "main" / "scala",
    jflexSources <<= jflexSourceDirectory map { dir =>
      (dir ** "*.flex").get
    },
    jflexGenerate <<= jflexGenerateTask
  )

  def jflexGenerateTask = (jflexSources, jflexOutputDirectory, streams) map {
    (src, dir, s) => {
      Options.dot = false
      Options.dump = false
      Options.verbose = false
      Options.emitScala = true
      Options.setDir(dir.getPath)
      s.log.info("src: " + src)
      src.foreach(s => Main.generate(s))
    }
  }

}
