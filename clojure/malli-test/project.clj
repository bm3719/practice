(defproject malli-test "0.1.0-SNAPSHOT"
  :description "Practice project for Malli"
  :url "https://github.com/bm3719/practice"
  :license {:name "GNU General Public License 3.0"
            :url "https://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [metosin/malli "0.6.1"]
                 [borkdude/sci "0.2.6"]]
  :global-vars {*print-length* 100}
  :repl-options {:init-ns user}
  :main malli-test.core
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "1.1.0"]]}})
