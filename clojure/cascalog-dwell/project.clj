(defproject cascalog-dwell "0.1.0-SNAPSHOT"
  :description "Calculates dwell time over AIS data using Cascalog."
  :url "http://www.potomacfusion.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"conjars" "http://conjars.org/repo/"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [cascalog "1.9.0"]
                 [cascading/cascading-hadoop "2.0.2"]
                 [com.twitter/maple "0.2.1"]
                 [clj-time "0.4.4"]
                 [midje "1.4.0"]]
  :profiles {:dev {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                                  [midje-cascalog "0.4.0"]]}}
  :repl-options {:timeout 60000}
  :plugins [[lein-swank "1.4.4"]
            [lein-midje "2.0.0-SNAPSHOT"]]
  :main cascalog-dwell.core
  ;; Not sure if I need this.
  :repl-init-script cascalog-dwell.core)
