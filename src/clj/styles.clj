(ns styles
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :refer [px percent]]))


(defstyles zipcss
  [:body {:font-family "courier new"
          :background-color :grey
          :width (px 750)
          :margin {:left :auto
                   :right :auto
                   :top (px 100)}}]
  
  [:h1 {:border-bottom :solid
        :color :white
        :opacity 0.7
        :padding-bottom (px 20)
        :font-size (px 70)}]

  [:button {:background-color :grey
            :color :white
            :line-height (px 40)
            :width (px 46)
            :font {:family "helvetica"
                   :size (px 25)}
            :border {:style :solid
                     :color :white
                     :width (px 2)
                     :radius (px 40)}
            :margin {:right (px 20)
                     :top (px 30)}}
   [:&:hover
    {:background-color :orange
     :opacity 0.5}]]

  [:input {:color :white
           :padding-left (px 15)
           :border {:radius (px 15)
                    :style :solid
                    :color :white}
           :font-size (px 20)
           :line-height (px 26)
           :width (px 100)
           :opacity 0.7
           :background-color :orange
           :margin-bottom (px 30)}]

  [:pre {:font-size (px 18)
         :margin-top (px 50)}]

  [:ol {:line-height (px 30)
        :font-size (px 18)
        :padding {:top (px 40)
                  :bottom (px 40)
                  :left (px 80)}
        :background-color :white
        :width (px 360)
        :opacity 0.7}])
