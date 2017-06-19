# sbt-jflex-scala [![Build Status](https://travis-ci.org/3tty0n/sbt-jflex-scala.svg?branch=master)](https://travis-ci.org/3tty0n/sbt-jflex-scala)

A plugin for sbt 0.13.x, generates Scala code based on a jflex specification.

This is inspired by [sbt-jflex](https://github.com/dlwh/sbt-jflex).

## Installation

### `project/plugins.sbt`:

```scala
addSbtPlugin("com.github.3tty0n" % "sbt-jflex-scala" % "0.2.2")
```

## Usage

### `src/main/flex`:

Put your `*.flex` file.

### In your sbt shell:

#### Manual generation

```
> jflex::generate
```

then `Yylex.scala` is generated in `target/scala-<scala-version>/src_managed`.

#### Auto generation

If you execute `compile`, `Yylex.scala` will be generated in `target/scala-<scala-version>/src_managed` automatically.

## License

MIT
