name := "AnormMetaModel"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "anorm" % "2.3.8")