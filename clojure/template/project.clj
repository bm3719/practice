(defproject template "0.1.0-SNAPSHOT"
  :description "A project template"
  :url "https://github.com/bm3719/practice/tree/master/clojure/template"
  :license {:name "BSD 2-Clause License"
            :url "https://opensource.org/licenses/BSD-2-Clause"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main template.core
  :repl-options {:init-ns user}
  :global-vars {*print-length* 100}
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.8"]]}
             :uberjar {:aot :all}})
