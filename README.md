# Get test data
To download the data please run `get-data.sh`

Usage:
 `./get-data.sh {YEAR} {MONTH}`
 
 Example:
 
 `./get-data.sh 2018 01`
 
 # Run spark job
 
 To run this job you have to provide input params:
 
 - inputPath - path where all the input data are (*.gz files) - path to containing directory
 - repositoryOutputFilePath - path to directory where you want to dump result of repositories aggregation, directory must not exists before
 - userOutputFilePath - path to directory where you want to dump result of users aggregation, directory must not exists before
 
 Example:
 
`spark-submit --class "phobosive.main.Boot" --driver-memory 12g project_2.11-0.1.jar ./data ./repo ./user`

For proper working you have to adjust `--driver-memory` value. For tests I was using dataset from 2018-01 and was running it on my own computer with 32GB RAM. To avoid OOM had to give driver 12GB of memory.

You also have to have proper spark configuration (env vals: SPARK_HOME, HADOOP_HOME)

# Test

To test just run `sbt test`. Env vals SPARK_HOME and HADOOP_HOME have to be properly set. If running on Windows `winutils.exe` must be accesible in cmd from every directory (place it on PATH env val).

# Others

I wasn't able to determine event for `starring` the repo... was looking over in official docs [https://developer.github.com/v3/activity/events/] and just couldn't find any info how to infer this. Current dev therefore don't spot this and FollowEvent is counted in place of it.  
