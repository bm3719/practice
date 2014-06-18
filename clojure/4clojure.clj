;;;; Exercises from 4clojure.com.  Using difficulty ordering.  Will probably
;;;; stop somewhere in the Medium range.

;;; Elementary

;; #2: Simple Math
(= (- 10 (* 2 3)) 4)

;; #3: Intro to Strings
(= "HELLO WORLD" (.toUpperCase "hello world"))

;; #4: Intro to Lists
(= (list :a :b :c) '(:a :b :c))

;; #5: Lists: conj
(= '(1 2 3 4) (conj ('2 3 4) 1))
(= '(1 2 3 4) (conj '(3 4) 2 1))

;; #6: Intro to Vectors
(= [:a :b :c] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))

;; #7: Vectors: conj
(= '(1 2 3 4) (conj [1 2 3] 4))
(= '(1 2 3 4) (conj [1 2] 3 4))

;; #8: Intro to Sets
(= #{:a :b :c :d} (set '(:a :a :b :c :c :c :c :d :d)))
(= #{:a :b :c :d} (clojure.set/union #{:a :b :c} #{:b :c :d}))

;; #9: Sets: conj
(= #{1 2 3 4} (conj #{1 4 3} 2))

;; #10: Intro to Maps
(= 20 ((hash-map :a 10, :b 20, :c 30) :b))
(= 20 (:b {:a 10, :b 20, :c 30}))

;; #11: Maps: conj
(= {:a 1, :b 2, :c 3} (conj {:a 1} {:b 2} [:c 3]))

;; #12: Intro to Sequences
(= 3 (first '(3 2 1)))
(= 3 (second [2 3 4]))
(= 3 (last (list 1 2 3)))

;; #13 Sequences: rest
(= '(20 30 40) (rest [10 20 30 40]))

;; #14: Intro to Functions
(= 8 ((fn add-five [x] (+ x 5)) 3))
(= 8 ((fn [x] (+ x 5)) 3))
(= 8 (#(+ % 5) 3))
(= 8 ((partial + 5) 3))

;; #15: Double Down
(= (#(* % 2) 2) 4)
(= (#(* % 2) 3) 6)
(= (#(* % 2) 11) 22)
(= (#(* % 2) 7) 14)

;; #16: Hello World
(= (#(str "Hello, " % "!") "Dave") "Hello, Dave!")
(= (#(str "Hello, " % "!") "Jenn") "Hello, Jenn!")
(= (#(str "Hello, " % "!") "Rhea") "Hello, Rhea!")

;; #17: Sequences: map
(= '(6 7 8) (map #(+ % 5) '(1 2 3)))

;; #18: Sequences: filter
(= '(6 7) (filter #(> % 5) '(3 4 5 6 7)))

;; #35: Local Bindings
(= 7 (let [x 5] (+ 2 x)))
(= 7 (let [x 3, y 10] (- y x)))
(= 7 (let [x 21] (let [y 3] (/ x y))))

;; #36: Let it Be
(= 10 (let [x 7, y 3, z 1] (+ x y)))
(= 4 (let [x 7, y 3, z 1] (+ y z)))
(= 1 (let [x 7, y 3, z 1] z))

;; #37: Regular Expressions
(= "ABC" (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))

;; #64: Intro to Reduce
(= 15 (reduce + [1 2 3 4 5]))
(= 0 (reduce + []))
(= 6 (reduce + 1 [2 3]))

;; #57: Simple Recursion
(= '(5 4 3 2 1) ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x ))) 5))

;; #71: Rearranging Code: ->
;; The -> macro threads an expression x through a variable number of forms, in
;; each threading, making it a list if it is not already one.
(= (last (sort (rest (reverse [2 5 4 1 3 6]))))
   (-> [2 5 4 1 3 6] (reverse) (rest) (sort) (last))
   5)

;; #68: Recurring Theme
(= '(7 6 5 4 3)
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
(true? (#(and (= nil (%1 %2)) (contains? %2 %1)) :a {:a nil :b 2}))
(false? (#(and (= nil (%1 %2)) (contains? %2 %1)) :b {:a nil :b 2}))
(false? (#(and (= nil (%1 %2)) (contains? %2 %1)) :c {:a nil :b 2}))

;; #145: For the win
(= '(1 5 9 13 17 21 25 29 33 37) (for [x (range 40)
            :when (= 1 (rem x 4))]
        x))
(= '(1 5 9 13 17 21 25 29 33 37) (for [x (iterate #(+ 4 %) 0)
            :let [z (inc x)]
            :while (< z 40)]
        z))
(= '(1 5 9 13 17 21 25 29 33 37) (for [[x y] (partition 2 (range 20))]
        (+ x y)))

;; #162: Logical falsity and truth
(= 1 (if-not false 1 0))
(= 1 (if-not nil 1 0))
(= 1 (if true 1 0))
(= 1 (if [] 1 0))
(= 1 (if [0] 1 0))
(= 1 (if 0 1 0))
(= 1 (if 1 1 0))

;; #156: Map Defaults
(= ((fn f [x s] (reduce conj (map #(hash-map % x) s))) 0 [:a :b :c])
   {:a 0 :b 0 :c 0})
(= ((fn f [x s] (reduce conj (map #(hash-map % x) s))) "x" [1 2 3])
   {1 "x" 2 "x" 3 "x"})
(= ((fn f [x s] (reduce conj (map #(hash-map % x) s))) [:a :b] [:foo :bar])
   {:foo [:a :b] :bar [:a :b]})

;;; Easy

;; #19: Last Element
(= (#(first (reverse %)) [1 2 3 4 5]) 5)
(= (#(first (reverse %)) '(5 4 3)) 3)
(= (#(first (reverse %)) ["b" "c" "d"]) "d")

;; #20: Penultimate Element
(= (#(->> % (reverse) (take 2) (last)) (list 1 2 3 4 5)) 4)
(= (#(->> % (reverse) (take 2) (last)) ["a" "b" "c"]) "b")
(= (#(->> % (reverse) (take 2) (last)) [[1 2] [3 4]]) [1 2])

;; #21: Nth Element
(= ((fn f [lst n] (if (= n 0) (first lst) (f (rest lst) (- n 1))))
    '(4 5 6 7) 2) 6)
(= ((fn f [lst n] (if (= n 0) (first lst) (f (rest lst) (- n 1))))
    [:a :b :c] 0) a)
(= ((fn f [lst n] (if (= n 0) (first lst) (f (rest lst) (- n 1))))
    [1 2 3 4] 1) 2)
(= ((fn f [lst n] (if (= n 0) (first lst) (f (rest lst) (- n 1))))
    '([1 2] [3 4] [5 6]) 2) [5 6])

;; #22: Count a Sequence
(= ((fn f [lst] (if (= lst '()) 0 (+ 1 (f (rest lst))))) '(1 2 3 3 1)) 5)
(= ((fn f [lst] (if (= lst '()) 0 (+ 1 (f (rest lst))))) "Hello World") 11)
(= ((fn f [lst] (if (= lst '()) 0 (+ 1 (f (rest lst))))) [[1 2] [3 4] [5 6]]) 3)
(= ((fn f [lst] (if (= lst '()) 0 (+ 1 (f (rest lst))))) '(13)) 1)
(= ((fn f [lst] (if (= lst '()) 0 (+ 1 (f (rest lst))))) '(:a :b :c)) 3)

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
(= ((fn f [lst] (if (<= (count lst) 1) lst
                    (cons (last lst) (f (butlast lst)))))
    [1 2 3 4 5]) [5 4 3 2 1])
(= ((fn f [lst] (if (<= (count lst) 1) lst
                    (cons (last lst) (f (butlast lst)))))
    (sorted-set 5 7 2 7)) '(7 5 2))
(= ((fn f [lst] (if (<= (count lst) 1) lst
                    (cons (last lst) (f (butlast lst)))))
    [[1 2] [3 4] [5 6]]) [[5 6] [3 4] [1 2]])

;; #27: Palindrome Detector
;; There's probably a cleaner way to do this.
(false? (#(if (= (type %) java.lang.String)
            (= % (apply str (reverse %)))
            (= % (reverse %)))
         '(1 2 3 4 5)))
(true? (#(if (= (type %) java.lang.String)
            (= % (apply str (reverse %)))
            (= % (reverse %)))
        "racecar"))
(true? (#(if (= (type %) java.lang.String)
            (= % (apply str (reverse %)))
            (= % (reverse %)))
        [:foo :bar :foo]))
(true? (#(if (= (type %) java.lang.String)
            (= % (apply str (reverse %)))
            (= % (reverse %)))
        '(1 1 3 3 1 1)))
(false? (#(if (= (type %) java.lang.String)
            (= % (apply str (reverse %)))
            (= % (reverse %)))
         '(:a :b :c)))

;; #26: Fibonacci Sequence
(= (#(map (fn fib [x] (if (< x 3) 1 (+ (fib (- x 1)) (fib (- x 2)))))
           (range 1 (+ % 1)))
    3) '(1 1 2))
(= (#(map (fn fib [x] (if (< x 3) 1 (+ (fib (- x 1)) (fib (- x 2)))))
           (range 1 (+ % 1)))
    6) '(1 1 2 3 5 8))
(= (#(map (fn fib [x] (if (< x 3) 1 (+ (fib (- x 1)) (fib (- x 2)))))
           (range 1 (+ % 1)))
    8) '(1 1 2 3 5 8 13 21))

;; #38: Maximum value
(= ((fn mymax [x & xs] (if (= (count xs) 0) x
      (let [ox (apply mymax xs)]
        (if (> x ox) x ox))))
    1 8 3 4) 8)
(= ((fn mymax [x & xs] (if (= (count xs) 0) x
      (let [ox (apply mymax xs)]
        (if (> x ox) x ox))))
    30 20) 30)
(= ((fn mymax [x & xs] (if (= (count xs) 0) x
      (let [ox (apply mymax xs)]
        (if (> x ox) x ox))))
    45 67 11) 67)

;; #29: Get the Caps
(= ((fn f [s] (apply str (filter #(Character/isUpperCase %) s)))
     "HeLlO, WoRlD!") "HLOWRD")
(empty? ((fn f [s] (apply str (filter #(Character/isUpperCase %) s)))
         "nothing"))
(= ((fn f [s] (apply str (filter #(Character/isUpperCase %) s)))
    "$#A(*&987Zf") "AZ")

;; #32: Duplicate a Sequence
(= ((fn f [lst] (if (= (count lst) 0) lst
                    (conj (conj (f (rest lst)) (first lst)) (first lst))))
    [1 2 3]) '(1 1 2 2 3 3))
(= ((fn f [lst] (if (= (count lst) 0) lst
                    (conj (conj (f (rest lst)) (first lst)) (first lst))))
    [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))
(= ((fn f [lst] (if (= (count lst) 0) lst
                    (conj (conj (f (rest lst)) (first lst)) (first lst))))
    [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

;; #48: Intro to some
(= 6 (some #{2 7 6} [5 6 7 8]))
(= 6 (some #(when (even? %) %) [5 6 7 8]))

;; #34: Implement range
(= ((fn myrange [s n] (if (= s n) '() (conj (myrange (+ s 1) n) s)))
    1 4) '(1 2 3))
(= ((fn myrange [s n] (if (= s n) '() (conj (myrange (+ s 1) n) s)))
    -2 2) '(-2 -1 0 1))
(= ((fn myrange [s n] (if (= s n) '() (conj (myrange (+ s 1) n) s)))
    5 8) '(5 6 7))

;; #28: Flatten a sequence
(= ((fn f [lst] (cond (= nil (first lst)) lst
                      (sequential? (first lst)) (concat (f (first lst)) (f (rest lst)))
                      :else (conj (f (rest lst)) (first lst))))
    '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))
(= ((fn f [lst] (cond (= nil (first lst)) lst
                      (sequential? (first lst)) (concat (f (first lst)) (f (rest lst)))
                      :else (conj (f (rest lst)) (first lst))))
    ["a" ["b"] "c"]) '("a" "b" "c"))
(= ((fn f [lst] (cond (= nil (first lst)) lst
                      (sequential? (first lst)) (concat (f (first lst)) (f (rest lst)))
                      :else (conj (f (rest lst)) (first lst))))
    '(((:a)))) '(:a))

;; #42: Factorial Fun
(= ((fn fac [x] (if (< x 3) x (* x (fac (- x 1))))) 1) 1)
(= ((fn fac [x] (if (< x 3) x (* x (fac (- x 1))))) 3) 6)
(= ((fn fac [x] (if (< x 3) x (* x (fac (- x 1))))) 5) 120)
(= ((fn fac [x] (if (< x 3) x (* x (fac (- x 1))))) 8) 40320)

;; #39: Interleave Two Seqs
(= (#(flatten (map list %1 %2))
    [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))
(= (#(flatten (map list %1 %2))
    [1 2] [3 4 5 6]) '(1 3 2 4))
(= (#(flatten (map list %1 %2))
    [1 2 3 4] [5]) [1 5])
(= (#(flatten (map list %1 %2))
    [30 20] [25 15]) [30 25 20 15])

;; #30: Compress a Sequence
(= (apply str ((fn f [s] (cond (empty? s) '()
                               (= (first s) (second s)) (f (rest s)) 
                               :else (conj (f (rest s)) (first s))))
           "Leeeeeerrroyyy")) "Leroy")
(= ((fn f [s] (cond (empty? s) '()
                (= (first s) (second s)) (f (rest s)) 
                :else (conj (f (rest s)) (first s))))
    [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))
(= ((fn f [s] (cond (empty? s) '()
                (= (first s) (second s)) (f (rest s)) 
                :else (conj (f (rest s)) (first s))))
    [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))

;; #47: Contain Yourself
(contains? #{4 5 6} 4)
(contains? [1 1 1 1 1] 4)
(contains? {4 :a 2 :b} 4)
(not (contains? '(1 2 4) 4))

;; #45: Intro to Iterate
(= '(1 4 7 10 13) (take 5 (iterate #(+ 3 %) 1)))

;; #33: Replicate a Sequence
(= ((fn f [s n] (reduce concat (map #(take n (iterate identity %)) s)))
    [1 2 3] 2) '(1 1 2 2 3 3))
(= ((fn f [s n] (reduce concat (map #(take n (iterate identity %)) s)))
    [:a :b] 4) '(:a :a :a :a :b :b :b :b))
(= ((fn f [s n] (reduce concat (map #(take n (iterate identity %)) s)))
    [4 5 6] 1) '(4 5 6))
(= ((fn f [s n] (reduce concat (map #(take n (iterate identity %)) s)))
    [[1 2] [3 4]] 2) '([1 2] [1 2] [3 4] [3 4]))
(= ((fn f [s n] (reduce concat (map #(take n (iterate identity %)) s)))
    [44 33] 2) [44 44 33 33])

;; #40: Interpose a Seq
(= ((fn f [k s] (if (< (count s) 2) s (conj (f k (rest s)) k (first s))))
    0 [1 2 3]) [1 0 2 0 3])
(= (apply str ((fn f [k s] (if (< (count s) 2) s (conj (f k (rest s)) k (first s))))
               ", " ["one" "two" "three"])) "one, two, three")
(= ((fn f [k s] (if (< (count s) 2) s (conj (f k (rest s)) k (first s))))
    :z [:a :b :c :d]) [:a :z :b :z :c :z :d])

;; #31: Pack a Sequence
(= (#(partition-by identity %) [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))
(= (#(partition-by identity %) [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))
(= (#(partition-by identity %) [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))

;; #41: Drop Every Nth Item
(= ((fn f [lst n] (if (< (count lst) n) lst
                      (concat (take (- n 1) lst) (f (drop n lst) n))))
    [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])
(= ((fn f [lst n] (if (< (count lst) n) lst
                      (concat (take (- n 1) lst) (f (drop n lst) n))))
    [:a :b :c :d :e :f] 2) [:a :c :e])
(= ((fn f [lst n] (if (< (count lst) n) lst
                      (concat (take (- n 1) lst) (f (drop n lst) n))))
    [1 2 3 4 5 6] 4) [1 2 3 5 6])

;; #52: Intro to Destructuring
(= [2 4] (let [[a b c d e f g] (range)] [c e]))

;; #49: Split a sequence
(= ((fn f [n lst] (if (<= (count lst) n) (list lst)
                      (conj (list (drop n lst)) (take n lst))))
    3 [1 2 3 4 5 6]) [[1 2 3] [4 5 6]])
(= ((fn f [n lst] (if (<= (count lst) n) (list lst)
                      (conj (list (drop n lst)) (take n lst))))
    1 [:a :b :c :d]) [[:a] [:b :c :d]])
(= ((fn f [n lst] (if (<= (count lst) n) (list lst)
                      (conj (list (drop n lst)) (take n lst))))
    2 [[1 2] [3 4] [5 6]]) [[[1 2] [3 4]] [[5 6]]])

;; #51: Advanced Destructuring
(= [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] [1 2 3 4 5]] [a b c d]))

;; #83: A Half-Truth
(= false (#(true? (and (some identity %&) (not (every? identity %&)))) false false))
(= true (#(true? (and (some identity %&) (not (every? identity %&)))) true false))
(= false (#(true? (and (some identity %&) (not (every? identity %&)))) true))
(= true (#(true? (and (some identity %&) (not (every? identity %&)))) false true false))
(= false (#(true? (and (some identity %&) (not (every? identity %&)))) true true true))
(= true (#(true? (and (some identity %&) (not (every? identity %&)))) true true true false))

;; #61: Map Construction
(= (#(into {} (map vector %1 %2)) [:a :b :c] [1 2 3]) {:a 1, :b 2, :c 3})
(= (#(into {} (map vector %1 %2)) [1 2 3 4] ["one" "two" "three"]) {1 "one", 2 "two", 3 "three"})
(= (#(into {} (map vector %1 %2)) [:foo :bar] ["foo" "bar" "baz"]) {:foo "foo" :bar "bar"})

;; #166: Comparisons
(= :gt (#(cond (%1 %2 %3) :lt (%1 %3 %2) :gt :else :eq) < 5 1))
(= :eq (#(cond (%1 %2 %3) :lt (%1 %3 %2) :gt :else :eq)
        (fn [x y] (< (count x) (count y))) "pear" "plum"))
(= :lt (#(cond (%1 %2 %3) :lt (%1 %3 %2) :gt :else :eq)
        (fn [x y] (< (mod x 5) (mod y 5))) 21 3))
(= :gt (#(cond (%1 %2 %3) :lt (%1 %3 %2) :gt :else :eq) > 0 2))

;; #66: Greatest Common Divisor
;; Using the Euclidean algorithm.
(= ((fn g [x y] (if (= y 0) x (g y (mod x y)))) 2 4) 2)
(= ((fn g [x y] (if (= y 0) x (g y (mod x y)))) 10 5) 5)
(= ((fn g [x y] (if (= y 0) x (g y (mod x y)))) 5 7) 1)
(= ((fn g [x y] (if (= y 0) x (g y (mod x y)))) 1023 858) 33)

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
(= 256 (((fn [x] (fn [y] (int (Math/pow y x)))) 2) 16) (((fn [x] (fn [y] (int (Math/pow y x)))) 8) 2))
(= [1 8 27 64] (map ((fn [x] (fn [y] (int (Math/pow y x)))) 3) [1 2 3 4]))
(= [1 2 4 8 16] (map #(((fn [x] (fn [y] (int (Math/pow y x)))) %) 2) [0 1 2 3 4]))

;; #99: Product Digits
(= (#(map read-string (map str (seq (str (* %1 %2))))) 1 1) [1])
(= (#(map read-string (map str (seq (str (* %1 %2))))) 99 9) [8 9 1])
(= (#(map read-string (map str (seq (str (* %1 %2))))) 999 99) [9 8 9 0 1])

;; #90: Cartesian Product
;; There's probably a cleaner way to do this.
(= ((fn f [l1 l2] (if (= (count l1) 1) (into #{} (map #(vector (first l1) %) l2))
                      (into #{} (concat (f (rest l1) l2) (map #(vector (first l1) %) l2)))))
    #{"ace" "king" "queen"} #{"♠" "♥" "♦" "♣"})
   #{["ace"   "♠"] ["ace"   "♥"] ["ace"   "♦"] ["ace"   "♣"]
     ["king"  "♠"] ["king"  "♥"] ["king"  "♦"] ["king"  "♣"]
     ["queen" "♠"] ["queen" "♥"] ["queen" "♦"] ["queen" "♣"]})
(= ((fn f [l1 l2] (if (= (count l1) 1) (into #{} (map #(vector (first l1) %) l2))
                      (into #{} (concat (f (rest l1) l2) (map #(vector (first l1) %) l2)))))
    #{1 2 3} #{4 5})
   #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]})
(= 300 (count ((fn f [l1 l2] (if (= (count l1) 1) (into #{} (map #(vector (first l1) %) l2))
                                 (into #{} (concat (f (rest l1) l2) (map #(vector (first l1) %) l2)))))
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
(= ((fn [n] (last (take n (iterate
                           (fn [prev-row]
                             (concat [(first prev-row)]
                                     (map #(apply + %) (partition 2 1 prev-row))
                                     [(last prev-row)]))
                           [1]))))
    1) [1])
(= (map (fn [n] (last (take n (iterate
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
(= ((fn [n] (last (take n (iterate
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

;; #55: Count Occurances
;; (= (__ [1 1 2 3 2 1 1]) {1 4, 2 2, 3 1})
;; (= (__ [:b :a :b :a :b]) {:a 2, :b 3})
;; (= (__ '([1 2] [1 3] [1 3])) {[1 2] 1, [1 3] 2})
