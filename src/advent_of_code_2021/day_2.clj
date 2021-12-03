(ns advent-of-code-2021.day-2
  (:require [clojure.string :as str]))

(defn input->course-change
  [input]
  (let [parts (str/split input #"\s+")]
    {:direction (first parts) :distance (Integer/parseInt (second parts))}))


(defn input [path] (map #(input->course-change %)
                        (-> (slurp path)
                            (clojure.string/split-lines))))

(def current-location {:x 0, :y 0, :aim 0})

(defn distance-traveled [coll coordinates]
  (if (empty? coll)
    (* (:x coordinates) (:y coordinates))
    (recur (rest coll)
           (case (:direction (first coll))
             "forward" (update coordinates :x + (:distance (first coll)))
             "down" (update coordinates :y + (:distance (first coll)))
             "up" (update coordinates :y - (:distance (first coll)))))))

(defn distance-traveled2 [coll coordinates]
  (if (empty? coll)
    (* (:x coordinates) (:y coordinates))
    (recur (rest coll)
           (case (:direction (first coll))
             "forward" (assoc coordinates
                              :x (+ (:distance (first coll)) (:x coordinates))
                              :y (+ (:y coordinates) (* (:aim coordinates) (:distance (first coll)))))
             "down" (update coordinates :aim + (:distance (first coll)))
             "up" (update coordinates :aim - (:distance (first coll)))))))

(println "Part 1: " (distance-traveled (input "resources/input2") current-location))
(println "Part 2: " (distance-traveled2 (input "resources/input2") current-location))