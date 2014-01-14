(ns collections-api.handler
  (:use compojure.core
        ring.middleware.json
        ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [collections-api.views :as views]
            [clojure.java.io :as io]))

(defn- get-ean-map
  "Retrieves initial data from EAN database"
  [id]
  (slurp (str "http://eandata.com/feed/?v=3&keycode=027F74ED52886617&mode=json&find="id)))

(defroutes app-routes
  (GET "/" [] (views/index-page))
  (GET "/:id" [id] (response (get-ean-map id)))
  (route/resources "/")
  (route/not-found "Not Found"))

;(def app(handler/site app-routes))
(def app (wrap-json-response app-routes))
