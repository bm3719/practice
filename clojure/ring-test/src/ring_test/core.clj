(ns ring-test.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [ring.middleware.resource :as resource]
            [ring.middleware.content-type :as content-type]
            [ring.middleware.not-modified :as not-modified]))

;; An example request object:
;; {:ssl-client-cert nil
;;  :remote-addr "0:0:0:0:0:0:0:1"
;;  :headers {"accept-encoding" "gzip,deflate,sdch"
;;            "cache-control" "max-age=0"
;;            "connection" "keep-alive"
;;            "user-agent" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36"
;;            "accept-language" "en-US,en;q=0.8"
;;            "accept" "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
;;            "host" "localhost:3000"}
;;  :server-port 3000
;;  :content-length nil
;;  :content-type nil
;;  :character-encoding nil
;;  :uri "/"
;;  :server-name "localhost"
;;  :query-string nil
;;  :body #
;;  :scheme :http
;;  :request-method :get}

(defn handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (cons (:remote-addr request) '("Hello there.<p /><img src=\"test-img.jpg\"\\>"))})

;; Note: The :body can be of type String, ISeq (a list implements ISeq), File,
;; or InputStream.

;;; Middleware: Higher-level functions that add additional functionality to
;;; handlers.

(defn wrap-content-type [handler content-type]
  (fn [request]
    (let [response (handler request)]
      (assoc-in response [:headers "Content-Type"] content-type))))

;; Apply middleware to a handler.
(def app
  (-> handler
      ;; This enables the resource/public directory to serve static content.
      (resource/wrap-resource "public")
      ;; NOTE: The following needs to come after wrap-resources.
      ;; Auto-wraps content-type based upon file extension.  A custom mime-type
      ;; map can be added if necessary.
      (content-type/wrap-content-type)
      ;; Ensure clients don't need to download already cached resources.
      (not-modified/wrap-not-modified)))

;; Middleware is used for all request handling apart from raw HTTP requests.
;; This includes parameters, sessions, and file uploading (handled in the
;; standard Ring library).

;;; Responses: Use the ring.util.response namespace.

(response/response "Hello World.")

(-> (response/response "Hello World.")
    (response/content-type "text/plain"))

;; Redirects
(response/redirect "http://localhost:3001/")

;; Return static files/resources.  Note: This seems to not return a map, as
;; expected.
(response/file-response "readme.html" {:root "public"})
(response/resource-response "readme.html" {:root "public"})

;;; Parameters: URL-encoded parameters are handled by ring.middleware.params.

;; See https://github.com/ring-clojure/ring/wiki/Parameters if I ever need to
;; do this.

;;; Cookies: https://github.com/ring-clojure/ring/wiki/Cookies

;;; Sessions: https://github.com/ring-clojure/ring/wiki/Sessions

;; Probably would want to use lib-noir if doing this.

;;; Third party libraries: Full list is at:
;;; https://github.com/ring-clojure/ring/wiki/Third-Party-Libraries
