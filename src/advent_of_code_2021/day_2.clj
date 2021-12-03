(ns advent-of-code-2021.day-2
  (:require [clojure.string :as str]))

(defn input->course-change
  [input]
  (let [parts (str/split input #"\s+")]
    {:direction (first parts) :distance (Integer/parseInt (second parts))}))


(defn input [path] (map #(input->course-change %)
                        (-> (slurp path)
                            (clojure.string/split-lines))))

(def current-location {:x 0, :y 0})

(defn distance-traveled [coll coordinates]
  (if (empty? coll)
    (* (:x coordinates) (:y coordinates))
    (recur (rest coll)
           (case (:direction (first coll))
             "forward" (update coordinates :x + (:distance (first coll)))
             "down" (update coordinates :y + (:distance (first coll)))
             "up" (update coordinates :y - (:distance (first coll)))))))


(distance-traveled (input "resources/input2") current-location)
