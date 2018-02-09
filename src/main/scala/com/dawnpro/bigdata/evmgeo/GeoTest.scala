package com.dawnpro.bigdata.evmgeo

import org.apache.spark.sql.SparkSession
import magellan.{Point, Polygon}
import org.apache.spark.sql.magellan.dsl.expressions._
import org.apache.spark.sql.types._

object GeoTest {
  def main(args: Array[String]) {

    val spark = SparkSession.builder()
      .appName("this is my test")
      .master("local")
      .config("spark.sql.warehouse.dir", "file:///Users/apple/Downloads")
      .getOrCreate()
    val df = spark.sqlContext.read.format("magellan").option("type","geojson").load("mapdata")
    df.show(10)
    spark.close()
  }
}
