(ns two_zero_four_eight.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defn append-zeroes
  [size coll]
  (take size (concat coll (repeat 0))))

(def move-left-row-without-zeroes
  (comp
    (partial map #(apply + %))
    (partial mapcat #(partition-all 2 %))
    (partial partition-by identity)
    (partial #(filter pos? %))))

(def move-left-row
  (comp
    #(apply append-zeroes %)
    (juxt count move-left-row-without-zeroes)))

(def reverse-board #(map reverse %))

(def transpose #(apply map list %))

(def move-board-left #(map move-left-row %))

(def move-board-right (comp reverse-board move-board-left reverse-board))

(def move-board-up (comp transpose move-board-left transpose))

(def move-board-down (comp transpose move-board-right transpose))

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
  [:div {:class       "board"
         :tabIndex    -1
         :on-key-down #(case (.-which %)
                         38 (swap! state move-board-up)
                         40 (swap! state move-board-down)
                         37 (swap! state move-board-left)
                         39 (swap! state move-board-right)
                         nil)}
   (map create-row @state)])

(reagent/render-component [board]
                          (. js/document (getElementById "app")))