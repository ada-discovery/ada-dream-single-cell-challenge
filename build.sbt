organization := "org.adada"

name := "ada-dream-single-cell-challenge"

version := "0.1.0"

description := "Ada extension for Single Cell Transcriptomics DREAM Challenge containing a few handy runnables/scripts."

isSnapshot := false

scalaVersion := "2.11.12"

resolvers ++= Seq(
  Resolver.mavenLocal
)

libraryDependencies ++= Seq(
  "org.adada" %% "ada-server" % "0.8.1"
)

// POM settings for Sonatype
homepage := Some(url("https://ada-discovery.github.io"))

publishMavenStyle := true

scmInfo := Some(ScmInfo(url("https://github.com/ada-discovery/ada-dream-single-cell-challenge"), "scm:git@github.com:ada-discovery/ada-dream-single-cell-challenge.git"))

developers := List(Developer("bnd", "Peter Banda", "peter.banda@protonmail.com", url("https://peterbanda.net")))

licenses += "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")

publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)
