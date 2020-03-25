(ns two_zero_four_eight.core
  (:require [reagent.core :as reagent :refer [atom]]
            [two-zero-four-eight.logic :as logic]))

(enable-console-print!)

(defonce state (reagent/atom {:board '((0 0 0 0)
                                       (2 2 0 0)
                                       (0 0 0 2)
                                       (0 0 0 0))
                              :over  false}))

(defn update-state [board]
  (if (logic/game-over? board)
    {:board board :over true}
    {:board (logic/place-new-cell board) :over false}))

(defn move-up [state]
  (update-state (logic/move-board-up (:board state))))

(defn move-down [state]
  (update-state (logic/move-board-down (:board state))))

(defn move-left [state]
  (update-state (logic/move-board-left (:board state))))

(defn move-right [state]
  (update-state (logic/move-board-right (:board state))))


(defn create-cell [cell]
  [:div {:class "cell"} (if (= cell 0) "" (str cell))])

(defn create-row [row]
  [:div {:class "row"}
   (map create-cell row)])

(defn game-over-view [state]
  (if
    (:over state)
    [:div {:class "game-over"} "Game over"]))

(defn board []
  [:div {:class "main"}
   [:h1 "2048"]
   [:div {:class       "board"
          :tabIndex    1
          :autofocus   1
          :on-key-down #(case (.-which %)
                          38 (swap! state move-up)
                          40 (swap! state move-down)
                          37 (swap! state move-left)
                          39 (swap! state move-right)
                          nil)}
    (map create-row (:board @state))]
   (game-over-view @state)])

(reagent/render-component
  [board]
  (. js/document (getElementById "app")))