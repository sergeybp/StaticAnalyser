name := "StaticAnalyser"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

mainClass := Some("Runner")

libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.0"
libraryDependencies += "com.github.javaparser" % "javaparser-core" % "3.6.2"

enablePlugins(AssemblyPlugin)