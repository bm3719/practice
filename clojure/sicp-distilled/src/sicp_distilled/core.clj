;;;; This is my attempt to work through SICP Distilled, a port of SICP to
;;;; Clojure.  http://www.sicpdistilled.com/

;;;; Background: SICP Distilled was a crowd-funded projected that would
;;;; hopefully port the entire text, reworking the concepts into presentations
;;;; idiomatic to Clojure.  Unfortunately for backers who funded it at 4x the
;;;; originally requested £3000, it seems this turned into one of those
;;;; abandoned efforts that screwed everyone over, never completing delivery on
;;;; any of its promises.  Never crowd-fund anything, kids.  On the other hand,
;;;; the author claims that it at least does cover the core important concepts
;;;; that SICP really delivers upon.  If that's true, then it's good enough for
;;;; my purposes.

;;;; As someone who's primary language is Clojure, the downsides listed are
;;;; outweighed by the massive gain in using a more useful and arguably better
;;;; language.

;;;; Tip: Clone the website repository so as to be able to copy exercise text
;;;; directly from the markdown files.  That's located here:
;;;; https://github.com/SICPDistilled/website

(ns sicp-distilled.core)

;;; Chapter 1

;; 1.1

;; Below is a sequence of expressions. What is the result printed by the
;; interpreter in response to each expression? Assume that the sequence is to
;; be evaluated in the order in which it is presented.

;; 10
10
;; (+ 5 3 4)
12
;; (- 9 1)
8
;; (/ 6 2)
3
;; (+ (* 2 4) (- 4 6))
6
;; (def a 3)
;; (def b (+ a 1))
;; (+ a b (* a b))
19
;; (= a b)
false
;; (if (and (> b a) (< b (* a b)))
;;   b
;;   a)
4
;; (cond (= a 4) 6
;;       (= b 4) (+ 6 7 a)
;;       :else 25)
16
;; (+ 2 (if (> b a) b a))
6
;; (* (cond (> a b) a
;;          (< a b) b
;;          :else -1)
;;    (+ a 1))
16


;; 1.2

;; Translate the following expression into prefix form:

;; $$ \frac{5+4+(2-(3-(6+\frac{4}{5})))}{3(6-2)(2-7)} $$

(/ (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5))))) (* 3 (- 6 2) (- 2 7)))


;; 1.3

;; Define a function that takes three numbers as arguments and returns the sum
;; of the squares of the two larger numbers.

