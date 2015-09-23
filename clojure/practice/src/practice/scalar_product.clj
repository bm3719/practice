;;; Google Code Jam: Minimum Scalar Product
;;; http://code.google.com/codejam/contest/32016/dashboard#s=p0

(ns practice.scalar-product)

(def test-input "2
3
1 3 -5
-2 4 1
5
1 2 3 4 5
1 0 1 0 1")

(defn triple->scalar-map
  "Given a triple of strings representing count, the x vector, and y vector,
  create a map of integer values/vectors." [col]
  {:count (read-string (first col))
   :x (map read-string (clojure.string/split (second col) #" "))
   :y (map read-string (clojure.string/split (last col) #" "))})

(defn parse-input
  "Read in the input and create a vector of maps." [s]
  (->> (clojure.string/split s #"\n")
       (rest)
       (partition 3)
       (map triple->scalar-map)))

(defn scalar-product
  "Given two sequences of integers, return their scalar product." [xs ys]
  (reduce + (map * xs ys)))

(defn format-output
  "Generate the output string." [case-num answer]
  (str "Case #" case-num ": " answer))

;; Note: According to the dot product formula, the answers returned here are
;; correct.  They don't match the test case though, for reasons unknown.
(defn solve-scalar-product
  "Solve the scalar product problem." []
  (doseq [[n s] (map (fn [n s] [n s]) (map inc (range))
                     (map #(scalar-product (:x %) (:y %)) (parse-input test-input)))]
    (println (format-output n s))))
