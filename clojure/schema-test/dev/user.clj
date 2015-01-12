;;;; This is a dev namespace to contain utility functions useful for
;;;; interacting with the data at the REPL, or for general development.

(ns user
  (:require [schema-test.core :as core]
            [schema.core :as s]
            [clj-time.core :as t]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.repl :refer :all]
            [clojure.pprint :refer [pp pprint]]))

;;; Field transform functions.

(defn str->int
  "Safely converts a string to an int, throwing an exception if not." [s]
  (if (integer? (read-string s))
    (read-string s)
    (throw (Exception. (str "Not an integer: " s)))))

(defn interpret-date-str
  "Converts a date string of the form YYYY/MM/DD to a clj-time object.  Just a
  test function.  Will need a better one in production."
  [dstr]
  (try
    (apply t/date-time (map read-string (clojure.string/split dstr #"/")))
    (catch Exception e (str "Unable to parse date from " dstr))))

;;; Input maps.

;; A simple test schema.
(def test-schema
  {:a (s/pred (and s/Int pos?))})

;; The final schema that the data must match.
(def source1-schema
  {:uic (s/pred (and s/Str #(= (count %) 6)))
   :free-text s/Str
   :date-something-happened org.joda.time.DateTime
   :enum-value (s/enum "DIV" "BDE" "BN" "CO")
   :some-number s/Int
   :some-pos-number (s/pred (and s/Int pos?))
   ;; :some-2char-string-that-could-be-blank (s/pred (or s/))
   })

;; An example of raw input data that transforms properly and passes validation.
(def source1-test-data
  {:uic "WAAXFF"
   :free-text "sldfkjsdljfkds"
   :date-something-happened "2013/01/01"
   :enum-value "DIV"
   :some-number "-2"
   :some-pos-number "12"
   ;; :some-2char-string-that-could-be-blank ""
   })

;; An example of raw input data that has both transform and validation errors.
(def source1-bad-test-data
  {:uic "BLAPFFF"
   :free-text 3
   :date-something-happened "2011/JUL/01"
   :enum-value "SDKJVF"
   :some-number "NAN"
   :some-pos-number "-2"
   ;; :some-2char-string-that-could-be-blank "FFFF"
   :some-extra-field "sdf"})

;; A collection of operations, to be performed on the test data, prior to it
;; being validated.
;;
;; TODO: Make this a list of function symbols that are applied in sequence.
(def source1-fns
  {:uic #(->> % (take 6) (apply str))
   :free-text identity
   :date-something-happened interpret-date-str
   :enum-value identity
   :some-number str->int
   :some-pos-number str->int})

;;; Interface functions.

(defn find-missing-keys
  "Find if there exist keys in the schema that aren't in the data and return
  them." [schema data]
  (clojure.set/difference (set (keys schema)) (set (keys data))))

(defn find-extra-keys
  "Find if there exist keys in the data that aren't in the schema and return
  them." [schema data]
  (clojure.set/difference (set (keys data)) (set (keys schema))))

(defn validate-map
  "Encapsulate a validation and return true if successful, otherwise return a
  structure that extracts the failed keys and reasons from the exception
  message.  Will first apply the data-transforms to the data collection, then
  tests it against the schema.

  Returns a map of the form: {:schema-key <result>}, where <result> is
  true (for validation success) or a string describing the error.

  Note: In the final version of this, once things are successful, we'll want to
  propagate the transformed data to the datastore.  At that point, the hygiene
  phase is complete and we can't go back to it."
  [schema data-transforms data]
  (into
   {} (for [k (keys schema)]
        (try
          (when (s/validate (into {} [[k (k schema)]])
                            (into {} [[k ((k data-transforms) (k data))]]))
            [k true])
          ;; If the validation fails, grab the message.
          (catch clojure.lang.ExceptionInfo e [k (.getMessage e)])
          ;; Catch all other exceptions.  These are probably data-transform
          ;; exceptions, like if the transform calls first on an integer, or
          ;; it's missing completely.
          (catch Exception e [k (str "Transformation error: " (.getMessage e))])))))

;;; Test expressions.
;; (validate-map source1-schema source1-fns source1-test-data)
;; (validate-map source1-schema source1-fns source1-bad-test-data)
