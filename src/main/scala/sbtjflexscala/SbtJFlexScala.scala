package sbtjflexscala

import sbt._
import Keys._

import jflex.{ Options, Main }

import scala.util.{ Try, Success, Failure }

object SbtJFlexScala extends AutoPlugin {

  case class JFlexToolConfiguration(
    dot: Boolean = false,
    dump: Boolean = false,
    verbose: Boolean = false,
    emitScala: Boolean = true
  )

  case class PluginConfiguration(
    grammarSuffix: String = ".flex"
  )

  object autoImport {
    lazy val jflex = taskKey[Unit]("jflex")
    lazy val sourceDirectory = settingKey[File]("jflex-source-directory")
    lazy val outputDirectory = settingKey[File]("jflex-output-directory")
    lazy val toolConfiguration = settingKey[JFlexToolConfiguration]("jflex-tool-configuration")
    lazy val pluginConfiguration = settingKey[PluginConfiguration]("jflex-plugin-configuration")
    lazy val jflexSources = taskKey[Seq[File]]("jflex-sources")
    lazy val generate = taskKey[Unit]("jflex-generate")
  }

  import autoImport._

  override def trigger: PluginTrigger = allRequirements

  override val projectSettings: Seq[Setting[_]] = Seq(
    sourceDirectory in jflex := sourceDirectory.value / "main" / "flex",
    outputDirectory in jflex := sourceDirectory.value / "main" / "scala" /  "flex",
    toolConfiguration := JFlexToolConfiguration(),
    pluginConfiguration := PluginConfiguration(),
    jflexSources := (sourceDirectory.value ** "*.flex").get,
    generate := jflexGeneratorTask.value,
    unmanagedSourceDirectories in Compile += (sourceDirectory in jflex).value,
  )

  lazy val jflexGeneratorTask: Def.Initialize[Task[Unit]] = Def.task {
    generateWithJFlex(
      (jflexSources in jflex).value,
      (outputDirectory in jflex).value,
      toolConfiguration.value,
      pluginConfiguration.value,
      streams.value.log
    )
  }

  private[this] def generateWithJFlex(
    srcDir: Seq[File],
    target: File,
    tool: JFlexToolConfiguration,
    options: PluginConfiguration,
    log: Logger
  ): Unit = {
    target.mkdirs()

    log.info(s"JFlex: Using JFlex version ${Main.version} to generate source files.")
    Options.dot = tool.dot
    Options.verbose = tool.verbose
    Options.dump = tool.dump
    Options.setDir(target.getPath)
    Options.emitScala = tool.emitScala

    val grammars = (srcDir ** ("*" + options.grammarSuffix)).get
    log.info(s"JFlex: Generating source files for ${grammars.size} grammars.")

    grammars.foreach { g =>
      log.info(s"JFlex: Grammar file ${g.getPath} detected.")
      Try { Main.generate(g) } match {
        case Success(_) =>
          log.info(s"JFlex: File generated successfully.")
        case Failure(_) =>
          log.error(s"JFlex: File generation is failed.")
      }
    }
  }


}
