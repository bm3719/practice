;;;; PCL

;;; Ch 5: Functions

;; FUNCALL demonstration.  Accepts a function object as first argument and
;; plots an ASCII histogram of the values returned when invoked with min and
;; max, stepping by step, e.g. (plot #'exp 0 4 1/2)
(defun plot (fn min max step)
  (loop for i from min to max by step do
       (loop repeat (funcall fn i) do (format t "*"))
       (format t "~%")))
;; APPLY can be used to sorta do the same thing over variable argument lists.

;; LAMBDA
;; The following are equivalent:
;; (funcall #'(lambda (x y) (+ x y)) 2 3)
;; ((lambda (x y) (+ x y)) 2 3)
;; Another example:
;; (plot #'(lambda (x) (* 2 x)) 0 10 1)

;;; Ch 6: Variables

;; Note to self: Lisp is dynamically (but not loosely) typed and won't detect
;; the following error at compile time.
(defun testf (x y) (+ x y))
(defun testf2 ()
  (testf "sdf" 4))

;; LET
;; (let ((x 10) (y 5)) (+ x y)) => 15

;; LET scoping
(defun foo (x)
  (format t "Parameter: ~a~%" x)        ; |<------ x is argument 
  (let ((x 2))                          ; |
    (format t "Outer LET: ~a~%" x)      ; | |<---- x is 2
    (let ((x 3))                        ; | |
      (format t "Inner LET: ~a~%" x))   ; | | |<-- x is 3
    (format t "Outer LET: ~a~%" x))     ; | |
  (format t "Parameter: ~a~%" x))       ; |

;; LET* allows use of variables defined earlier in the arguments list:
;; (let* ((x 10)
;;        (y (+ x 10)))
;;   (list x y))
;; This is less ugly than creating nested LETs.

;; Lexical scoping
;; A closure can reference variables in the binding it closes over.
(defparameter *fn* (let ((count 0)) #'(lambda () (setf count (1+ count)))))
;; (funcall *fn*)
;; (funcall *fn*)

;; Dynamic/special variables
;; DEFVAR assigns initial value if undefined, DEFPARAMETER always assigns.
(defvar *count* 0
  "Count of widgets made so far.")
(defparameter *gap-tolerance* 0.001
  "Tolerance to be allowed in widget gaps.")

;; LET can be used to rebind globals within their scope, even when calling
;; expressions that normally would use the global scope if called alone.
(defvar *x* 10)
(defun foo2 () (format t "X: ~d~%" *x*)) ; outputs 10
(defun bar ()
  (foo2)                  ; 10
  (let ((*x* 20)) (foo2)) ; 20
  (foo2))                 ; 10

;; Constants
;; Be sure to name these in the form +constant-name+.
(defconstant +acceleration+ 9.8
  "Acceleration due to gravity.")

;; SETF
;; Sets a value within the current scope.  The following outputs 20.
;; (defun foo3 (x) (setf x 10))
;; (let ((y 20))
;;   (foo3 y)
;;   (print y))
;; Can also do multiple assignments like so:
;; (setf x 1 y 2)
;; And nest SETFs to get the same value in two places:
;; (setf x (setf y (random 10)))

;; AREF accesses array index.  GETHASH does hash lookup.
;; (setf (aref a 0) 10)
;; (setf (gethash 'key hash) 10)
;; (setf (field o) 10) ; Some user-defined object access function.

;; ROTATEF swaps values in place.
;; (rotatef a b)
;; SHIFTF shifts the values of all input variables to the left.
;; (shiftf a b 10) ; Returns value of a, a gets b's value, b gets 10.

;;; Ch 7: Macros

;; PROGN
;; Allows multiple sexps in a single form, e.g. performing multiple actions in
;; an if conditional.
;; (if (spam-p current-message)
;;     (progn
;;       (file-in-spam-folder current-message)
;;       (update-spam-database current-message)))

;; WHEN does the same as above:
;; (when (spam-p current-message)
;;   (file-in-spam-folder current-message)
;;   (update-spam-database current-message))
;; WHEN has a macro form similar to:
(defmacro my-when (condition &rest body)
  `(if ,condition (progn ,@body)))
;; Reminder: @ splices lists into form containing the @list.

;; UNLESS is the opposite and probably looks like:
(defmacro my-unless (condition &rest body)
  `(if (not ,condition) (progn ,@body)))

;; COND is another control structure:
;; (cond (a (do-x))
;;       (b (do-y))
;;       (t (do-z)))

;; DOLIST
;; (dolist (x '(1 2 3)) (print x))
;; Break out of a DOLIST with RETURN.
;; (dolist (x '(1 2 3)) (print x) (if (evenp x) (return)))

;; DOTIMES (RETURN works here too.)
;; (dotimes (i 4) (print i))

;; DO:
;; General looping construct that lets you specify all and as many of the
;; effects involved (loop vars, end test, end forms to eval).
;; (do (variable-definition*)
;;     (end-test-form result-form*)
;;   statement*)
;; Variable definitions take the form of (var init-form [step-form]).
(defun do-example ()
  (do ((n 0 (1+ n))
       (cur 0 next)
       (next 1 (+ cur next)))
      ((= 10 n) cur))) ; No body statements.
;; Another example that waits for a specific time.
(defun do-example-2 ()
  (do ()
      ((> (get-universal-time) *some-future-date*))
    (format t "Waiting~%")
    (sleep 60)))

;; LOOP
;; Basic LOOP iterates forever, requiring a RETURN, e.g.:
(defun loop-example-1 ()
  (loop
     (when (> (get-universal-time) *some-future-date*)
       (return))
     (format t "Waiting~%")
     (sleep 60)))

;; Extended LOOP includes various common looping idioms. This example collects
;; numbers from 1 to 10 in a list using DO.
(defun do-example-3 ()
  (do ((nums nil) (i 1 (1+ i)))
      ((> i 10) (nreverse nums))
    (push i nums))) ; ==> (1 2 3 4 5 6 7 8 9 10)
;; Here's the same with LOOP:
(defun extended-loop-example-1 ()
  (loop for i from 1 to 10 collecting i)) ; ==> (1 2 3 4 5 6 7 8 9 10)
;; More examples:
(defun extended-loop-example-2 ()
  (loop for x from 1 to 10 summing (expt x 2))) ; ==> 385
(defun extended-loop-example-3 ()
  (loop for x across "the quick brown fox jumps over the lazy dog"
     counting (find x "aeiou")))        ; ==> 11
;; Same as do-example, but using LOOP:
(defun extended-loop-example-4 ()
  (loop for i below 10
     and a = 0 then b
     and b = 1 then (+ b a)
     finally (return  a)))

;;; Ch 8: Writing macros

;; Steps for macro writing:
;;  1. Write a sample call to the macro and the code it should expand into, or
;;     vice versa.
;;  2. Write code that generates the handwritten expansion from the arguments
;;     in the sample call.
;;  3. Make sure the macro abstraction doesn't "leak."

;; do-primes helpers:
(defun primep (number)
  (when (> number 1)
    (loop for fac from 2 to (isqrt number) never (zerop (mod number fac)))))
(defun next-prime (number)
  (loop for n from number when (primep n) return n))

;; Step 1: We want a call to do-primes to look something like:
;; (do-primes (p 0 19)
;;   (format t "~d " p))

;; Step 2: The expanded version of the same code should look something like:
;; (do ((p (next-prime 0) (next-prime (1+ p))))
;;     ((> p 19))
;;   (format t "~d " p))

;; Step 3: Write the macro.
;; &body is a synonym of &rest.
(defmacro do-primes ((p min max) &body body)
  `(do ((,p (next-prime ,min) (next-prime (1+ ,p))))
       ((> ,p ,max))
     ,@body))

;; Test by macroexpanding the code in step 1:
;; CL-USER> (macroexpand-1 '(do-primes (p 0 19) (format t "~d " p)))
;; (DO ((P (NEXT-PRIME 0) (NEXT-PRIME (1+ P)))) ((> P 19)) (FORMAT T "~d " P))

;; Leaky abstractions
;; Here's a "proper" form of the above macro.
;; NOTES:
;; - Adding a variable for the end allows expressions passed into the "end"
;;   argument to only be evaluated once (since it appears twice in the macro).
;; - After adding that though, we need to ensure that end is evaluated before
;;   start, since this is the order in which they are passed in.
;; - Lastly, we need to use gensym to generate an used variable name since
;;   someone could inadvertently pass in a symbol also named what we called our
;;   ending-value.
(defmacro do-primes ((var start end) &body body)
  (let ((ending-value-name (gensym)))
    `(do ((,var (next-prime ,start) (next-prime (1+ ,var)))
          (,ending-value-name ,end))
         ((> ,var ,ending-value-name))
       ,@body)))
;; This addresses the following macro good practices:
;; 1. Unless there's a particular reason to do otherwise, include any subforms
;;    in the expansion in positions that will be evaluated in the same order 
;;    as the subforms appear in the macro call.
;; 2. Unless there's a particular reason to do otherwise, make sure subforms 
;;    are evaluated only once by creating a variable in the expansion to hold
;;    the value of evaluating the argument form and then using that variable 
;;    anywhere else the value is needed in the expansion.
;; 3. Use GENSYM at macro expansion time to create variable names used in the
;;    expansion.

;; Macro-writing macros
(defmacro with-gensyms ((&rest names) &body body)
  `(let ,(loop for n in names collect `(,n (gensym)))
     ,@body))

;; Now we can rewrite do-primes to use this.
(defmacro do-primes ((var start end) &body body)
  (with-gensyms (ending-value-name)
    `(do ((,var (next-prime ,start) (next-prime (1+ ,var)))
          (,ending-value-name ,end))
         ((> ,var ,ending-value-name))
       ,@body)))


