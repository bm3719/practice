;; PCL
;; Ch 3: CD Database example

;; NOTES:
;; - plist: (list :a 1 :b 2 :c 3)
;; - getf: Grabs at keyword, e.g. (getf '(:a 2 :b 4 :c 9) :b)

;; A single CD record, using a plist.
(defun make-cd (title artist rating ripped)
  (list :title title :artist artist :rating rating :ripped ripped))

;; A database we can add records to, as a global variable.
(defun *db* nil)

;; Normally, we'd use PUSH to put items on it, but we can wrap that into a new
;; function for readability, since we'll always use *db*.
(defun add-record (cd) (push cd *db*))

;; Format output of *db* contents for easy eyeballing.  This uses the DOLIST
;; macro which applies FORMAT to each element.

;; Regarding format directives, these all start with ~.  ~a is the aesthetic
;; directive, which converts keywords into text and prints strings.  ~10 tells
;; FORMAT to allow ten spaces before the next item.  ~t tabulates.  ~{ ~} tells
;; FORMAT to iterative the inner directives over list elements.  ~% is newline.
(defun dump-db ()
  (dolist (cd *db*)
    (format t "~{~a:~10t~a~%~}~%" cd)))
;; This could also be written as:
;; (defun dump-db ()
;;   (format t "~{~{~a:~10t~a~%~}~%~}" *db*))

;; Create a TUI for entering a string.  *query-io* is a TWO-WAY-STREAM.  Using
;; it as the FORMAT destination uses the output stream part.  FORCE-OUTPUT
;; ensures Lisp doesn't wait for a newline before printing the prompt.
;; READ-LINE makes the output of this function return the input string w/o the
;; newline.
(defun prompt-read (prompt)
  (format *query-io* "~a: " prompt)
  (force-output *query-io*)
  (read-line *query-io*))

;; Combine make-cd and prompt-read so input creates a CD record.  The
;; :junk-allowed keyword on PARSE-INTEGER causes it to suppress errors.
;; However, non-parsable strings will return NIL, so we default to 0 in those
;; cases.  For booleans, Y-OR-N-P handles all this stuff for us.
(defun prompt-for-cd ()
  (make-cd
   (prompt-read "Title")
   (prompt-read "Artist")
   (or (parse-integer (prompt-read "Rating") :junk-allowed t) 0)
   (y-or-n-p "Ripped [y/n]: ")))

;; Keep adding CDs until the user is done.  Uses the LOOP macro.
(defun add-cds ()
  (loop (add-record (prompt-for-cd))
     (if (not (y-or-n-p "Another? [y/n]: ")) (return))))

;; Save *db* to a file.  Second param to the WITH-OPEN-FILE macro isn't a
;; function but some flags.  WITH-STANDARD-IO-SYNTAX is a macro that ensures
;; PRINT's variables are set in a standard way.
(defun save-db (filename)
  (with-open-file (out filename
                       :direction :output
                       :if-exists :supercede)
    (with-standard-io-syntax
      (print *db* out))))

;; Load back in the database.  SETF is an assignment operator.
(defun load-db (filename)
  (with-open-file (in filename)
    (with-standard-io-syntax
      (setf *db* (read in)))))

;; An example of the use of REMOVE-IF-NOT.  #'x is syntax for `the function of
;; the name x'.
;;(remove-if-not #'evenp '(1 2 3 4 5 6 7 8 9 10))
;; Here's the same sexp with a lambda expression.
;;(remove-if-not #'(lambda (x) (= 0 (mod x 2))) '(1 2 3 4 5 6 7 8 9 10))

;; Query the database based on keyword.  This is a little different than the
;; artist-specific one in the book.
(defun select (keyword value)
  (remove-if-not #'(lambda (x) (equal (getf x keyword) value)) *db*))

;; Here's the book version (needed for the WHERE demonstration later).
(defun select-by-artist (artist)
  (remove-if-not
   #'(lambda (cd) (equal (getf cd :artist) artist))
   *db*))
(defun select (selector-fn)
  (remove-if-not selector-fn *db*))

;; Keyword parameters.
(defun foo (&key a b c) (list a b c))
;; (foo :a 1 :b 2 :c 3)  ==> (1 2 3)
;; (foo :c 3 :b 2 :a 1)  ==> (1 2 3)
;; (foo :a 1 :c 3)       ==> (1 NIL 3)
;; (foo)                 ==> (NIL NIL NIL)

;; supplied-p parameters.
(defun foo (&key a (b 20) (c 30 c-p)) (list a b c c-p))
;; (foo)         ==> (NIL 20 30 NIL)
;; (foo :c NIL)  ==> (NIL 20 NIL T)

;; Allow multiple keyword selections from SELECT.
(defun where (&key title artist rating (ripped nil ripped-p))
  #'(lambda (cd)
      (and
       (if title    (equal (getf cd :title)  title)  t)
       (if artist   (equal (getf cd :artist) artist) t)
       (if rating   (equal (getf cd :rating) rating) t)
       (if ripped-p (equal (getf cd :ripped) ripped) t))))
;; Now you can do something like:
;; (select (where :artist "Me" :rating 10))

;; Update a record in the database.  MAPCAR is like MAP but only works on lists.
(defun update (selector-fn &key title artist rating (ripped nil ripped-p))
  (setf *db*
        (mapcar
         #'(lambda (row)
             (when (funcall selector-fn row)
               (if title    (setf (getf row :title) title))
               (if artist   (setf (getf row :artist) artist))
               (if rating   (setf (getf row :rating) rating))
               (if ripped-p (setf (getf row :ripped) ripped)))
             row) *db*)))

;; Delete rows from database.
(defun delete-rows (selector-fn)
  (setf *db* (remove-if selector-fn *db*)))

;; Demonstration of macros.
(defmacro backwards (expr) (reverse expr))
;; Can be used on something like this:
;; (backwards (4 3 +))

;; A function to generate expressions equivalent to previously used
;; conditionals like: (equal (getf cd :ripped) ripped)
;; Backquote is used here to flag everything within as non-evaluated.  Comma
;; excludes this behavior.
(defun make-comparison-expr (field value)
  `(equal (getf cd ,field) ,value))

;; A version closer to what we want, which generates a list of the above
;; expressions, in a single list.  It assumes that the fields will be sent as a
;; parameter of the form '(:artist artist :rating rating), which isn't exactly
;; the final form.
(defun make-comparisons-list (fields)
  (loop while fields
     collecting (make-comparison-expr (pop fields) (pop fields))))

;; Wrap make-comparison-list in an AND and an anonymous function.  &rest
;; indicates an arbitrary number of arguments, which are joined into a list and
;; passed to the function/macro.  The ,@ splices an element into a list,
;; e.g. `(and ,@(list 1 2 3)) => (AND 1 2 3)
(defmacro where (&rest clauses)
  `#'(lambda (cd) (and ,@(make-comparisons-list clauses))))
;; This will return an unevaluated expression.  To examine it, send it to
;; MACROEXPAND-1, e.g. (macroexpand-1 '(where :title "Roses" :ripped t))
