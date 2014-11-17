(ns incanter-test.db
  (:require [monger.core :as m]
            [monger.command :as cmd]
            [monger.collection :as col]
            [monger.conversion :as conv :refer [from-db-object]]
            [monger.result :as res]
            [monger.query :as q]
            [monger.operators :refer :all]
            [monger.joda-time])
  (:import [org.bson.types ObjectId]
           [com.mongodb MongoClient DB WriteConcern MongoClientOptions
            WriteResult])
  (:gen-class))

(defn create-conn
  "Creates the connection to MongoDB." [host port]
  (m/connect (m/server-address host port) (m/mongo-options {:connections-per-host 100})))

(defn mongo-setup
  "Sets connection parameters and returns the DB object, under the assumption
  that a single DB will be used." [conn db db-user db-pwd]
  (m/authenticate (m/get-db conn db) db-user (.toCharArray db-pwd))
  (m/set-default-write-concern! WriteConcern/JOURNAL_SAFE)
  (m/get-db conn db))

(defn mongo-shutdown
  "Disconnect from MongoDB." [conn]
  (m/disconnect conn))

;;; Data insertion.

(defn put-map
  "Given a collection, insert a map as a document."
  [db col m]
  (res/ok? (col/insert db col m)))

(defn put-maps
  "Given a collection, insert a vector of maps."
  [db col v]
  (res/ok? (col/insert-batch db col v)))

;;; Data retrieval.

(defn get-map
  "Get a document from a collection, given some criteria.
  e.g. (get-map \"person\" {:nameFirst \"JOHN\"})"
  [db col m]
  (col/find-one-as-map db col m))

(defn get-maps
  "Get multiple documents from a collection."
  ([db col]
    (col/find-maps db col))
  ([db col conditions]
    (col/find-maps db col conditions))
  ([db col conditions projection]
    (col/find-maps db col conditions projection))
  ([db col conditions projection sort-map]
    (q/with-collection db col
      (q/find conditions)
      (q/fields projection)
      (q/sort sort-map))))

(defn get-by-id
  "Get a document, specified by _id (either as an ObjectId or string)."
  [db col id]
  (let [id (if (= org.bson.types.ObjectId (class id))
             id (ObjectId. id))]
    (col/find-one-as-map db col {:_id id})))

;;; Deleting data

(defn del-map
  "Delete a single document from a collection, specified by _id (either as an
  ObjectId or string).  Returns true if a record was found and deleted."
  [db col id]
  (let [id (if (= org.bson.types.ObjectId (class id))
             id (ObjectId. id))]
    (= 1 (.getField (col/remove-by-id db col id) "n"))))

(defn del-maps
  "Delete multiple documents from a collection.  Returns true if one or more
  documents were deleted.  Optional conditions argument is a map of
  MongoDB-compatible conditions.
  e.g. (del-maps unit {:category \"JOINT\"})

  WARNING: Not specifying a conditions parameter will delete ALL documents from
  the collection."
  ([db col] (< 0 (.getField (col/remove db col) "n")))
  ([db col conditions] (< 0 (.getField (col/remove db col conditions) "n"))))

;;; Data updating.

(defn update-by-id
  "Update entity based on the _id passed in.  Will not delete missing fields
  from new data."
  [db col document id]
  {:pre [(= (type id) java.lang.String)]}
  (let [id  (ObjectId. id)
        doc (dissoc document :_id)]
  (col/update db col {:_id id} {$set doc})))

;; Custom querying.

(defn get-distinct-values
  "Get the distinct values for key from col."
  [db col key]
  (col/distinct db col key))
