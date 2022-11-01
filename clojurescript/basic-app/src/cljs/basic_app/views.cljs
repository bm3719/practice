(ns basic-app.views
  (:require [re-frame.core :as re-frame]
            [clojure.string :as str]))

(defn clock
  []
  [:div.example-clock
   {:style {:color @(re-frame/subscribe [:time-color])}}
   (-> @(re-frame/subscribe [:time])
       .toTimeString
       (str/split " ")
       first)])

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @(re-frame/subscribe [:time-color])
            :on-change #(re-frame/dispatch
                         [:time-color-change (-> % .-target .-value)])}]])

(defn ui []
  [:div
   [:h1 "Hello, world, it is now"]
   [clock]
   [color-input]])
