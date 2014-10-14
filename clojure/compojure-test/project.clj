(defproject compojure-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.9"]
                 [ring/ring-jetty-adapter "1.3.1"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler compojure-test.handler/app}
  :jvm-opts ["-Xmx3g"]
  :main compojure-test.handler
  :repl-options {:init-ns user}
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]
                                  [org.clojure/tools.namespace "0.2.5"]]}
             :uberjar {:aot :all}})
