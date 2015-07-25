(defproject seesaw-test "0.1.0-SNAPSHOT"
  :description "A test application for seesaw"
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [seesaw "1.4.5"]]
  :main seesaw-test.core
  :repl-options {:init-ns user}
  :global-vars {*print-length* 100}
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.10"]]}
             :uberjar {:aot :all}})
