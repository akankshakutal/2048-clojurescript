(ns two-zero-four-eight.logic)

(defn second-index [pos]
  [(quot pos 4) (mod pos 4)])

(defn get-empty-cells
  [board]
  (map second-index
       (keep-indexed (fn [k v] (when (zero? v) k)) (flatten board))))

(defn create-new-cell [board]
  (if (zero? (count (get-empty-cells board)))
    [-1 -1]
    (rand-nth (get-empty-cells board))))

(defn place-new-cell [board]
  (map #(apply list %)
       (assoc-in (mapv vec board)
                 (create-new-cell board) (rand-nth [2 4]))))

(def move-left-row-without-zeroes
  (comp
    (partial map #(apply + %))
    (partial mapcat #(partition-all 2 %))
    (partial partition-by identity)
    (partial #(filter pos? %))))

(def move-left-row
  (comp
    (partial apply #(take %1 (concat %2 (repeat 0))))
    (juxt count move-left-row-without-zeroes)))

(def move-board-left #(map move-left-row %))

(def reverse-board #(map reverse %))

(def move-board-right (comp reverse-board move-board-left reverse-board))

(def transpose #(apply map list %))

(def move-board-up (comp transpose move-board-left transpose))

(def move-board-down (comp transpose move-board-right transpose))