(defn sum-squares-larger-2 [x y z]
  (let [all (map #(* % %) [x y z])]
    (- (apply + all) (apply min all))))


;; 1.4

;; Observe that our model of evaluation allows for combinations whose operators
;; are compound expressions. Use this observation to describe the behavior of
;; the following function:

;; (defn a-plus-abs-b [a b]
;;   ((if (> b 0) + -)
;;    a
;;    b))

;; Answer: The function to be applied is determined at runtime.


;; 1.5

;; Ben Bitdiddle has invented a test to determine whether the interpreter he is
;; faced with is using applicative-order evaluation or normal-order evaluation.

;; He defines the following two functions:

;; (defn p []
;;   (p))

;; (defn test [x y]
;;   (if (= x 0)
;;     0
;;     y))

;; Then he evaluates the expression

;; (test 0 (p))

;; What behavior will Ben observe with an interpreter that uses
;; applicative-order evaluation?

;; What behavior will he observe with an interpreter that uses normal-order
;; evaluation?

;; (Assume that the evaluation rule for the special form if is the same whether
;; the interpreter is using normal or applicative order: The predicate
;; expression is evaluated first, and the result determines whether to evaluate
;; the consequent or the alternative expression.)

;; Answer: Applicative order: returns 0. Normal order: infinitely recurses.


;; 1.6

;; Alyssa P. Hacker doesn’t see why if needs to be provided as a special
;; form. “Why can’t I just define it as an ordinary function in terms of cond
;; ?” she asks. Alyssa’s friend Eva Lu Ator claims this can indeed be done, and
;; she defines a new version of if :

;; (defn new-if [predicate then-clause else-clause]
;;   (cond predicate then-clause
;;         :else else-clause))

;; Eva demonstrates the program for Alyssa:

;; > (new-if (= 2 3) 0 5)
;; 5

;; > (new-if (= 1 1) 0 5)
;; 0

;; Delighted, Alyssa uses new-if to rewrite the squareroot program:

;; (defn sqrt-iter [guess x]
;;   (new-if (good-enough? guess x)
;;           guess
;;           (recur (improve guess x) x)))

;; What happens when Alyssa attempts to use this to compute square roots?
;; Explain.

;; Answer: The parameters to new-if will be evaluated prior to the cond inside
;; the body of new-if.


;; 1.7

;; The `good-enough?` test used in computing square roots will not be very
;; effective for finding the square roots of very small numbers. Also, in real
;; computers, arithmetic operations are almost always performed with limited
;; precision. This makes our test inadequate for very large numbers. Explain
;; these statements, with examples showing how the test fails for small and
;; large numbers. An alternative strategy for implementing `good-enough?` is to
;; watch how guess changes from one iteration to the next and to stop when the
;; change is a very small fraction of the guess.

;; Design a square-root function that uses this kind of end test.

;; Does this work better for small and large numbers?

;; Setup
(defn square [x]
  (* x x))

(defn abs [n]
  (max n (- n)))

(defn average [x y]
  (/ (+ x y) 2))

(defn improve [guess x]
  (average guess (/ x guess)))

(defn good-enough? [guess x]
  (< (abs (- (square guess) x)) 0.001))

(defn sqrt-iter [guess x]
  (if (good-enough? guess x)
    guess
    (sqrt-iter (improve guess x) x)))

(defn sqrt [x]
  (sqrt-iter 1.0 x))

;; Answer: Just doing the guess function.  Integrating it just calls this one
;; instead of the original.
(defn alt-good-enough? [guess x]
  (< (abs (/ (square guess) x)) 0.001))


;; 1.9

;; Each of the following two procedures defines a method for adding two
;; positive integers in terms of the procedures inc , which increments its
;; argument by 1, and dec , which decrements its argument by 1.

;; (defn + [a b]
;;   (if (= a 0) b (inc (+ (dec a) b))))

;; (defn + [a b]
;;   (if (= a 0) b (+ (dec a) (inc b))))

;; Using the substitution model, illustrate the process generated by each
;; procedure in evaluating `(+ 4 5)`

;; Are these processes iterative or recursive?

;; Answer: Skipping drawing out the evaluation trees.  The first is recursive,
;; the second iterative.


;; 1.10

;; The following procedure computes a mathematical function called Ackermann’s
;; function.

(defn A [x y]
  (cond (= y 0) 0
        (= x 0) (* 2 y)
        (= y 1) 2
        :else (A (- x 1) (A x (- y 1)))))

;; What are the values of the following expressions?
;; (A 1 10)
1024
;; (A 2 4)
65536
;; (A 3 3)
65536

;; Consider the following procedures, where A is the procedure defined above:

;; (def (f n) (A 0 n))
;; (def (g n) (A 1 n))
;; (def (h n) (A 2 n))
;; (def (k n) (* 5 n n))

;; Give concise mathematical definitions for the functions computed by the
;; procedures `f`, `g`, and `h` for positive integer values of `n`. For
;; example, `(k n)` computes $5n^2$.

;; Answer:
;; (f n) -> 2n
;; (g n) -> 2^n
;; (h n) -> 2_1^2_2...2_(n-1)^2_n


;; 1.11

;; A function f is defined by the rule that

;; <img src="/images/ex1.11.png" width="100%">

;; Write a procedure that computes f by means of

;; * a recursive process
;; * an iterative process
