name := "eraser"

version := "1.0"

scalaVersion := "2.10.0"

mainClass := Some("com.zmack.Main")

libraryDependencies ++= Seq(
 "com.sun.mail" % "javax.mail" % "1.5.1",
 "com.sun.mail" % "gimap" % "1.5.1"
)
