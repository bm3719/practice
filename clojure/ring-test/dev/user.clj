;;;; This is a dev namespace to contain utility functions useful for
;;;; interacting with the data at the REPL, or for general development.

(ns user
  (:require [ring-test.core :as core]
            [ring.adapter.jetty :as jetty]
            [clojure.pprint :refer [pp pprint]]
            [clojure.repl :refer [doc]]
            [clojure.string :as string]
            [clojure.tools.namespace.repl :refer [refresh]]))

(defn test-ns
  "A confirmation function to run from the user REPL" []
  "user namespace active.")

(defn boot
  "Run this to start the server from the REPL."
  ([join?]
     (defonce server
       (jetty/run-jetty core/app {:join join? :port 3000})))
  ([] (boot false)))
