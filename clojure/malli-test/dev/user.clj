(ns user
  (:require [malli-test.core :as core]
            [clojure.tools.namespace.repl :refer [refresh]]))

;; Possibly a useful function for dynamically merging sub-schemas.

(defn deep-merge
  "Recursively merge maps." [& maps]
  (reduce (fn m [& xs]
            (if (some #(and (map? %) (not (record? %))) xs)
              (apply merge-with m xs)
              (last xs)))
          maps))
