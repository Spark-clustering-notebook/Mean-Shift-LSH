organization := "spartakus"

name := "Neirest Neighbours Mean Shift LSH"

version := "1.0"

scalaVersion := "2.10.5"

val sparkVersion = "1.6.0"

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % sparkVersion % "provided",
	"org.apache.spark"  %% "spark-mllib"  % sparkVersion % "provided",
	"org.scalatest" % "scalatest_2.10" % "2.2.4" % "test"
)
