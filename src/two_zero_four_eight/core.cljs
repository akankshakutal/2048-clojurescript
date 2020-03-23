(ns two_zero_four_eight.core
  (:require [reagent.core :as reagent :refer [atom]]
            [two-zero-four-eight.logic :as logic]))

(enable-console-print!)

(defonce state (reagent/atom {:board '((0 0 0 0)
                                       (2 2 0 0)
                                       (0 0 0 2)
                                       (0 0 0 0))}))

(defn move-up [state]
  {:board (logic/place-new-cell (logic/move-board-up (:board state)))})

(defn move-down [state]
  {:board (logic/place-new-cell (logic/move-board-down (:board state)))})

(defn move-left [state]
  {:board (logic/place-new-cell (logic/move-board-left (:board state)))})

(defn move-right [state]
  {:board (logic/place-new-cell (logic/move-board-right (:board state)))})

(defn create-cell [cell]
  [:div {:class "cell"} (if (= cell 0) "" (str cell))])

(defn create-row [row]
  [:div {:class "row"}
   (map create-cell row)])

(defn board []
  [:div {:class       "board"
         :tabIndex    1
         :on-key-down #(case (.-which %)
                         38 (swap! state move-up)
                         40 (swap! state move-down)
                         37 (swap! state move-left)
                         39 (swap! state move-right)
                         nil)}
   (map create-row (:board @state))])

(reagent/render-component
  [board]
  (. js/document (getElementById "app")))