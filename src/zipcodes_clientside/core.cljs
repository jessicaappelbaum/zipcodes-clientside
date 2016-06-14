(ns zipcodes-clientside.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :as ajax]))

(enable-console-print!)

(println "This text is printed from src/zipcodes-clientside/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state* (atom {:text "Hello world!"}))

(defn- get-all-zips
  []
  (ajax/GET "http://localhost:3000/zipcode/all"
            {:handler (fn [zip-codes]
                        (swap! app-state* assoc :zip-codes zip-codes)
                        (cljs.pprint/pprint @app-state*))
             :params {:offset 2
                      :limit 20}
             :format :transit
             :response-format :transit}))

(defn hello-world
  []
  (get-all-zips)
  (fn []
    [:div
     [:h1 (:text @app-state*)]
     [:ol (doall (map (fn [z]
                        ^{:key (:_id z)}
                        [:li (str (:city z) ", " (:state z) " " (:_id z))])
                      (:zip-codes @app-state*)))]]))

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
