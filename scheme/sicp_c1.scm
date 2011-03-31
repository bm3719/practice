;;;; My solutions to exercises from SICP, chapter 1

;;;; exercise 1.1
10
; 10
(+ 5 3 4)
; 12
(- 9 1)
; 8
(/ 6 2)
; 3
(+ (* 2 4) (- 4 6))
; 6
(define a 3)
; a = 3
(define b (+ a 1))
; b = 4
(+ a b (* a b))
; 19
(= a b)
; #f
(if (and (> b a) (< b (* a b)))
    b
    a)
; 4
(cond ((= a 4) 6)
      ((= b 4) (+ 6 7 a))
      (else 25))
; 16
(+ 2 (if (> b a) b a))
; 6
(* (cond ((> a b) a)
         ((< a b) b)
         (else -1))
   (+ a 1))
; 16

;;;; exercise 1.2
(/ (+ 5	4 (- 2 (- 3 (+ 6 (/ 4 5)))))
   (* 3 (- 6 2) (- 2 7)))

;;;; exercise 1.3
(define (e13 x y z)
  (cond ((and (> x z) (> y z)) (+ (* x x) (* y y)))
        ((and (> x y) (> z y)) (+ (* x x) (* z z)))
        (else (+ (* y y) (* z z)))))

;;;; exercise 1.4
; if conditional returns an operator

;;;; exercise 1.5
; applicative-order evaluation returns #t
; normal-order evaluations infinitely recurses

;;;; exercise 1.6
; infinitely recurses

;;;; exercise 1.7
; setup for 1.7
(define (sqrt-iter guess x)
  (if (good-enough? guess x)
      guess
      (sqrt-iter (improve guess x)
                 x)))
(define (improve guess x)
  (average guess (/ x guess)))
(define (average x y)
  (/ (+ x y) 2))
(define (good-enough? guess x)
  (< (abs (- (square guess) x)) 0.001))
(define (square x)
  (* x x))
; example of inadequate precision
;(square (sqrt 0.0000001))
; example of buffer overflow
;(square (sqrt 1000000000000000))
; improved version of good-enough? (requires tracking of previous guess)
(define (good-enough2? guess last-guess x)
  (< (/ (abs (- guess last-guess))
        guess)
     0.0001))

;;;; exercise 1.8
(define (cube-iter guess last-guess x)
  (if (good-enough2? guess last-guess x)
      guess
      (cube-iter (cube-improve guess x)
                 guess x)))
(define (cube-improve guess x)
  (/ (+ (/ x (square guess)) (* 2 guess))
     3))
(define (cube-root x)
  (cube-iter 1.0 0.0 x))

;;;; exercise 1.9
; first + implementation
; (inc (+ 3 5))
; (inc (inc (+ 2 5)))
; (inc (inc (inc (+ 1 5))))
; (inc (inc (inc (inc (+ 0 5)))))
; (inc (inc (inc (inc 5))))
; (inc (inc (inc 6)))
; (inc (inc 7))
; (inc 8)
; 9
; second + implementation
; (+ (dec 4) (inc 5))
; (+ (dec 3) (inc 6))
; (+ (dec 2) (inc 7))
; (+ (dec 1) (inc 8))
; 9

;;;; exercise 1.10
; Ackermann function
(define (A x y)
  (cond ((= y 0) 0)
        ((= x 0) (* 2 y))
        ((= y 1) 2)
        (else (A (- x 1)
                 (A x (- y 1))))))
(A 1 10)
; 1024
(A 2 4)
; 65536
(A 3 3)
; 65536
(define (f n) (A 0 n))
; (f n) computes 2n 
(define (g n) (A 1 n))
; (g n) computes 2^n
(define (h n) (A 2 n))
; (h n) computes 2^2^n

;;;; exercise 1.11
; f(n) = n if n < 3
; f(n) = f(n - 1) + 2f(n - 2) + 3f(n - 3) if n >= 3
; recursive solution
(define (f1 n)
  (cond ((< n 3) n)
        (else (+ (f1 (- n 1)) (* 2 (f1 (- n 2))) (* 3 (f1 (- n 3)))))))
; iterative solution
(define (f2 x)
  (define (f-iter a b c n)
    (if (< n 3) a
        (f-iter (+ a (* 2 b) (* 3 c))
                a
                b
                (- n 1))))
  (if (< x 3) x
      (f-iter 2 1 0 x)))

