data = load '${input_dir}' using PigStorage(',') as (id:int, name:chararray, gender:chararray, age:int, sub1:int, sub2:int, sub3:int);
by_gender = filter data by gender == '${gender}';
store by_gender into '${output_dir}' using PigStorage(',');