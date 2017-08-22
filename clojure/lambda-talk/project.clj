(defproject lambda-talk "0.1.0-SNAPSHOT"
  :description "The λ-calculus, demonstrated."
  :license {:name "GNU Public License Version 3"
            :url "http://www.gnu.org/copyleft/gpl.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :repl-options {:init-ns user}
  :global-vars {*print-length* 100}
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]]}})
