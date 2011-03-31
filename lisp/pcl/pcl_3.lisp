;;;; PCL
;;;; Ch 9: Unit Test Framework

;; A simple example test.
(defun test-+ ()
  (format t "~:[FAIL~;pass~] ... ~a~%" (= (+ 1 2) 3) '(= (+ 1 2) 3))
  (format t "~:[FAIL~;pass~] ... ~a~%" (= (+ 1 2 3) 6) '(= (+ 1 2 3) 6))
  (format t "~:[FAIL~;pass~] ... ~a~%" (= (+ -1 -3) -4) '(= (+ -1 -3) -4)))

;; Factor out the FORMAT calls.
(defun report-result (result form)
  (format t "~:[FAIL~;pass~] ... ~a~%" result form))

;; Now we redesign test-+ to use this.
(defun test-+ ()
  (report-result (= (+ 1 2) 3) '(= (+ 1 2) 3))
  (report-result (= (+ 1 2 3) 6) '(= (+ 1 2 3) 6))
  (report-result (= (+ -1 -3) -4) '(= (+ -1 -3) -4)))

;; Next, we get rid of the duplicate occurrences of the test functions by
;; creating a macro to treat expressions as both code and data.
(defmacro check (form)
  `(report-result ,form ',form))

;; Redesign test-+ again.
(defun test-+ ()
  (check (= (+ 1 2) 3))
  (check (= (+ 1 2 3) 6))
  (check (= (+ -1 -3) -4)))

;; Might as well get rid of repeated calls to check too.
(defmacro check (&body forms)
  `(progn
     ,@(loop for f in forms collect `(report-result ,f ',f))))

;; Another test-+ rewrite.
(defun test-+ ()
  (check
    (= (+ 1 2) 3)
    (= (+ 1 2 3) 6)
    (= (+ -1 -3) -4)))

;; Since we need to know whether our test run passed/failed overall, change
;; report-result to return the result value.
(defun report-result (result form)
  (format t "~:[FAIL~;pass~] ... ~a~%" result form)
  result)
;; To make use of this, we need to create a macro that keeps track of a running
;; result, but this introduces leaky abstraction problems, so we'll need
;; with-gensyms (originally created in pcl_1.lisp).
(defmacro with-gensyms ((&rest names) &body body)
  `(let ,(loop for n in names collect `(,n (gensym)))
     ,@body))
;; Defining combine-results:
(defmacro combine-results (&body forms)
  (with-gensyms (result)
    `(let ((,result t))
       ,@(loop for f in forms collect `(unless ,f (setf ,result nil)))
       ,result)))
;; Now replace PROGN with combine-results in check.
(defmacro check (&body forms)
  `(combine-results
    ,@(loop for f in forms collect `(report-result ,f ',f))))

;; We can introduce an intentional failure to test this:
(defun test-+ ()
  (check
    (= (+ 1 2) 3)
    (= (+ 1 2 3) -6)                    ; fails
    (= (+ -1 -3) -4)))

;; Organizing tests
;; Say we want a test case for *, we can define another set of tests for that.
(defun test-* ()
  (check
    (= (* 2 2) 4)
    (= (* 3 5) 15)))
;; Then create a master test function to call both.
(defun test-arithmetic ()
  (combine-results
    (test-+)
    (test-*)))
;; But, we might want our test framework to report the failing function, if we
;; have a lot.  Here's a top-level var to store the test name.
(defvar *test-name* nil)
;; And a change to report-result to format output differently.
(defun report-result (result form)
  (format t "~:[FAIL~;pass~] ... ~a: ~a~%" result *test-name* form)
  result)
;; Now we need to change our test functions to assign to the var.
(defun test-+ ()
  (let ((*test-name* 'test-+))
    (check
      (= (+ 1 2) 3)
      (= (+ 1 2 3) 6)
      (= (+ -1 -3) -4))))
(defun test-* ()
  (let ((*test-name* 'test-*))
    (check
      (= (* 2 2) 4)
      (= (* 3 5) 15))))

;; But, it's obvious from the above we have some duplicated code, so let's
;; abstract that.  This macro generates a defun.
(defmacro deftest (name parameters &body body)
  `(defun ,name ,parameters
     (let ((*test-name* ',name))
       ,@body)))
;; Now let's rewrite our tests yet again.
(deftest test-+ ()
  (check
    (= (+ 1 2) 3)
    (= (+ 1 2 3) 6)
    (= (+ -1 -3) -4)))
(deftest test-* ()
  (check
    (= (* 2 2) 4)
    (= (* 3 5) 15)))

;; Test heirarchies
;; Using deftest, we can test collections of tests, and have the collection
;; name in *test-name*.  Be sure to recompile the lower level test cases.
(defmacro deftest (name parameters &body body)
  `(defun ,name ,parameters
     (let ((*test-name* (append *test-name* (list ',name))))
       ,@body)))
(deftest test-arithmetic ()
  (combine-results
    (test-+)
    (test-*)))

;; Now we can build arbitrarily complex test case collections.
(deftest test-math ()
    (test-arithmetic))
