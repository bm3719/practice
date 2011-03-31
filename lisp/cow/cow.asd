;;;; -*- Mode: Lisp; Syntax: ANSI-Common-Lisp; Base: 10 -*-
;; This be a test project to learn asdf.

(defpackage #:cow-asd
  (:use :cl :asdf))

(in-package :cow-asd)

;; defsystem form
;; Only first line mandatory.
(defsystem cow
  :name "cow"
  :version "0.0.0"
  :maintainer "T. God"
  :author "Desmon Table"
  :licence "BSD sans advertising clause (see file COPYING for details)"
  :description "Cow"
  :long-description "Lisp implementation of our favorite ruminant")
  
