import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, IntegerType}

// Initialize SparkSession
val spark = SparkSession.builder().appName("NYPD Arrest Data Profiling").getOrCreate()

import spark.implicits._

val Path = "new4.csv"
val df = spark.read.option("header", "true").option("inferSchema", "true").csv(Path)


// Convert all numeric columns to double 
val dfNumerics = df
  .withColumn("PD_CD", $"PD_CD".cast(DoubleType))
  .withColumn("X_COORD_CD", $"X_COORD_CD".cast(DoubleType))
  .withColumn("Y_COORD_CD", $"Y_COORD_CD".cast(DoubleType))
  .withColumn("Latitude", $"Latitude".cast(DoubleType))
  .withColumn("Longitude", $"Longitude".cast(DoubleType))

// Selecting the numerical columns 
val numericalCols = Seq("PD_CD", "X_COORD_CD", "Y_COORD_CD", "Latitude", "Longitude")

// Get the mean median mode std of the numerical value
numericalCols.foreach { colName =>
  val colmean = dfNumerics.select(mean(colName)).first()(0).asInstanceOf[Double]
  val colmedian = dfNumerics.stat.approxQuantile(colName, Array(0.5), 0.01).head
  val colmode = dfNumerics.groupBy(colName).count().orderBy(desc("count")).first()(0).asInstanceOf[Double]
  val colstd = dfNumerics.select(stddev(colName)).first()(0).asInstanceOf[Double]
// some of the commands above I consulted GPT

  println(s"Statistics:")
  println(s"Mean: $colmean")
  println(s"Median: $colmedian")
  println(s"Mode: $colmode")
  println(s"Standard Deviation: $colstd")
}
// Calculate and print the count of unique values for each column
df.columns.foreach { colName =>
  val uniqueCount = df.select(colName).distinct().count()
  println(s"Unique count for $colName: $uniqueCount")
}



spark.stop()
