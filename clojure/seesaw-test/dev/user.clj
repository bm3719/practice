;;;; This is a dev namespace to contain utility functions useful for
;;;; interacting with the data at the REPL, or for general development.
(ns user
  (:require [seesaw-test.core :as core]
            [seesaw.core :as sc]
            [seesaw.font :as sf]
            [clojure.repl :refer [doc]]
            [clojure.tools.namespace.repl :as repl]))

;; Tell seesaw to put UI elements in their OS-specific places.
(sc/native!)

;; Create a new frame.
(def f (sc/frame :title "Test Application"))

;; Display the frame.
(-> f sc/pack! sc/show!)

;; Query widget properties.
(sc/config f :title)

;; Modify widget properties.
(sc/config! f :title "Super Test Application")

;; Give frame some content.  This creates a label.
(sc/config! f :content "This is some content.")

;; A label can be manually created.
(def lbl (sc/label "I'm a label."))
(sc/config! f :content lbl)

;; Create a function to add a label to the frame.
(defn display [content]
  (sc/config! f :content content)
  content)

(display lbl)

;; seesaw knows about CSS color names and codes.  Also, multiple properties can
;; be set with one config! call.
(sc/config! lbl :background :pink :foreground "#00f")

;; Change the font.  This uses "FAMILY-STYLE-SIZE" strings.
(sc/config! lbl :font "ARIAL-BOLD-21")

;; seesaw.font/font gives a little more control.
(sc/config! lbl :font (sf/font :name :monospaced
                               :style #{:bold :italic}
                               :size 18))

;; Buttons
(def b (sc/button :text "Click Me"))
