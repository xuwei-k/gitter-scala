import build._

baseSettings

name := "gitter-scala"

description := "purely functional scala gitter api client"

libraryDependencies ++= Seq(
  "io.argonaut" %% "argonaut-scalaz" % "6.2",
  "com.github.xuwei-k" %% "httpz" % httpzVersion,
  "com.github.xuwei-k" %% "httpz-native" % httpzVersion % "test",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.2.12" % "test"
)

enablePlugins(BuildInfoPlugin)
