(defproject test-xml "0.1.0-SNAPSHOT"
  :description "A test project to investigate AOS data."
  :url ""
  :license {:name "None" :url ""}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :jvm-opts ["-Xmx4G"]
  :main test-xml.core
  :global-vars {*print-length* 100}
  :repl-options {:init-ns user}
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.8"]]}})
