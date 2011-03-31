;;; Ch 10: Numbers, Characters, and Strings

;;; Numbers

;; Complex numbers are of the form a + bi and represented in Lisp as #c(12 3)
;; where the second numeric is the imaginary component.

;; Use the /= function for `not equals'.

;;; Characters

;; Single character syntax: #\x => x

;; Some characters have names, like Space, Tab, Page, Rubout, Linefeed, Return,
;; Backspace.  e.g. #\Space

;; For comparing characters, use the following table of functions:
;; Numeric Analog Case-Sensitive Case-Insensitive  
;; =              CHAR=          CHAR-EQUAL        
;; /=             CHAR/=         CHAR-NOT-EQUAL    
;; <              CHAR<          CHAR-LESSP        
;; >              CHAR>          CHAR-GREATERP     
;; <=             CHAR<=         CHAR-NOT-GREATERP 
;; >=             CHAR>=         CHAR-NOT-LESSP

;;; Strings

;; For string comparisons, use the following table of functions:
;; Numeric Analog Case-Sensitive Case-Insensitive    
;; =              STRING=        STRING-EQUAL        
;; /=             STRING/=       STRING-NOT-EQUAL    
;; <              STRING<        STRING-LESSP        
;; >              STRING>        STRING-GREATERP     
;; <=             STRING<=       STRING-NOT-GREATERP 
;; >=             STRING>=       STRING-NOT-LESSP

;; Can use indexes for substring comparisons like so.  Start values are
;; inclusive, end values are exclusive (or nil to indicate end of string).
(defun string-compare-ex ()
  (string= "foobarbaz" "quuxbarfoo" :start1 3 :end1 6 :start2 4 :end2 7))

;;; CH 11: Collections

;; Vectors

;; Fixed-size vectors:
;; (vector)     ==> #()
;; (vector 1)   ==> #(1)
;; (vector 1 2) ==> #(1 2)

;; MAKE-ARRAY: More general, capable of any dimensionality, and can create
;; fixed or variable sized vectors.  Use :intial-element to initialize values.
;; Use :fill-pointer for variable sized arrays (the fill pointer keeps track of
;; the index of the next position).
;; (defparameter *x* (make-array 5 :fill-pointer 0))  ; max of 5 elements.
;; (vector-push 'a *x*) ==> 0
;; *x*                  ==> #(A)
;; (vector-push 'b *x*) ==> 1
;; *x*                  ==> #(A B)
;; (vector-push 'c *x*) ==> 2
;; *x*                  ==> #(A B C)
;; (vector-pop *x*)     ==> C
;; *x*                  ==> #(A B)
;; Add :adjustable t to make vector infinitely resizable.  Use
;; VECTOR-PUSH-EXTEND instead of VECTOR-PUSH.
;; (make-array 5 :fill-pointer 0 :adjustable t :element-type 'numeric)

;; Sequence functions: LENGTH, ELT.  Note that ELT returns an element type, so
;; (setf (elt *x* 0) 10) works.

;; More functions: COUNT, FIND, POSITION, REMOVE, SUBSTITUTE. All mostly 
;; self-explanatory. Examples:
;; (count "foo" #("foo" "bar" "baz") :test #'string=)    ==> 1
;; (find 'c #((a 10) (b 20) (c 30) (d 40)) :key #'first) ==> (C 30)

;; Higher-order function variants
;; For each of the above functions, Lisp provides 2 higher-order versions.
;; Seems more Lispy to me.
;; (count-if #'evenp #(1 2 3 4 5))         ==> 2
;; (count-if-not #'evenp #(1 2 3 4 5))     ==> 3
;; (position-if #'digit-char-p "abcd0001") ==> 4
;; (remove-if-not #'(lambda (x) (char= (elt x 0) #\f))
;;   #("foo" "bar" "baz" "foom")) ==> #("foo" "foom")

;; REMOVE-DUPLICATES
;; (remove-duplicates #(1 2 1 2 3 1 2 3 4)) ==> #(1 2 3 4)

;; Whole sequence manipulation: CONCATENATE, REVERSE, COPY-SEQ.
;; (concatenate 'vector #(1 2 3) '(4 5 6))    ==> #(1 2 3 4 5 6)
;; (concatenate 'list #(1 2 3) '(4 5 6))      ==> (1 2 3 4 5 6)
;; (concatenate 'string "abc" '(#\d #\e #\f)) ==> "abcdef"
;; Note that REVERSE and COPY-SEQ don't deep copy.

;; Sorting and Merging
;; SORT, STABLE-SORT:
;; (sort (vector "foo" "bar" "baz") #'string<) ==> #("bar" "baz" "foo")
;; Assigning a sorted sequence:
;; (setf my-sequence (sort my-sequence #'string<))
;; Merging only creates a sorted sequence if both inputs are sorted.
;; (merge 'list #(1 3 5) #(2 4 6) #'<)

;; Subsequent Manipulations
;; SUBSEQ (also SETF-able).
;; (subseq "foobarbaz" 3)   ==> "barbaz"
;; (subseq "foobarbaz" 3 6) ==> "bar"
;; (defparameter *x* (copy-seq "foobarbaz"))
;; (setf (subseq *x* 3 6) "xxx")  ; subsequence and new value are same length
;; *x* ==> "fooxxxbaz"
;; (setf (subseq *x* 3 6) "abcd") ; new value too long, extra character ignored.
;; *x* ==> "fooabcbaz"
;; (setf (subseq *x* 3 6) "xx")   ; new value too short, only two characters changed
;; *x* ==> "fooxxcbaz"
;; Also: FILL, SEARCH (like POSITION but takes two sequences), MISMATCH
;; (returns index at first mismatch).

;; Sequence Predicates: EVERY, SOME, NOTANY, NOTEVERY.
;; (every #'evenp #(1 2 3 4 5))    ==> NIL      ; one seq
;; (every #'> #(1 2 3 4) #(5 4 3 2))    ==> NIL ; pairwise

;; Mapping functions

;; MAP
;; (map 'vector #'(lambda (x) (+ x 5)) #(1 2 3 4))
;; (map 'vector #'* #(1 2 3 4 5) #(10 9 8 7 6)) ==> #(10 18 24 28 30)

;; MAP-INTO
;; e.g., if a b c are vectors, this sums them into a:
;; (map-into a #'+ a b c)

;; REDUCE
;; (reduce #'+ #(1 2 3 4 5 6 7 8 9 10)) ==> 55

;; Hash tables

;; Use MAKE-HASH-TABLE to create.  If making a hash table with strings as keys,
;; use :test 'equal.
;; (defparameter *h* (make-hash-table))
;; (gethash 'foo *h*) ==> NIL
;; (setf (gethash 'foo *h*) 'quux)
;; (gethash 'foo *h*) ==> QUUX

;; Since GETHASH returns multiple values, you can use MULTIPLE-VALUE-BIND to
;; grab them (otherwise you just get the first value).
(defun show-value (key hash-table)
  (multiple-value-bind (value present) (gethash key hash-table)
    (if present
        (format nil "Value ~a actually present." value)
        (format nil "Value ~a because key not found." value))))
;; Also note: REMHASH and CLRHASH.

;; Iterating over a hash table
;; MAPHASH: Like MAP.
;; (maphash #'(lambda (k v) (format t "~a => ~a~%" k v)) *h*)
;; This removes all values less than 10.
;; (maphash #'(lambda (k v) (when (< v 10) (remhash k *h*))) *h*)

;;; Ch 12: Lists

;; CONS, CAR, CDR... Already know most of this stuff.

;; Note that destructive operations can change shared state like in this
;; example:
;; (defparameter *list-1* (list 1 2))
;; (defparameter *list-2* (list 3 4))
;; (defparameter *list-3* (append *list-1* *list-2*))
;; Now *list-2* is (3 4) and *list-3* is (1 2 3 4)
;; (setf (first *list-2*) 0)
;; *list-2* ==> (0 4)
;; *list-3* ==> (1 2 0 4)

;; What's happening here is that the lists from all former parameters to APPEND
;; are duplicated, and strung together.  Then, the last parameter has it's
;; contents pointed to by this new list, and that's what's returned.  Kinda
;; lame, but that's how it is.  Always be on the lookout for functions that
;; return data that includes the original structure of input.

;; Some functions edit values in place intentionally to return results.  These
;; are non-consing versions, usually prepended with `N'.
;; CL-USER> (reverse *l4*)
;; (7 0 5 4 3 2 1)
;; CL-USER> *l4*
;; (1 2 3 4 5 0 7)
;; CL-USER> (nreverse *l4*)
;; (7 0 5 4 3 2 1)
;; CL-USER> *l4*
;; (1)
;; The end results of what's in the list after most N-functions are not
;; specified, so don't depend on them.

