(defproject cascalog-corr "0.1.0-SNAPSHOT"
  :description "A project to correlate vast data using Cascalog."
  :url "http://www.potomacfusion.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"conjars" "http://conjars.org/repo/"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [cascalog "1.9.0"]
                 [cascading/cascading-hadoop "2.0.2"]
                 [com.twitter/maple "0.2.1"]
                 [org.apache.commons/commons-math "2.1"]]
  :profiles {:dev {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                                  [midje-cascalog "0.4.0"]]}}
  :plugins [[lein-swank "1.4.4"]]
  :main cascalog-corr.core
  ;; Not sure if I need this.
  :repl-init-script cascalog-corr.core)
