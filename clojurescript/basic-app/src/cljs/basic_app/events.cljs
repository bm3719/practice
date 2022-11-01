(ns basic-app.events
  (:require [re-frame.core :as re-frame]
            [basic-app.db :as db]))

(defn dispatch-timer-event
  []
  (let [now (js/Date.)]
    (re-frame/dispatch [:timer now])))

(defonce do-timer (js/setInterval dispatch-timer-event 1000))

(re-frame/reg-event-db
 :initialize
 (fn [_ _]
   {:time (js/Date.)
    :time-color "#f88"}))

(re-frame/reg-event-db
 :time-color-change
 (fn [db [_ new-color-value]]
   (assoc db :time-color new-color-value)))

(re-frame/reg-event-db
 :timer
 (fn [db [_ new-time]]
   (assoc db :time new-time)))
