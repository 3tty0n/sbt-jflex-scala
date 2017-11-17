version := "0.1"

scalaVersion := "2.12.2"

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "test"

jflexSourceDirectory := baseDirectory.value / "flex"

jflexOutputDirectory := baseDirectory.value / "src"
