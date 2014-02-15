# clj-wordcount

A non-distributed word count implementation against files, written in Clojure. 

The source is [here](https://github.com/bm3719/practice/blob/master/clojure/clj-wordcount/src/clj_wordcount/core.clj).


## Usage

Running this (by default, against the files in resources/) can be done with:

    lein run

The histogram is implemented in Gnuplot:

    gnuplot vis.gp

This outputs something like this:

<img src="https://raw.github.com/bm3719/practice/master/clojure/clj-wordcount/histogram.png">
  

## License

None.
