# sbt-jflex-scala [![Build Status](https://travis-ci.org/3tty0n/sbt-jflex-scala.svg?branch=master)](https://travis-ci.org/3tty0n/sbt-jflex-scala)

A plugin for sbt 0.13.x, generates code based on a jflex specifiaction.

This is inspired by [sbt-jflex](https://github.com/dlwh/sbt-jflex).

## Usage

In your `project/plugins.sbt`,

```scala
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

addSbtPlugin("com.github.3tty0n" % "sbt-jflex-scala" % "0.1.0-SNAPSHOT")
```

Put your `*.flex` file in `src/main/flex`.

Next, in your sbt shell, type 

```
> jflexGenerate
```

then `Yylex.scala` is generated in `src/main/scala/flex`.

If you want to generate `Yylex.scala` when you compile, in `build.sbt`,

```scala
compile := ((compile in Compile).dependsOn(jflexGenerate)).value
```

## License

MIT