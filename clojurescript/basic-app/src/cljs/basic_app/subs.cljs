(ns basic-app.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :time
 (fn [db _]
   (:time db)))

(re-frame/reg-sub
 :time-color
 (fn [db _]
   (:time-color db)))
