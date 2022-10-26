(defproject basic-app "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [reagent "0.6.0"]
                 [re-frame "0.9.2"]]
  :plugins [[lein-cljsbuild "1.1.5" :exclusions [[org.clojure/clojure]]]
            [lein-shell "0.5.0"]]
  :min-lein-version "2.5.3"
  :source-paths ["src/clj"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :figwheel {:css-dirs ["resources/public/css"]
             ;; :builds-to-start [:dev]
             }
  :profiles {:dev {:dependencies [[binaryage/devtools "0.8.2"]
                                  [figwheel-sidecar "0.5.14"] ; use here whatever the current version of figwheel is
                                  [cider/piggieback "0.5.1"]
                                  ]
                   :plugins      [[lein-figwheel "0.5.14"]]}}
  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src/cljs"]
                :figwheel     {:on-jsload "basic-app.core/mount-root"}
                :compiler     {:main                 basic-app.core
                               :output-to            "resources/public/js/compiled/app.js"
                               :output-dir           "resources/public/js/compiled/out"
                               :asset-path           "js/compiled/out"
                               :source-map-timestamp true
                               :preloads             [devtools.preload]
                               :external-config      {:devtools/config {:features-to-install :all}}}}
               {:id           "min"
                :source-paths ["src/cljs"]
                :compiler     {:main            basic-app.core
                               :output-to       "resources/public/js/compiled/app.js"
                               :optimizations   :advanced
                               :closure-defines {goog.DEBUG false}
                               :pretty-print    false}}]})
