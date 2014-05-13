(ns cascalog-dwell.core-test
  (:use clojure.test
        cascalog-dwell.core
        midje.sweet)
  (:gen-class))

(fact "Haversine functional."
      (dist-km 36.12 -86.67 33.94 -118.40) => 2887.259950607111)
