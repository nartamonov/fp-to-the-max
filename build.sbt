lazy val fptothemax = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "fp-to-the-max",

    fork := true,
    run / connectInput := true,
    outputStrategy := Some(StdoutOutput),

    scalacOptions += "-Ypartial-unification",

    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6"),

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.2.0",
      "org.typelevel" %% "cats-effect" % "1.0.0-RC2",
      "com.lihaoyi" %% "pprint" % "0.5.3",
      "org.scalactic" %% "scalactic" % "3.0.5",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test"
    )
  )

lazy val commonSettings = Seq(
  version               := "0.1",
  startYear             := Some(2017),
  scalaVersion          := "2.12.6",
  scalacOptions         ++= Seq("-target:jvm-1.8", "-deprecation", "-unchecked", "-Xcheckinit", "-encoding", "utf8", "-feature"),
  scalacOptions         ++= Seq(
    "-language:implicitConversions",
    "-language:postfixOps",
    "-language:reflectiveCalls",
    "-language:higherKinds"
  ),

  // configure prompt to show current project
  shellPrompt           := { s => Project.extract(s).currentProject.id + " > " },

  initialCommands in console :=
    """
      |import java.nio.file._
      |import scala.concurrent._
      |import scala.concurrent.duration._
      |import ExecutionContext.Implicits.global
    """.stripMargin
)
