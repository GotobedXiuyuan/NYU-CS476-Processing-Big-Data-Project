import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, IntegerType}

// Initialize SparkSession
val spark = SparkSession.builder().appName("NYPD Arrest Data").getOrCreate()

import spark.implicits._

val Path = "NYPD_Arrest_Data__Year_to_Date__20240303.csv"
val df = spark.read.option("header", "true").option("inferSchema", "true").csv(Path)


// Convert all numeric columns to double 
val dfNumerics = df
  .withColumn("ARREST_KEY", $"ARREST_KEY".cast(DoubleType))
  .withColumn("PD_CD", $"PD_CD".cast(DoubleType))
  .withColumn("KY_CD", $"KY_CD".cast(DoubleType))
  .withColumn("X_COORD_CD", $"X_COORD_CD".cast(DoubleType))
  .withColumn("Y_COORD_CD", $"Y_COORD_CD".cast(DoubleType))
  .withColumn("Latitude", $"Latitude".cast(DoubleType))
  .withColumn("Longitude", $"Longitude".cast(DoubleType))

// Selecting the numerical columns 
val numericalCols = Seq("ARREST_KEY", "PD_CD", "KY_CD", "X_COORD_CD", "Y_COORD_CD", "Latitude", "Longitude")

// Get the mean median mode std of the numerical value
numericalCols.foreach { colName =>
  val mean = dfNumerics.select(mean(colName)).first()(0).asInstanceOf[Double]
  val median = dfNumerics.stat.approxQuantile(colName, Array(0.5), 0.01).head
  val mode = dfNumerics.groupBy(colName).count().orderBy(desc("count")).first()(0).asInstanceOf[Double]
  val std = dfNumerics.select(stddev(colName)).first()(0).asInstanceOf[Double]
// some of the commands above I consulted GPT

  println(s"Statistics:")
  println(s"Mean: $mean")
  println(s"Median: $median")
  println(s"Mode: $mode")
  println(s"Standard Deviation: $stdl")
}


val dfWithFormattedText = df.withColumn("Clean_PD_DESC", lower(trim($"PD_DESC")))

val dfFinal = dfWithFormattedText.withColumn("Is_Felony", when($"LAW_CAT_CD" === "F", 1).otherwise(0))



dfFinal.show()

dfFinal.write.option("header", "true").csv(""/user/xw2147_nyu_edu/NYPD_Arrest_Data_Cleaned.csv")

spark.stop()
