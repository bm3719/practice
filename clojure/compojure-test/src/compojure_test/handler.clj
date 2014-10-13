(ns compojure-test.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]))

;;; Responses from HTTP requests

;; Note that the rest of the route expression is in an implicit do block.  If
;; the return value is a string, then it auto-encased in a standard response.
;; 
;; {:status 200
;;  :headers {"Content-Type" "text/html; charset=utf-8"}
;;  :body "string goes here"}
;;
;; compojure.response/render is what turns types into responses.  It can be
;; overridden for custom rendering.

;;; Nesting Routes

;; For normal nested routes, see below.

;; For externally defined routes, do something like this:
(defn inner-routes [user-id]
  (routes
   (GET "/profile" [] (str "hello " user-id))
   (GET "/posts" [] (str "hi " user-id))))

(defroutes app-routes
  (GET "/" [] "Hello World")
  ;; Example route with path and vector of parameters.
  (GET "/user/:id" [id] (str "<h1>Hello " id "</h1>"))
  ;; Example route with regular expression for parameter (returning nil if it
  ;; doesn't match) and destructuring on the parameters.
  (GET ["/stuff/:id" :id #"[0-9]+"] {{id :id} :params} (str id))
  ;; Example route that binds to all request types and with full request map capture.
  (ANY "/stuff/" request (str request))
  ;; Example route that binds the full destructuring map.
  (GET "/test/:id" [id :as {uri :uri}] (str "<h1>" uri "</h1>"))
  ;; Use the context macro for grouping by common prefix.
  (context "/common" []                 ; Bindings accessible to the route can
                                        ; go here too.
           (GET "/profile" [] "hi there.")
           (GET "/posts" [] "oh hai."))
  ;; Externally defined context.
  (context "/thread/:user-id" [user-id]
           (inner-routes user-id))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
