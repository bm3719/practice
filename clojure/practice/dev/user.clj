;;;; This is a dev namespace to contain utility functions useful for
;;;; interacting with the data at the REPL, or for general development.

(ns user
  (:require [practice.core :as core]
            [practice.store-credit :as sc]
            [clojure.tools.namespace.repl :refer [refresh]]))
