;;;; Project Euler problems, solved in Clojure.
;;;; https://projecteuler.net/

;; #1: Multiples of 3 and 5
;; 
;; If we list all the natural numbers below 10 that are multiples of 3 or 5, we
;; get 3, 5, 6 and 9. The sum of these multiples is 23.
;;
;; Find the sum of all the multiples of 3 or 5 below 1000.

(apply + (filter #(or (zero? (mod % 5)) (zero? (mod % 3))) (range 1 1000)))

;; #2: Even Fibonacci Numbers
;;
;; Each new term in the Fibonacci sequence is generated by adding the previous
;; two terms. By starting with 1 and 2, the first 10 terms will be:
;;
;; 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
;;
;; By considering the terms in the Fibonacci sequence whose values do not
;; exceed four million, find the sum of the even-valued terms.

(apply + (filter even?
                 ((fn [col]
                    (if (> (last col) 4000000) col
                        (recur (conj col (+ (last col) (last (butlast col)))))))
                  [1 2])))

;; #3: Largest prime factor
;;
;; The prime factors of 13195 are 5, 7, 13, 29.
;;
;; What is the largest prime factor of the number 600851475143?
(defn primes
  "Sieve of Eratosthenes." [n]
  (loop [p 2 seq (range 2 n)]
    (if (> (* p p) n)
      seq
      (let [new-seq (filter #(or (= % p) (not= 0 (mod % p))) seq)
        new-p (first (filter #(> % p) new-seq))]
    (recur new-p new-seq)))))

(defn l-factor [n]
  (let [ps (primes (int (Math/sqrt n)))]
    (apply max (for [x ps
                     :when (zero? (rem n x))]
                 x))))

(l-factor 600851475143)

;; #4: Largest palindrome product
;;
;; A palindromic number reads the same both ways. The largest palindrome made
;; from the product of two 2-digit numbers is 9009 = 91 × 99.
;;
;; Find the largest palindrome made from the product of two 3-digit numbers.

(defn palindrome?
  "Check if a string is a palindrome." [s]
  (if (<= (count s) 1) true
      (and (= (first s) (last s)) (palindrome? (rest (butlast s))))))

(defn l-palindrome-product []
  (apply max
         (for [x (range 100 1000)
               y (range 100 1000)
               :when (palindrome? (str (* x y)))]
           (* x y))))

(l-palindrome-product)

;; #5: Smallest multiple
;;
;; 2520 is the smallest number that can be divided by each of the numbers from
;; 1 to 10 without any remainder.
;;
;; What is the smallest positive number that is evenly divisible by all of the
;; numbers from 1 to 20?




