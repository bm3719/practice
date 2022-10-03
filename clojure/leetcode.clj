;;; leetcode problems, solved in Clojure (which the auto-checker doesn't
;;; support currently).  https://leetcode.com/problemset/all

(require '[clojure.test :refer :all])

;;; Easy

;; 1: Two Sum
(defn two-sum [nums target]
  (letfn [(indexify [es] (map vector es (range (count es))))
          (pairs [es] (if (< (count es) 2) []
                          (concat (map #(vector (first es) %) (rest es))
                                  (pairs (rest es)))))
          (target-reduce [coll [x y] target]
            (if (= (+ (first x) (first y)) target) [x y] coll))]
    (->> (indexify nums)
         (pairs)
         (reduce #(target-reduce %1 %2 target) [])
         (mapv second))))

(are [x y] (= x y)
  (two-sum [2 7 11 15] 9) [0 1]
  (two-sum [3 2 4] 6) [1 2]
  (two-sum [3 3] 6) [0 1])

;; 9: Palindrome Number
(defn palindrome-num?
  "Determine if an integer reads the same backwards as forwards." [x]
  (let [s (str x)]
    (cond (<= (count s) 1) true
          (= (count s) 2) (= (first s) (last s))
          :else (and (= (first s) (last s))
                     (palindrome-num? (->> (rest s)
                                           (butlast)
                                           (apply str)
                                           (read-string)))))))

(are [x y] (= x y)
  (palindrome-num? 121) true
  (palindrome-num? -121) false
  (palindrome-num? 10) false)

;; 13: Roman to Integer

;; 14: Longest Common Prefix
(defn longest-common [strs]
  (->> (apply (partial map vector) strs)
       (take-while #(apply = %))
       (map first)
       (apply str)))

(are [x y] (= x y)
  (longest-common ["flower" "flow" "flight"]) "fl"
  (longest-common ["dog" "racecar" "car"]) "")

;; 20: Valid Parentheses
(defn valid-parens?
  "Determines if `s` contains a sequence of paired parens, braces, or
  brackets (no nesting)." [s]
  (letfn [(pair? [c1 c2] (or (and (= c1 \() (= c2 \)))
                             (and (= c1 \[) (= c2 \]))
                             (and (= c1 \{) (= c2 \}))))]
    (cond
      (= (count s) 0) true
      (= (count s) 1) false
      :else (and (pair? (first s) (second s))
                 (valid-parens? (apply str (drop 2 s)))))))

(are [x y] (= x y)
  (valid-parens? "()") true
  (valid-parens? "()[]{}") true
  (valid-parens? "(]") false)

;; 21: Merge Two Sorted Lists
(defn merge-sorted
  "Merge two sorted linked lists." [lst1 lst2]
  (cond
    (and (empty? lst1) (empty? lst2)) []
    (empty? lst1) lst2
    (empty? lst2) lst1
    :else (if (< (first lst1) (first lst2))
            (concat [(first lst1)] (merge-sorted (rest lst1) lst2))
            (concat [(first lst2)] (merge-sorted lst1 (rest lst2))))))

(are [x y] (= x y)
  (merge-sorted [1 2 4] [1 3 4]) [1 1 2 3 4 4]
  (merge-sorted [] []) []
  (merge-sorted [] [0]) [0])

;; 26: Remove Duplicates from Sorted Array
(defn remove-dupes
  "Remove duplicates from a sorted array." [coll]
  (distinct coll))

(are [x y] (= x y)
  (remove-dupes [1 1 2]) [1 2]
  (remove-dupes [0 0 1 1 1 2 2 3 3 4]) [0 1 2 3 4])

;; 27: Remove Element
(defn remove-element
  "Remove all occurrences of `e` from `coll`." [coll e]
  (remove #(= e %) coll))

(are [x y] (= x y)
  (remove-element [3 2 2 3] 3) [2 2]
  (remove-element [0 1 2 2 3 0 4 2] 2) [0 1 3 0 4])

;; 28: Implement strStr()
(defn str-str
  "Find index of `sub` within string `s`, it if exists.  If not, return -1 and 0
  when `sub` is empty."
  [s sub]
  (->> (for [i (range (count s))]
         [i (take (count sub) (drop i s))])
       (filterv #(= (into '() sub) (second %)))
       (#(conj % [-1 nil]))
       ffirst))

(are [x y] (= x y)
  (str-str "hello" "ll") 2
  (str-str "aaaaa" "bba") -1)

;; 35: Search Insert Position
(defn find-index-pos
  "Given a sorted collection `coll`, find the index of value `n`.  If it doesn't
  exist, return the index of where it would be inserted." [coll n]
  (reduce (fn [acc x] (if (<= n x) acc (inc acc))) 0 coll))

(are [x y] (= x y)
  (find-index-pos [1 3 5 6] 5) 2
  (find-index-pos [1 3 5 6] 2) 1
  (find-index-pos [1 3 5 6] 7) 4)

;; 58: Length of Last Word
(defn last-word-len
  "Return the length of the last word in string `s`." [s]
  (->> (clojure.string/trim s)
       (partition-by #(= \space %))
       last
       count))

(are [x y] (= x y)
  (last-word-len "Hello World") 5
  (last-word-len "   fly me   to   the moon  ") 4
  (last-word-len "luffy is still joyboy") 6)

;; 66: Plus One
(defn plus-one
  "Given a vector of integers representing digits in a single integer, return
  what the increment of that representation would be in the same form." [coll]
  (letfn [(exp [x n] (reduce * (repeat n x)))
          (to-int [coll] (->> (reverse coll)
                              (map #(* %2 (exp 10 %1))
                                   (range (count coll)))
                              (reduce +)))
          (to-coll [n] (cond (= n 0) []
                             :else (conj (-> (/ n 10) Math/floor int to-coll)
                                         (mod n 10))))]
    (-> coll to-int inc to-coll)))

(are [x y] (= x y)
  (plus-one [1 2 3]) [1 2 4]
  (plus-one [4 3 2 1]) [4 3 2 2]
  (plus-one [9]) [1 0])

;;; Medium

;; 2. Add Two Numbers
(defn add-list-nums
  "Given two lists which contain digits of two numbers in reverse order, return
  another list that is the same representation of the sum of those numbers."
  ([l1 l2] (add-list-nums l1 l2 0))
  ([l1 l2 carry] (if (and (empty? l1) (empty? l2))
                   (if (zero? carry) '() (list carry))
                   (let [nil->0 #(if (= (first %) nil) 0 (first %))
                         sum    (+ (nil->0 l1) (nil->0 l2) carry)
                         digit  (mod sum 10)
                         r      (int (/ sum 10))]
                     (conj (add-list-nums (rest l1) (rest l2) r) digit)))))

(are [x y] (= x y)
  (add-list-nums '(2 4 3) '(5 6 4)) '(7 0 8)
  (add-list-nums '(0) '(0)) '(0)
  (add-list-nums '(9 9 9 9 9 9 9) '(9 9 9 9)) '(8 9 9 9 0 0 0 1))

;; 3: Longest Substring Without Repeating Characters
(defn longest-substring
  "Find the length of the longest substring in `s` that has no repeating
  characters."
  ([s] (count (longest-substring s "")))
  ([s cur]
   (letfn [(longest-check [s1 s2] (if (> (count s1) (count s2)) s1 s2))
           (has-char? [s1 c] (some #(= c %) s1))
           (drop-match [s1 c] (->> (drop-while #(not= % c) s1)
                                   (rest)
                                   (#(concat % (list c)))
                                   (apply str)))]
     (if (empty? s) cur
         (let [fs      (first s)
               new-cur (if (has-char? cur fs)
                         (drop-match cur fs)
                         (apply str (concat cur (list fs))))]
           (longest-check cur (longest-substring (rest s) new-cur)))))))

(are [x y] (= x y)
  (longest-substring "abcabcbb") 3
  (longest-substring "bbbbb") 1
  (longest-substring "pwwkew") 3)

;; 5: Longest Palindromic Substring
;; Skipping for now, since it's too similar to #3.

;; 6: Zigzag conversion
(defn zigzag-convert
  "Convert a string to \"zigzag\" notation and return the characters as if they
  were read by sequential rows." [s]
  (let [svec (map vector s (range (count s)))]
    (->> (concat (filter #(= 0 (mod (second %) 4)) svec)
                 (filter #(odd? (second %)) svec)
                 (filter #(and (even? (second %))
                               (not= 0 (mod (second %) 4))) svec))
         (map first)
         (apply str))))

(are [x y] (= x y)
  (zigzag-convert "PAYPALISHIRING") "PAHNAPLSIIGYIR")

;; Oops, solved this without reading the instructions and noticing it needed a
;; num-rows param.  Solution could instead be a reducer where the acc is a
;; matrix.  A supporting fn can determine the next slot (with nils).  Then
;; another fn flattens it back into a string.  Might implement this later.

;; 7: Reverse Integer

;; 8: String to Integer (atoi)

;; 11: Container With Most Water
(defn largest-container
  "Given a vector of integers that represent walls of heights equivalent to their
  value, find the largest volume that a pair of those values could make." [v]
  (let [vm  (map vector v (range 1 (inc (count v))))
        vol (fn [i1 i2] (* (min (first i1) (first i2))
                           (Math/abs (- (second i2) (second i1)))))]
    (apply max (for [i1 vm
                     i2 vm]
                 (vol i1 i2)))))

(are [x y] (= x y)
  (largest-container [1 8 6 2 5 4 8 3 7]) 49
  (largest-container [1 1]) 1)

;; Lazy seq version of Fizz Buzz.
(defn fizzbuzz "Returns a lazy seq of fizz buzz"
  ([] (fizzbuzz 1))
  ([n] (cons (cond (zero? (mod n 15)) "FizzBuzz"
                   (zero? (mod n 5)) "Buzz"
                   (zero? (mod n 3)) "Fizz"
                   :else n)
             (lazy-seq (fizzbuzz (inc n))))))
;; ...

;; 17: Letter Combinations of a Phone Number
(defn letter-combos
  "Given a string of an integer, return the possible combinations of letters that
  the digits composing it can make.  Ignores the 1 digit." [i]
  (let [lookup {\1 ""
                \2 "abc"
                \3 "def"
                \4 "ghi"
                \5 "jkl"
                \6 "mno"
                \7 "pqrs"
                \8 "tuv"
                \9 "wxyz"
                \0 " "}
        lcoll (map lookup i)]
    (reduce (fn [coll s]
              (for [perm coll
                    c s]
                (apply str (conj (vec perm) c))))
            (map str (first lcoll))
            (rest lcoll))))

(are [x y] (= x y)
  (letter-combos "23") ["ad" "ae" "af" "bd" "be" "bf" "cd" "ce" "cf"]
  (letter-combos "") []
  (letter-combos "2") ["a" "b" "c"])
