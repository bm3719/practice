(ns practice.core)

;; FizzBuzz in Clojure.
(def fb (map #(cond (zero? (mod % 15)) "FizzBuzz"
                    (zero? (mod % 3)) "Fizz"
                    (zero? (mod % 5)) "Buzz"
                    :else (str %)) (range 1 101)))

;;; Google Code Jam: Store Credit
;;; https://code.google.com/codejam/contest/351101/dashboard#s=p0

(def test-input "3
100
3
5 75 25
200
7
150 24 79 50 88 345 3
8
8
2 1 9 4 4 56 90 3")

(defn triple->shop-map
  "Given a collection of strings representing 1.) credit, 2.) number of items
  in the store, and 3.) the price of items in the store, return a map with the
  same info, only using integers and vectors." [col]
  {:credit (read-string (first col))
   :#-items (read-string (second col))
   :prices (map read-string (clojure.string/split (last col) #" "))})

(defn parse-input
  "Take the raw input string and return a vector of cycles." [s]
  (as-> (clojure.string/split s #"\n") $
    (partition 3 (rest $))
    (map triple->shop-map $)))

(defn last-n
  "Take the last n items from col." [n col]
  (reverse (take n (reverse col))))

(defn gen-pairs
  "Given a collection of prices, the number of prices (which is already
  available), return a collection that joins them together without
  self-matches or repeats." [prices num-prices]
  (apply concat
   (for [c (range num-prices)]
     (for [item (last-n (- num-prices c 1) prices)]
       [(nth prices c) item]))))

(defn find-optimal-pair
  "Given a collection of pairs, find the one where the sum matches the
  credit." [pairs credit]
  (some (fn [v] (when (= credit (apply + v)) v)) pairs))

(defn format-output
  "Generate the output string." [case-num n1 n2]
  (str "Case #" case-num ": " n1 " " n2))

(defn find-indexes
  "Given an optimal pair, get the 1-indexed indexes.  Since these can be dual
  pairs of the same number, replace the first number after removing it."
  [col pair]
  (let [i1 (.indexOf col (first pair))
        col (assoc (vec col) i1 -1)
        i2 (.indexOf col (second pair))]
    (map inc [i1 i2])))

(defn solve-store-credit
  "Solve the problem." []
  (doseq [[cycle n] (map #(vector (find-indexes (:prices %1)
                                                (find-optimal-pair
                                                 (gen-pairs (:prices %1) (:#-items %1)) (:credit %1))) (inc %2))
                         (parse-input test-input) (range))]
    (println (format-output n (first cycle) (second cycle)))))
