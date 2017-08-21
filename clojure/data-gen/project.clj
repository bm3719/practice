(defproject data-gen "0.1.0-SNAPSHOT"
  :description "Movie data generator"
  :global-vars {*print-length* 100}
  :min-lein-version "2.0.0"
  :repl-options {:init-ns user}
  :main data-gen.core
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.10"]]}
             :uberjar {:aot :all}})
