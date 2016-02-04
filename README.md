# Helping commands

This setup is done and tested using HDP Ambari 2.3
https://cwiki.apache.org/confluence/display/AMBARI/Quick+Start+Guide

You will also need some extra libraries to run Pig and Hive actions. Please add them.

-- Ports used (update in the respective properties and workflow, if required.)
- namenode : 8020
- job-tracker : 8050
- hcat metastore : 9083

### Commands to create and copy initial setup on HDFS
```sh
$ hdfs dfs -mkdir -p /abafna/oozie/lib
$ hdfs dfs -mkdir -p /abafna/oozie/data
$ hdfs dfs -mkdir -p /abafna/oozie/scripts

# copy oozie-wf-example and oozie-cluster in the current directory
$ hdfs dfs -put oozie-wf-example-1.0-SNAPSHOT.jar /abafna/oozie/lib/
$ hdfs dfs -put scripts/* /abafna/oozie/scripts/

# delete and copy
$ hdfs dfs -rm -skipTrash /abafna/oozie/coordinator1.xml
$ hdfs dfs -put oozie-cluster/coordinator1.xml /abafna/oozie
$ hdfs dfs -rm -skipTrash /abafna/oozie/coordinator2.xml
$ hdfs dfs -put oozie-cluster/coordinator2.xml /abafna/oozie

$ hdfs dfs -rm -skipTrash /abafna/oozie/workflow1.xml
$ hdfs dfs -put oozie-cluster/workflow1.xml /abafna/oozie
$ hdfs dfs -rm -skipTrash /abafna/oozie/workflow2.xml
$ hdfs dfs -put oozie-cluster/workflow2.xml /abafna/oozie

$ hdfs dfs -rm -skipTrash /abafna/oozie/bundle.xml
$ hdfs dfs -put oozie-cluster/bundle.xml /abafna/oozie
```

### Commands to validate oozie workflow/coordinator/bundle xmls
```sh
# set the oozie web service URL 
$ export OOZIE_URL=http://c6402.ambari.apache.org:11000/oozie

$ oozie validate oozie-cluster/bundle.xml
$ oozie validate oozie-cluster/coordinator1.xml
$ oozie validate oozie-cluster/workflow1.xml
$ oozie validate oozie-cluster/coordinator2.xml
$ oozie validate oozie-cluster/workflow2.xml
```

### Commands to launch and run a oozie workflow/coordinator/bundle
```sh
$ oozie job -config oozie-cluster/job.coord1.properties -run
$ oozie job -config oozie-cluster/job.coord2.properties -run
$ oozie job -config oozie-cluster/job.wf1.properties -run
$ oozie job -config oozie-cluster/job.wf2.properties -run
$ oozie job -config oozie-cluster/job.bundle.properties -run

# status and information about the oozie job
$ oozie job -info <job_id>
$ oozie job -log <job_id>
$ oozie job -config <properties_file> -rerun <job_id>
$ oozie job -kill <job_id>
$ oozie  job -rerun <coord_job_id> -refresh -action [1, 1-2]
```
