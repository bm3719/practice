;;;; Ch 15: A Portable Pathname Library

;; *FEATURES* contains a bunch of environment keywords.  Use read-time
;; conditionalization to detect them.
(defun feature-check ()
  #+allegro (format t "allegro")
  #+sbcl (format t "sbcl")
  #+clisp (format t "clisp")
  #+cmu (format t "cmu")
  #-(or allegro sbcl clisp cmu) (format t "error: not implemented"))

;; Package declarations for the library.
(in-package :cl-user)

(defpackage :com.gigamonkeys.pathnames
  (:use :common-lisp)
  (:export
   :list-directory
   :file-exists-p
   :directory-pathname-p
   :file-pathname-p
   :pathname-as-directory
   :pathname-as-file
   :walk-directory
   :directory-p
   :file-p))
(in-package :com.gigamonkeys.pathnames)

;; Directory listing

;; This deals with the problem of pathnames dropping the leaf node if in file
;; form, e.g.
;; (directory (make-pathname :name :wild :type :wild :defaults "/home/bm3719"))
;; returns the contents of /home, whereas this works
;; (directory (make-pathname :name :wild :type :wild :defaults "/home/bm3719/"))

;; Check if a component of a pathname exists.
(defun component-present-p (value)
  (and value (not (eql value :unspecific))))
;; Checks if pathname in directory form already.
(defun directory-pathname-p  (p)
  (and
   (not (component-present-p (pathname-name p)))
   (not (component-present-p (pathname-type p)))
   p))
;; Converts any pathname to directory form.
(defun pathname-as-directory (name)
  (let ((pathname (pathname name)))
    (when (wild-pathname-p pathname)
      (error "Can't reliably convert wild pathnames."))
    (if (not (directory-pathname-p name))
        (make-pathname
         :directory (append (or (pathname-directory pathname) (list :relative))
                            (list (file-namestring pathname)))
         :name      nil
         :type      nil
         :defaults pathname)
        pathname)))
;; clisp doesn't like :type :wild.  Instead requires :type :nil.
(defun directory-wildcard (dirname)
  (make-pathname
   :name :wild
   :type #-clisp :wild #+clisp nil
   :defaults (pathname-as-directory dirname)))

;; First attempt at a list-directory function.
(defun list-directory (dirname)
  (when (wild-pathname-p dirname)
    (error "Can only list concrete directory names."))
  (directory (directory-wildcard dirname)))

;; This works for me in SBCL, but OpenMCL won't list subdirs by default unless
;; you pass :directories to DIRECTORY.  clisp requires :wild as the last
;; element in the directory component and NIL in the name and type components.
;; Lastly, we also need all versions to return subdirs in directory form, but
;; Allegro requires the implementation-specific keyword :directories-are-files
;; set to NIL.
(defun list-directory (dirname)
  (when (wild-pathname-p dirname)
    (error "Can only list concrete directory names."))
  (let ((wildcard (directory-wildcard dirname)))
    #+(or sbcl cmu lispworks)
    (directory wildcard)

    #+openmcl
    (directory wildcard :directories t)

    #+allegro
    (directory wildcard :directories-are-files nil)

    #+clisp
    (nconc
     (directory wildcard)
     (directory (clisp-subdirectories-wildcard wildcard)))

    #-(or sbcl cmu lispworks openmcl allegro clisp)
    (error "list-directory not implemented")))

;; The above uses a function to collect clisp-specific stuff, so let's defun
;; it, but only for clisp.
#+clisp
(defun clisp-subdirectories-wildcard (wildcard)
  (make-pathname
   :directory (append (pathname-directory wildcard) (list :wild))
   :name nil
   :type nil
   :defaults wildcard))

;; Testing file existence: Creating a custom, portable version of PROBE-FILE,
;; called file-exists-p.

;; SBCL works out of the box, but CMUCL and Allegro need some extra work.
;; clisp PROBE-FILE will error if given a directory form, so we'll need to
;; convert those to file form.
(defun file-exists-p (pathname)
  #+(or sbcl lispworks openmcl)
  (probe-file pathname)
  #+(or allegro cmu)
  (or (probe-file (pathname-as-directory pathname))
      (probe-file pathname))

  #+clisp
  (or (ignore-errors
        (probe-file (pathname-as-file pathname)))
      (ignore-errors
        (let ((directory-form (pathname-as-directory pathname)))
          (when (ext:probe-directory directory-form)
            directory-form))))

  #-(or sbcl cmu lispworks openmcl allegro clisp)
  (error "file-exists-p not implemented"))

;; Clisp needs this.
(defun pathname-as-file (name)
  (let ((pathname (pathname name)))
    (when (wild-pathname-p pathname)
      (error "Can't reliably convert wild pathnames."))
    (if (directory-pathname-p name)
      (let* ((directory (pathname-directory pathname))
             (name-and-type (pathname (first (last directory)))))
        (make-pathname
         :directory (butlast directory)
         :name (pathname-name name-and-type)
         :type (pathname-type name-and-type)
         :defaults pathname))
      pathname)))

;; Walk a directory tree.  This is used later in the book.
(defun walk-directory (dirname fn &key directories (test (constantly t)))
  (labels
      ((walk (name)
         (cond
           ((directory-pathname-p name)
            (when (and directories (funcall test name))
              (funcall fn name))
            (dolist (x (list-directory name)) (walk x)))
           ((funcall test name) (funcall fn name)))))
    (walk (pathname-as-directory dirname))))
