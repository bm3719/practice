(defproject schema-test "0.1.0-SNAPSHOT"
  :description "A test project to investigate Prismatic Schema"
  :url ""
  :license {:name "None"
            :url ""}
  :repositories [["conjars" "http://conjars.org/repo/"]
                 ["sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [prismatic/schema "0.3.3"]
                 [clj-time "0.9.0"]]
  :jvm-opts ["-Xmx3g"]
  :main schema-test.core
  :repl-options {:init-ns user}
  :global-vars {*print-length* 100}
  :deploy-branches ["master"]
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.5"]]}
             :uberjar {:aot :all}})
