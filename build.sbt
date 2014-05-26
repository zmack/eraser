name := "eraser"

version := "1.0"

scalaVersion := "2.10.4"

mainClass := Some("com.zmack.Main")

resolvers += "akka" at "http://repo.akka.io/snapshots"

libraryDependencies ++= Seq(
 "com.sun.mail" % "javax.mail" % "1.5.1",
 "com.sun.mail" % "gimap" % "1.5.1",
 "com.typesafe.akka" %% "akka-actor" % "2.4-SNAPSHOT",
 "com.typesafe" % "config" % "1.2.1"
)
