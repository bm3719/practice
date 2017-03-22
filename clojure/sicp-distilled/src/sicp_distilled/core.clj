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

;;; 1.1

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


;;; 1.2

;; Translate the following expression into prefix form:

;; $$ \frac{5+4+(2-(3-(6+\frac{4}{5})))}{3(6-2)(2-7)} $$

(/ (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5))))) (* 3 (- 6 2) (- 2 7)))


;;; 1.3

;; Define a function that takes three numbers as arguments and returns the sum
;; of the squares of the two larger numbers.

(defn sum-squares-larger-2
  "A little more clear than the juxt-based solution." [x y z]
  (#(- (apply + %) (apply min %))
   (map #(* % %) [x y z])))


;;; 1.4

;; Observe that our model of evaluation allows for combinations whose operators
;; are compound expressions. Use this observation to describe the behavior of
;; the following function:

;; (defn a-plus-abs-b [a b]
;;   ((if (> b 0) + -)
;;    a
;;    b))

;; Answer: The function to be applied is determined at runtime.


;;; 1.5

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


;;; 1.6

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


;;; 1.7

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


;;; 1.9

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


;;; 1.10

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


;;; 1.11

;; A function f is defined by the rule that

;; f(n) = n if n < 3
;; f(n) = f(n - 1) + 2f(n - 2) + 3f(n - 3) if n >= 3

;; Write a procedure that computes f by means of

;; * a recursive process
;; * an iterative process

(defn f1
  "Recursive solution." [n]
  (if (< n 3) n
      (+ (f1 (- n 1)) (* 2 (f1 (- n 2))) (* 3 (f1 (- n 3))))))

(defn f2
  "Iterative solution." [n]
  (letfn [(iter [a b c count]
            (if (< count 3) a
                (iter (+ a (* 2 b) (* 3 c)) a b (- count 1))))]
    (if (< n 3) n
        (iter 2 1 0 n))))

(defn f3
  "A more idiomatic Clojure solution, using lazy sequences." [n]
  (letfn [(f-seq [c b a]
            (lazy-seq (cons c (f-seq b a (+ a (* 2 b) (* 3 c))))))]
    (last (take (+ n 1) (f-seq 0 1 2)))))


;;; 1.15

;; The sine of an angle (specified in radians) can be computed by making use of
;; the approximation sin x ≈ x if x is sufficiently small, and the
;; trigonometric identity

;; $$ \sin x = 3 \sin \frac{x}{3} - 4 \sin^{3} \frac{x}{3} $$

;; to reduce the size of the argument of sin. (For purposes of this exercise an
;; angle is considered “sufficiently small” if its magnitude is not greater
;; than 0.1 radians.) These ideas are incorporated in the following procedures:

(defn cube [x]
  (* x x x))

(defn p [x]
  (- (* 3 x) (* 4 (cube x))))

(defn sine [angle]
  (if (not (> (abs angle) 0.1))
    angle
    (p (sine (/ angle 3.0)))))

;; a. How many times is the procedure p applied when (sine 12.15) is evaluated?

;; Answer: 5 times.

;; b. What is the order of growth in space and number of steps (as a function
;; of a) used by the process generated by the sine procedure when (sine a) is
;; evaluated?

;; Answer: Θ(log a).


;;; 1.16

;; Design a procedure that evolves an iterative exponentiation process
;; that uses successive squaring and uses a logarithmic number of steps,
;; as does fast-expt.

;; (Hint: Using the observation that $(b^{\frac{n}{2}})^2 = (b^2 )^\frac{n}{2}$
;; keep, along with the exponent $n$ and the base $b$ , an additional state
;; variable $a$ , and define the state transformation in such a way that the
;; product $ab^n$ is unchanged from state to state. At the beginning of the
;; process $a$ is taken to be $1$, and the answer is given by the value of $a$
;; at the end of the process. In general, the technique of defining an
;; invariant quantity that remains unchanged from state to state is a powerful
;; way to think about the design of iterative algorithms.)

(defn fast-expt2 [b n]
  ((fn f-iter [a n b]
     (cond (zero? n) a
           (even? n) (f-iter a (/ n 2) (square b))
           :else (f-iter (* a b) (- n 1) b)))
   1 n b))


;;; 1.20

(defn gcd [a b]
  (if (= b 0)
    a
    (recur b (rem a b))))

;; The process that a procedure generates is of course dependent on the rules
;; used by the interpreter.  As an example, consider the iterative gcd
;; procedure given above. Suppose we were to interpret this procedure using
;; normal-order evaluation, as discussed in Section 1.1.5. (The
;; normal-order-evaluation rule for if is described in Exercise 1.5.)

;; Using the substitution method (for normal order), illustrate the process
;; generated in evaluating (gcd 206 40) and indicate the remainder operations
;; that are actually performed.

;; How many remainder operations are actually performed in:

;; * the normal-order evaluation of (gcd 206 40)?

;; Answer: 4

;; * the applicative-order evaluation?

;; Answer: 4


;; 1.41

;; Define a function double that takes a function of one argument as argument
;; and returns a function that applies the original function twice. For
;; example, if inc is a function that adds 1 to its argument, then `(double
;; inc)` should be a function that adds 2.  What value is returned by

;; (((double (double double)) inc) 5)

(defn double-f
  "Renaming to double-f, so as not to clobber clojure.core/double." [f]
  #(f (f %)))

;; (((double-f (double-f double-f)) inc) 5) returns 21.


;;; 1.42

;; Let f and g be two one-argument functions.

;; The *composition* of f and g, denoted $ f \circ  g $ is defined to be the function

;; $$ x \rightarrow f(g(x)) $$

;; Define a function `compose` that implements composition.

;; You should find that

;; > ((compose square inc) 6)
;; 49

;; After you are done, take a peek at `(source comp)` to see how clojure does it in
;; a variadic way

(defn compose [f g]
  #(f (g %)))


;;; 1.43

;; If f is a numerical function and n is a positive integer, then we can form
;; the n<sup>th</sup> repeated application of f , which is defined to be the
;; function whose value at x is

;; $$ f (f (\dots (f (x )) \dots )) $$

;; For example, if f is the function $ x \rightarrow x + 1 $ then the
;; n<sup>th</sup> repeated application of f is the function $ x \rightarrow x+n
;; $

;; If f is the operation of squaring a number, then the n<sup>th</sup> repeated
;; application of f is the function

;; $$ x \rightarrow x^{2^n} $$

;; Write a function that takes as inputs a function that computes f and a
;; positive integer n and returns the function that computes the n<sup>th</sup>
;; repeated application of f . Your function should be able to be used as
;; follows:

;; => ((repeated square 2) 5)
;; 625

(defn repeated [f x]
  #((apply comp (repeat x f)) %))


;;; 2.53

;; What would the interpreter print in response
;; to evaluating each of the following expressions?

;; (list 'a 'b 'c)

;; Answer: (a b c)

;; (list (list 'george))

;; Answer: ((george))

;; (rest '((x1 x2) (y1 y2)))

;; Answer: ((y1 y2))

;; (list? (first '(a short list)))

;; Answer: a

;; (memq 'red '((red shoes) (blue socks)))

;; Answer: false

;; (memq 'red '(red shoes blue socks))

;; Answer: (red shoes blue socks)


;;; 2.54

;; Two lists are said to be equal? if they contain equal elements arranged in
;; the same order. For example,

;; (equal? '(this is a list) '(this is a list))

;; is true, but

;; (equal? '(this is a list) '(this (is a) list))

;; is false. To be more precise, we can define equal? recursively in terms of
;; the basic `=` equality of symbols by saying that a and b are equal? if they
;; are both symbols and the symbols are eq? , or if they are both lists such
;; that `(first a)` is `equal?` to `(first b)` and `(rest a)` is `equal?` to
;; `(rest b)` .

;; Using this idea, implement equal? as a procedure

(defn equal? [a b]
  (cond (and (symbol? a) (symbol? b)) (= a b)
        (and (empty? a) (empty? b)) true
        (or (empty? a) (empty? b)) false
        (and (list? a) (list? b)) (and (= (first a) (first b))
                                       (equal? (rest a) (rest b)))
        :else false))


;;; 2.55

;; Eva Lu Ator types to the interpreter the expression

;; (first ''abracadabra)

;; To her surprise, the interpreter prints back `quote` why?

;; Answer: ''a is just syntactic sugar for (quote (quote a)).  In this case,
;; the first quote gets evaluated.
