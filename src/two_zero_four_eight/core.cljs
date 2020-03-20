(ns two_zero_four_eight.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defonce state (reagent/atom '((:EMPTY :EMPTY :EMPTY 2)
                               (:EMPTY :EMPTY :EMPTY :EMPTY)
                               (:EMPTY :EMPTY :EMPTY :EMPTY)
                               (2 2 :EMPTY :EMPTY))))
(defn create-cell [cell]
  [:div {:class "cell"} (if (= cell :EMPTY) "" (str cell))])

(defn create-row [row]
  [:div {:class "row"}
   (map create-cell row)])

(defn board []
  [:div {:class "board"}
   (map create-row @state)])

(reagent/render-component [board]
                          (. js/document (getElementById "app")))