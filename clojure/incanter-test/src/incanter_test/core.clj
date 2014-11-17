(ns incanter-test.core
  (:require [incanter-test.config :as config]
            [incanter-test.crypto :as crypto]
            [incanter.core :refer :all]
            [incanter.stats :as stats]
            [incanter.charts :as charts]
            [incanter.io :as io]
            [incanter.datasets :as datasets]
            [incanter.pdf :as pdf]))

(defn -main [& args]
  (with-data (datasets/get-dataset :cars)
    (view (conj-cols (range (nrow $data)) $data))))
