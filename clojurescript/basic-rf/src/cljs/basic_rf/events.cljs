(ns basic-rf.events
  (:require [re-frame.core :as re-frame]
            [basic-rf.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
