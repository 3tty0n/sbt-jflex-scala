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
    lazy val jflexSourceDirectory = settingKey[File]("jflex-source-directory")
    lazy val jflexOutputDirectory = settingKey[File]("jflex-output-directory")
    lazy val toolConfiguration = settingKey[JFlexToolConfiguration]("jflex-tool-configuration")
    lazy val pluginConfiguration = settingKey[PluginConfiguration]("jflex-plugin-configuration")
    lazy val jflexSources = taskKey[Seq[File]]("jflex-sources")
    lazy val jflexGenerate = taskKey[Unit]("jflex-generate")
  }

  import autoImport._

  override def trigger: PluginTrigger = allRequirements

  override val projectSettings: Seq[Setting[_]] = Seq(
    jflexSourceDirectory := (sourceDirectory in Compile).value / "flex",
    jflexOutputDirectory := (sourceDirectory in Compile).value / "scala",
    toolConfiguration := JFlexToolConfiguration(),
    pluginConfiguration := PluginConfiguration(),
    jflexSources := ((jflexSourceDirectory in Compile).value ** "*.flex").get ++ ((jflexSourceDirectory in Test).value ** ".flex").get,
    jflexGenerate := jflexGeneratorTask.value,
    unmanagedSourceDirectories in Compile += (jflexSourceDirectory in Compile).value,
    unmanagedSourceDirectories in Test += (jflexSourceDirectory in Test).value,
    compile := (compile in Compile).dependsOn(jflexGeneratorTask).value
  )

  lazy val jflexGeneratorTask: Def.Initialize[Task[Unit]] = Def.task {
    generateWithJFlex(
      jflexSources.value,
      jflexOutputDirectory.value,
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

    log.info(s"Using JFlex version ${Main.version} to generate source files.")
    Options.dot = tool.dot
    Options.verbose = tool.verbose
    Options.dump = tool.dump
    Options.setDir(target.getPath)
    Options.emitScala = tool.emitScala

    val grammars = (srcDir ** ("*" + options.grammarSuffix)).get

    if (grammars.isEmpty) {
      log.warn(s"No JFlex grammars is detected.")
    } else {
      grammars.foreach { g =>
      log.info(s"Grammar file ${g.getPath} detected. Generating...")
      Try { Main.generate(g) } match {
        case Success(_) =>
          log.info(s"File generation is succeeded. Generated files are at ${target.getPath}.")
        case Failure(_) =>
          log.error(s"File generation is failed.")
      }}
    }
  }
}
