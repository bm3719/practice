import CONSTANTS
import shutil
import os
import bigdataUtilities as util

if __name__ == "__main__":

    util.obnoxiousPrint("Ensure the file 'final_output.csv' is in the user's HDFS directory.")
    util.obnoxiousPrint("See the README.md file for details.")
   
    util.obnoxiousPrint("Checking project dependencies.") 
    util.subprocessCall(["lein", "deps"])
    util.obnoxiousPrint("Creating uberjar.") 
    util.subprocessCall(["lein", "uberjar"])
    util.obnoxiousPrint("Running Cascalog correlation.") 
    util.subprocessCall(["hadoop", "jar", "target/cascalog-dwell-0.1.0-SNAPSHOT-standalone.jar",
                         "clojure.main"])
    
    util.obnoxiousPrint("Copying results from HDFS to local output directory.")
    try:
        shutil.rmtree("output")
    except:
        pass
    os.mkdir("output")

    paths = util.lsrHdfsDir(CONSTANTS.CASCALOG_HDFS_PATH)
    fobj = open("output/sorted_out","w")
    for path in paths:
        if "ais-dwell-out/part-" in path:
            util.subprocessCall(["hadoop", "fs", "-cat", path], stdout=fobj)
    
    # Clean up.
    try:
        os.remove(CONSTANTS.TEMP_FILE_PATH)
        os.remove("__temp_file")
    except:
        pass
