CREATE DATABASE IF NOT EXISTS student;

CREATE EXTERNAL TABLE IF NOT EXISTS student.student (
id int,
name String,
gender String,
age int,
sub1 int,
sub2 int,
sub3 int
)
COMMENT 'Student data'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;