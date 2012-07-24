(ns clsimple.core  (:use cascalog.api
        cascalog.vars
        [cascalog.workflow :only (fields)])
  (:import [cascading.scheme.hadoop TextDelimited]
           [cascading.tuple Fields]))

(def gen-tuples
  [(range 3)
   (range 1 4)
   (range 2 5)])

(defn -main
  [] (?<- (hfs-textline "test-out.tsv")
          [?a ?b ?c]
          (gen-tuples :> ?a ?b ?c)))
