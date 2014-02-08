;;;; Non-distributed word count, using only functionality in the default
;;;; Clojure namespaces.
(ns clj-wordcount.core (:use clojure.core
                             clojure.java.io
                             [clojure.string :only [split]]))

(defn file-word-count
  "Count words in a file.  Splits on any any whitespace length."
  [file]
  (defn hash-update
    "Increment a hash-map value in place, or initialize to 1."
    [hm word]
    (update-in hm [word] (fnil inc 0)))
  (reduce hash-update {} (split (slurp file) #"\s+")))

(defn word-count
  "Gets per-file counts and merges them."
  [files]
  (reduce (fn [x y] (merge-with + x y)) (map (fn [file] (file-word-count file))
                                             (rest files))))

(defn -main
  "Word counts the contents of files in resources/ and formats the results in
  the same manner as the canonical MapReduce job."  []
  (let [dir (file "resources")
        files (file-seq dir)]
    (doseq [kv-pair (word-count files)]
           (printf "%s %s\n" (key kv-pair) (val kv-pair)))))

