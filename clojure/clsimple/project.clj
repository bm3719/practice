(defproject clsimple "0.1.0-SNAPSHOT"
  :description "A test project to do a very basic analytic using Cascalog."
  :url "http://www.potomacfusion.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"conjars" "http://conjars.org/repo/"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [cascalog "1.9.0"]
                 [cascading/cascading-hadoop "2.0.2"]
                 [com.twitter/maple "0.2.1"]
                 ]
;  :profiles {:dev {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]]}}
  :plugins [[lein-swank "1.4.4"]]
;  :main clsimple.core
  :namespaces [clsimple.core])
