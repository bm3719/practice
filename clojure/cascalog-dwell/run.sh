#!/bin/bash

if [ ! -f "./final_output_seaseek.csv" ]
then
	echo "final_output_seaseek.csv was not found in the root directory; please consult the README.md for instructions on retrieving this file"
	exit 1
fi

cp ../../correlation_projects/util/CONSTANTS.py .
cp ../../correlation_projects/util/bigdataUtilities.py .

hadoop fs -rmr final_output.csv 
hadoop fs -rmr /user/bigdata/ais-dwell-out
hadoop fs -copyFromLocal final_output_seaseek.csv final_output.csv

#echo "cascalog:start:`date +%s`" >> ../timing_logs/temp
python Driver.py local
#echo "cascalog:end:`date +%s`" >> ../timing_logs/temp

rm  CONSTANTS.p*
rm  bigdataUtilities.p*
