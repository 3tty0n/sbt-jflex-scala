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
    lazy val sources = taskKey[Seq[File]]("jflex-sources")
    lazy val generate = taskKey[Unit]("jflex-generate")
  }

  import autoImport._

  override def trigger: PluginTrigger = allRequirements

  override val projectSettings: Seq[Setting[_]] = Seq(
    sourceDirectory in Compile in jflex := (sourceDirectory in Compile).value / "flex",
    sourceDirectory in Test in jflex := (sourceDirectory in Test).value / "flex",
    outputDirectory in jflex := sourceManaged.value,
    toolConfiguration := JFlexToolConfiguration(),
    pluginConfiguration := PluginConfiguration(),
    sources in jflex :=
      ((sourceDirectory in Compile in jflex).value ** "*.flex").get ++
      ((sourceDirectory in Test in jflex).value ** ".flex").get,
    generate in jflex := jflexGeneratorTask.value,
    unmanagedSourceDirectories in Compile += (sourceDirectory in Compile in jflex).value,
    unmanagedSourceDirectories in Test += (sourceDirectory in Test in jflex).value,
    compile := (compile in Compile).dependsOn(jflexGeneratorTask).value
  )

  lazy val jflexGeneratorTask: Def.Initialize[Task[Unit]] = Def.task {
    generateWithJFlex(
      (sources in jflex).value,
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
          log.info(s"JFlex: File generation is succeeded. Generated files are at ${target.getPath}.")
        case Failure(_) =>
          log.error(s"JFlex: File generation is failed.")
      }
    }
  }


}
