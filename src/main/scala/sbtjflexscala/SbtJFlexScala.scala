package sbtjflexscala

import sbt._
import Keys._

import jflex.{ Options, Main }

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
    lazy val jflexGenerateWithCompile = settingKey[Boolean]("jflex-with-compile")
    lazy val jflexSources = taskKey[Seq[File]]("jflex-sources")
    lazy val jflexGenerate = taskKey[Unit]("jflex-generate")
  }

  import autoImport._

  override def trigger: PluginTrigger = allRequirements

  override val projectSettings: Seq[Setting[_]] = Seq(
    jflexSourceDirectory := sourceDirectory.value / "main" / "flex",
    jflexOutputDirectory := sourceDirectory.value / "main" / "scala" / "flex",
    toolConfiguration := JFlexToolConfiguration(),
    pluginConfiguration := PluginConfiguration(),
    jflexSources := (jflexSourceDirectory.value ** "*.flex").get,
    jflexGenerate := jflexGeneratorTask.value,
    unmanagedSourceDirectories in Compile += jflexSourceDirectory.value,
    jflexGenerateWithCompile := false
  ) ++ Seq(
    compile := {
      if (jflexGenerateWithCompile.value)
        (compile in Compile).dependsOn(jflexGenerate).value
      else
        (compile in Compile).value
      }
  )

  lazy val jflexGeneratorTask = Def.task {
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

    log.info(s"JFlex: Using JFlex version ${Main.version} to generate source files.")
    Options.dot = tool.dot
    Options.verbose = tool.verbose
    Options.dump = tool.dump
    Options.setDir(target.getPath)
    Options.emitScala = tool.emitScala

    val grammars = (srcDir ** ("*" + options.grammarSuffix)).get
    log.info(s"JFlex: Generating source files for ${grammars.size} grammars.")

    grammars.foreach { g =>
      Main.generate(g)
      log.info(s"JFlex: Grammar file ${g.getPath} detected.")
    }
  }


}