;; An exception is NCONC, which is an example of a function that always reuses
;; everything.  (Also NSUBSTITUTE).
;; (defparameter *x* (list 1 2 3))
;; (nconc *x* (list 4 5 6)) ==> (1 2 3 4 5 6)
;; *x* ==> (1 2 3 4 5 6)

;; Note to self: Use something like copy-seq when mutating sequences in this
;; way.

;; Idiomatic example of consing in practice.
(defun upto (max)
  (let ((result nil))
    (dotimes (i max)
      (push i result))
    (nreverse result)))

;; List-manipulation functions

;; NTH, NTHCDR, LAST, BUTLAST, LDIFF, TAILP, LIST*, ATOM, LISTP, NULL, etc.

;; List mapping

;; MAPCAR: Same as MAP with list type specified.
;; MAPLIST: Function called on each element has access to the CDR of the rest
;; of the list.
;; MAPCAN, MAPCON: Same as MAPCAR and MAPLIST, except they recycle.
;; MAPC, MAPL: Control constructs (used as functions) that are like MAPCAR and
;; MAPLIST, except just return the first argument and are used when the
;; function parameter does some kind of side effect.


;;; Ch 13. More Cons cell structures

;; Lists as Trees

;; COPY-TREE deep copies trees.
;; TREE-EQUAL does a full walk.
;; SUBST replaces elements anywhere in a tree.  Includes :key and :test
;; keywords.  (See also, SUBST-IF, SUBST-IF-NOT, NSUBST, etc.)


