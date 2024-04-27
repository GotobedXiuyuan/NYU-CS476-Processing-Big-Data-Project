# NYU-CS476-Processing-Big-Data-Project
Directory Descriptions:
1) /data_ingest:

https://data.cityofnewyork.us/Public-Safety/NYPD-Arrest-Data-Year-to-Date-/uip8-fykc/about_data

Here are the column names along with their corresponding data types:

ARREST_KEY: String
ARREST_DATE: String
PD_CD: String
PD_DESC: String
KY_CD: String
OFNS_DESC: String
LAW_CODE: String
LAW_CAT_CD: String
ARREST_BORO: String
ARREST_PRECINCT: String
JURISDICTION_CODE: String
AGE_GROUP: String
PERP_SEX: String
PERP_RACE: String
X_COORD_CD: String
Y_COORD_CD: String
Latitude: String
Longitude: String
New Georeferenced Column: String

The dataset is zipped to fit in the Github's Maximum file size of 25 MB

2) /etl_code

Directory containing .java, .class, and .jar files used to clean the NYPD_Arrest_Data__Year_to_Date__20240303.csv using Mapreduce, (Removing unwanted columns and missing values).

3) /profiling_code

Directory containing scala file used to do data profiling 

4) /ana_code:

Directory containing scala file to do analytics of cleaned dataset

5) /screenshots

Directory containing screenshots of all cleaning(mapreduce), profiling(scala), analytics(scala), including a small step from Mapreduce output to scala input

6) /test_code

Contains some mapreduce code to profile the data, was a previous assignment code

Step by step:

1) Cleaning the Data:

We first remove missing values and unwanted columns,
the columns we keep are:
ARREST_DATE,PD_CD,LAW_CAT_CD,ARREST_BORO,ARREST_PRECINCT,JURISDICTION_CODE,AGE_GROUP,PERP_SEX,PERP_RACE,X_COORD_CD,Y_COORD_CD,Latitude,Longitude


To clean the data, go to etl_code directory then run the command (if the java, class and jar files are already there):

hadoop jar Clean.jar Clean NYPD_Arrest_Data__Year_to_Date__20240303.csv project_cleaned

To see the cleaned dataset look like, run the line:
hdfs dfs -cat project_cleaned/part-r-00001

However，since the profiling and analytics jobs are done using scala, we need a few lines to transform the mapreduce output to the input for scala, as the mapreduce output does not have any columns names. To do so, run the commands in the screenshots:extra_mapreduce_to_spark1

2) Profiling the Data:

We then can profile our data, checking the statistics of numerical columns and check how many value counts of each column.

To profile the data, go to the profiling_code directory then run the command:

spark-shell --deploy-mode client -i profiling.scala

You can see the statistics of numerical columns and how many value counts of each column directly after running the scala

3) Analyzing the Data

To do analytics on the cleaned dataset, go to the ana_code directory then run the command:

spark-shell --deploy-mode client -i analytics.scala

The analytics jobs do the following 2 analytics:
1. show the trend of each month's arrest numbers
2. show the distribution of charge severity across different boroughs 
After completing this step, we have finished our step-by-step guide

