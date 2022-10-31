(defproject basic-fwm "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main"]}
  :source-paths ["src/clj" "src/cljs"]
  :resource-paths ["target" "resources"]
  :profiles {:dev {:dependencies [[org.clojure/clojurescript "1.10.773"]
                                  [com.bhauman/figwheel-main "0.2.18"]
                                  ;; Helpful for terminal REPL.
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]
                   :clean-targets ^{:protect false} ["target"]}})
