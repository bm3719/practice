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

(defn l-palindrome-product [l h]
  (apply max
         (for [x (range l h)
               y (range l h)
               :when (palindrome? (str (* x y)))]
           (* x y))))

(l-palindrome-product 100 1000)

;; #5: Smallest multiple
;;
;; 2520 is the smallest number that can be divided by each of the numbers from
;; 1 to 10 without any remainder.
;;
;; What is the smallest positive number that is evenly divisible by all of the
;; numbers from 1 to 20?

(defn smallest-multiple
  "Find the smallest number that can be divided by each of the numbers from 1
  to n." [n]
  (let [ns (range 1 (inc n))
        is-div? (fn [x] (every? identity (map #(zero? (rem x %)) ns)))]
    (first (filter is-div? (map inc (range))))))

(defn smallest-multiple 20)

;; #6: Sum square difference
;;
;; The sum of the squares of the first ten natural numbers is,
;; 1^2 + 2^2 + ... + 10^2 = 385
;;
;; The square of the sum of the first ten natural numbers is,
;; (1 + 2 + ... + 10)^2 = 55^2 = 3025
;;
;; Hence the difference between the sum of the squares of the first ten natural
;; numbers and the square of the sum is 3025 − 385 = 2640.
;;
;; Find the difference between the sum of the squares of the first one hundred
;; natural numbers and the square of the sum.

(- (#(* % %) (reduce + (range 1 101))) (reduce + (map #(* % %) (range 1 101))))

;; #7: 10001st prime
;;
;; By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see
;; that the 6th prime is 13.
;;
;; What is the 10001st prime number?

(defn prime? [n]
  (empty? (for [x (range 2 (inc (int (Math/floor (Math/sqrt n)))))
                :when (zero? (mod n x))]
            x)))

(last (take 10001 (filter prime? (map (comp inc inc) (range)))))

;; #8: Largest product in a series
;;
;; The four adjacent digits in the 1000-digit number that have the greatest
;; product are 9 × 9 × 8 × 9 = 5832.
;;
;; 73167176531330624919225119674426574742355349194934
;; 96983520312774506326239578318016984801869478851843
;; 85861560789112949495459501737958331952853208805511
;; 12540698747158523863050715693290963295227443043557
;; 66896648950445244523161731856403098711121722383113
;; 62229893423380308135336276614282806444486645238749
;; 30358907296290491560440772390713810515859307960866
;; 70172427121883998797908792274921901699720888093776
;; 65727333001053367881220235421809751254540594752243
;; 52584907711670556013604839586446706324415722155397
;; 53697817977846174064955149290862569321978468622482
;; 83972241375657056057490261407972968652414535100474
;; 82166370484403199890008895243450658541227588666881
;; 16427171479924442928230863465674813919123162824586
;; 17866458359124566529476545682848912883142607690042
;; 24219022671055626321111109370544217506941658960408
;; 07198403850962455444362981230987879927244284909188
;; 84580156166097919133875499200524063689912560717606
;; 05886116467109405077541002256983155200055935729725
;; 71636269561882670428252483600823257530420752963450
;;
;; Find the thirteen adjacent digits in the 1000-digit number that have the
;; greatest product. What is the value of this product?
    
(def digits '(\7 \3 \1 \6 \7 \1 \7 \6 \5 \3 \1 \3 \3 \0 \6 \2 \4 \9 \1 \9 \2 \2
\5 \1 \1 \9 \6 \7 \4 \4 \2 \6 \5 \7 \4 \7 \4 \2 \3 \5 \5 \3 \4 \9 \1 \9 \4 \9
\3 \4 \9 \6 \9 \8 \3 \5 \2 \0 \3 \1 \2 \7 \7 \4 \5 \0 \6 \3 \2 \6 \2 \3 \9 \5
\7 \8 \3 \1 \8 \0 \1 \6 \9 \8 \4 \8 \0 \1 \8 \6 \9 \4 \7 \8 \8 \5 \1 \8 \4 \3
\8 \5 \8 \6 \1 \5 \6 \0 \7 \8 \9 \1 \1 \2 \9 \4 \9 \4 \9 \5 \4 \5 \9 \5 \0 \1
\7 \3 \7 \9 \5 \8 \3 \3 \1 \9 \5 \2 \8 \5 \3 \2 \0 \8 \8 \0 \5 \5 \1 \1 \1 \2
\5 \4 \0 \6 \9 \8 \7 \4 \7 \1 \5 \8 \5 \2 \3 \8 \6 \3 \0 \5 \0 \7 \1 \5 \6 \9
\3 \2 \9 \0 \9 \6 \3 \2 \9 \5 \2 \2 \7 \4 \4 \3 \0 \4 \3 \5 \5 \7 \6 \6 \8 \9
\6 \6 \4 \8 \9 \5 \0 \4 \4 \5 \2 \4 \4 \5 \2 \3 \1 \6 \1 \7 \3 \1 \8 \5 \6 \4
\0 \3 \0 \9 \8 \7 \1 \1 \1 \2 \1 \7 \2 \2 \3 \8 \3 \1 \1 \3 \6 \2 \2 \2 \9 \8
\9 \3 \4 \2 \3 \3 \8 \0 \3 \0 \8 \1 \3 \5 \3 \3 \6 \2 \7 \6 \6 \1 \4 \2 \8 \2
\8 \0 \6 \4 \4 \4 \4 \8 \6 \6 \4 \5 \2 \3 \8 \7 \4 \9 \3 \0 \3 \5 \8 \9 \0 \7
\2 \9 \6 \2 \9 \0 \4 \9 \1 \5 \6 \0 \4 \4 \0 \7 \7 \2 \3 \9 \0 \7 \1 \3 \8 \1
\0 \5 \1 \5 \8 \5 \9 \3 \0 \7 \9 \6 \0 \8 \6 \6 \7 \0 \1 \7 \2 \4 \2 \7 \1 \2
\1 \8 \8 \3 \9 \9 \8 \7 \9 \7 \9 \0 \8 \7 \9 \2 \2 \7 \4 \9 \2 \1 \9 \0 \1 \6
\9 \9 \7 \2 \0 \8 \8 \8 \0 \9 \3 \7 \7 \6 \6 \5 \7 \2 \7 \3 \3 \3 \0 \0 \1 \0
\5 \3 \3 \6 \7 \8 \8 \1 \2 \2 \0 \2 \3 \5 \4 \2 \1 \8 \0 \9 \7 \5 \1 \2 \5 \4
\5 \4 \0 \5 \9 \4 \7 \5 \2 \2 \4 \3 \5 \2 \5 \8 \4 \9 \0 \7 \7 \1 \1 \6 \7 \0
\5 \5 \6 \0 \1 \3 \6 \0 \4 \8 \3 \9 \5 \8 \6 \4 \4 \6 \7 \0 \6 \3 \2 \4 \4 \1
\5 \7 \2 \2 \1 \5 \5 \3 \9 \7 \5 \3 \6 \9 \7 \8 \1 \7 \9 \7 \7 \8 \4 \6 \1 \7
\4 \0 \6 \4 \9 \5 \5 \1 \4 \9 \2 \9 \0 \8 \6 \2 \5 \6 \9 \3 \2 \1 \9 \7 \8 \4
\6 \8 \6 \2 \2 \4 \8 \2 \8 \3 \9 \7 \2 \2 \4 \1 \3 \7 \5 \6 \5 \7 \0 \5 \6 \0
\5 \7 \4 \9 \0 \2 \6 \1 \4 \0 \7 \9 \7 \2 \9 \6 \8 \6 \5 \2 \4 \1 \4 \5 \3 \5
\1 \0 \0 \4 \7 \4 \8 \2 \1 \6 \6 \3 \7 \0 \4 \8 \4 \4 \0 \3 \1 \9 \9 \8 \9 \0
\0 \0 \8 \8 \9 \5 \2 \4 \3 \4 \5 \0 \6 \5 \8 \5 \4 \1 \2 \2 \7 \5 \8 \8 \6 \6
\6 \8 \8 \1 \1 \6 \4 \2 \7 \1 \7 \1 \4 \7 \9 \9 \2 \4 \4 \4 \2 \9 \2 \8 \2 \3
\0 \8 \6 \3 \4 \6 \5 \6 \7 \4 \8 \1 \3 \9 \1 \9 \1 \2 \3 \1 \6 \2 \8 \2 \4 \5
\8 \6 \1 \7 \8 \6 \6 \4 \5 \8 \3 \5 \9 \1 \2 \4 \5 \6 \6 \5 \2 \9 \4 \7 \6 \5
\4 \5 \6 \8 \2 \8 \4 \8 \9 \1 \2 \8 \8 \3 \1 \4 \2 \6 \0 \7 \6 \9 \0 \0 \4 \2
\2 \4 \2 \1 \9 \0 \2 \2 \6 \7 \1 \0 \5 \5 \6 \2 \6 \3 \2 \1 \1 \1 \1 \1 \0 \9
\3 \7 \0 \5 \4 \4 \2 \1 \7 \5 \0 \6 \9 \4 \1 \6 \5 \8 \9 \6 \0 \4 \0 \8 \0 \7
\1 \9 \8 \4 \0 \3 \8 \5 \0 \9 \6 \2 \4 \5 \5 \4 \4 \4 \3 \6 \2 \9 \8 \1 \2 \3
\0 \9 \8 \7 \8 \7 \9 \9 \2 \7 \2 \4 \4 \2 \8 \4 \9 \0 \9 \1 \8 \8 \8 \4 \5 \8
\0 \1 \5 \6 \1 \6 \6 \0 \9 \7 \9 \1 \9 \1 \3 \3 \8 \7 \5 \4 \9 \9 \2 \0 \0 \5
\2 \4 \0 \6 \3 \6 \8 \9 \9 \1 \2 \5 \6 \0 \7 \1 \7 \6 \0 \6 \0 \5 \8 \8 \6 \1
\1 \6 \4 \6 \7 \1 \0 \9 \4 \0 \5 \0 \7 \7 \5 \4 \1 \0 \0 \2 \2 \5 \6 \9 \8 \3
\1 \5 \5 \2 \0 \0 \0 \5 \5 \9 \3 \5 \7 \2 \9 \7 \2 \5 \7 \1 \6 \3 \6 \2 \6 \9
\5 \6 \1 \8 \8 \2 \6 \7 \0 \4 \2 \8 \2 \5 \2 \4 \8 \3 \6 \0 \0 \8 \2 \3 \2 \5
\7 \5 \3 \0 \4 \2 \0 \7 \5 \2 \9 \6 \3 \4 \5 \0))


