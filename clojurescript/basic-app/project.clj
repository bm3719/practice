(defproject basic-app "0.1.0-SNAPSHOT"
  :description "Sample lein-figwheel+re-frame project."
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.773"]
                 [org.clojure/core.async  "0.4.500"]
                 [reagent "0.6.0"]
                 [re-frame "0.9.2"]]
  :plugins [[lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            ;; [lein-shell "0.5.0"]
            [lein-figwheel "0.5.20"]
            ]
  :min-lein-version "2.5.3"
  :source-paths ["src/clj"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :figwheel {:css-dirs ["resources/public/css"]
             ;; :builds-to-start [:dev]
             }
  :profiles {:dev {:dependencies [[binaryage/devtools "1.0.0"]
                                  [figwheel-sidecar "0.5.20"] ; Match with current version of figwheel.
                                  ;; [cider/piggieback "0.5.1"]  ; Might not need.
                                  ]
                   ;; :plugins [[lein-figwheel "0.5.14"]]
                   :source-paths ["src" "dev"]
                   ;; need to add the compiled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]

                   }}
  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src/cljs"]
                :figwheel     {:on-jsload "basic-app.core/mount-root"
                               :open-urls ["http://localhost:3449/index.html"]}
                :compiler     {:main                 basic-app.core
                               :asset-path           "js/compiled/out"
                               :output-to            "resources/public/js/compiled/app.js"
                               :output-dir           "resources/public/js/compiled/out"
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
