(ns practice.core)

;; FizzBuzz in Clojure.
(def fb (map #(cond (zero? (mod % 15)) "FizzBuzz"
                    (zero? (mod % 3)) "Fizz"
                    (zero? (mod % 5)) "Buzz"
                    :else (str %)) (range 1 101)))
