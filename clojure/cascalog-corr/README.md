# cascalog-corr

Correlate some data from HDFS with itself using Pearson's Correlation.

## Usage

Just run `run.sh` from the main project directory and find the results in
`output/sorted_output`.

Or, to do it manually...

Put data in user directory in HDFS.  If it's already ingested via the
correlation_projects/ingest-data method, run these commands:

`hadoop fs -cat /user/hive/warehouse/vast_time_series_data/000000_0 > ~/vast_time_series_data.tsv`
`hadoop fs -copyFromLocal ~/vast_time_series_data.tsv vast_time_series_data.tsv`

Grab the dependencies with `lein deps` and build the jar file with `lein
uberjar`.  Then run the analytic (from the base cascalog-corr project directory) with:

`hadoop jar target/cascalog-corr-0.1.0-SNAPSHOT-standalone.jar clojure.main`

Get your answers in some place like `/user/bigdata/corr-out/part-00000`.

## Notes

It may be necessary to manually clean up the user's HDFS directory before
running the job.  This is usually when the cleanup operation fails for some
reason.

For larger input datasets, edit core.clj and set the number of mappers to
something appropriate.  It currently defaults to 20.

## License

BSD
