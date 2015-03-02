;;;; This is a dev namespace to contain utility functions useful for
;;;; interacting with the data at the REPL, or for general development.

(ns user
  (:require [test-xml.core :as core]
            [clojure.walk :as walk]
            [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.pprint :refer [pp pprint]]
            [clojure.repl :refer [doc]]
            [clojure.tools.namespace.repl :refer [refresh]]))


;; (def x (xml/parse (java.io.ByteArrayInputStream. (.getBytes (slurp "test.xml")))))

;; Uncomment this to ingest the real data.
;; (def x (xml/parse (java.io.ByteArrayInputStream. (.getBytes (slurp "1bct_1ad.xml")))))

(def test-data
  [{:tag :bleh
    :content [{:tag :field1
               :attrs {:a 2}
               :content [{:tag :ESTABD_OBJ_TYPE_ID,
                          :attrs nil,
                          :content ["72060793943849738"]}
                         {:tag :OBJ_TYPE_ESTAB_IX,
                          :attrs nil,
                          :content ["72060793943849972"]}]}]}
   {:tag :bleh2
    :content [{:tag :field4
               :attrs {:a 2}
               :content [{:tag :ESTABD_OBJ_TYPE_ID,
                          :attrs nil,
                          :content ["72060793943849738"]}
                         {:tag :OBJ_TYPE_ESTAB_IX,
                          :attrs nil,
                          :content ["72060793943849972"]}]}]}])

(defn get-response-messages
  "Retrieve just the SQL log generated during the query that created the XML
  file." [m]
  (map :attrs (:content (first (second (last m))))))

(defn get-entity-contents [m]
  (second (last (second (second (last m))))))

(defn restructure-walk
  "Doesn't quite work when vectors are included." [m]
  (walk/postwalk-replace m {:tag :content}))

(defn content-walk
  "Walk the collection and restructure the XML parse map output to use the keys
  from the XML document as map keys.  Drops the :attrs field (XML tag
  attributes), which seems empty most of the time and filled with useless info
  the rest.  Also removes extraneous vectors that all content strings are
  in." [col]
  (walk/prewalk #(cond (map? %) {(:tag %) (:content %)}
                       (and (vector? %) (string? (first %)) (= 1 (count %))) (first %)
                       :default %) col))

(defn content-scan
  "Test function to get a sample of large vectors within a map
  structure." [col]
  (walk/prewalk #(when (vector? %) (when (> (count %) 10) (take 10 %))) col))


;; This returns a vector of 12 maps, with these top level keys:
;; ((:GFM_ALIAS_TYPE_TBL) (:GFM_OBJECT_ITEM_ASSOC_ALIAS_TBL)
;; (:GFM_OBJ_TYPE_ESTAB_ALIAS_TBL) (:GFM_OBJT_ESTAB_OBJT_DET_ALIAS_TBL)
;; (:GFM_PERS_TYPE_SKILL_ATTR_TBL) (:OBJ_ITEM_OO_TBL) (:OBJ_ITEM_ALIAS_TBL)
;; (:OBJ_ITEM_ASSOC_TBL) (:OBJ_ITEM_OBJ_TYPE_ESTAB_TBL) (:OBJ_TYPE_OO_TBL)
;; (:OBJ_TYPE_ESTAB_TBL) (:OBJ_TYPE_ESTAB_OBJT_DET_TBL))
;; (map #((first (keys %)) %) (content-walk (get-entity-contents x)))

;; Look at a sample of each of the 12 maps.
;; (map #(take 1 %) (map #((first (keys %)) %) (content-walk (get-entity-contents x))))
