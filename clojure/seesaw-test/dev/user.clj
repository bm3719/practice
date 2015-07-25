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
(def f (sc/frame :title "Test Application"
                 :minimum-size [640 :by 480]))

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

;; Display an alert.
;; (sc/alert "I'm an alert")

;; Get input from user.  Return value is input.  This function blocks until
;; returning.
;; (sc/input "What's your favorite color?")

;; This version sets a parent frame to display the input window in the middle
;; of f.
;; (sc/input f "What's your favorite color?")

;; Buttons
(def b (sc/button :text "Click Me"))
(display b)

;; Register an event handler for the button.  The listener parameter is the
;; event object.  This can be used as a proxy for the widget that triggered the
;; event.  Here, it just becomes the parent of the alert.
(sc/listen b :action (fn [e] (sc/alert e "Thanks!")))

;; listen returns a function.  To unregister it, just call it.
;; (*1)

;; Multiple events can be registered.
(sc/listen b :mouse-entered #(sc/config! % :foreground :blue)
           :mouse-exited #(sc/config! % :foreground :red))

;; Create a listbox.
(def lb (sc/listbox :model (-> 'seesaw.core ns-publics keys sort)))
(display lb)

;; Make listbox scrollable.
(display (sc/scrollable lb))

;; Get the selected item.  Will return nil if nothing is selected, otherwise a
;; symbol.
(sc/selection lb)

;; For getting multi-selected elements.  Will return a list.
;; (sc/selection lb {:multi? true})

;; Set the selection.
(sc/selection! lb 'all-frames)

;; Register an event when selection changes (works on other widgets).
(sc/listen lb :selection (fn [e] (println "Selection is " (sc/selection e))))

;; Text fields
(def field (display (sc/text "This is a text field.")))

;; Get contents.
(sc/text field)

;; Change contents.
(sc/text! field "A new value")

;; Format text contents.
(sc/config! field :font "MONOSPACED-PLAIN-12" :background "#f88")

;; Make it multi-line for multiple lines of text.
(def area (sc/text :multi-line? true :font "MONOSPACED-PLAIN-14"
                   :text "This
is
multi
lined"))

(display area)

;; Fill up the area with something slurpable.
(sc/text! area (java.net.URL. "http://clojure.com"))

;; Activate scrollbars.
(display (sc/scrollable area))

;; Scroll to the top.  Works with other widgets.
(sc/scroll! area :to :top)
;; Scroll to the bottom.
(sc/scroll! area :to :bottom)
;; Scroll to line.
(sc/scroll! area :to [:line 50])

;; Use a splitter to show two widgets at once.
(def split (sc/left-right-split (sc/scrollable lb) (sc/scrollable area) :divider-location 1/3))
(display split)

;; Hook listbox to text area.
(defn doc-str [s] (-> (symbol "seesaw.core" (name s)) resolve meta :doc))
(sc/listen lb :selection
        (fn [e]
          (when-let [s (sc/selection e)]
            (-> area
                (sc/text! (doc-str s))
                (sc/scroll! :to :top)))))

;; Radio buttons
(def rbs (for [i [:source :doc]]
           (sc/radio :id i :class :type :text (name i))))

;; ;; Create a border panel, which has a north, south, east, west, center panel
;; ;; structure.
(display (sc/border-panel
         :north (sc/horizontal-panel :items rbs)
         :center split
         :vgap 5 :hgap 5 :border 5))

;; To access the radio buttons, a selector can be used.  This returns a list
;; with both.
(sc/select f [:JRadioButton])

;; Or, specify the :class option.
(sc/select f [:.type])

;; Or, use the :id to just get one.
(sc/select f [:#source])

;; Put the radio buttons in a button group.  This will allow for mutually
;; exclusive buttons.
(def group (sc/button-group))
;; config! can take a seq and a widget.
(sc/config! (sc/select f [:.type]) :group group)

;; Get the group selection.
(sc/selection group)
(sc/id-of (sc/selection group))

;; Register a listener for group selection changes.
(sc/listen group :selection
           (fn [e]
             (when-let [s (sc/selection group)]
               (println "Selection is " (sc/id-of s)))))
