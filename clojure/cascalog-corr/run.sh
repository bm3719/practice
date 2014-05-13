#!/bin/bash


# verify data is loaded, and exit if not.
basedir=$PWD
cd ..
./verify_data_loaded.sh
if [ $? -eq 1 ] 
then
  exit 1
fi
cd $basedir
echo "data verified to exist."

cp ../util/CONSTANTS.py .
cp ../util/bigdataUtilities.py .
python Driver.py local
returnCode=$?
rm  CONSTANTS.p*
rm  bigdataUtilities.p*
exit $returnCode
