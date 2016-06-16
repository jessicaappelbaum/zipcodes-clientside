(ns zipcodes-clientside.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            ;;[ajax.core :as ajax]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))



(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state* (reagent/atom {:offset 0 :limit 20}))

;; get/all -  paginated results from the API using the cljs-http library

(defn- get-zipcodes []
  (go (let [response (<! (http/get "http://localhost:3000/zipcode/all" 
                                   {:with-credentials? false
                                    :query-params {:offset (get @app-state* :offset)
                                                   :limit  (get @app-state* :limit)}}))]
        (swap! app-state* assoc :zip-codes (:body response)))))

;; this is the reagent component that displays the paginated results!

(defn- per-page []
  [:div [:pre "how many zipcodes do you want to see on each page?"]
   [:input {:type "text"
            :on-change (fn [e]
                         (swap! app-state* assoc :limit
                                (* 1 (.-value (.-target e))))
                         (get-zipcodes))}]])

(defn list-zipcodes []
  [:ol (doall (map (fn [z]
                 ^{:key (:_id z)}
                 [:li (str (:city z) ", " (:state z) " " (:_id z))])
                   (:zip-codes @app-state*)))])

(defn- next-page [offset]
  (+ offset (get @app-state* :limit)))

(defn- previous-page [offset]
  (- offset (get @app-state* :limit)))

(defn- page-button [direction title]
  [:button {:on-click (fn []
                        (swap! app-state* update-in [:offset] direction)
                        (get-zipcodes)
                        (println @app-state*))} title])

(defn- jump-page []
  [:div [:pre "jump to page"]
   [:input {:type "text"
            :on-change (fn [e]
                         (swap! app-state* assoc :offset
                                (* (get @app-state* :limit) (- (.-value (.-target e)) 1)))
                         (get-zipcodes)
                         (println @app-state*))}]])

(defn app []
  (get-zipcodes)
  (fn []
    [:div
     [:h1 "zipcodes"]
     (per-page)
     (list-zipcodes)
     (page-button previous-page "previous")
     (page-button next-page "next")
     (jump-page)]))


;; this renders the function app 

(reagent/render-component [app]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;;(swap! app-state* update-in [:__figwheel_counter] inc)
)

;; displays paginated results clientside from api using cljs-ajax library

#_(defn- get-zipcodes
  []
  (ajax/GET "http://localhost:3000/zipcode/all"
            {:handler (fn [zip-codes]
                        (swap! app-state* assoc :zip-codes zip-codes)
                        (cljs.pprint/pprint @app-state*))
             :params {:offset 2
                      :limit 20}
             :format :transit
             :response-format :transit}))
