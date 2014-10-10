(defproject ring-test "0.1.0-SNAPSHOT"
  :description "A test project for learning Ring."
  :url "http://localhost:3000/"
  :license {:name "None"
            :url ""}
  :repositories [["conjars" "http://conjars.org/repo/"]
                 ["sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-jetty-adapter "1.3.1"]]
  :jvm-opts ["-Xmx3g"]
  :main ring-test.core
  :repl-options {:init-ns user}
  :deploy-branches ["master"]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler ring-test.core/app}
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.5"]]}
             :uberjar {:aot :all}})
