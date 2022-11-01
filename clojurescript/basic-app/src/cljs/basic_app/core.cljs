(ns basic-app.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [basic-app.events]
            [basic-app.subs]
            [basic-app.views :as views]
            [basic-app.config :as config]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/ui]
                  (js/document.getElementById "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize])
  (dev-setup)
  (mount-root))
