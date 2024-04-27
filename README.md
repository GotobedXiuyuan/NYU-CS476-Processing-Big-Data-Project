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

We first must remove some missing values and unwanted columns,
the columns we keep are:
ARREST_DATE,PD_CD,LAW_CAT_CD,ARREST_BORO,ARREST_PRECINCT,JURISDICTION_CODE,AGE_GROUP,PERP_SEX,PERP_RACE,X_COORD_CD,Y_COORD_CD,Latitude,Longitude


To clean the data, navigate to the /etl_code directory then run the command:

hadoop jar clean.jar Clean NYPD_Arrest_Data__Year_to_Date__20240303.csv 

If you wish to see the cleaned dataset, you can run the commands:

hdfs dfs -get CleanDataset/output 
cat output/part-r-00000

2) Profiling the Data:

We then can profile our data, checking how many lines remain after the initial cleaning.

To profile the data, navigate to the /profiling_code directory then run the command:

hadoop jar countRecs.jar CountRecs /user/<netID>/CleanDataset/output/part-r-00000 /user/<netID>/ProfileDataset/output

If you wish to see how many records there are, you can run the commands:

hdfs dfs -get ProfileDataset/output 
cat output/part-r-00000

3) Analyzing the Data

After cleaning and profiling, we can now analyze our data. These files analyze our data in two way, tracking how average teacher salaries change and how average board of education member salaries change.

To analyze the average teacher salary, navigate to the /ana_code directory then run the command:

hadoop jar averageTeacherSalary.jar AverageTeacherSalary /user/<netID>/CleanDataset/output/part-r-00000 /user/<netID>/AverageTeacherSalary/output

If you wish to see the average teacher salary throughout the years, you can run the commands:

hdfs dfs -get AverageTeacherSalary/output 
cat output/part-r-00000

To analyze the average board of education member salary, navigate to the /ana_code directory then run the command:

hadoop jar averageTeacherSalary.jar AverageTeacherSalary /user/<netID>/CleanDataset/output/part-r-00000 /user/<netID>/AverageTeacherSalary/output

If you wish to see the average teacher salary throughout the years, you can run the commands:

hdfs dfs -get AverageTeacherSalary/output 
cat output/part-r-00000

After completing this step, we have finished our step-by-step guide to analyzing education spending in Montana.

