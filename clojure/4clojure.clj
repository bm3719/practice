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
(def a19 #(first (reverse %)))

(= (a19 [1 2 3 4 5]) 5)
(= (a19 '(5 4 3)) 3)
(= (a19 ["b" "c" "d"]) "d")

;; #20: Penultimate Element
(def a20 #(last (take 2 (reverse %))))

(= (a20 (list 1 2 3 4 5)) 4)
(= (a20 ["a" "b" "c"]) "b")
(= (a20 [[1 2] [3 4]]) [1 2])

;; #21: Nth Element
(def a21 #(last (take (inc %2) %1)))

(= (a21 '(4 5 6 7) 2) 6)
(= (a21 [:a :b :c] 0) :a)
(= (a21 [1 2 3 4] 1) 2)
(= (a21 '([1 2] [3 4] [5 6]) 2) [5 6])

;; #22: Count a Sequence
(def a22 #(reduce + (for [_ %] 1)))

(= (a22 '(1 2 3 3 1)) 5)
(= (a22 "Hello World") 11)
(= (a22 [[1 2] [3 4] [5 6]]) 3)
(= (a22 '(13)) 1)
(= (a22 '(:a :b :c)) 3)

;; #24: Sum It All Up
(def a24 #(reduce + %))

(= (a24 [1 2 3]) 6)
(= (a24 (list 0 -2 5 5)) 8)
(= (a24 #{4 2 1}) 7)
(= (a24 '(0 0 -1)) -1)
(= (a24 '(1 10 3)) 14)

;; #25: Find the odd numbers
(def a25 #(filter odd? %))

(= (a25 #{1 2 3 4 5}) '(1 3 5))
(= (a25 [4 2 1 6]) '(1))
(= (a25 [2 2 4 6]) '())
(= (a25 [1 1 1 3]) '(1 1 1 3))

;; #23: Reverse a Sequence
(def a23 (fn f [lst] (if (seq lst) (cons (last lst) (f (butlast lst))) lst)))

(= (a23 [1 2 3 4 5]) [5 4 3 2 1])
(= (a23 (sorted-set 5 7 2 7)) '(7 5 2))
(= (a23 [[1 2] [3 4] [5 6]]) [[5 6] [3 4] [1 2]])

;; #27: Palindrome Detector
;; NOTE: On the site, you can't use as-> for some reason.  Would have to use ->>.
(def a27 #(as-> (vec %) $ (= $ (reverse $))))

(false? (a27 '(1 2 3 4 5)))
(true? (a27 "racecar"))
(true? (a27 [:foo :bar :foo]))
(true? (a27 '(1 1 3 3 1 1)))
(false? (a27 '(:a :b :c)))

;; #26: Fibonacci Sequence
(def a26 #(take % ((fn f [] (lazy-cat [1 1] (map + (f) (rest (f))))))))

(= (a26 3) '(1 1 2))
(= (a26 6) '(1 1 2 3 5 8))
(= (a26 8) '(1 1 2 3 5 8 13 21))

;; #38: Maximum value
(def a38 (fn f [x & xs]
           (if (seq xs)
             (let [ox (apply f xs)]
               (if (> x ox) x ox)) x)))

(= (a38 1 8 3 4) 8)
(= (a38 30 20) 30)
(= (a38 45 67 11) 67)

;; #29: Get the Caps
(def a29 (fn f [s] (apply str (filter #(Character/isUpperCase %) s))))

(= (a29 "HeLlO, WoRlD!") "HLOWRD")
(empty? (a29 "nothing"))
(= (a29 "$#A(*&987Zf") "AZ")

;; #32: Duplicate a Sequence
(def a32 #(apply concat (for [e %] [e e])))

(= (a32 [1 2 3]) '(1 1 2 2 3 3))
(= (a32 [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))
(= (a32 [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

;; #48: Intro to some
(def a48 6)

(= a48 (some #{2 7 6} [5 6 7 8]))
(= a48 (some #(when (even? %) %) [5 6 7 8]))

;; #34: Implement range
(def a34 #(take (- %2 %1) ((fn f [] (lazy-cat [%1] (map inc (f)))))))

(= (a34 1 4) '(1 2 3))
(= (a34 -2 2) '(-2 -1 0 1))
(= (a34 5 8) '(5 6 7))

;; #28: Flatten a sequence
(def a28
  (fn f [lst]
    (cond (nil? (first lst)) lst
          (sequential? (first lst)) (concat (f (first lst)) (f (rest lst)))
          :else (conj (f (rest lst)) (first lst)))))

(= (a28 '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))
(= (a28 ["a" ["b"] "c"]) '("a" "b" "c"))
(= (a28 '(((:a)))) '(:a))

;; #39: Interleave Two Seqs
(def a39 #(flatten (map list %1 %2)))

(= (a39 [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))
(= (a39 [1 2] [3 4 5 6]) '(1 3 2 4))
(= (a39 [1 2 3 4] [5]) [1 5])
(= (a39 [30 20] [25 15]) [30 25 20 15])

;; #42: Factorial Fun
(def a42 #(reduce * (map inc (range %))))

(= (a42 1) 1)
(= (a42 3) 6)
(= (a42 5) 120)
(= (a42 8) 40320)

;; #30: Compress a Sequence
(def a30 #(map first (partition-by identity %)))

(= (apply str (a30 "Leeeeeerrroyyy")) "Leroy")
(= (a30 [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))
(= (a30 [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))

;; #47: Contain Yourself
(def a47 4)

(contains? #{4 5 6} a47)
(contains? [1 1 1 1 1] a47)
(contains? {4 :a 2 :b} a47)
(not (contains? [1 2 4] a47))

;; #33: Replicate a Sequence
(def a33 #(apply concat (for [e %1] (repeat %2 e))))

(= (a33 [1 2 3] 2) '(1 1 2 2 3 3))
(= (a33 [:a :b] 4) '(:a :a :a :a :b :b :b :b))
(= (a33 [4 5 6] 1) '(4 5 6))
(= (a33 [[1 2] [3 4]] 2) '([1 2] [1 2] [3 4] [3 4]))
(= (a33 [44 33] 2) [44 44 33 33])

;; #45: Intro to Iterate
(def a45 [1 4 7 10 13])

(= a45 (take 5 (iterate #(+ 3 %) 1)))

;; #40: Interpose a Seq
(def a40 #(conj (vec (apply concat (for [e (butlast %2)] [e %1]))) (last %2)))

(= (a40 0 [1 2 3]) [1 0 2 0 3])
(= (apply str (a40 ", " ["one" "two" "three"])) "one, two, three")
(= (a40 :z [:a :b :c :d]) [:a :z :b :z :c :z :d])

;; #31: Pack a Sequence
(def a31 #(partition-by identity %))

(= (a31 [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))
(= (a31 [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))
(= (a31 [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))

;; #41: Drop Every Nth Item
(def a41 #(filter (complement nil?)
                  (flatten (partition (dec %2) %2 (repeat nil) %1))))

(= (a41 [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])
(= (a41 [:a :b :c :d :e :f] 2) [:a :c :e])
(= (a41 [1 2 3 4 5 6] 4) [1 2 3 5 6])

;; #52: Intro to Destructuring
(= [2 4] (let [[a b c d e f g] (range)] [c e]))

;; #49: Split a sequence
(def a49 #(conj (list (drop %1 %2)) (take %1 %2)))

(= (a49 3 [1 2 3 4 5 6]) [[1 2 3] [4 5 6]])
(= (a49 1 [:a :b :c :d]) [[:a] [:b :c :d]])
(= (a49 2 [[1 2] [3 4] [5 6]]) [[[1 2] [3 4]] [[5 6]]])

;; #51: Advanced Destructuring
(def a51 [1 2 3 4 5])

(= [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] a51] [a b c d]))

;; #83: A Half-Truth
(def a83 #(true? (and (some identity %&) (not (every? identity %&)))))

(= false (a83 false false))
(= true (a83 true false))
(= false (a83 true))
(= true (a83 false true false))
(= false (a83 true true true))
(= true (a83 true true true false))

;; #61: Map Construction
(def a61 #(into {} (map vector %1 %2)))

(= (a61 [:a :b :c] [1 2 3]) {:a 1, :b 2, :c 3})
(= (a61 [1 2 3 4] ["one" "two" "three"]) {1 "one", 2 "two", 3 "three"})
(= (a61 [:foo :bar] ["foo" "bar" "baz"]) {:foo "foo" :bar "bar"})

;; #66: Greatest Common Divisor
;; Using the Euclidean algorithm.
(def a66 (fn g [x y] (if (= y 0) x (g y (mod x y)))))

(= (a66 2 4) 2)
(= (a66 10 5) 5)
(= (a66 5 7) 1)
(= (a66 1023 858) 33)

;; #166: Comparisons
(def a166 #(cond (%1 %2 %3) :lt (%1 %3 %2) :gt :else :eq))

(= :gt (a166 < 5 1))
(= :eq (a166 (fn [x y] (< (count x) (count y))) "pear" "plum"))
(= :lt (a166 (fn [x y] (< (mod x 5) (mod y 5))) 21 3))
(= :gt (a166 > 0 2))

;; #81: Set Intersection
(def a81 #(clojure.set/difference %1 (clojure.set/difference %1 %2)))

(= (a81 #{0 1 2 3} #{2 3 4 5}) #{2 3})
(= (a81 #{0 1 2} #{3 4 5}) #{})
(= (a81 #{:a :b :c :d} #{:c :e :a :f :d}) #{:a :c :d})

;; #62: Re-implement Iterate
(def a62 (fn iter [f x] (lazy-seq (cons x (iter f (f x))))))

(= (take 5 (a62 #(* 2 %) 1)) [1 2 4 8 16])
(= (take 100 (a62 inc 0)) (take 100 [range]))
(= (take 9 (a62 #(inc (mod % 3)) 1)) (take 9 (cycle [1 2 3])))

;; #107: Simple closures
(def a107 (fn [x] (fn [y] (int (Math/pow y x)))))

(= 256 ((a107 2) 16) ((a107 8) 2))
(= [1 8 27 64] (map (a107 3) [1 2 3 4]))
(= [1 2 4 8 16] (map #((a107 %) 2) [0 1 2 3 4]))

;; #99: Product Digits
(def a99 #(map read-string (map str (seq (str (* %1 %2))))))

(= (a99 1 1) [1])
(= (a99 99 9) [8 9 1])
(= (a99 999 99) [9 8 9 0 1])

;; #90: Cartesian Product
(def a90 #(into #{} (for [x %1 y %2] [x y])))

(= (a90 #{"ace" "king" "queen"} #{"♠" "♥" "♦" "♣"})
   #{["ace"   "♠"] ["ace"   "♥"] ["ace"   "♦"] ["ace"   "♣"]
     ["king"  "♠"] ["king"  "♥"] ["king"  "♦"] ["king"  "♣"]
     ["queen" "♠"] ["queen" "♥"] ["queen" "♦"] ["queen" "♣"]})
(= (a90 #{1 2 3} #{4 5})
   #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]})
(= 300 (count (a90 (into #{} (range 10))
                   (into #{} (range 30)))))

;; #63: Group a Sequence
(def a63 (fn [f v] (apply merge-with #(conj %1 (first %2)) (map #(hash-map (f %) (vector %)) v))))

(= (a63 #(> % 5) [1 3 6 8]) {false [1 3], true [6 8]})
(= (a63 #(apply / %) [[1 2] [2 4] [4 6] [3 6]])
   {1/2 [[1 2] [2 4] [3 6]], 2/3 [[4 6]]})
(= (a63 count [[1] [1 2] [3] [1 2 3] [2 3]])
   {1 [[1] [3]], 2 [[1 2] [2 3]], 3 [[1 2 3]]})

;; #122: Read a binary number
(def a122 (fn [s] (let [x (reverse (take (count (map char s))
                                         (iterate #(* 2 %) 1)))]
                    (reduce + (map #(if (= %1 \0) 0 %2) (map char s) x)))))

(= 0 (a122 "0"))
(= 7 (a122 "111"))
(= 8 (a122 "1000"))
(= 9 (a122 "1001"))
(= 255 (a122 "11111111"))
(= 1365 (a122 "10101010101"))
(= 65535 (a122 "1111111111111111"))

;; #88: Symmetric Difference
(def a88 #(clojure.set/union (clojure.set/difference %1 %2)
                             (clojure.set/difference %2 %1)))

(= (a88 #{1 2 3 4 5 6} #{1 3 5 7}) #{2 4 6 7})
(= (a88 #{:a :b :c} #{}) #{:a :b :c})
(= (a88 #{} #{4 5 6}) #{4 5 6})
(= (a88 #{[1 2] [2 3]} #{[2 3] [3 4]}) #{[1 2] [3 4]})

;; #143: dot product
(def a143 #(reduce + (map * %1 %2)))

(= 0 (a143 [0 1 0] [1 0 0]))
(= 3 (a143 [1 1 1] [1 1 1]))
(= 32 (a143 [1 2 3] [4 5 6]))
(= 256 (a143 [2 5 6] [100 10 1]))

;; #126: Through the Looking Glass
(def a126 Class)

(let [x a126]
  (and (= (class x) x) x))

;; #135: Infix Calculator
(def a135 (fn infix [& rest]
            (if (< (count rest) 3) (first rest)
                ((nth rest (- (count rest) 2))
                 (apply infix (drop-last 2 rest))
                 (last rest)))))

(= 7 (a135 2 + 5))
(= 42 (a135 38 + 48 - 2 / 2))
(= 8 (a135 10 / 2 - 1 * 2))
(= 72 (a135 20 / 2 + 2 + 4 + 8 - 6 - 10 * 9))

;; #97: Pascal's Triangle
(def a97 (fn [n]
           (last (take n (iterate
                          (fn [prev-row]
                            (concat [(first prev-row)]
                                    (map #(apply + %) (partition 2 1 prev-row))
                                    [(last prev-row)]))
                          [1])))))

(= (a97 1) [1])
(= (map a97 (range 1 6))
   [     [1]
    [1 1]
    [1 2 1]
    [1 3 3 1]
    [1 4 6 4 1]])
(= (a97 11)
   [1 10 45 120 210 252 210 120 45 10 1])

;; #157: Indexing Sequences
(def a157 #(for [i (range (count %))] [(nth % i) i]))

(= (a157 [:a :b :c]) [[:a 0] [:b 1] [:c 2]])
(= (a157 [0 1 3]) '((0 0) (1 1) (3 2)))
(= (a157 [[:foo] {:bar :baz}]) [[[:foo] 0] [{:bar :baz} 1]])

;; #118: Re-implement Map
(def a118 (fn m [f col]
            (lazy-seq
             (if-not (seq col) '()
                     (cons (f (first col)) (m f (rest col)))))))

(= [3 4 5 6 7]
   (a118 inc [2 3 4 5 6]))
(= (repeat 10 nil)
   (a118 (fn [_] nil) (range 10)))
(= [1000000 1000001]
   (->> (a118 inc (range))
        (drop (dec 1000000))
        (take 2)))

;; #120: Sum of square of digits
(def a120
  (fn [col] (letfn [(f [x] (reduce + (map (comp #(* % %) read-string str)
                                          (str x))))]
              (count (filter #(< % (f %)) col)))))

(= 8 (a120 (range 10)))
(= 19 (a120 (range 30)))
(= 50 (a120 (range 100)))
(= 50 (a120 (range 1000)))

;; #95: To Tree, or not to Tree
(def a95 (fn tree? [col]
           (if-not (and (= (count col) 3) (every? #(not= false %) col)) false
                   (every? true? (map #(if (sequential? %) (tree? %) true) col)))))

(= (a95 '(:a (:b nil nil) nil))
   true)
(= (a95 '(:a (:b nil nil)))
   false)
(= (a95 [1 nil [2 [3 nil nil] [4 nil nil]]])
   true)
(= (a95 [1 [2 nil nil] [3 nil nil] [4 nil nil]])
   false)
(= (a95 [1 [2 [3 [4 nil nil] nil] nil] nil])
   true)
(= (a95 [1 [2 [3 [4 false nil] nil] nil] nil])
   false)
(= (a95 '(:a nil ()))
   false)

;; #128: Recognize Playing Cards
(def a128 (fn [s] (let [suit #({\D :diamond \H :heart \C :club \S :spade} %)
                        rank #(if (re-find #"[A-Z]" (str %))
                                ({\T 8 \J 9 \Q 10 \K 11 \A 12} %)
                                (- (read-string (str %)) 2))]
                    {:suit (suit (first s)) :rank (rank (second s))})))

(= {:suit :diamond :rank 10}
   (a128 "DQ"))
(= {:suit :heart :rank 3}
   (a128 "H5"))
(= {:suit :club :rank 12}
   (a128 "CA"))
(= (range 13)
   (map (comp :rank a128 str)
        '[S2 S3 S4 S5 S6 S7
          S8 S9 ST SJ SQ SK SA]))

;; #100: Least Common Multiple
(def a100
  (fn [& rest]
    (let [x          (apply min rest)
          divisible? #(zero? (rem %1 %2))]
      (first (take 1 (filter #(every? (fn [n] (divisible? % n)) rest)
                             (iterate (partial + x) x)))))))

(== (a100 2 3) 6)
(== (a100 5 3 7) 105)
(== (a100 1/3 2/5) 2)
(== (a100 3/4 1/6) 3/2)
(== (a100 7 5/7 2 3/5) 210)

;; #147: Pascal's Trapezoid
(def a147
  (fn [col]
    (iterate
     (fn [prev-row]
       (concat [(first prev-row)]
               (map #(apply +' %) (partition 2 1 prev-row))
               [(last prev-row)])) col)))

(= (second (a147 [2 3 2])) [2 5 5 2])
(= (take 5 (a147 [1])) [[1] [1 1] [1 2 1] [1 3 3 1] [1 4 6 4 1]])
(= (take 2 (a147 [3 1 2])) [[3 1 2] [3 4 3 2]])
(= (take 100 (a147 [2 4 2])) (rest (take 101 (a147 [2 2]))))

;; #173: Intro to Destructuring 2
(= 3
   (let [[x y] [+ (range 3)]] (apply x y))
   (let [[[x y] b] [[+ 1] 2]] (x y b))
   (let [[x y] [inc 2]] (x y)))

;; #96: Beauty is Symmetry
(def a96
  (fn symmetric? [tree]
    (let [left (second tree)
          right (last tree)]
      (if (not= (first left) (first right)) false
          (if (sequential? left)
            (and (symmetric? (list nil (second left) (last right)))
                 (symmetric? (list nil (last left) (second right))))
            true)))))

(= (a96 '(:a (:b nil nil) (:b nil nil))) true)
(= (a96 '(:a (:b nil nil) nil)) false)
(= (a96 '(:a (:b nil nil) (:c nil nil))) false)
(= (a96 [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
         [2 [3 nil [4 [6 nil nil] [5 nil nil]]] nil]])
   true)
(= (a96 [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
         [2 [3 nil [4 [5 nil nil] [6 nil nil]]] nil]])
   false)
(= (a96 [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
         [2 [3 nil [4 [6 nil nil] nil]] nil]])
   false)

;; #146: Trees into table
(def a146 #(into {} (for [[k1 v1] %
                          [k2 v2] v1]
                      [[k1 k2] v2])))

(= (a146 '{a {p 1, q 2}
           b {m 3, n 4}})
   '{[a p] 1, [a q] 2
     [b m] 3, [b n] 4})
(= (a146 '{[1] {a b c d}
           [2] {q r s t u v w x}})
   '{[[1] a] b, [[1] c] d,
     [[2] q] r, [[2] s] t,
     [[2] u] v, [[2] w] x})
(= (a146 '{m {1 [a b c] 3 nil}})
   '{[m 1] [a b c], [m 3] nil})

;; #153: Pairwise Disjoint Sets
(def a153 #(let [all-items  (for [s % e s] e)
                 dup-counts (for [[id freq] (frequencies all-items)
                                  :when (> freq 1)]
                              id)]
             (zero? (count dup-counts))))

(= (a153 #{#{\U} #{\s} #{\e \R \E} #{\P \L} #{\.}})
   true)
(= (a153 #{#{:a :b :c :d :e}
           #{:a :b :c :d}
           #{:a :b :c}
           #{:a :b}
           #{:a}})
   false)
(= (a153 #{#{[1 2 3] [4 5]}
           #{[1 2] [3 4 5]}
           #{[1] [2] 3 4 5}
           #{1 2 [3 4] [5]}})
   true)
(= (a153 #{#{'a 'b}
           #{'c 'd 'e}
           #{'f 'g 'h 'i}
           #{''a ''c ''f}})
   true)
(= (a153 #{#{'(:x :y :z) '(:x :y) '(:z) '()}
           #{#{:x :y :z} #{:x :y} #{:z} #{}}
           #{'[:x :y :z] [:x :y] [:z] [] {}}})
   false)
(= (a153 #{#{(= "true") false}
           #{:yes :no}
           #{(class 1) 0}
           #{(symbol "true") 'false}
           #{(keyword "yes") ::no}
           #{(class '1) (int \0)}})
   false)
(= (a153 #{#{distinct?}
           #{#(-> %) #(-> %)}
           #{#(-> %) #(-> %) #(-> %)}
           #{#(-> %) #(-> %) #(-> %)}})
   true)
(= (a153 #{#{(#(-> *)) + (quote mapcat) #_ nil}
           #{'+ '* mapcat (comment mapcat)}
           #{(do) set contains? nil?}
           #{, , , #_, , empty?}})
   false)

;;; Medium

;; #46: Flipping out
(def a46 (fn [f] #(f %2 %1)))

(= 3 ((a46 nth) 2 [1 2 3 4 5]))
(= true ((a46 >) 7 8))
(= 4 ((a46 quot) 2 8))
(= [1 2 3] ((a46 take) [1 2 3 4 5] 3))

;; #44: Rotate Sequence
(def a44
  #(let [n (mod %1 (count %2))] (concat (drop n %2) (take n %2))))

(= (a44 2 [1 2 3 4 5]) '(3 4 5 1 2))
(= (a44 -2 [1 2 3 4 5]) '(4 5 1 2 3))
(= (a44 6 [1 2 3 4 5]) '(2 3 4 5 1))
(= (a44 1 '(:a :b :c)) '(:b :c :a))
(= (a44 -4 '(:a :b :c)) '(:c :a :b))

;; #43: Reverse Interleave
(def a43 #(apply map list (partition %2 %1)))

(= (a43 [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6)))
(= (a43 (range 9) 3) '((0 3 6) (1 4 7) (2 5 8)))
(= (a43 (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9)))

;; #50: Split by Type
(def a50 #(partition-by (comp str type) (sort-by (comp str type) %)))

(= (set (a50 [1 :a 2 :b 3 :c])) #{[1 2 3] [:a :b :c]})
(= (set (a50 [:a "foo" "bar" :b])) #{[:a :b] ["foo" "bar"]})
(= (set (a50 [[1 2] :a [3 4] 5 6 :b])) #{[[1 2] [3 4]] [:a :b] [5 6]})

;; #55: Count Occurrences
(def a55 (fn [col] (reduce #(update-in %1 [%2] inc)
                           (reduce #(assoc %1 %2 0) {} (set col)) col)))

(= (a55
    [1 1 2 3 2 1 1]) {1 4, 2 2, 3 1})
(= (a55 [:b :a :b :a :b]) {:a 2, :b 3})
(= (a55 '([1 2] [1 3] [1 3])) {[1 2] 1, [1 3] 2})

;; #56: Find Distinct Items
(def a56 (fn f [col]
           (if (empty? col) []
               (let [es (f (butlast col))
                     e  (last col)]
                 (if (some #(= e %) es) es
                     (conj es e))))))

(= (a56 [1 2 1 3 1 2 4]) [1 2 3 4])
(= (a56 [:a :a :b :b :c :c]) [:a :b :c])
(= (a56 '([2 4] [1 2] [1 3] [1 3])) '([2 4] [1 2] [1 3]))
(= (a56 (range 50)) (range 50))

;; #58: Function Composition
(def a58 (fn [& fs]
           (let [fs (reverse fs)]
             #(loop [inner (apply (first fs) %&)
                     fs    (next fs)]
                (if fs
                  (recur ((first fs) inner) (next fs))
                  inner)))))

(= [3 2 1] ((a58 rest reverse) [1 2 3 4]))
(= 5 ((a58 (partial + 3) second) [1 2 3 4]))
(= true ((a58 zero? #(mod % 8) +) 3 5 7 9))
(= "HELLO" ((a58 #(.toUpperCase %) #(apply str %) take) 5 "hello world"))

;; #59: Juxtaposition
(def a59 (fn [& fs]
           #(for [f fs] (apply f %&))))

(= [21 6 1] ((a59 + max min) 2 3 5 1 6 4))
(= ["HELLO" 5] ((a59 #(.toUpperCase %) count) "hello"))
(= [2 6 4] ((a59 :a :c :b) {:a 2, :b 4, :c 6, :d 8, :e 10}))

;; #54: Partition a Sequence
(def a54 (fn my-part [n col]
           (if (< (count col) (* 2 n)) (list (take n col))
               (conj (my-part n (drop n col)) (take n col)))))

(= (a54 3 (range 9)) '((0 1 2) (3 4 5) (6 7 8)))
(= (a54 2 (range 8)) '((0 1) (2 3) (4 5) (6 7)))
(= (a54 3 (range 8)) '((0 1 2) (3 4 5)))

;; #70: Word Sorting
(def a70 (fn [s]
           (->> s (partition-by #(= \space %))
                (filter #(not= \space (first %)))
                (map (comp (partial re-find #"\w+") (partial apply str)))
                (sort-by #(.toLowerCase %)))))

(= (a70 "Have a nice day.")
   ["a" "day" "Have" "nice"])
(= (a70 "Clojure is a fun language!")
   ["a" "Clojure" "fun" "is" "language"])
(= (a70 "Fools fall for foolish follies.")
   ["fall" "follies" "foolish" "Fools" "for"])

;; #67: Prime Numbers
(def a67
  (fn [n]
    (let [prime? (fn [x]
                   (or (< x 3)
                       (not= 0 (reduce * (map #(mod x %)
                                              (range 2 (+ 1 (Math/sqrt x))))))))]
      (take n (for [x (iterate inc 2)
                    :when (prime? x)] x)))))

(= (a67 2) [2 3])
(= (a67 5) [2 3 5 7 11])
(= (last (a67 100)) 541)

;; #74: Filter Perfect Squares
(def a74 (fn [s]
           (->> s
                (partition-by #(= \, %))
                (filter #(not= \, (first %)))
                (map (comp read-string #(apply str %)))
                (filter #(zero? (mod % (Math/sqrt %))))
                (map str)
                (interpose ",")
                (apply str))))

(= (a74 "4,5,6,7,8,9") "4,9")
(= (a74 "15,16,25,36,37") "16,25,36")

;; #65: Black Box Testing
(def a65
  (fn [col]
    (let [[x y c] [(gensym) (gensym) (count col)]]
      (if (= (+ c 2) (count (conj col [x x] [x x])))
        (if (= y (first (conj col x y))) :list :vector)
        (if (= (+ 1 c) (count (conj col [x 1] [x 2]))) :map :set)))))

(= :map (a65 {:a 1, :b 2}))
(= :list (a65 (range (rand-int 20))))
(= :vector (a65 [1 2 3 4 5 6]))
(= :set (a65 #{10 (rand-int 5)}))
(= [:map :set :vector :list]
   (map a65 [{} #{} [] ()]))

;; #76: Intro to Trampoline
(def a76 [1 3 5 7 9 11])

(= a76
   (letfn
       [(foo [x y] #(bar (conj x y) y))
        (bar [x y] (if (> (last x) 10)
                     x
                     #(foo x (+ 2 y))))]
     (trampoline foo [] 1)))

;; #80: Perfect Numbers
(def a80 (fn [x] (= (reduce + (filter #(zero? (mod x %))
                                      (map inc (range (int (/ x 2)))))) x)))

(= (a80 6) true)
(= (a80 7) false)
(= (a80 496) true)
(= (a80 500) false)
(= (a80 8128) true)

;; #77: Anagram Finder
(def a77
  (fn [col] (set (filter #(> (count %) 1)
                         (for [s (set (map set col))]
                           (set (filter #(= s (set %)) col)))))))

(= (a77 ["meat" "mat" "team" "mate" "eat"])
   #{#{"meat" "team" "mate"}})
(= (a77 ["veer" "lake" "item" "kale" "mite" "ever"])
   #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}})

;; #60: Sequence Reductions
(def a60
  (fn f [g & args]
    (let [[i coll] args
          [i coll] (if (nil? coll) [(first i) (rest i)] [i coll])]
      (if (empty? coll) [i]
          (lazy-seq (cons i (f g (g i (first coll)) (rest coll))))))))

(= (take 5 (a60 + (range))) [0 1 3 6 10])
(= (a60 conj [1] [2 3 4]) [[1] [1 2] [1 2 3] [1 2 3 4]])
(= (last (a60 * 2 [3 4 5])) (reduce * 2 [3 4 5]) 120)


;; #69: Merge with a Function
(def a69
  (fn [f & maps]
    (letfn [(m-w [m1 m2]
              (reduce (fn [m [k v]]
                        (assoc m k
                               (if (nil? (get m k)) v
                                   (f (get m k) v))))
                      m1 m2))]
      (reduce m-w maps))))

(= (a69 * {:a 2, :b 3, :c 4} {:a 2} {:b 2} {:c 5}))
(= (a69 - {1 10, 2 20} {1 3, 2 10, 3 15})
   {1 7, 2 10, 3 15})
(= (a69 concat {:a [3], :b [6]} {:a [4 5], :c [8 9]}, {:b [7]})
   {:a [3 4 5], :b [6 7], :c [8 9]})

;; #102: intoCamelCase
(def a102
  (fn [s]
    (let [[h & t] (clojure.string/split s #"-")]
      (clojure.string/join
       (conj (map clojure.string/capitalize t) h)))))

(= (a102 "something") "something")
(= (a102 "multi-word-key") "multiWordKey")
(= (a102 "leaveMeAlone") "leaveMeAlone")

;; #75: Euler's Totient Function
(def a75
  (fn [x]
    (letfn [(gcd [x y] (if (= y 0) x (gcd y (mod x y))))]
      (->> (rest (range (inc x)))
           (filter #(= (gcd % x) 1))
           (count)))))

(= (a75 1) 1)
(= (a75 10) (count '(1 3 7 9)) 4)
(= (a75 40) 16)
(= (a75 99) 60)

;; #86: Happy numbers
(def a86
  (fn f [n & [coll]]
    (if (some #(= % n) (rest coll))
      false
      (let [x (->> (str n) (map str)
                   (map read-string) (map #(* % %)) (apply +))]
        (if (= x 1) true
            (f x (cons x coll)))))))

(= (a86 7) true)
(= (a86 986543210) true)
(= (a86 2) false)
(= (a86 3) false)

;; #78: Reimplement Trampoline
(def a78 (fn t [f & args] (if (fn? f) (t (apply f args)) f)))

(= (letfn [(triple [x] #(sub-two (* 3 x)))
           (sub-two [x] #(stop?(- x 2)))
           (stop? [x] (if (> x 50) x #(triple x)))]
     (a78 triple 2))
   82)
(= (letfn [(my-even? [x] (if (zero? x) true #(my-odd? (dec x))))
           (my-odd? [x] (if (zero? x) false #(my-even? (dec x))))]
     (map (partial a78 my-even?) (range 6)))
   [true false true false true false])

;; #115: The Balance of N
(def a115
  (fn [n]
    (let [n (str n)
          x (Math/floor (/ (count n) 2))
          ns [(take x n) (take x (reverse n))]]
      (->> (for [cs ns] (map #(read-string (str %)) cs))
           (map #(apply + %))
           (apply =)))))

(= true (a115 11))
(= true (a115 121))
(= false ((fn [n]
            (let [n (str n)
                  x (Math/floor (/ (count n) 2))
                  ns [(take x n) (take x (reverse n))]]
              (->> (for [cs ns] (map #(read-string (str %)) cs))
                   (map #(apply + %))
                   (apply =)))) 123))
(= true (a115 0))
(= false (a115 88099))
(= true (a115 89098))
(= true (a115 89089))
(= (take 20 (filter a115 (range)))
   [0 1 2 3 4 5 6 7 8 9 11 22 33 44 55 66 77 88 99 101])

;; #85: Power Set
;; NOTE: Last test works on local REPL, but site says it times out.  Maybe come
;; up with a non-recursive solution to this.
(def a85 (fn ps [s]
           (if (empty? s) #{s}
               (reduce #(into %1 %2) #{s}
                       (for [i s]
                         (ps (disj s i)))))))

(= (a85 #{1 :a}) #{#{1 :a} #{:a} #{} #{1}})
(= (a85
    #{}) #{#{}})
(= (a85
    #{1 2 3})
   #{#{} #{1} #{2} #{3} #{1 2} #{1 3} #{2 3} #{1 2 3}})
(= (count (a85 (into #{} (range 10)))) 1024)

;; #98: Equivalence classes
(def a98 (fn [f coll]
           (->> (group-by f coll)
                (map second)
                (map #(into #{} %))
                (into #{}))))

(= (a98 #(* % %) #{-2 -1 0 1 2})
   #{#{0} #{1 -1} #{2 -2}})
(= (a98 #(rem % 3) #{0 1 2 3 4 5 })
   #{#{0 3} #{1 4} #{2 5}})
(= (a98 identity #{0 1 2 3 4})
   #{#{0} #{1} #{2} #{3} #{4}})
(= (a98 (constantly true) #{0 1 2 3 4})
   #{#{0 1 2 3 4}})

;; #105: Identify keys and values
(def a105
  (fn f [coll]
    (if (empty? coll) {}
        (let [[is r] (split-with number? (rest coll))]
          (into {(first coll) is} (f r))))))

(= {} (a105 []))
(= {:a [1]} (a105 [:a 1]))
(= {:a [1], :b [2]} (a105 [:a 1, :b 2]))
(= {:a [1 2 3], :b [], :c [4]} (a105 [:a 1 2 3 :b :c 4]))

;; #137: Digits and bases
(def a137
  (fn f [n b]
    (if (< n 2) [n]
        (let [nr (int (/ n b))]
          (conj (if (zero? nr) [] (f nr b))
                (mod n b))))))

(= [1 2 3 4 5 0 1] (a137 1234501 10))
(= [0] (a137 0 11))
(= [1 0 0 1] (a137  9 2))
(= [1 0] (let [n (rand-int 100000)] (a137 n n)))
(= [16 18 5 24 15 1] (a137 Integer/MAX_VALUE 42))

;; #144: Oscilrate
(def a144
  (fn f [x & fs]
    (let [y ((first fs) x)]
      (lazy-seq (concat [x] (apply (partial f y)
                                   (conj (vec (rest fs))
                                         (first fs))))))))

(= (take 3 (a144 3.14 int double)) [3.14 3 3.0])
(= (take 5 (a144 3 #(- % 3) #(+ 5 %))) [3 0 5 2 7])
(= (take 12 (a144 0 inc dec inc dec inc)) [0 1 0 1 0 1 2 1 2 1 2 3])

;; #110: Sequence of pronunciations
(def a110
  (fn f [coll]
    (letfn [(p [coll]
              (if (empty? coll) coll
                  (let [x (first coll)
                        c (count (take-while #(= x %) coll))]
                    (concat [c x] (p (drop c coll))))))]
      (rest (iterate p coll)))))

(= [[1 1] [2 1] [1 2 1 1]] (take 3 (a110 [1])))
(= [3 1 2 4] (first (a110 [1 1 1 4 4])))
(= [1 1 1 3 2 1 3 2 1 1] (nth (a110 [1]) 6))
(= 338 (count (nth (a110 [3 2]) 15)))

;; #158: Decurry
(def a158
  (fn g [f]
    (fn h [& args]
      (if (= (count args) 1) (f (first args))
          ((apply h (drop-last args)) (last args))))))

(= 10 ((a158
        (fn [a]
          (fn [b]
            (fn [c]
              (fn [d]
                (+ a b c d))))))
       1 2 3 4))
(= 24 ((a158
        (fn [a]
          (fn [b]
            (fn [c]
              (fn [d]
                (* a b c d))))))
       1 2 3 4))
(= 25 ((a158
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

;; #93: Partially Flatten a Sequence
(def a93
  (fn [colls]
    (filter #(and (sequential? %) (not-any? sequential? %))
            (rest (tree-seq sequential? seq colls)))))

(= (a93 [["Do"] ["Nothing"]])
   [["Do"] ["Nothing"]])
(= (a93 [[[[:a :b]]] [[:c :d]] [:e :f]])
   [[:a :b] [:c :d] [:e :f]])
(= (a93 '((1 2)((3 4)((((5 6)))))))
   '((1 2)(3 4)(5 6)))

;; #114: Global take-while
(def a114
  (fn f [n p coll]
    (if (or (empty? coll) (and (= n 1) (p (first coll)))) []
        (cons (first coll) (f (if (p (first coll)) (dec n) n) p (rest coll))))))

(= [2 3 5 7 11 13]
   (a114 4 #(= 2 (mod % 3))
         [2 3 5 7 11 13 17 19 23]))
(= ["this" "is" "a" "sentence"]
   (a114 3 #(some #{\i} %)
         ["this" "is" "a" "sentence" "i" "wrote"]))
(= ["this" "is"]
   (a114 1 #{"a"}
         ["this" "is" "a" "sentence" "i" "wrote"]))
