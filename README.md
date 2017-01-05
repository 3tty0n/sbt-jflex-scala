# sbt-jflex-scala

A plugin for sbt 0.13.x, generates code based on an jflex specifiaction.

This is inspired by [sbt-jflex](https://github.com/dlwh/sbt-jflex).

## Usage

In your `project/plugins.sbt`, 

```scala

addSbtPlugin("com.micchon" % "sbt-jflex-plugin" % "0.1.0-SNAPSHOT")
```

Put your `*.flex` file in `src/main/flex`.

Next, in your sbt shell, 

```
$ jflexGenerate
```

then `Yylex.scala` is generated in `src/main/scala`.

## License

MIT