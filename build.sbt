name := "evmgeo"

version := "0.1"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8")

//sparkVersion := "2.2.0"

val testSparkVersion = settingKey[String]("The version of Spark to test against.")

testSparkVersion := sys.props.get("spark.testVersion").getOrElse("2.2.0")

val testHadoopVersion = settingKey[String]("The version of Hadoop to test against.")

testHadoopVersion := sys.props.getOrElse("hadoop.testVersion", "2.7.3")

//sparkComponents := Seq("core", "sql")

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.4",
  "org.slf4j" % "slf4j-api" % "1.7.16" % "provided",
  "com.lihaoyi" % "fastparse_2.11" % "0.4.3" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.vividsolutions" % "jts" % "1.13" % "test",
  "com.esri.geometry" % "esri-geometry-api" % "1.2.1" % "test"
)

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-client" % testHadoopVersion.value,
  "org.apache.spark" %% "spark-core" % testSparkVersion.value exclude("org.apache.hadoop", "hadoop-client"),
  "org.apache.spark" %% "spark-sql" % testSparkVersion.value  exclude("org.apache.hadoop", "hadoop-client")
)

libraryDependencies += "magellan" % "test" % "1.0" from "file:///Users/apple/Downloads/magellan-1.0.5-s_2.11.jar"