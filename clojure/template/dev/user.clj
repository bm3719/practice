;;;; This is a dev namespace to contain utility functions useful for
;;;; interacting with the data at the REPL, or for general development.

(ns user
  (:require [template.core :as core]
            [clojure.tools.namespace.repl :refer [refresh]]))
