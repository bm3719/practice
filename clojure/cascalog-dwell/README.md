# cascalog-dwell

A Cascalog implementation of a dwell time analytic against Automatic Identification System (AIS) data.

## Usage

Retrieve the file /data/AIS/final_output_seaseek.tgz from the FTP
site.  It's probably a good idea to just run against a subset of this data (at
least on the VM).  To do that, follow these steps:

`head -n 100000 final_output_seaseek.csv > final_output.csv`
`hadoop fs -copyFromLocal final_output.csv final_output.csv`

Then you can either execute run.sh or follow the manual steps below.

Run `lein deps` and `lein uberjar`.  Then run this command:

`hadoop jar target/cascalog-dwell-0.1.0-SNAPSHOT-standalone.jar clojure.main`

With the full dataset, this is currently quite slow (at least on the dev VM)
due to the way data has to be aggregated and that filtering cannot occur prior
to the expensive aggregations.

## License

BSD
