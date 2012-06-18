(ns upenn.core)

;;;; http://www.cis.upenn.edu/~matuszek/cis554-2011/Assignments/clojure-01-exercises.html
;;;; Skipping the unit testing.

;;; First Group: You don't need higher-order functions to program these, but if
;;; you see an opportunity to use them, go ahead. Generallly, the purpose is to
;;; get you used to writing recursive functions the Clojure way.

;; (collatz n)
;; Defined for positive integers n. If n is 1, return 1; else if n is even,
;; return collatz(n/2); else return collatz(3n + 1). (Yes, this is just a
;; complicated way to compute 1.)
(defn collatz [n]
  (cond (= n 1) 1
        (even? n) (collatz (/ n 2))
        :else (collatz (+ (* 3 n) 1))))

;; (shallow-reverse lst)
;; Reverses the elements of list lst. For example, the list (1 2 (3 4)) becomes
;; the sequence ((3 4) 2 1).
(defn shallow-reverse [lst]
  (if (empty? lst) ()
      (cons (last lst) (shallow-reverse (butlast lst)))))

;; (remove-duplicates lst)
;; Removes duplicate elements of lst. For example, given (1 2 3 1 4 1 2),
;; remove-duplicates returns a sequence containing the elements (1 2 3 4), in
;; some order.
(defn remove-duplicates [lst]
  (if (empty? lst) ()
      (cons (first lst)
            (remove-duplicates (filter (fn [x] (not= x (first lst)))
                                       (rest lst))))))

;; (my-flatten lst)
;; Returns as value the sequence lst with all inner parentheses (or brackets)
;; removed, returning a "flat" list of values. For example, if lst is (1 (2 3)
;; ( ) (( )) :a), the result should be (1 2 3 :a). Hint: Use the predicate
;; seq?.
(defn my-flatten [lst]
  (if (empty? lst) ()
      (concat (if (seq? (first lst)) (my-flatten (first lst))
                  (list (first lst)))
              (my-flatten (rest lst)))))

;; (skeleton lst)
;; Removes all the non-sequence elements of list lst, but retains all the
;; sequence structure. For example, if lst is (1 (2 (3 4)) 5 6 (7) ( )), the
;; result is ((( )) ( ) ( )).
(defn skeleton [lst]
  (if (empty? lst) ()
      (if (seq? (first lst))
        (cons (skeleton (first lst)) (skeleton (rest lst)))
        (skeleton (rest lst)))))

;; (deep-reverse lst)
;; Reverses the elements of L at all levels. For example, if lst is (:a (:b :c
;; (:d)) :e), deep-reverse should return (:e ((:d) :c :b) :a).
(defn deep-reverse [lst]
  (if (empty? lst) ()
      (cons (if (seq? (last lst)) (deep-reverse (last lst))
                (last lst))
            (deep-reverse (butlast lst)))))

;; (eliminate value lst)
;; Returns the sequence lst with all occurrences of the value removed, at all
;; levels. For example, if lst is (:a :b (:b :c :d (:a (:b))) :e), (eliminate
;; :b lst) should return(:a (:c :d (:a ())) :e). Note that the value may be any
;; value, for example, a sequence.
(defn eliminate [n lst]
  (let [l (filter (fn [x] (not= n x)) lst)]
    (if (empty? l) ()
        (cons (if (seq? (first l)) (eliminate n (first l))
                  (first l))
              (eliminate n (rest l))))))
                

;;; Second group: These are all probably best done with higher-order functions,
;;; so please use them wherever they seem to work.

;; (zap-gremlins text)
;; Remove from the given text all invalid ASCII characters. (Valid characters
;; are decimal 10 (linefeed), 13 (carriage return) and 32 through 126,
;; inclusive. Hint: filter.
(defn zap-gremlins [text]
  (apply str (filter (fn [c] (or (= 10 (int c)) (= 13 (int c))
                                 (and (>= (int c) 32) (<= (int c) 126))))
                     text)))

;; (rot-13 text)
;; Apply the rot-13 transformation to text. This is a simple encoding in which
;; each letter is replaced by the letter 13 further along, end-around. Both
;; uppercase and lowercase letters should be rotated; other characters should
;; be unaffected. You may need the coercions (int ch) and (char n). Hint: map.
(defn rot-13 [text]
  (defn rotate [n lower upper]
    (let [x (mod (+ n 13) upper)]
      (if (< x lower) (char (+ x lower)) (char x))))
  (apply str (map (fn [c]
                    (let [x (int c)]
                      (cond (and (>= x 65) (<= x 90)) (rotate x 65 90)
                            (and (>= x 97) (<= x 122)) (rotate x 97 122)
                            :else (char x))))
                  text)))

;; (sqrt n)
;; Compute the square root of the positive number n, using Newton's
;; method. That is, choose some arbitrary number, say 2, as the initial
;; approximation r to the square root; then to get the next approximation,
;; compute the average of r and n/r. Continue until you have five significant
;; digits to the right of the decimal point. Do this by taking an infinite
;; series of approximations, and taking approximations until they differ by
;; less than 0.00001. Hint: iterate.
(defn sqrt [n]
  (defn abs [n]
    (if (< n 0) (- 0 n) n))
  (defn dif [n1 n2]
    (if (< (abs (- n1 n2)) 0.00001) true false))
  (defn newton [n r]
    (let [guess (/ (+ r (/ n r)) 2)]
      (if (dif r guess) guess
          (newton n guess))))
  (newton n 2))

;; (longest-collatz lo hi)
;; The Collatz sequence eventually converges to 1. Find which starting value,
;; in the range lo to hi (including both end points) takes the longest to
;; converge. If two values take equally long to converge, return either value.
(defn longest-collatz [lo hi]
  (defn collatz-count [n c]
    (cond (= n 1) c
          (even? n) (collatz-count (/ n 2) (+ c 1))
          :else (collatz-count (+ (* 3 n) 1) (+ c 1))))
  (let [lo-count (collatz-count lo 0)
        hi-count (collatz-count hi 0)]
    (if (> lo-count hi-count) lo hi)))
