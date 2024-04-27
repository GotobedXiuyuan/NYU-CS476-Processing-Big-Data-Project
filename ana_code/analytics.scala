import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

val spark = SparkSession.builder()
  .appName("NYPD Arrest Data Analytics")
  .getOrCreate()

import spark.implicits._

val cleanedDF = spark.read.option("header", "true").csv("new4.csv")

val monthlyArrestsDF = cleanedDF
  .withColumn("ARREST_MONTH", expr("substring(ARREST_DATE, 0, 2)"))
  .groupBy("ARREST_MONTH")
  .count()
  .orderBy("ARREST_MONTH")

val severityDistributionDF = cleanedDF
  .groupBy("ARREST_BORO", "LAW_CAT_CD")
  .count()
  .orderBy("ARREST_BORO", "LAW_CAT_CD")

monthlyArrestsDF.show()
severityDistributionDF.show()

spark.stop()

