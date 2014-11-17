;;;; This is a dev namespace to contain utility functions useful for
;;;; interacting with the data at the REPL, or for general development.

(ns user
  (:require [incanter-test.core :as core]
            [incanter-test.config :as config]
            [incanter-test.crypto :as crypto]
            [incanter-test.db :as db]
            [clojure.pprint :refer [pp pprint]]
            [clojure.repl :refer [doc]]
            [clojure.string :as string]
            [clojure.tools.namespace.repl :refer [refresh]]
            [incanter.core :refer :all]
            [incanter.stats :as stats]
            [incanter.charts :as charts]
            [incanter.io :as io]
            [incanter.datasets :as datasets]
            [incanter.pdf :as pdf]))

(defn test-ns
  "A confirmation function to run from the user REPL" []
  "user namespace active.")

(defn setup
  "Activate MongoDB connection to test database.  Run this to create a var
  named db that can then be used to pass to functions like
  db/retrieve-maps." []
  (defonce db (db/mongo-setup (db/create-conn config/host config/port)
                              config/db config/user config/pwd)))

;;; Tutorial found here: http://data-sorcery.org/2010/01/03/datasets_mongodb/
;;; This seems pretty out of date.

;; This doesn't seem to work.
(def data (io/read-dataset
           "http://github.com/liebke/incanter/raw/master/data/cars.csv"
           :header true))

;; Grab a predefined dataset.
(def d (datasets/get-dataset :cars))

;; (incanter/dim d)

;; Create a scatter plot with a regression line using the fitted values
;; returned from the linear-model function.  Call pdf/save-pdf on the result to
;; save it to a file.
(defn scatter-plot []
  (with-data d
    (let [lm (stats/linear-model ($ :dist) ($ :speed))]
      (doto (charts/scatter-plot ($ :speed) ($ :dist))
        (charts/add-lines ($ :speed) (:fitted lm))
        view))))

;; Inside a with-data expression, the dataset is bound to $data.
(defn chart []
  (with-data (datasets/get-dataset :cars)
    (view (conj-cols (range (nrow $data)) $data))))

;; Query datasets with $where.
($where {:speed 10} d)
($where {:speed {:$gt 10 :$lt 20}} d)
($where {:speed {:$in #{4 7 24 25}}} d)
($where {:speed {:$nin #{4 7 24 25}}} d)

;; The data is passed implicitly within a $where expression used in a with-data
;; call.
(with-data d
  (stats/mean ($ :speed ($where {:speed {:$gt -10 :$lt 10}}))))

;;; Another tutorial:
;;; http://rhnh.net/2011/08/02/exploring-data-with-clojure-incanter-and-leiningen

;; This read-dataset call works, unlike the one above.
(def plot-data (io/read-dataset
                "https://raw.github.com/liebke/incanter/master/data/iris.dat"
                :delim \space
                :header true))

;; Create a scatter-plot you can later run view on.
(def plot (charts/scatter-plot
           (sel plot-data :cols 0)
           (sel plot-data :cols 1)
           :x-label "Sepal Length"
           :y-label "Sepal Width"
           :group-by (sel plot-data :cols 4)))

;; Get a quick, useful summary of a dataset.
(stats/summary plot-data)
