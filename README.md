# sbt-jflex-scala [![Build Status](https://travis-ci.org/3tty0n/sbt-jflex-scala.svg?branch=master)](https://travis-ci.org/3tty0n/sbt-jflex-scala)

A plugin for sbt 0.13.x, generates Scala code based on a jflex specification.

This is inspired by [sbt-jflex](https://github.com/dlwh/sbt-jflex).

## Installation

### `project/plugins.sbt`:


#### __release__ (Stable)

```scala
addSbtPlugin("com.github.3tty0n" % "sbt-jflex-scala" % "0.1.1")
```

Please read this [page](https://github.com/3tty0n/sbt-jflex-scala/tree/5fe2ae6d2103ca212fc09e57c07250eb735a8df0) to know how to use version `0.1.1`.

#### __snapshot__ (Latest)

```scala
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

addSbtPlugin("com.github.3tty0n" % "sbt-jflex-scala" % "0.2.1-SNAPSHOT")
```


## Usage

### `src/main/flex`:

Put your `*.flex` file.

### In your sbt shell:

```
> jflex::generate
```

then `Yylex.scala` is generated in `src/main/scala/flex`.

## License

MIT
