(ns advent-of-code-2021.day-3)

;; https://cek.io/blog/2017/08/17/clojure-introduction-binary-bitwise/

(defn input
  [path]
  (-> (slurp path)
      (clojure.string/split-lines)))

(defn- on-setting
  "returns true if a bit is 1"
  [reading index]
  (bit-test (Integer/parseInt reading 2) index))

(defn- off-setting
  "returns true if a bit is 0"
  [reading index]
  (not (bit-test (Integer/parseInt reading 2) index)))

(defn power-readout 
  "Updates a vector representing a running tally of how many
   times each bit is 1 or 0 (on or off)"
  [reading measurement]
  (loop [x (- (count reading) 1)
         tally (vec (repeat (count reading) 0))]
    (if (< x 0)
      tally
      (recur (- x 1)
             (if (measurement reading x)
               (update tally (- 11 x) inc)
               tally)))))

(defn bit-comparison [i colla collb]
  (if (> (get colla i) (get collb i)) 1 0))

(defn power-levels->consumption
  "Returns power consumption by decoding gamma and epsilon values"
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

(defn power-consumption [diagnostics]
  (loop [remaining-readings diagnostics
         on (vec (repeat (count (first diagnostics)) 0))
         off (vec (repeat (count (first diagnostics)) 0))]
    (if (empty? remaining-readings)
      (power-levels->consumption {:on-count (into [] on) :off-count (into [] off)})
      (recur (rest remaining-readings)
             (map + on (power-readout (first remaining-readings) on-setting))
             (map + off (power-readout (first remaining-readings) off-setting))))))

(println "Part 1: the power level is" (power-consumption (input "resources/input3")))