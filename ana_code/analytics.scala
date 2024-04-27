import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

val spark = SparkSession.builder()
  .appName("NYPD Arrest Data Analytics")
  .getOrCreate()

import spark.implicits._

val cleaned = spark.read.option("header", "true").csv("new4.csv")

val monthlyArrestsTrend = cleaned
  .withColumn("ARREST_MONTH", expr("substring(ARREST_DATE, 0, 2)"))
  .groupBy("ARREST_MONTH")
  .count()
  .orderBy("ARREST_MONTH")

val severityDistributionForBorough = cleaned
  .groupBy("ARREST_BORO", "LAW_CAT_CD")
  .count()
  .orderBy("ARREST_BORO", "LAW_CAT_CD")

monthlyArrestsTrend.show()
severityDistributionForBorough.show()

spark.stop()

