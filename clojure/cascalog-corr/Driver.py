import CONSTANTS
import shutil
import os
import bigdataUtilities as util

if __name__ == "__main__":
    util.obnoxiousPrint("Cleaning up HDFS data.")
    util.subprocessCall(["hadoop", "fs", "-rm", CONSTANTS.CASCALOG_HDFS_PATH + '/\*'], False)

    fobj = open("__temp_file", 'w')
    util.hiveScript("select * from vast_time_series_data", stdout=fobj)
    fobj.close()
    
    util.obnoxiousPrint("Moving data to Cascalog readable location.")
    util.subprocessCall(["hadoop", "fs", "-copyFromLocal", "__temp_file",
                         "vast_time_series_data.tsv"])
   
    util.obnoxiousPrint("Checking project dependencies.") 
    util.subprocessCall(["lein", "deps"])
    util.obnoxiousPrint("Creating uberjar.") 
    util.subprocessCall(["lein", "uberjar"])
    util.obnoxiousPrint("Running Cascalog correlation.") 
    util.subprocessCall(["hadoop", "jar", "target/cascalog-corr-0.1.0-SNAPSHOT-standalone.jar",
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
        if "corr-out/part-" in path:
            util.subprocessCall(["hadoop", "fs", "-cat", path], stdout=fobj)

    fobj.close()

    # clean up the output
    fobj = open("output/sorted_out","r")
    lines = fobj.readlines()
    fobj.close()
    lines = map(lambda x: x.strip().split("\t"),lines)
    lines = filter(lambda x: x[0] < x[1],lines)
    lines = map(lambda x: "\t".join(x)+"\n",lines)
    fobj = open("output/sorted_out","w")
    fobj.writelines(lines)
    fobj.close()


    
    # Clean up.
    os.remove(CONSTANTS.TEMP_FILE_PATH)
    os.remove("__temp_file")