;; Lists as Sets

;; ADJOIN: Returns set with new item if not exist.
;; PUSHNEW: Same but modifies in place.
;; MEMBER, MEMBER-IF, MEMBER-IF-NOT: s/e
;; INTERSECTION, UNION, SET-DIFFERENCE, SET-EXCLUSEIVE-OR, SUBSETP: s/e

;; Alists, Plists

;; alist
;; (defparameter *a1* '((A . 1) (B . 2) (C . 3)))
;; (assoc 'a *a1*)  ==> (A . 1)
;; (cdr (assoc 'a *a1*))  ==> 1

;; These use EQL for comparisons, so for strings, use :test.
;; (assoc "a" '(("a" . 1) ("b" . 2) ("c" . 3)) :test #'string=)
;; ("a" . 1)

;; Adding a new value:
;; (cons (cons 'new-key 'new-value) alist)
;; (acons 'new-key 'new-value alist)  ; same thing
;; But, these can't modify in place values, so:
;; (setf alist (acons 'new-key 'new-value alist))
;; (push (cons 'new-key 'new-value) alist)  ; same thing

;; ASSOC-IF, ASSOC-IF-NOT: Return first value satisfying some predicate.
;; RASSOC, RASSOC-IF, RASSOC-IF-NOT: Reverse versions of the above.

;; COPY-ALIST: Deep copies, but only for the list structure (not the objects
;; contained therein.)

;; PAIRLIS: Note that order of the resulting alist is not guaranteed.
;; (pairlis '(a b c) '(1 2 3))
;; ((C . 3) (B . 2) (A . 1))

;; plist: A list with the properties and values as interchanging elements in a
;; list.

;; (defparameter *plist* ())
;; (setf (getf *plist* :a) 1) ==> (:a 1)
;; (setf (getf *plist* :a) 2) ==> (:a 2)
;; (remf *plist* :a) ==> T
;; *plist* ==> NIL

;; GET-PROPERTIES
(defparameter *p1* '(:a 1 :b 2))
(defparameter *keys* '(:a :b))
(defun process-property (key value)
  (format t "~a ~a~%" key value))
(defun process-properties (plist keys)
  (loop while plist do
       (multiple-value-bind (key value tail) (get-properties plist keys)
         (when key (process-property key value))
         (setf plist (cddr tail)))))
;; (process-properties *p1* *keys*)

;; GET, REM handier than GETF, REMF for plists:
;; (get 'symbol 'key) === (getf (symbol-plist 'symbol) 'key)
;; (remprop 'symbol 'key) === (remf (symbol-plist 'symbol key))

;; DESTRUCTURING-BIND Macro: Destructure arbitrary lists.
;; (destructuring-bind (a b c) (list 1 '(2 3) '(4 5 '(6 7)))
;;   (list :a a :b b :c c))
;; (:A 1 :B (2 3) :C (4 5 '(6 7)))
;; This example uses keywords, including &whole.
;; (destructuring-bind (&whole whole &key x y z) (list :z 1 :y 2 :x 3)
;;   (list :x x :y y :z z :whole whole))
;; (:X 3 :Y 2 :Z 1 :WHOLE (:Z 1 :Y 2 :X 3))

;; Note to self: Use TRACE and UNTRACE to toggle tracing of functions.

;;; Ch 14: Files

;; Reading Files: OPEN, READ, READ-LINE, READ-CHAR, CLOSE.
;; A simple example (prints first line):
;; (let ((in (open "/some/file/name.txt")))
;;   (format t "~a~%" (read-line in))
;;   (close in))
;; Error-checking version of the above:
;; (let ((in (open "/some/file/name.txt" :if-does-not-exist nil)))
;;   (when in
;;     (format t "~a~%" (read-line in))
;;     (close in)))

;; READ-* functions can take a 3rd optional parameter that, if set, will return
;; that value instead of an error when they hit EOF.  This is useful if you
;; want to read in all lines of a file.
(defun full-file-read ()
  (let ((in (open "/home/bm3719/swank.lisp" :if-does-not-exist nil)))
    (when in
      (loop for line = (read-line in nil)
         while line do (format t "~a~%" line))
      (close in))))

;; READ: reads in sexps. 
;; (defparameter *s* (open "/some/file/name.txt"))
;; *S*
;; CL-USER> (read *s*)
;; (1 2 3)
;; Note: READ and PRINT are a good way to read in sexp data, like in a config
;; file for an app.

;; Reading binary data
;; Flag OPEN with :element-type '(unsigned-byte 8), then use READ-BYTE, which
;; will give you an integer between 0-255 for each read.

;; Bulk reads
;; Pass READ-SEQUENCE a sequence (like a vector) and a stream, and it fills the
;; sequence with the data.

;; File output
;; Call OPEN with :direction :output. :if-exists :supercede will overwrite.
;; WRITE-CHAR, WRITE-LINE, FRESH-LINE, TERPRI.
;; PRINT, PPRINT, PRIN1 prints sexps.
;; Global *PRINT-READABLY* if set to T will error if outputting something READ
;; won't understand.
;; PRINC prints lisp objects in human readable form (i.e. strings w/o quotes.)
;; Binary data writing: same flags as reading.
;; Also supports bulk writes with WRITE-SEQUENCE.

;; Closing files
;; The above examples include poor management of file handles, since if an
;; error occurs or a RETURN exists in the stream-handling, these handles can be
;; left open.  A safer way of writing file code is using WITH-OPEN-FILE, which
;; is built upon UNWIND-PROTECT.
(defun full-file-load ()
  (with-open-file (stream "/home/bm3719/swank.lisp") :if-does-not-exist nil
                  (loop for line = (read-line stream nil)
                     while line do (format t "~a~%" line))))

;; Filenames
;; Pathnames provide a portable interface to files.  However, they support all
;; manner of obsolete filesystems and introduce a little unecessary complexity
;; when just dealing with *nix/windows.
;; CL-USER> (pathname "/home/bm3719/swank.lisp")
;; #P"/home/bm3719/swank.lisp"
;; CL-USER> (pathname-type *)
;; "lisp"
;; CL-USER> (pathname-name **)
;; "swank"
;; CL-USER> (pathname-directory ***)
;; (:ABSOLUTE "home" "bm3719")
;; CL-USER> (pathname-device #P"/home/bm3719/swank.lisp")
;; NIL
;; This last example would only be valid on Windows.  Device = drive letter.

;; MAKE-PATHNAME with :defaults is a good way to make protable pathnames.
;; (make-pathname :directory '(:relative "backups")
;;                :defaults #p"/foo/bar/baz.txt") ==> #p"backups/baz.txt"
;; MERGE-PATHNAMES is handy for concatenating directories.
;; (merge-pathnames #p"foo/bar.html" #p"/www/html/")
;;   ==> #p"/www/html/foo/bar.html"
;; ENOUGH-NAMESTRING does the opposite.
;; (enough-namestring #p"/www/html/foo/bar.html" #p"/www/")
;;   ==> "html/foo/bar.html"

(defun full-file-load ()
  (with-open-file (stream (pathname "/home/bm3719/swank.lisp")) :if-does-not-exist nil
                  (loop for line = (read-line stream nil)
                     while line do (format t "~a~%" line))))

;; File system interaction: Finding out ownership, modified dates, length, etc.

;; PROBE-FILE: Use to test the existence of a file by pathname. Returns a
;; pathname with all of its various attributes populated correctly if exists.
;; DIRECTORY: Similar to PROBE-FILE for directories, but not always portable.
;; DELETE-FILE, RENAME-FILE: s/e
;; ENSURE-DIRECTORIES-EXIST: Creates directories as necessary.
;; FILE-WRITE-DATE: Returns moddate in number or sec since 1/1/1900.
;; FILE-AUTHOR: Returns file owner.
;; FILE-LENGTH: Takes a stream instead of pathname.  Returns length in bytes.
;; FILE-POSITION: Current position in a stream.

;; Other I/O

;; String streams:
;; WITH-INPUT-FROM-STRING, WITH-OUTPUT-TO-STRING: Wraps
;; MAKE-STRING-INPUT-STREAM and MAKE-STRING-OUTPUT-STREAM which require
;; UNWIND-PROTECT.
;; (with-input-from-string (s "1.23")
;;   (read s))  ; returns a float
;; (with-output-to-string (out)
;;   (format out "hello, world ")
;;   (format out "~s" (list 1 2 3))) ; good way to collect string data

;; Stream plumbing:
;; BROADCAST-STREAM: Output stream that sends data to other output streams
;; specified by MAKE-BROADCAST-STREAM.
;; CONCATENATED-STREAM: Same as above, but for input.

;; Bidirectional streams:
;; TWO-WAY-STREAM, MAKE-TWO-WAY-STREAM, ECHO-STREAM, MAKE-ECHO-STREAM: Take an
;; input and output stream and return a bidirectional one.  Thereafter, writes
;; go to output, reads are from input. The echo type also echos.

;; There's also some implementation-provided network streams.


