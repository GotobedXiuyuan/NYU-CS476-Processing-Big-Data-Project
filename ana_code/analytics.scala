import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

val spark = SparkSession.builder()
  .appName("NYPD Arrest Data Analytics")
  .getOrCreate()

import spark.implicits._

// Read the cleaned DataFrame
val cleanedDF = spark.read.option("header", "true").csv("new4.csv")

// Trend Analysis of Arrests Over Time
val monthlyArrestsDF = cleanedDF
  .withColumn("ARREST_MONTH", expr("substring(ARREST_DATE, 0, 2)"))
  .groupBy("ARREST_MONTH")
  .count()
  .orderBy("ARREST_MONTH")

// Charge Severity Distribution Across Different Boroughs
val severityDistributionDF = cleanedDF
  .groupBy("ARREST_BORO", "LAW_CAT_CD")
  .count()
  .orderBy("ARREST_BORO", "LAW_CAT_CD")

// Show the results
monthlyArrestsDF.show()
severityDistributionDF.show()

spark.stop()

