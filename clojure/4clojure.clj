;;;; Exercises from 4clojure.com.  Using difficulty ordering.  Will probably
;;;; stop somewhere in the Medium range.

;;; Elementary

;; #1: Nothing but the Truth
(def a1 true)

(= a1 true)

;; #2: Simple Math
(def a2 4)

(= (- 10 (* 2 3)) a2)

;; #3: Intro to Strings
(def a3 "HELLO WORLD")

(= a3 (.toUpperCase "hello world"))

;; #4: Intro to Lists
(= (list :a :b :c) '(:a :b :c))

;; #5: Lists: conj
(def a5 '(1 2 3 4))

(= a5 (conj '(2 3 4) 1))
(= a5 (conj '(3 4) 2 1))

;; #6: Intro to Vectors
(= [:a :b :c] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))

;; #7: Vectors: conj
(def a7 [1 2 3 4])
(= a7 (conj [1 2 3] 4))
(= a7 (conj [1 2] 3 4))

;; #8: Intro to Sets
(def a8 #{:a :b :c :d})

(= a8 (set '(:a :a :b :c :c :c :c :d :d)))
(= a8 (clojure.set/union #{:a :b :c} #{:b :c :d}))

;; #9: Sets: conj
(def a9 2)

(= #{1 2 3 4} (conj #{1 4 3} a9))

;; #10: Intro to Maps
(def a10 20)

(= a10 ((hash-map :a 10, :b 20, :c 30) :b))
(= a10 (:b {:a 10, :b 20, :c 30}))

;; #11: Maps: conj
(def a11 {:b 2})

(= {:a 1, :b 2, :c 3} (conj {:a 1} a11 [:c 3]))

;; #12: Intro to Sequences
(def a12 3)

(= a12 (first '(3 2 1)))
(= a12 (second [2 3 4]))
(= a12 (last (list 1 2 3)))

;; #13 Sequences: rest
(def a13 [20 30 40])

(= a13 (rest [10 20 30 40]))

;; #14: Intro to Functions
(def a14 8)

(= a14 ((fn add-five [x] (+ x 5)) 3))
(= a14 ((fn [x] (+ x 5)) 3))
(= a14 (#(+ % 5) 3))
(= a14 ((partial + 5) 3))

;; #15: Double Down
(def a15 #(* % 2))

(= (a15 2) 4)
(= (a15 3) 6)
(= (a15 11) 22)
(= (a15 7) 14)

;; #16: Hello World
(def a16 #(str "Hello, " % "!"))

(= (a16 "Dave") "Hello, Dave!")
(= (a16 "Jenn") "Hello, Jenn!")
(= (a16 "Rhea") "Hello, Rhea!")

;; #17: Sequences: map
(def a17 [6 7 8])

(= a17 (map #(+ % 5) '(1 2 3)))

;; #18: Sequences: filter
(def a18 [6 7])

(= a18 (filter #(> % 5) '(3 4 5 6 7)))

;; #35: Local Bindings
(def a35 7)

(= a35 (let [x 5] (+ 2 x)))
(= a35 (let [x 3, y 10] (- y x)))
(= a35 (let [x 21] (let [y 3] (/ x y))))

;; #36: Let it Be
(= 10 (let [x 7 y 3 z 1] (+ x y)))
(= 4 (let [x 7 y 3 z 1] (+ y z)))
(= 1 (let [x 7 y 3 z 1] z))

;; #37: Regular Expressions
(def a37 "ABC")

(= a37 (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))

;; #64: Intro to Reduce
(def a64 +)

(= 15 (reduce a64 [1 2 3 4 5]))
(= 0 (reduce a64 []))
(= 6 (reduce a64 1 [2 3]))

;; #57: Simple Recursion
(def a57 [5 4 3 2 1])

(= a57 ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x ))) 5))

;; #71: Rearranging Code: ->
;; The -> macro threads an expression x through a variable number of forms, in
;; each threading, making it a list if it is not already one.
(def a71 last)

(= (a71 (sort (rest (reverse [2 5 4 1 3 6]))))
   (-> [2 5 4 1 3 6] (reverse) (rest) (sort) (a71))
   5)

;; #68: Recurring Theme
(def a68 [7 6 5 4 3])

(= a68
   (loop [x 5
          result []]
     (if (> x 0)
       (recur (dec x) (conj result (+ 2 x)))
       result)))

;; #72: Rearranging Code: ->>
;; ->> is the same as ->, except that it inserts x as the last item in the
;; forms passed.
(= (reduce + (map inc (take 3 (drop 2 [2 5 4 1 3 6]))))
   (->> [2 5 4 1 3 6] (drop 2) (take 3) (map inc) (reduce +))
   11)

;; #134: A nil key
(def a134 #(and (= nil (%1 %2)) (contains? %2 %1)))

(true? (a134 :a {:a nil :b 2}))
(false? (a134 :b {:a nil :b 2}))
(false? (a134 :c {:a nil :b 2}))

;; #145: For the win
(def a145 [1 5 9 13 17 21 25 29 33 37])

(= a145 (for [x (range 40)
              :when (= 1 (rem x 4))]
          x))
(= a145 (for [x (iterate #(+ 4 %) 0)
              :let [z (inc x)]
              :while (< z 40)]
          z))
(= a145 (for [[x y] (partition 2 (range 20))]
          (+ x y)))

;; #162: Logical falsity and truth
(def a162 1)

(= a162 (if-not false 1 0))
(= a162 (if-not nil 1 0))
(= a162 (if true 1 0))
(= a162 (if [] 1 0))
(= a162 (if [0] 1 0))
(= a162 (if 0 1 0))
(= a162 (if 1 1 0))

;; #156: Map Defaults
(def a156 (fn f [x s] (reduce conj (map #(hash-map % x) s))))

(= (a156 0 [:a :b :c])
   {:a 0 :b 0 :c 0})
(= (a156 "x" [1 2 3])
   {1 "x" 2 "x" 3 "x"})
(= (a156 [:a :b] [:foo :bar])
   {:foo [:a :b] :bar [:a :b]})

;;; Easy

;; #19: Last Element
(= (#(first (reverse %)) [1 2 3 4 5]) 5)
(= (#(first (reverse %)) '(5 4 3)) 3)
(= (#(first (reverse %)) ["b" "c" "d"]) "d")

;; #20: Penultimate Element
(= (#(last (take 2 (reverse %))) (list 1 2 3 4 5)) 4)
(= (#(last (take 2 (reverse %))) ["a" "b" "c"]) "b")
(= (#(last (take 2 (reverse %))) [[1 2] [3 4]]) [1 2])

;; #21: Nth Element
(= (#(last (take (inc %2) %1)) '(4 5 6 7) 2) 6)
(= (#(last (take (inc %2) %1)) [:a :b :c] 0) a)
(= (#(last (take (inc %2) %1)) [1 2 3 4] 1) 2)
(= (#(last (take (inc %2) %1)) '([1 2] [3 4] [5 6]) 2) [5 6])

;; #22: Count a Sequence
(= (#(reduce + (for [_ %] 1)) '(1 2 3 3 1)) 5)
(= (#(reduce + (for [_ %] 1)) "Hello World") 11)
(= (#(reduce + (for [_ %] 1)) [[1 2] [3 4] [5 6]]) 3)
(= (#(reduce + (for [_ %] 1)) '(13)) 1)
(= (#(reduce + (for [_ %] 1)) '(:a :b :c)) 3)

;; #24: Sum It All Up
(= (#(reduce + %) [1 2 3]) 6)
(= (#(reduce + %) (list 0 -2 5 5)) 8)
(= (#(reduce + %) #{4 2 1}) 7)
(= (#(reduce + %) '(0 0 -1)) -1)
(= (#(reduce + %) '(1 10 3)) 14)

;; #25: Find the odd numbers
(= (#(filter odd? %) #{1 2 3 4 5}) '(1 3 5))
(= (#(filter odd? %) [4 2 1 6]) '(1))
(= (#(filter odd? %) [2 2 4 6]) '())
(= (#(filter odd? %) [1 1 1 3]) '(1 1 1 3))

;; #23: Reverse a Sequence
(= ((fn f [lst] (if (seq lst) (cons (last lst) (f (butlast lst))) lst))
    [1 2 3 4 5]) [5 4 3 2 1])
(= ((fn f [lst] (if (seq lst) (cons (last lst) (f (butlast lst))) lst))
    (sorted-set 5 7 2 7)) '(7 5 2))
(= ((fn f [lst] (if (seq lst) (cons (last lst) (f (butlast lst))) lst))
    [[1 2] [3 4] [5 6]]) [[5 6] [3 4] [1 2]])

;; #27: Palindrome Detector
;; NOTE: On the site, you can't use as-> for some reason.  Would have to use ->>.
(false? (#(as-> (vec %) $ (= $ (reverse $)))
         '(1 2 3 4 5)))
(true? (#(as-> (vec %) $ (= $ (reverse $)))
        "racecar"))
(true? (#(as-> (vec %) $ (= $ (reverse $)))
        [:foo :bar :foo]))
(true? (#(as-> (vec %) $ (= $ (reverse $)))
        '(1 1 3 3 1 1)))
(false? (#(as-> (vec %) $ (= $ (reverse $)))
         '(:a :b :c)))

;; #26: Fibonacci Sequence
(= (#(take % ((fn f [] (lazy-cat [1 1] (map + (f) (rest (f)))))))
    3) '(1 1 2))
(= (#(take % ((fn f [] (lazy-cat [1 1] (map + (f) (rest (f)))))))
    6) '(1 1 2 3 5 8))
(= (#(take % ((fn f [] (lazy-cat [1 1] (map + (f) (rest (f)))))))
    8) '(1 1 2 3 5 8 13 21))

;; #38: Maximum value
(= ((fn f [x & xs]
      (if (seq xs) (let [ox (apply f xs)] (if (> x ox) x ox)) x))
    1 8 3 4) 8)
(= ((fn f [x & xs]
      (if (seq xs) (let [ox (apply f xs)] (if (> x ox) x ox)) x))
    30 20) 30)
(= ((fn f [x & xs]
      (if (seq xs) (let [ox (apply f xs)] (if (> x ox) x ox)) x))
    45 67 11) 67)

;; #29: Get the Caps
(= ((fn f [s] (apply str (filter #(Character/isUpperCase %) s)))
    "HeLlO, WoRlD!") "HLOWRD")
(empty? ((fn f [s] (apply str (filter #(Character/isUpperCase %) s)))
         "nothing"))
(= ((fn f [s] (apply str (filter #(Character/isUpperCase %) s)))
    "$#A(*&987Zf") "AZ")

;; #32: Duplicate a Sequence
(= (#(apply concat (for [e %] [e e]))
    [1 2 3]) '(1 1 2 2 3 3))
(= (#(apply concat (for [e %] [e e]))
    [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))
(= (#(apply concat (for [e %] [e e]))
    [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

;; #48: Intro to some
(= 6 (some #{2 7 6} [5 6 7 8]))
(= 6 (some #(when (even? %) %) [5 6 7 8]))

;; #34: Implement range
(= (#(take (- %2 %1) ((fn f [] (lazy-cat [%1] (map inc (f))))))
    1 4) '(1 2 3))
(= (#(take (- %2 %1) ((fn f [] (lazy-cat [%1] (map inc (f))))))
    -2 2) '(-2 -1 0 1))
(= (#(take (- %2 %1) ((fn f [] (lazy-cat [%1] (map inc (f))))))
    5 8) '(5 6 7))

;; #28: Flatten a sequence
(= ((fn f [lst] (cond (nil? (first lst)) lst
                      (sequential? (first lst)) (concat (f (first lst)) (f (rest lst)))
                      :else (conj (f (rest lst)) (first lst))))
    '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))
(= ((fn f [lst] (cond (nil? (first lst)) lst
                      (sequential? (first lst)) (concat (f (first lst)) (f (rest lst)))
                      :else (conj (f (rest lst)) (first lst))))
    ["a" ["b"] "c"]) '("a" "b" "c"))
(= ((fn f [lst] (cond (nil? (first lst)) lst
                      (sequential? (first lst)) (concat (f (first lst)) (f (rest lst)))
                      :else (conj (f (rest lst)) (first lst))))
    '(((:a)))) '(:a))

;; #39: Interleave Two Seqs
(= (#(flatten (map list %1 %2))
    [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))
(= (#(flatten (map list %1 %2))
    [1 2] [3 4 5 6]) '(1 3 2 4))
(= (#(flatten (map list %1 %2))
    [1 2 3 4] [5]) [1 5])
(= (#(flatten (map list %1 %2))
    [30 20] [25 15]) [30 25 20 15])

;; #42: Factorial Fun
(= (#(reduce * (map inc (range %))) 1) 1)
(= (#(reduce * (map inc (range %))) 3) 6)
(= (#(reduce * (map inc (range %))) 5) 120)
(= (#(reduce * (map inc (range %))) 8) 40320)

;; #30: Compress a Sequence
(= (apply str (#(map first (partition-by identity %))
               "Leeeeeerrroyyy")) "Leroy")
(= (#(map first (partition-by identity %))
    [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))
(= (#(map first (partition-by identity %))
    [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))

;; #47: Contain Yourself
(contains? #{4 5 6} 4)
(contains? [1 1 1 1 1] 4)
(contains? {4 :a 2 :b} 4)
(not (contains? '(1 2 4) 4))

;; #33: Replicate a Sequence
(= (#(apply concat (for [e %1] (repeat %2 e)))
    [1 2 3] 2) '(1 1 2 2 3 3))
(= (#(apply concat (for [e %1] (repeat %2 e)))
    [:a :b] 4) '(:a :a :a :a :b :b :b :b))
(= (#(apply concat (for [e %1] (repeat %2 e)))
    [4 5 6] 1) '(4 5 6))
(= (#(apply concat (for [e %1] (repeat %2 e)))
    [[1 2] [3 4]] 2) '([1 2] [1 2] [3 4] [3 4]))
(= (#(apply concat (for [e %1] (repeat %2 e)))
    [44 33] 2) [44 44 33 33])

;; #45: Intro to Iterate
(= '(1 4 7 10 13) (take 5 (iterate #(+ 3 %) 1)))

;; #40: Interpose a Seq
(= (#(conj (vec (apply concat (for [e (butlast %2)] [e %1]))) (last %2))
    0 [1 2 3]) [1 0 2 0 3])
(= (apply str (#(conj (vec (apply concat (for [e (butlast %2)] [e %1]))) (last %2))
               ", " ["one" "two" "three"])) "one, two, three")
(= (#(conj (vec (apply concat (for [e (butlast %2)] [e %1]))) (last %2))
    :z [:a :b :c :d]) [:a :z :b :z :c :z :d])

;; #31: Pack a Sequence
(= (#(partition-by identity %) [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))
(= (#(partition-by identity %) [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))
(= (#(partition-by identity %) [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))

;; #41: Drop Every Nth Item
(= (#(filter (complement nil?)
             (flatten (partition (dec %2) %2 (repeat nil) %1)))
    [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])
(= (#(filter (complement nil?)
             (flatten (partition (dec %2) %2 (repeat nil) %1)))
    [:a :b :c :d :e :f] 2) [:a :c :e])
(= (#(filter (complement nil?)
             (flatten (partition (dec %2) %2 (repeat nil) %1)))
    [1 2 3 4 5 6] 4) [1 2 3 5 6])

;; #52: Intro to Destructuring
(= [2 4] (let [[a b c d e f g] (range)] [c e]))

;; #49: Split a sequence
(= (#(conj (list (drop %1 %2)) (take %1 %2))
    3 [1 2 3 4 5 6]) [[1 2 3] [4 5 6]])
(= (#(conj (list (drop %1 %2)) (take %1 %2))
    1 [:a :b :c :d]) [[:a] [:b :c :d]])
(= (#(conj (list (drop %1 %2)) (take %1 %2))
    2 [[1 2] [3 4] [5 6]]) [[[1 2] [3 4]] [[5 6]]])

;; #51: Advanced Destructuring
(= [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] [1 2 3 4 5]] [a b c d]))

;; #83: A Half-Truth
(= false (#(true? (and (some identity %&) (not (every? identity %&))))
          false false))
(= true (#(true? (and (some identity %&) (not (every? identity %&))))
         true false))
(= false (#(true? (and (some identity %&) (not (every? identity %&))))
          true))
(= true (#(true? (and (some identity %&) (not (every? identity %&))))
         false true false))
(= false (#(true? (and (some identity %&) (not (every? identity %&))))
          true true true))
(= true (#(true? (and (some identity %&) (not (every? identity %&))))
         true true true false))

;; #61: Map Construction
(= (#(into {} (map vector %1 %2))
    [:a :b :c] [1 2 3]) {:a 1, :b 2, :c 3})
(= (#(into {} (map vector %1 %2))
    [1 2 3 4] ["one" "two" "three"]) {1 "one", 2 "two", 3 "three"})
(= (#(into {} (map vector %1 %2))
    [:foo :bar] ["foo" "bar" "baz"]) {:foo "foo" :bar "bar"})

;; #66: Greatest Common Divisor
;; Using the Euclidean algorithm.
(= ((fn g [x y] (if (= y 0) x (g y (mod x y)))) 2 4) 2)
(= ((fn g [x y] (if (= y 0) x (g y (mod x y)))) 10 5) 5)
(= ((fn g [x y] (if (= y 0) x (g y (mod x y)))) 5 7) 1)
(= ((fn g [x y] (if (= y 0) x (g y (mod x y)))) 1023 858) 33)

;; #166: Comparisons
(= :gt (#(cond (%1 %2 %3) :lt (%1 %3 %2) :gt :else :eq) < 5 1))
(= :eq (#(cond (%1 %2 %3) :lt (%1 %3 %2) :gt :else :eq)
        (fn [x y] (< (count x) (count y))) "pear" "plum"))
(= :lt (#(cond (%1 %2 %3) :lt (%1 %3 %2) :gt :else :eq)
        (fn [x y] (< (mod x 5) (mod y 5))) 21 3))
(= :gt (#(cond (%1 %2 %3) :lt (%1 %3 %2) :gt :else :eq) > 0 2))

;; #81: Set Intersection
(= (#(clojure.set/difference %1 (clojure.set/difference %1 %2))
    #{0 1 2 3} #{2 3 4 5}) #{2 3})
(= (#(clojure.set/difference %1 (clojure.set/difference %1 %2))
    #{0 1 2} #{3 4 5}) #{})
(= (#(clojure.set/difference %1 (clojure.set/difference %1 %2))
    #{:a :b :c :d} #{:c :e :a :f :d}) #{:a :c :d})

;; #62: Re-implement Iterate
(= (take 5 ((fn iter [f x] (lazy-seq (cons x (iter f (f x)))))
            #(* 2 %) 1)) [1 2 4 8 16])
(= (take 100 ((fn iter [f x] (lazy-seq (cons x (iter f (f x)))))
              inc 0)) (take 100 [range]))
(= (take 9 ((fn iter [f x] (lazy-seq (cons x (iter f (f x)))))
            #(inc (mod % 3)) 1)) (take 9 (cycle [1 2 3])))

;; #107: Simple closures
(= 256 (((fn [x] (fn [y] (int (Math/pow y x)))) 2) 16)
   (((fn [x] (fn [y] (int (Math/pow y x)))) 8) 2))
(= [1 8 27 64] (map ((fn [x] (fn [y] (int (Math/pow y x)))) 3)
                    [1 2 3 4]))
(= [1 2 4 8 16] (map #(((fn [x] (fn [y] (int (Math/pow y x)))) %) 2)
                     [0 1 2 3 4]))

;; #99: Product Digits
(= (#(map read-string (map str (seq (str (* %1 %2))))) 1 1) [1])
(= (#(map read-string (map str (seq (str (* %1 %2))))) 99 9) [8 9 1])
(= (#(map read-string (map str (seq (str (* %1 %2))))) 999 99) [9 8 9 0 1])

;; #90: Cartesian Product
(= (#(into #{} (for [x %1 y %2] [x y]))
    #{"ace" "king" "queen"} #{"♠" "♥" "♦" "♣"})
   #{["ace"   "♠"] ["ace"   "♥"] ["ace"   "♦"] ["ace"   "♣"]
     ["king"  "♠"] ["king"  "♥"] ["king"  "♦"] ["king"  "♣"]
     ["queen" "♠"] ["queen" "♥"] ["queen" "♦"] ["queen" "♣"]})
(= (#(into #{} (for [x %1 y %2] [x y]))
    #{1 2 3} #{4 5})
   #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]})
(= 300 (count (#(into #{} (for [x %1 y %2] [x y]))
               (into #{} (range 10))
               (into #{} (range 30)))))

;; #63: Group a Sequence
(= ((fn [f v] (apply merge-with #(conj %1 (first %2)) (map #(hash-map (f %) (vector %)) v)))
    #(> % 5) [1 3 6 8]) {false [1 3], true [6 8]})
(= ((fn [f v] (apply merge-with #(conj %1 (first %2)) (map #(hash-map (f %) (vector %)) v)))
    #(apply / %) [[1 2] [2 4] [4 6] [3 6]])
   {1/2 [[1 2] [2 4] [3 6]], 2/3 [[4 6]]})
(= ((fn [f v] (apply merge-with #(conj %1 (first %2)) (map #(hash-map (f %) (vector %)) v)))
    count [[1] [1 2] [3] [1 2 3] [2 3]])
   {1 [[1] [3]], 2 [[1 2] [2 3]], 3 [[1 2 3]]})

;; #122: Read a binary number
(= 0 ((fn [s] (let [x (reverse (take (count (map char s)) (iterate #(* 2 %) 1)))]
                (reduce + (map #(if (= %1 \0) 0 %2) (map char s) x))))
      "0"))
(= 7 ((fn [s] (let [x (reverse (take (count (map char s)) (iterate #(* 2 %) 1)))]
                (reduce + (map #(if (= %1 \0) 0 %2) (map char s) x))))
      "111"))
(= 8 ((fn [s] (let [x (reverse (take (count (map char s)) (iterate #(* 2 %) 1)))]
                (reduce + (map #(if (= %1 \0) 0 %2) (map char s) x))))
      "1000"))
(= 9 ((fn [s] (let [x (reverse (take (count (map char s)) (iterate #(* 2 %) 1)))]
                (reduce + (map #(if (= %1 \0) 0 %2) (map char s) x))))
      "1001"))
(= 255 ((fn [s] (let [x (reverse (take (count (map char s)) (iterate #(* 2 %) 1)))]
                  (reduce + (map #(if (= %1 \0) 0 %2) (map char s) x))))
        "11111111"))
(= 1365 ((fn [s] (let [x (reverse (take (count (map char s)) (iterate #(* 2 %) 1)))]
                   (reduce + (map #(if (= %1 \0) 0 %2) (map char s) x))))
         "10101010101"))
(= 65535 ((fn [s] (let [x (reverse (take (count (map char s)) (iterate #(* 2 %) 1)))]
                    (reduce + (map #(if (= %1 \0) 0 %2) (map char s) x))))
          "1111111111111111"))

;; #88: Symmetric Difference
(= (#(clojure.set/union (clojure.set/difference %1 %2) (clojure.set/difference %2 %1))
    #{1 2 3 4 5 6} #{1 3 5 7}) #{2 4 6 7})
(= (#(clojure.set/union (clojure.set/difference %1 %2) (clojure.set/difference %2 %1))
    #{:a :b :c} #{}) #{:a :b :c})
(= (#(clojure.set/union (clojure.set/difference %1 %2) (clojure.set/difference %2 %1))
    #{} #{4 5 6}) #{4 5 6})
(= (#(clojure.set/union (clojure.set/difference %1 %2) (clojure.set/difference %2 %1))
    #{[1 2] [2 3]} #{[2 3] [3 4]}) #{[1 2] [3 4]})

;; #143: dot product
(= 0 (#(reduce + (map * %1 %2)) [0 1 0] [1 0 0]))
(= 3 (#(reduce + (map * %1 %2)) [1 1 1] [1 1 1]))
(= 32 (#(reduce + (map * %1 %2)) [1 2 3] [4 5 6]))
(= 256 (#(reduce + (map * %1 %2)) [2 5 6] [100 10 1]))

;; #126: Through the Looking Glass
(let [x Class]
  (and (= (class x) x) x))

;; #135: Infix Calculator
(= 7 ((fn infix [& rest]
        (if (< (count rest) 3) (first rest)
            ((nth rest (- (count rest) 2)) (apply infix (drop-last 2 rest)) (last rest))))
      2 + 5))
(= 42 ((fn infix [& rest]
         (if (< (count rest) 3) (first rest)
             ((nth rest (- (count rest) 2)) (apply infix (drop-last 2 rest)) (last rest))))
       38 + 48 - 2 / 2))
(= 8 ((fn infix [& rest]
        (if (< (count rest) 3) (first rest)
            ((nth rest (- (count rest) 2)) (apply infix (drop-last 2 rest)) (last rest))))
      10 / 2 - 1 * 2))
(= 72 ((fn infix [& rest]
         (if (< (count rest) 3) (first rest)
             ((nth rest (- (count rest) 2)) (apply infix (drop-last 2 rest)) (last rest))))
       20 / 2 + 2 + 4 + 8 - 6 - 10 * 9))

;; #97: Pascal's Triangle
(= ((fn [n]
      (last (take n (iterate
                     (fn [prev-row]
                       (concat [(first prev-row)]
                               (map #(apply + %) (partition 2 1 prev-row))
                               [(last prev-row)]))
                     [1]))))
    1) [1])
(= (map (fn [n]
          (last (take n (iterate
                         (fn [prev-row]
                           (concat [(first prev-row)]
                                   (map #(apply + %) (partition 2 1 prev-row))
                                   [(last prev-row)]))
                         [1]))))
        (range 1 6))
   [     [1]
         [1 1]
         [1 2 1]
         [1 3 3 1]
         [1 4 6 4 1]])
(= ((fn [n]
      (last (take n (iterate
                     (fn [prev-row]
                       (concat [(first prev-row)]
                               (map #(apply + %) (partition 2 1 prev-row))
                               [(last prev-row)]))
                     [1]))))
    11)
   [1 10 45 120 210 252 210 120 45 10 1])

;; #157: Indexing Sequences
(= (#(for [i (range (count %))] [(nth % i) i])
    [:a :b :c]) [[:a 0] [:b 1] [:c 2]])
(= (#(for [i (range (count %))] [(nth % i) i])
    [0 1 3]) '((0 0) (1 1) (3 2)))
(= (#(for [i (range (count %))] [(nth % i) i])
    [[:foo] {:bar :baz}]) [[[:foo] 0] [{:bar :baz} 1]])

;; #118: Re-implement Map
(= [3 4 5 6 7]
   ((fn m [f col]
      (lazy-seq
       (if-not (seq col) '()
               (cons (f (first col)) (m f (rest col))))))
    inc [2 3 4 5 6]))
(= (repeat 10 nil)
   ((fn m [f col]
      (lazy-seq
       (if-not (seq col) '()
               (cons (f (first col)) (m f (rest col))))))
    (fn [_] nil) (range 10)))
(= [1000000 1000001]
   (->> ((fn m [f col]
           (lazy-seq
            (if-not (seq col) '()
                    (cons (f (first col)) (m f (rest col))))))
         inc (range))
        (drop (dec 1000000))
        (take 2)))

;; #120: Sum of square of digits
(= 8 ((fn [col] (let [f (fn [x] (reduce + (map (comp #(* % %) read-string str) (str x))))]
                  (count (filter #(< % (f %)) col))))
      (range 10)))
(= 19 ((fn [col] (let [f (fn [x] (reduce + (map (comp #(* % %) read-string str) (str x))))]
                   (count (filter #(< % (f %)) col))))
       (range 30)))
(= 50 ((fn [col] (let [f (fn [x] (reduce + (map (comp #(* % %) read-string str) (str x))))]
                   (count (filter #(< % (f %)) col))))
       (range 100)))
(= 50 ((fn [col] (let [f (fn [x] (reduce + (map (comp #(* % %) read-string str) (str x))))]
                   (count (filter #(< % (f %)) col))))
       (range 1000)))

;; #95: To Tree, or not to Tree
(= ((fn tree? [col]
      (if-not (and (= (count col) 3) (every? #(not= false %) col)) false
              (every? true? (map #(if (sequential? %) (tree? %) true) col))))
    '(:a (:b nil nil) nil))
   true)
(= ((fn tree? [col]
      (if-not (and (= (count col) 3) (every? #(not= false %) col)) false
              (every? true? (map #(if (sequential? %) (tree? %) true) col))))
    '(:a (:b nil nil)))
   false)
(= ((fn tree? [col]
      (if-not (and (= (count col) 3) (every? #(not= false %) col)) false
              (every? true? (map #(if (sequential? %) (tree? %) true) col))))
    [1 nil [2 [3 nil nil] [4 nil nil]]])
   true)
(= ((fn tree? [col]
      (if-not (and (= (count col) 3) (every? #(not= false %) col)) false
              (every? true? (map #(if (sequential? %) (tree? %) true) col))))
    [1 [2 nil nil] [3 nil nil] [4 nil nil]])
   false)
(= ((fn tree? [col]
      (if-not (and (= (count col) 3) (every? #(not= false %) col)) false
              (every? true? (map #(if (sequential? %) (tree? %) true) col))))
    [1 [2 [3 [4 nil nil] nil] nil] nil])
   true)
(= ((fn tree? [col]
      (if-not (and (= (count col) 3) (every? #(not= false %) col)) false
              (every? true? (map #(if (sequential? %) (tree? %) true) col))))
    [1 [2 [3 [4 false nil] nil] nil] nil])
   false)
(= ((fn tree? [col]
      (if-not (and (= (count col) 3) (every? #(not= false %) col)) false
              (every? true? (map #(if (sequential? %) (tree? %) true) col))))
    '(:a nil ()))
   false)

;; #128: Recognize Playing Cards
(= {:suit :diamond :rank 10}
   ((fn [s] (let [suit #({\D :diamond \H :heart \C :club \S :spade} %)
                  rank #(if (re-find #"[A-Z]" (str %))
                          ({\T 8 \J 9 \Q 10 \K 11 \A 12} %)
                          (- (read-string (str %)) 2))]
              {:suit (suit (first s)) :rank (rank (second s))}))
    "DQ"))
(= {:suit :heart :rank 3}
   ((fn [s] (let [suit #({\D :diamond \H :heart \C :club \S :spade} %)
                  rank #(if (re-find #"[A-Z]" (str %))
                          ({\T 8 \J 9 \Q 10 \K 11 \A 12} %)
                          (- (read-string (str %)) 2))]
              {:suit (suit (first s)) :rank (rank (second s))}))
    "H5"))
(= {:suit :club :rank 12}
   ((fn [s] (let [suit #({\D :diamond \H :heart \C :club \S :spade} %)
                  rank #(if (re-find #"[A-Z]" (str %))
                          ({\T 8 \J 9 \Q 10 \K 11 \A 12} %)
                          (- (read-string (str %)) 2))]
              {:suit (suit (first s)) :rank (rank (second s))}))
    "CA"))
(= (range 13)
   (map (comp :rank (fn [s] (let [suit #({\D :diamond \H :heart \C :club \S :spade} %)
                                  rank #(if (re-find #"[A-Z]" (str %))
                                          ({\T 8 \J 9 \Q 10 \K 11 \A 12} %)
                                          (- (read-string (str %)) 2))]
                              {:suit (suit (first s)) :rank (rank (second s))}))
              str)
        '[S2 S3 S4 S5 S6 S7
          S8 S9 ST SJ SQ SK SA]))

;; #100: Least Common Multiple
(== ((fn [& rest]
       (let [x          (apply min rest)
             divisible? #(zero? (rem %1 %2))]
         (first (take 1 (filter #(every? (fn [n] (divisible? % n)) rest)
                                (iterate (partial + x) x))))))
     2 3) 6)
(== ((fn [& rest]
       (let [x          (apply min rest)
             divisible? #(zero? (rem %1 %2))]
         (first (take 1 (filter #(every? (fn [n] (divisible? % n)) rest)
                                (iterate (partial + x) x))))))
     5 3 7) 105)
(== ((fn [& rest]
       (let [x          (apply min rest)
             divisible? #(zero? (rem %1 %2))]
         (first (take 1 (filter #(every? (fn [n] (divisible? % n)) rest)
                                (iterate (partial + x) x))))))
     1/3 2/5) 2)
(== ((fn [& rest]
       (let [x          (apply min rest)
             divisible? #(zero? (rem %1 %2))]
         (first (take 1 (filter #(every? (fn [n] (divisible? % n)) rest)
                                (iterate (partial + x) x))))))
     3/4 1/6) 3/2)
(== ((fn [& rest]
       (let [x          (apply min rest)
             divisible? #(zero? (rem %1 %2))]
         (first (take 1 (filter #(every? (fn [n] (divisible? % n)) rest)
                                (iterate (partial + x) x))))))
     7 5/7 2 3/5) 210)

;; #147: Pascal's Trapezoid
(= (second ((fn [col]
              (iterate
               (fn [prev-row]
                 (concat [(first prev-row)]
                         (map #(apply +' %) (partition 2 1 prev-row))
                         [(last prev-row)])) col))
            [2 3 2])) [2 5 5 2])
(= (take 5 ((fn [col]
              (iterate
               (fn [prev-row]
                 (concat [(first prev-row)]
                         (map #(apply +' %) (partition 2 1 prev-row))
                         [(last prev-row)])) col))
            [1])) [[1] [1 1] [1 2 1] [1 3 3 1] [1 4 6 4 1]])
(= (take 2 ((fn [col]
              (iterate
               (fn [prev-row]
                 (concat [(first prev-row)]
                         (map #(apply +' %) (partition 2 1 prev-row))
                         [(last prev-row)])) col))
            [3 1 2])) [[3 1 2] [3 4 3 2]])
(= (take 100 ((fn [col]
                (iterate
                 (fn [prev-row]
                   (concat [(first prev-row)]
                           (map #(apply +' %) (partition 2 1 prev-row))
                           [(last prev-row)])) col))
              [2 4 2]))
   (rest (take 101 ((fn [col]
                      (iterate
                       (fn [prev-row]
                         (concat [(first prev-row)]
                                 (map #(apply +' %) (partition 2 1 prev-row))
                                 [(last prev-row)])) col))
                    [2 2]))))

;; #173: Intro to Destructuring 2
(= 3
   (let [[x y] [+ (range 3)]] (apply x y))
   (let [[[x y] b] [[+ 1] 2]] (x y b))
   (let [[x y] [inc 2]] (x y)))

;; #96: Beauty is Symmetry
(= ((fn symmetric? [tree]
      (let [left (second tree)
            right (last tree)]
        (if (not= (first left) (first right)) false
            (if (sequential? left)
              (and (symmetric? (list nil (second left) (last right)))
                   (symmetric? (list nil (last left) (second right))))
              true))))
    '(:a (:b nil nil) (:b nil nil))) true)
(= ((fn symmetric? [tree]
      (let [left (second tree)
            right (last tree)]
        (if (not= (first left) (first right)) false
            (if (sequential? left)
              (and (symmetric? (list nil (second left) (last right)))
                   (symmetric? (list nil (last left) (second right))))
              true))))
    '(:a (:b nil nil) nil)) false)
(= ((fn symmetric? [tree]
      (let [left (second tree)
            right (last tree)]
        (if (not= (first left) (first right)) false
            (if (sequential? left)
              (and (symmetric? (list nil (second left) (last right)))
                   (symmetric? (list nil (last left) (second right))))
              true))))
    '(:a (:b nil nil) (:c nil nil))) false)
(= ((fn symmetric? [tree]
      (let [left (second tree)
            right (last tree)]
        (if (not= (first left) (first right)) false
            (if (sequential? left)
              (and (symmetric? (list nil (second left) (last right)))
                   (symmetric? (list nil (last left) (second right))))
              true))))
    [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
     [2 [3 nil [4 [6 nil nil] [5 nil nil]]] nil]])
   true)
(= ((fn symmetric? [tree]
      (let [left (second tree)
            right (last tree)]
        (if (not= (first left) (first right)) false
            (if (sequential? left)
              (and (symmetric? (list nil (second left) (last right)))
                   (symmetric? (list nil (last left) (second right))))
              true))))
    [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
     [2 [3 nil [4 [5 nil nil] [6 nil nil]]] nil]])
   false)
(= ((fn symmetric? [tree]
      (let [left (second tree)
            right (last tree)]
        (if (not= (first left) (first right)) false
            (if (sequential? left)
              (and (symmetric? (list nil (second left) (last right)))
                   (symmetric? (list nil (last left) (second right))))
              true))))
    [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
     [2 [3 nil [4 [6 nil nil] nil]] nil]])
   false)

;; #146: Trees into table
(= (#(into {} (for [[k1 v1] %
                    [k2 v2] v1]
                [[k1 k2] v2]))
    '{a {p 1, q 2}
      b {m 3, n 4}})
   '{[a p] 1, [a q] 2
     [b m] 3, [b n] 4})
(= (#(into {} (for [[k1 v1] %
                    [k2 v2] v1]
                [[k1 k2] v2]))
    '{[1] {a b c d}
      [2] {q r s t u v w x}})
   '{[[1] a] b, [[1] c] d,
     [[2] q] r, [[2] s] t,
     [[2] u] v, [[2] w] x})
(= (#(into {} (for [[k1 v1] %
                    [k2 v2] v1]
                [[k1 k2] v2]))
    '{m {1 [a b c] 3 nil}})
   '{[m 1] [a b c], [m 3] nil})

;; #153: Pairwise Disjoint Sets
(= (#(let [all-items  (for [s % e s] e)
           dup-counts (for [[id freq] (frequencies all-items)
                            :when (> freq 1)]
                        id)]
       (zero? (count dup-counts)))
    #{#{\U} #{\s} #{\e \R \E} #{\P \L} #{\.}})
   true)
(= (#(let [all-items  (for [s % e s] e)
           dup-counts (for [[id freq] (frequencies all-items)
                            :when (> freq 1)]
                        id)]
       (zero? (count dup-counts)))
    #{#{:a :b :c :d :e}
      #{:a :b :c :d}
      #{:a :b :c}
      #{:a :b}
      #{:a}})
   false)
(= (#(let [all-items  (for [s % e s] e)
           dup-counts (for [[id freq] (frequencies all-items)
                            :when (> freq 1)]
                        id)]
       (zero? (count dup-counts)))
    #{#{[1 2 3] [4 5]}
      #{[1 2] [3 4 5]}
      #{[1] [2] 3 4 5}
      #{1 2 [3 4] [5]}})
   true)
(= (#(let [all-items  (for [s % e s] e)
           dup-counts (for [[id freq] (frequencies all-items)
                            :when (> freq 1)]
                        id)]
       (zero? (count dup-counts)))
    #{#{'a 'b}
      #{'c 'd 'e}
      #{'f 'g 'h 'i}
      #{''a ''c ''f}})
   true)
(= (#(let [all-items  (for [s % e s] e)
           dup-counts (for [[id freq] (frequencies all-items)
                            :when (> freq 1)]
                        id)]
       (zero? (count dup-counts)))
    #{#{'(:x :y :z) '(:x :y) '(:z) '()}
      #{#{:x :y :z} #{:x :y} #{:z} #{}}
      #{'[:x :y :z] [:x :y] [:z] [] {}}})
   false)
(= (#(let [all-items  (for [s % e s] e)
           dup-counts (for [[id freq] (frequencies all-items)
                            :when (> freq 1)]
                        id)]
       (zero? (count dup-counts)))
    #{#{(= "true") false}
      #{:yes :no}
      #{(class 1) 0}
      #{(symbol "true") 'false}
      #{(keyword "yes") ::no}
      #{(class '1) (int \0)}})
   false)
(= (#(let [all-items  (for [s % e s] e)
           dup-counts (for [[id freq] (frequencies all-items)
                            :when (> freq 1)]
                        id)]
       (zero? (count dup-counts)))
    #{#{distinct?}
      #{#(-> %) #(-> %)}
      #{#(-> %) #(-> %) #(-> %)}
      #{#(-> %) #(-> %) #(-> %)}})
   true)
(= (#(let [all-items  (for [s % e s] e)
           dup-counts (for [[id freq] (frequencies all-items)
                            :when (> freq 1)]
                        id)]
       (zero? (count dup-counts)))
    #{#{(#(-> *)) + (quote mapcat) #_ nil}
      #{'+ '* mapcat (comment mapcat)}
      #{(do) set contains? nil?}
      #{, , , #_, , empty?}})
   false)

;;; Medium

;; #46: Flipping out
(= 3 (((fn [f] #(f %2 %1)) nth) 2 [1 2 3 4 5]))
(= true (((fn [f] #(f %2 %1)) >) 7 8))
(= 4 (((fn [f] #(f %2 %1)) quot) 2 8))
(= [1 2 3] (((fn [f] #(f %2 %1)) take) [1 2 3 4 5] 3))

;; #44: Rotate Sequence
(= (#(let [n (mod %1 (count %2))] (concat (drop n %2) (take n %2)))
    2 [1 2 3 4 5]) '(3 4 5 1 2))
(= (#(let [n (mod %1 (count %2))] (concat (drop n %2) (take n %2)))
    -2 [1 2 3 4 5]) '(4 5 1 2 3))
(= (#(let [n (mod %1 (count %2))] (concat (drop n %2) (take n %2)))
    6 [1 2 3 4 5]) '(2 3 4 5 1))
(= (#(let [n (mod %1 (count %2))] (concat (drop n %2) (take n %2)))
    1 '(:a :b :c)) '(:b :c :a))
(= (#(let [n (mod %1 (count %2))] (concat (drop n %2) (take n %2)))
    -4 '(:a :b :c)) '(:c :a :b))

;; #43: Reverse Interleave
(= (#(apply map list (partition %2 %1))
    [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6)))
(= (#(apply map list (partition %2 %1))
    (range 9) 3) '((0 3 6) (1 4 7) (2 5 8)))
(= (#(apply map list (partition %2 %1))
    (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9)))

;; #50: Split by Type
(= (set (#(partition-by (comp str type) (sort-by (comp str type) %))
         [1 :a 2 :b 3 :c])) #{[1 2 3] [:a :b :c]})
(= (set (#(partition-by (comp str type) (sort-by (comp str type) %))
         [:a "foo" "bar" :b])) #{[:a :b] ["foo" "bar"]})
(= (set (#(partition-by (comp str type) (sort-by (comp str type) %))
         [[1 2] :a [3 4] 5 6 :b])) #{[[1 2] [3 4]] [:a :b] [5 6]})

;; #55: Count Occurrences
(= ((fn [col] (reduce #(update-in %1 [%2] inc)
                      (reduce #(assoc %1 %2 0) {} (set col)) col))
    [1 1 2 3 2 1 1]) {1 4, 2 2, 3 1})
(= ((fn [col] (reduce #(update-in %1 [%2] inc)
                      (reduce #(assoc %1 %2 0) {} (set col)) col))
    [:b :a :b :a :b]) {:a 2, :b 3})
(= ((fn [col] (reduce #(update-in %1 [%2] inc)
                      (reduce #(assoc %1 %2 0) {} (set col)) col))
    '([1 2] [1 3] [1 3])) {[1 2] 1, [1 3] 2})

;; #56: Find Distinct Items
(= ((fn f [col]
      (if (empty? col) []
          (let [es (f (butlast col))
                e  (last col)]
            (if (some #(= e %) es) es
                (conj es e)))))
    [1 2 1 3 1 2 4]) [1 2 3 4])
(= ((fn f [col]
      (if (empty? col) []
          (let [es (f (butlast col))
                e  (last col)]
            (if (some #(= e %) es) es
                (conj es e)))))
    [:a :a :b :b :c :c]) [:a :b :c])
(= ((fn f [col]
      (if (empty? col) []
          (let [es (f (butlast col))
                e  (last col)]
            (if (some #(= e %) es) es
                (conj es e)))))
    '([2 4] [1 2] [1 3] [1 3])) '([2 4] [1 2] [1 3]))
(= ((fn f [col]
      (if (empty? col) []
          (let [es (f (butlast col))
                e  (last col)]
            (if (some #(= e %) es) es
                (conj es e)))))
    (range 50)) (range 50))

;; #58: Function Composition
(= [3 2 1] (((fn [& fs]
               (let [fs (reverse fs)]
                 #(loop [inner (apply (first fs) %&)
                         fs    (next fs)]
                    (if fs
                      (recur ((first fs) inner) (next fs))
                      inner))))
             rest reverse) [1 2 3 4]))
(= 5 (((fn [& fs]
         (let [fs (reverse fs)]
           (loop [inner (apply (first fs) %&)
                  fs    (next fs)]
             (if fs
               (recur ((first fs) inner) (next fs))
               inner))))
       (partial + 3) second) [1 2 3 4]))
(= true (((fn [& fs]
            (let [fs (reverse fs)]
              #(loop [inner (apply (first fs) %&)
                      fs    (next fs)]
                 (if fs
                   (recur ((first fs) inner) (next fs))
                   inner))))
          zero? #(mod % 8) +) 3 5 7 9))
(= "HELLO" (((fn [& fs]
               (let [fs (reverse fs)]
                 #(loop [inner (apply (first fs) %&)
                         fs    (next fs)]
                    (if fs
                      (recur ((first fs) inner) (next fs))
                      inner))))
             #(.toUpperCase %) #(apply str %) take) 5 "hello world"))

;; #59: Juxtaposition
(= [21 6 1] (((fn [& fs]
                #(for [f fs] (apply f %&)))
              + max min) 2 3 5 1 6 4))
(= ["HELLO" 5] (((fn [& fs]
                   #(for [f fs] (apply f %&)))
                 #(.toUpperCase %) count) "hello"))
(= [2 6 4] (((fn [& fs]
               #(for [f fs] (apply f %&)))
             :a :c :b) {:a 2, :b 4, :c 6, :d 8, :e 10}))

;; #54: Partition a Sequence
(= ((fn my-part [n col]
      (if (< (count col) (* 2 n)) (list (take n col))
          (conj (my-part n (drop n col)) (take n col))))
    3 (range 9)) '((0 1 2) (3 4 5) (6 7 8)))
(= ((fn my-part [n col]
      (if (< (count col) (* 2 n)) (list (take n col))
          (conj (my-part n (drop n col)) (take n col))))
    2 (range 8)) '((0 1) (2 3) (4 5) (6 7)))
(= ((fn my-part [n col]
      (if (< (count col) (* 2 n)) (list (take n col))
          (conj (my-part n (drop n col)) (take n col))))
    3 (range 8)) '((0 1 2) (3 4 5)))

;; #70: Word Sorting
(= ((fn [s]
      (->> s (partition-by #(= \space %))
           (filter #(not= \space (first %)))
           (map (comp (partial re-find #"\w+") (partial apply str)))
           (sort-by #(.toLowerCase %))))
    "Have a nice day.")
   ["a" "day" "Have" "nice"])
(= ((fn [s]
      (->> s (partition-by #(= \space %))
           (filter #(not= \space (first %)))
           (map (comp (partial re-find #"\w+") (partial apply str)))
           (sort-by #(.toLowerCase %))))
    "Clojure is a fun language!")
   ["a" "Clojure" "fun" "is" "language"])
(= ((fn [s]
      (->> s (partition-by #(= \space %))
           (filter #(not= \space (first %)))
           (map (comp (partial re-find #"\w+") (partial apply str)))
           (sort-by #(.toLowerCase %))))
    "Fools fall for foolish follies.")
   ["fall" "follies" "foolish" "Fools" "for"])

;; #67: Prime Numbers
(= ((fn [n]
      (let [prime? (fn [x]
                     (or (< x 3)
                         (not= 0 (reduce * (map #(mod x %)
                                                (range 2 (+ 1 (Math/sqrt x))))))))]
        (take n (for [x (iterate inc 2)
                      :when (prime? x)] x))))
    2) [2 3])
(= ((fn [n]
      (let [prime? (fn [x]
                     (or (< x 3)
                         (not= 0 (reduce * (map #(mod x %)
                                                (range 2 (+ 1 (Math/sqrt x))))))))]
        (take n (for [x (iterate inc 2)
                      :when (prime? x)] x))))
    5) [2 3 5 7 11])
(= (last ((fn [n]
            (let [prime? (fn [x]
                           (or (< x 3)
                               (not= 0 (reduce * (map #(mod x %)
                                                      (range 2 (+ 1 (Math/sqrt x))))))))]
              (take n (for [x (iterate inc 2)
                            :when (prime? x)] x))))
          100)) 541)

;; #74: Filter Perfect Squares
(= ((fn [s]
      (->> s
           (partition-by #(= \, %))
           (filter #(not= \, (first %)))
           (map (comp read-string #(apply str %)))
           (filter #(zero? (mod % (Math/sqrt %))))
           (map str)
           (interpose ",")
           (apply str)))
    "4,5,6,7,8,9") "4,9")
(= ((fn [s]
      (->> s
           (partition-by #(= \, %))
           (filter #(not= \, (first %)))
           (map (comp read-string #(apply str %)))
           (filter #(zero? (mod % (Math/sqrt %))))
           (map str)
           (interpose ",")
           (apply str)))
    "15,16,25,36,37") "16,25,36")

;; #65: Black Box Testing
(= :map ((fn [col]
           (let [[x y c] [(gensym) (gensym) (count col)]]
             (if (= (+ c 2) (count (conj col [x x] [x x])))
               (if (= y (first (conj col x y))) :list :vector)
               (if (= (+ 1 c) (count (conj col [x 1] [x 2]))) :map :set))))
         {:a 1, :b 2}))
(= :list ((fn [col]
            (let [[x y c] [(gensym) (gensym) (count col)]]
              (if (= (+ c 2) (count (conj col [x x] [x x])))
                (if (= y (first (conj col x y))) :list :vector)
                (if (= (+ 1 c) (count (conj col [x 1] [x 2]))) :map :set))))
          (range (rand-int 20))))
(= :vector ((fn [col]
              (let [[x y c] [(gensym) (gensym) (count col)]]
                (if (= (+ c 2) (count (conj col [x x] [x x])))
                  (if (= y (first (conj col x y))) :list :vector)
                  (if (= (+ 1 c) (count (conj col [x 1] [x 2]))) :map :set))))
            [1 2 3 4 5 6]))
(= :set ((fn [col]
           (let [[x y c] [(gensym) (gensym) (count col)]]
             (if (= (+ c 2) (count (conj col [x x] [x x])))
               (if (= y (first (conj col x y))) :list :vector)
               (if (= (+ 1 c) (count (conj col [x 1] [x 2]))) :map :set))))
         #{10 (rand-int 5)}))
(= [:map :set :vector :list]
   (map (fn [col]
          (let [[x y c] [(gensym) (gensym) (count col)]]
            (if (= (+ c 2) (count (conj col [x x] [x x])))
              (if (= y (first (conj col x y))) :list :vector)
              (if (= (+ 1 c) (count (conj col [x 1] [x 2]))) :map :set))))
        [{} #{} [] ()]))

;; #76: Intro to Trampoline
(= [1 3 5 7 9 11]
   (letfn
       [(foo [x y] #(bar (conj x y) y))
        (bar [x y] (if (> (last x) 10)
                     x
                     #(foo x (+ 2 y))))]
     (trampoline foo [] 1)))

;; #80: Perfect Numbers
(= ((fn [x] (= (reduce + (filter #(zero? (mod x %))
                                 (map inc (range (int (/ x 2)))))) x))
    6) true)
(= ((fn [x] (= (reduce + (filter #(zero? (mod x %))
                                 (map inc (range (int (/ x 2)))))) x))
    7) false)
(= ((fn [x] (= (reduce + (filter #(zero? (mod x %))
                                 (map inc (range (int (/ x 2)))))) x))
    496) true)
(= ((fn [x] (= (reduce + (filter #(zero? (mod x %))
                                 (map inc (range (int (/ x 2)))))) x))
    500) false)
(= ((fn [x] (= (reduce + (filter #(zero? (mod x %))
                                 (map inc (range (int (/ x 2)))))) x))
    8128) true)

;; #77: Anagram Finder
(= ((fn [col] (set (filter #(> (count %) 1)
                           (for [s (set (map set col))]
                             (set (filter #(= s (set %)) col))))))
    ["meat" "mat" "team" "mate" "eat"])
   #{#{"meat" "team" "mate"}})
(= ((fn [col] (set (filter #(> (count %) 1)
                           (for [s (set (map set col))]
                             (set (filter #(= s (set %)) col))))))
    ["veer" "lake" "item" "kale" "mite" "ever"])
   #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}})

;; #60: Sequence Reductions
(= (take 5 ((fn f [g & args]
              (let [[i coll] args
                    [i coll] (if (nil? coll) [(first i) (rest i)] [i coll])]
                (if (empty? coll) [i]
                    (lazy-seq (cons i (f g (g i (first coll)) (rest coll)))))))
            + (range))) [0 1 3 6 10])
(= ((fn f [g & args]
      (let [[i coll] args
            [i coll] (if (nil? coll) [(first i) (rest i)] [i coll])]
        (if (empty? coll) [i]
            (lazy-seq (cons i (f g (g i (first coll)) (rest coll)))))))
    conj [1] [2 3 4]) [[1] [1 2] [1 2 3] [1 2 3 4]])
(= (last ((fn f [g & args]
            (let [[i coll] args
                  [i coll] (if (nil? coll) [(first i) (rest i)] [i coll])]
              (if (empty? coll) [i]
                  (lazy-seq (cons i (f g (g i (first coll)) (rest coll)))))))
          * 2 [3 4 5])) (reduce * 2 [3 4 5]) 120)


;; #69: Merge with a Function
(= ((fn [f & maps]
      (letfn [(m-w [m1 m2]
                (reduce (fn [m [k v]]
                          (assoc m k
                                 (if (nil? (get m k)) v
                                     (f (get m k) v))))
                        m1 m2))]
        (reduce m-w maps)))
    * {:a 2, :b 3, :c 4} {:a 2} {:b 2} {:c 5}))
(= ((fn [f & maps]
      (letfn [(m-w [m1 m2]
                (reduce (fn [m [k v]]
                          (assoc m k
                                 (if (nil? (get m k)) v
                                     (f (get m k) v))))
                        m1 m2))]
        (reduce m-w maps)))
    - {1 10, 2 20} {1 3, 2 10, 3 15})
   {1 7, 2 10, 3 15})
(= ((fn [f & maps]
      (letfn [(m-w [m1 m2]
                (reduce (fn [m [k v]]
                          (assoc m k
                                 (if (nil? (get m k)) v
                                     (f (get m k) v))))
                        m1 m2))]
        (reduce m-w maps)))
    concat {:a [3], :b [6]} {:a [4 5], :c [8 9]}, {:b [7]})
   {:a [3 4 5], :b [6 7], :c [8 9]})

;; #102: intoCamelCase
(= ((fn [s]
      (let [[h & t] (clojure.string/split s #"-")]
        (clojure.string/join
         (conj (map clojure.string/capitalize t) h)))) "something") "something")
(= ((fn [s]
      (let [[h & t] (clojure.string/split s #"-")]
        (clojure.string/join
         (conj (map clojure.string/capitalize t) h)))) "multi-word-key") "multiWordKey")
(= ((fn [s]
      (let [[h & t] (clojure.string/split s #"-")]
        (clojure.string/join
         (conj (map clojure.string/capitalize t) h)))) "leaveMeAlone") "leaveMeAlone")

;; #75: Euler's Totient Function
(= ((fn [x]
      (letfn [(gcd [x y] (if (= y 0) x (gcd y (mod x y))))]
        (->> (rest (range (inc x)))
             (filter #(= (gcd % x) 1))
             (count))))
    1) 1)
(= ((fn [x]
      (letfn [(gcd [x y] (if (= y 0) x (gcd y (mod x y))))]
        (->> (rest (range (inc x)))
             (filter #(= (gcd % x) 1))
             (count))))
    10) (count '(1 3 7 9)) 4)
(= ((fn [x]
      (letfn [(gcd [x y] (if (= y 0) x (gcd y (mod x y))))]
        (->> (rest (range (inc x)))
             (filter #(= (gcd % x) 1))
             (count))))
    40) 16)
(= ((fn [x]
      (letfn [(gcd [x y] (if (= y 0) x (gcd y (mod x y))))]
        (->> (rest (range (inc x)))
             (filter #(= (gcd % x) 1))
             (count))))
    99) 60)

;; #86: Happy numbers
(= ((fn f [n & [coll]]
      (if (some #(= % n) (rest coll))
        false
        (let [x (->> (str n) (map str)
                     (map read-string) (map #(* % %)) (apply +))]
          (if (= x 1) true
              (f x (cons x coll)))))) 7) true)
(= ((fn f [n & [coll]]
      (if (some #(= % n) (rest coll))
        false
        (let [x (->> (str n) (map str)
                     (map read-string) (map #(* % %)) (apply +))]
          (if (= x 1) true
              (f x (cons x coll)))))) 986543210) true)
(= ((fn f [n & [coll]]
      (if (some #(= % n) (rest coll))
        false
        (let [x (->> (str n) (map str)
                     (map read-string) (map #(* % %)) (apply +))]
          (if (= x 1) true
              (f x (cons x coll)))))) 2) false)
(= ((fn f [n & [coll]]
      (if (some #(= % n) (rest coll))
        false
        (let [x (->> (str n) (map str)
                     (map read-string) (map #(* % %)) (apply +))]
          (if (= x 1) true
              (f x (cons x coll)))))) 3) false)

;; #78: Reimplement Trampoline
(= (letfn [(triple [x] #(sub-two (* 3 x)))
           (sub-two [x] #(stop?(- x 2)))
           (stop? [x] (if (> x 50) x #(triple x)))]
     ((fn t [f & args] (if (fn? f) (t (apply f args)) f)) triple 2))
   82)
(= (letfn [(my-even? [x] (if (zero? x) true #(my-odd? (dec x))))
           (my-odd? [x] (if (zero? x) false #(my-even? (dec x))))]
     (map (partial (fn t [f & args] (if (fn? f) (t (apply f args)) f)) my-even?) (range 6)))
   [true false true false true false])

;; #115: The Balance of N
(= true ((fn [n]
           (let [n (str n)
                 x (Math/floor (/ (count n) 2))
                 ns [(take x n) (take x (reverse n))]]
             (->> (for [cs ns] (map #(read-string (str %)) cs))
                  (map #(apply + %))
                  (apply =)))) 11))
(= true ((fn [n]
           (let [n (str n)
                 x (Math/floor (/ (count n) 2))
                 ns [(take x n) (take x (reverse n))]]
             (->> (for [cs ns] (map #(read-string (str %)) cs))
                  (map #(apply + %))
                  (apply =)))) 121))
(= false ((fn [n]
            (let [n (str n)
                  x (Math/floor (/ (count n) 2))
                  ns [(take x n) (take x (reverse n))]]
              (->> (for [cs ns] (map #(read-string (str %)) cs))
                   (map #(apply + %))
                   (apply =)))) 123))
(= true ((fn [n]
           (let [n (str n)
                 x (Math/floor (/ (count n) 2))
                 ns [(take x n) (take x (reverse n))]]
             (->> (for [cs ns] (map #(read-string (str %)) cs))
                  (map #(apply + %))
                  (apply =)))) 0))
(= false ((fn [n]
            (let [n (str n)
                  x (Math/floor (/ (count n) 2))
                  ns [(take x n) (take x (reverse n))]]
              (->> (for [cs ns] (map #(read-string (str %)) cs))
                   (map #(apply + %))
                   (apply =)))) 88099))
(= true ((fn [n]
           (let [n (str n)
                 x (Math/floor (/ (count n) 2))
                 ns [(take x n) (take x (reverse n))]]
             (->> (for [cs ns] (map #(read-string (str %)) cs))
                  (map #(apply + %))
                  (apply =)))) 89098))
(= true ((fn [n]
           (let [n (str n)
                 x (Math/floor (/ (count n) 2))
                 ns [(take x n) (take x (reverse n))]]
             (->> (for [cs ns] (map #(read-string (str %)) cs))
                  (map #(apply + %))
                  (apply =)))) 89089))
(= (take 20 (filter (fn [n]
                      (let [n (str n)
                            x (Math/floor (/ (count n) 2))
                            ns [(take x n) (take x (reverse n))]]
                        (->> (for [cs ns] (map #(read-string (str %)) cs))
                             (map #(apply + %))
                             (apply =)))) (range)))
   [0 1 2 3 4 5 6 7 8 9 11 22 33 44 55 66 77 88 99 101])

;; #85: Power Set
;; NOTE: Last test works on local REPL, but site says it times out.  Maybe come
;; up with a non-recursive solution to this.
(= ((fn ps [s]
      (if (empty? s) #{s}
          (reduce #(into %1 %2) #{s}
                  (for [i s]
                    (ps (disj s i))))))
    #{1 :a}) #{#{1 :a} #{:a} #{} #{1}})
(= ((fn ps [s]
      (if (empty? s) #{s}
          (reduce #(into %1 %2) #{s}
                  (for [i s]
                    (ps (disj s i))))))
    #{}) #{#{}})
(= ((fn ps [s]
      (if (empty? s) #{s}
          (reduce #(into %1 %2) #{s}
                  (for [i s]
                    (ps (disj s i))))))
    #{1 2 3})
   #{#{} #{1} #{2} #{3} #{1 2} #{1 3} #{2 3} #{1 2 3}})
(= (count ((fn ps [s]
             (if (empty? s) #{s}
                 (reduce #(into %1 %2) #{s}
                         (for [i s]
                           (ps (disj s i))))))
           (into #{} (range 10)))) 1024)

;; #98: Equivalence classes
(= ((fn [f coll]
      (->> (group-by f coll)
           (map second)
           (map #(into #{} %))
           (into #{}))) #(* % %) #{-2 -1 0 1 2})
   #{#{0} #{1 -1} #{2 -2}})
(= ((fn [f coll]
      (->> (group-by f coll)
           (map second)
           (map #(into #{} %))
           (into #{}))) #(rem % 3) #{0 1 2 3 4 5 })
   #{#{0 3} #{1 4} #{2 5}})
(= ((fn [f coll]
      (->> (group-by f coll)
           (map second)
           (map #(into #{} %))
           (into #{}))) identity #{0 1 2 3 4})
   #{#{0} #{1} #{2} #{3} #{4}})
(= ((fn [f coll]
      (->> (group-by f coll)
           (map second)
           (map #(into #{} %))
           (into #{}))) (constantly true) #{0 1 2 3 4})
   #{#{0 1 2 3 4}})

;; #105: Identify keys and values
(= {}
   ((fn f [coll]
      (if (empty? coll) {}
          (let [[is r] (split-with number? (rest coll))]
            (into {(first coll) is} (f r)))))
    []))
(= {:a [1]}
   ((fn f [coll]
      (if (empty? coll) {}
          (let [[is r] (split-with number? (rest coll))]
            (into {(first coll) is} (f r)))))
    [:a 1]))
(= {:a [1], :b [2]}
   ((fn f [coll]
      (if (empty? coll) {}
          (let [[is r] (split-with number? (rest coll))]
            (into {(first coll) is} (f r)))))
    [:a 1, :b 2]))
(= {:a [1 2 3], :b [], :c [4]}
   ((fn f [coll]
      (if (empty? coll) {}
          (let [[is r] (split-with number? (rest coll))]
            (into {(first coll) is} (f r)))))
    [:a 1 2 3 :b :c 4]))

;; #137: Digits and bases
(= [1 2 3 4 5 0 1]
   ((fn f [n b]
      (if (< n 2) [n]
          (let [nr (int (/ n b))]
            (conj (if (zero? nr) [] (f nr b))
                  (mod n b))))) 1234501 10))
(= [0] ((fn f [n b]
          (if (< n 2) [n]
              (let [nr (int (/ n b))]
                (conj (if (zero? nr) [] (f nr b))
                      (mod n b))))) 0 11))
(= [1 0 0 1] ((fn f [n b]
                (if (< n 2) [n]
                    (let [nr (int (/ n b))]
                      (conj (if (zero? nr) [] (f nr b))
                            (mod n b))))) 9 2))
(= [1 0] (let [n (rand-int 100000)]
           ((fn f [n b]
              (if (< n 2) [n]
                  (let [nr (int (/ n b))]
                    (conj (if (zero? nr) [] (f nr b))
                          (mod n b))))) n n)))
(= [16 18 5 24 15 1]
   ((fn f [n b]
      (if (< n 2) [n]
          (let [nr (int (/ n b))]
            (conj (if (zero? nr) [] (f nr b))
                  (mod n b))))) Integer/MAX_VALUE 42))

;; #144: Oscilrate
(= (take 3 ((fn f [x & fs]
              (let [y ((first fs) x)]
                (lazy-seq (concat [x] (apply (partial f y)
                                             (conj (vec (rest fs))
                                                   (first fs))))))) 3.14 int double))
   [3.14 3 3.0])
(= (take 5 ((fn f [x & fs]
              (let [y ((first fs) x)]
                (lazy-seq (concat [x] (apply (partial f y)
                                             (conj (vec (rest fs))
                                                   (first fs))))))) 3 #(- % 3) #(+ 5 %)))
   [3 0 5 2 7])
(= (take 12 ((fn f [x & fs]
               (let [y ((first fs) x)]
                 (lazy-seq (concat [x] (apply (partial f y)
                                              (conj (vec (rest fs))
                                                    (first fs))))))) 0 inc dec inc dec inc))
   [0 1 0 1 0 1 2 1 2 1 2 3])

;; #110: Sequence of pronunciations
(= [[1 1] [2 1] [1 2 1 1]]
   (take 3 ((fn f [coll]
              (letfn [(p [coll]
                        (if (empty? coll) coll
                            (let [x (first coll)
                                  c (count (take-while #(= x %) coll))]
                              (concat [c x] (p (drop c coll))))))]
                (rest (iterate p coll)))) [1])))
(= [3 1 2 4]
   (first ((fn f [coll]
             (letfn [(p [coll]
                       (if (empty? coll) coll
                           (let [x (first coll)
                                 c (count (take-while #(= x %) coll))]
                             (concat [c x] (p (drop c coll))))))]
               (rest (iterate p coll)))) [1 1 1 4 4])))
(= [1 1 1 3 2 1 3 2 1 1]
   (nth ((fn f [coll]
           (letfn [(p [coll]
                     (if (empty? coll) coll
                         (let [x (first coll)
                               c (count (take-while #(= x %) coll))]
                           (concat [c x] (p (drop c coll))))))]
             (rest (iterate p coll)))) [1]) 6))
(= 338 (count (nth ((fn f [coll]
                      (letfn [(p [coll]
                                (if (empty? coll) coll
                                    (let [x (first coll)
                                          c (count (take-while #(= x %) coll))]
                                      (concat [c x] (p (drop c coll))))))]
                        (rest (iterate p coll)))) [3 2]) 15)))

;; #158: Decurry
(= 10 (((fn g [f]
          (fn h [& args]
            (if (= (count args) 1) (f (first args))
                ((apply h (drop-last args)) (last args)))))
        (fn [a]
          (fn [b]
            (fn [c]
              (fn [d]
                (+ a b c d))))))
       1 2 3 4))
(= 24 (((fn g [f]
          (fn h [& args]
            (if (= (count args) 1) (f (first args))
                ((apply h (drop-last args)) (last args)))))
        (fn [a]
          (fn [b]
            (fn [c]
              (fn [d]
                (* a b c d))))))
       1 2 3 4))
(= 25 (((fn g [f]
          (fn h [& args]
            (if (= (count args) 1) (f (first args))
                ((apply h (drop-last args)) (last args)))))
        (fn [a]
          (fn [b]
            (* a b))))
       5 5))

;; #108: Lazy Searching
(def a108
  (fn f [& args]
    (letfn [(match [as]
              (apply clojure.set/intersection (map #(into #{} %) as)))
            (idx-min [col] (.indexOf col (apply min col)))]
      (loop [indexes (repeat (count args) 0)]
        (let [m (match (map take (map inc indexes) args))]
          (if (empty? m)
            (recur (update (into [] indexes) (idx-min (map nth args indexes)) inc))
            (first m)))))))

(= 3 (a108
      [3 4 5]))
(= 4 (a108
      [1 2 3 4 5 6 7] [0.5 3/2 4 19]))
(= 7 (a108
      (range) (range 0 100 7/6) [2 3 5 7 11 13]))
(= 64 (a108
       (map #(* % % %) (range)) ;; perfect cubes
       (filter #(zero? (bit-and % (dec %))) (range)) ;; powers of 2
       (iterate inc 20))) ;; at least as large as 20