;;;; exercise 1.12
; indexes start at 1, x = row number, y = place
(define (pascal x y)
  (cond ((= y 1) 1)
        ((= (- x y) 0) 1)
        (else (+ (pascal (- x 1) (- y 1))
                 (pascal (- x 1) y)))))

;;;; exercise 1.13
; let phi = (1 + sqrt(5))/2
; let psi = (1 - sqrt(5))/2
; let fib(n) = 0 if n = 0
;            = 1 if n = 1
;            = fib(n - 1) + fib(n - 2) otherwise
; proposition: fib(n) = (phi^n - psi^n)/sqrt(5)
; base case:
; n = 0
;   fib(0) = (((1 + sqrt(5))/2)^0 - ((1 - sqrt(5))/2)^0)/sqrt(5) = 0
; n = 1
;   fib(1) = (((1 + sqrt(5))/2)^1 - ((1 - sqrt(5))/2)^1)/sqrt(5) = 1
; inductive hypothesis:
; for all k <= n
;   fib(k) = (phi^k - psi^k)/sqrt(5)
; inductive step:
;   phi^(k + 1) = phi^k + phi^(k - 1)
;   psi^(k + 1) = psi^k + psi^(k - 1)
;   fib(k + 1) = (phi^k + phi^(k - 1) - psi^k + psi^(k - 1))/sqrt(5)
;   fib(k + 1) = (phi^k - psi^k)/sqrt(5) + (phi^(k - 1) - psi^(k - 1))/sqrt(5)
;   fib(k + 1) = fib(k) + fib(k - 1)
;   fib(k + 1) = (phi^(k + 1) - psi^(k + 1))/sqrt(5)
; thus, for all n
;   fib(n) = (phi^n - psi^n)/sqrt(5)

;;;; helping some n00b on #scheme
(define count-oddV2 (lambda (lst)
  (cond ((null? lst) 0)
        ((odd? (car lst))
         (+ 1 (count-oddV2 (cdr lst))))
        (else
         (count-oddV2 (cdr lst))))))

;;;; exercise 1.14
; space: theta(n), growth/time: theta(n^5)

;;;; exercise 1.15
;; 1.15a
; 12.15 / 3 = 4.05
; 5 times
;; 1.15b
; theta(log(a))

;;;; exercise 1.16
; square defined in setup for 1.7
(define fast-exp2 (lambda (b n)
  (define fast-exp-iter (lambda (a x y)
     (cond ((= y 0) a)
           ((even? y) (fast-exp-iter a (square x) (/ y 2)))
           (else (fast-exp-iter (* x a) x (- y 1))))))
  (fast-exp-iter 1 b n)))

;;;; exercise 1.17
(define (double x)
  (* x 2))
(define (halve x)
  (/ x 2))
(define (fast-mult a b)
  (cond ((= b 0) 0)
        ((= b 1) a)
        ((even? b) (double (fast-mult a (halve b))))
        (else (+ a (fast-mult a (- b 1))))))

;;;; exercise 1.18
(define (fast-mult2 x y)
  (define (fast-mult2-iter n a b)
    (cond ((= b 0) 0)
          ((= b 1) (+ n a))
          ((even? b) (fast-mult2-iter n (double a) (halve b)))
          (else (fast-mult2-iter (+ n a) a (- b 1)))))
  (fast-mult2-iter 0 x y))

