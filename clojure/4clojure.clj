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
(= ((fn foo [a b] (if (or (empty? a) (empty? b)) '()
                      (conj (foo (rest a) (rest b)) (first b) (first a))))
    [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))
(= ((fn foo [a b] (if (or (empty? a) (empty? b)) '()
                      (conj (foo (rest a) (rest b)) (first b) (first a))))
    [1 2] [3 4 5 6]) '(1 3 2 4))
(= ((fn foo [a b] (if (or (empty? a) (empty? b)) '()
                      (conj (foo (rest a) (rest b)) (first b) (first a))))
    [1 2 3 4] [5]) [1 5])
(= ((fn foo [a b] (if (or (empty? a) (empty? b)) '()
                      (conj (foo (rest a) (rest b)) (first b) (first a))))
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

;; $166: Comparisons
;; (= :gt (__ < 5 1))
;; (= :eq (__ (fn [x y] (< (count x) (count y))) "pear" "plum"))
;; (= :lt (__ (fn [x y] (< (mod x 5) (mod y 5))) 21 3))
;; (= :gt (__ > 0 2))


