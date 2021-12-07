(ns advent-of-code-2021.day-3)

(defn input
  [path]
  (-> (slurp path)
      (clojure.string/split-lines)))

(defn- on-setting [reading index]
  (bit-test (Integer/parseInt reading 2) index))

(defn- off-setting [reading index]
  (not (bit-test (Integer/parseInt reading 2) index)))

(defn power-readout [reading measurement]
  (loop [x (- (count reading) 1)
         score (vec (repeat (count reading) 0))]
    (if (< x 0)
      score
      (recur (- x 1)
             (if (measurement reading x)
               (update score (- 11 x) inc)
               score)))))

(defn bit-comparison [i a b]
  (if (> (get a i) (get b i)) 1 0))


(defn power-levels->consumption
  [{:keys [on-count off-count]}]
  (loop [i 0
         gamma (vec (repeat (count on-count) 0))
         epsilon (vec (repeat (count off-count) 0))]
    (if (>= i 12)
      (* (Integer/parseInt (apply str gamma) 2)
         (Integer/parseInt (apply str epsilon) 2))
      (recur (+ i 1)
             (assoc gamma i (bit-comparison i on-count off-count))
             (assoc epsilon i (bit-comparison i off-count on-count))))))


(defn power-level [readings]
  (loop [remaining-readings readings
         on (vec (repeat (count (first readings)) 0))
         off (vec (repeat (count (first readings)) 0))]
    (if (empty? remaining-readings)
      (power-levels->consumption {:on-count (into [] on) :off-count (into [] off)})
      (recur (rest remaining-readings)
             (map + on (power-readout (first remaining-readings) on-setting))
             (map + off (power-readout (first remaining-readings) off-setting))))))

(println "The power level is" (power-level (input "resources/input3")))