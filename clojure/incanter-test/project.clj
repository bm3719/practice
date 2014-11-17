(defproject incanter-test "0.1.0-SNAPSHOT"
  :description "Incanter experimentation project"
  :repositories [["conjars" "http://conjars.org/repo/"]
                 ["sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.novemberain/monger "2.0.0"]
                 [org.mongodb/mongo-java-driver "2.12.3"]
                 [incanter "1.5.5"]
                 [cheshire "5.1.1"]
                 [clj-time "0.8.0"]]
  :jvm-opts ["-Xmx3g"]
  :main incanter-test.core
  :repl-options {:init-ns user}
  :global-vars {*print-length* 100}
  :deploy-branches ["master"]
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.5"]]}
             :uberjar {:aot :all}})
