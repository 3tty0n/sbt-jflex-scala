# sbt-jflex-scala [![Build Status](https://travis-ci.org/3tty0n/sbt-jflex-scala.svg?branch=master)](https://travis-ci.org/3tty0n/sbt-jflex-scala)

A plugin for sbt 0.13.x, generates Scala code based on a jflex specification.

This is inspired by [sbt-jflex](https://github.com/dlwh/sbt-jflex).

## Installation

Add your `project/plugins.sbt` file:

### __snapshot__ (Latest)

```scala
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

addSbtPlugin("com.github.3tty0n" % "sbt-jflex-scala" % "0.1.1-SNAPSHOT")
```

### __release__

```scala
addSbtPlugin("com.github.3tty0n" % "sbt-jflex-scala" % "0.1.0")
```

### JFlex specification file

Put your `*.flex` file in `src/main/flex`.

### Usage

In your sbt shell:

```
> jflexGenerate
```

then `Yylex.scala` is generated in `src/main/scala/flex`.

If you want to generate `Yylex.scala` with compile, add:

```scala
jflexGenerateWithCompile := true
```

## License

MIT