;;;; exercise 1.19
; Tpq(a, b) = { a <- bq + aq + ap
;             { b <- bp + aq
; Tp'q'(a, b) = { a <- (bp + aq)q + (bq + aq + ap)q + (bq + aq + ap)p
;               { b <- (bp + aq)p + (bq + aq + ap)q
; a' <- bpq + aqq + bqq + aqq + apq + bqp + aqp + app
;    <- b(pq + qq + pq) + a (pq + qq + pq) + a(pp + qq)
;    <- bq' + aq' + ap' | q' = 2pq + q^2, p' = p^2 + q^2
; b' <- bpp + aqp + bqq + aqq + apq
;    <- b(pp + qq) + a(pq + qq + pq)
;    <- bp' + aq' | p' = p^2 + q^2, q' = 2pq + q^2
(define (fib n)
  (fib-iter 1 0 0 1 n))
(define (fib-iter a b p q count)
  (cond ((= count 0) b)
        ((even? count)
         (fib-iter a
                   b
                   (+ (* p p) (* q q))   ; compute p'
                   (+ (* 2 p q) (* q q)) ; compute q'
                   (/ count 2)))
        (else (fib-iter (+ (* b q) (* a q) (* a p))
                        (+ (* b p) (* a q))
                        p
                        q
                        (- count 1)))))

;;;; exercise 1.20
;(define (gcd a b)
;  (if (= b 0) a
;      (gcd b (remainder a b))))
; This problem is a huge waste of time, so I'm skipping it.

;;;; exercise 1.21
(define (smallest-divisor n)
    (find-divisor n 2))
(define (find-divisor n test-divisor)
    (cond ((> (square test-divisor) n) n)
          ((divides? test-divisor n) test-divisor)
          (else (find-divisor n (+ test-divisor 1)))))
(define (divides? a b)
    (= (remainder b a) 0))
(smallest-divisor 199)
; 199
(smallest-divisor 1999)
; 1999
(smallest-divisor 19999)
; 7

;;;; exercise 1.22
; import slib's time library
(require 'common-lisp-time)
; replacing runtime in the book with current-time from slib
; rewrote the setup functions
(define (prime? n)
    (= n (smallest-divisor n)))
(define (prime-test n)
  (newline)
  (display n)
  (if (prime? n)
      (display " *** prime found"))
  n)
; note that this timing scheme only reports >0 sec with large n and/or c  
; values on a modern machine (couldn't find a time function in slib with
; millisecond precision)
(define (search-for-primes n c)
  (define (begin-search x z start-time)
    (if (< z c) (prime-test x))
    (if (prime? x) (set! z (+ z 1)))
    (if (< z c) (begin-search (+ x 1) z start-time)
        (- (current-time) start-time)))
  (if (even? n) (set! n (+ n 1)))
  (display (begin-search n 0 (current-time))))
(search-for-primes 10000 3)
; 10007, 10009, 10037
(search-for-primes 100000 3)
; 100003, 100019, 100043
(search-for-primes 1000000 3)
; 1000003, 1000033, 1000037

;;;; exercise 1.23
(define (next n)
  (if (= n 2) 3
      (+ n 2)))
(define (smallest-divisor2 n)
  (find-divisor2 n 2))
(define (find-divisor2 n test-divisor)
  (cond ((> (square test-divisor) n) n)
        ((divides? test-divisor n) test-divisor)
        (else (find-divisor n (next test-divisor)))))
(define (prime2? n)
  (= n (smallest-divisor2 n)))
(prime2? 10007)
(prime2? 10009)
(prime2? 10037)
(prime2? 100003)
(prime2? 100019)
(prime2? 100043)
(prime2? 1000003)
(prime2? 1000033)
(prime2? 1000037)

;;;; collatz sequences
(define (collatz n)
  (define (collatz-rec n i)
    (cond ((= n 1) i)
          ((even? n) (collatz-rec (/ n 2) (+ i 1)))
          (else (collatz-rec (+ (* n 3) 1) (+ i 1)))))
  (collatz-rec n 0))

;;;; exercise 1.24
(require 'random)
(define (expmod base exp m)
  (cond ((= exp 0) 1)
        ((even? exp)
         (remainder (square (expmod base (/ exp 2) m))
                    m))
        (else
         (remainder (* base (expmod base (- exp 1) m))
                    m))))
(define (fermat-test n)
  (define (try-it a)
    (= (expmod a n n) a))
  (try-it (+ 1 (random (- n 1)))))
(define (fast-prime? n times)
  (cond ((= times 0) #t)
        ((fermat-test n) (fast-prime? n (- times 1)))
        (else #f)))
; Lack of a good timing function is a bit annoying here, so I'm just skipping
; the timing metrics. The results are rather obvious anyway.
(fast-prime? 10007 100)
(fast-prime? 10009 100)
(fast-prime? 10037 100)
(fast-prime? 100003 100)
(fast-prime? 100019 100)
(fast-prime? 100043 100)
(fast-prime? 1000003 100)
(fast-prime? 1000033 100)
(fast-prime? 1000037 100)

;;;; exercise 1.25
(define (expmod2 base exp m)
  (remainder (fast-expt base exp) m))
