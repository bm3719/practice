# cascatest

Prep data (presumes a CSV in ~/data called 1987.csv):

     cat ~/data/1987.csv | tr , \\t > ~/data/1987.tsv

Make a subset of the data:

     head -n 30000 ~/data/1987.tsv > ~/data/1987-truncated.tsv

Put data in HDFS:

    hadoop fs -copyFromLocal ~/data/1987-truncated.tsv /

Execute `lein deps` to grab dependencies.  Then run with `lein run` or interact
with functions via `lein repl`.

## Usage

Use this as a template for real analytics, hopefully with much
optimization/customization.  This just does a boring count of flights
vs. unique carriers.

## License

None.
