package com.dawnpro.bigdata.evmgeo

import org.apache.spark.sql.SparkSession
import magellan.{Point, Polygon}
import org.apache.spark
import org.apache.spark.SparkConf
import org.apache.spark.sql.magellan.dsl.expressions._
import org.apache.spark.sql.types._

object GeoTest {
  def main(args: Array[String]) {

    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("MagellanUnitTest")
      .set("spark.sql.crossJoin.enabled", "true")

    val spark = SparkSession.builder()
      .config(conf)
      .config("spark.sql.crossJoin.enabled", "true")
      .getOrCreate()
    import spark.implicits._
    val sqlContext = spark.sqlContext
    val path = this.getClass.getClassLoader.getResource("china.json").getPath
    val df = sqlContext.read
      .format("magellan")
      .option("type", "geojson")
      .load(path)
    //df.select($"metadata"("name") as "name").show(35)

    val points = spark.createDataFrame(Seq((111.9397, 31.9263), (109.0579867, 33.9414337), (111.4228123, 34.2828126))).toDF("x","y").select(point($"x", $"y").as("point"))

    val result = points.join(df.select($"polygon", $"metadata"("name").as("name"))).where($"point" within $"polygon")
    result.show()

    spark.close()
  }
}
