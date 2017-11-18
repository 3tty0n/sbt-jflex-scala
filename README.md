# sbt-jflex-scala [![Build Status](https://travis-ci.org/3tty0n/sbt-jflex-scala.svg?branch=master)](https://travis-ci.org/3tty0n/sbt-jflex-scala)

A plugin for sbt 0.13.x/1.0.x, generates Scala code based on a jflex specification.

This is inspired by [sbt-jflex](https://github.com/dlwh/sbt-jflex).

## Setup

### `project/plugins.sbt`:

- stable (recommended)

  ```scala
  addSbtPlugin("com.github.3tty0n" % "sbt-jflex-scala" % "0.4.0")
  ```
- development

  ```scala
  addSbtPlugin("com.github.3tty0n" % "sbt-jflex-scala" % "0.4.1-SNAPSHOT")
  ```

## Usage

### `src/main/flex`:

Put your `*.flex` file.

### In your sbt shell:

#### Manual generation

```
> jflexGenerate
```

then `Yylex.scala` is generated in `src/main/scala`.

#### Auto generation

If you execute `compile`, `Yylex.scala` will be generated in `src/main/scala` automatically.

## Customize

If the structure of your application is nonstandard:

``` scala
.
├── build.sbt
├── flex
│   └── simple.flex
├── project
│   └── plugins.sbt
├── src
│   ├── Base.scala
│   └── Tokens.scala
└── test
```
### `build.sbt`:

``` scala
jflexSourceDirectory := baseDirectory.value / "flex"
jflexOutputDirectory := baseDirectory.value / "src"
```

## License

MIT
