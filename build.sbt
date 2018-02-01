import Dependencies._

lazy val root = (project in file("."))
    .settings(
        inThisBuild(List(
            organization := "com.artenc",
            scalaVersion := "2.12.4",
            version      := "0.1.0-SNAPSHOT"
        )),
        name := "Robotic Scala lib",
        libraryDependencies += "com.artenc" %% "scala-utility" % "0.1.0-SNAPSHOT",
        libraryDependencies += scalaTest % Test,
        libraryDependencies += "com.pi4j" % "pi4j-core" % "1.1"
    )
