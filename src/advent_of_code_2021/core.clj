(ns advent-of-code-2021.core
  (:gen-class))

(defn input1 [path] (map #(Integer/parseInt %)
                         (-> (slurp path)
                             (clojure.string/split-lines))))

(defn depth-counter [coll prev acc]
  (if (empty? coll)
    (count acc)
    (recur (rest coll)
           (first coll)
           (if (nil? prev) acc (if (> (first coll) prev) (cons prev acc) acc)))))

(defn -main
  [& args]
  (println "The answer is" (depth-counter (input1 "resources/input1") nil [])))

  ;;(def input (input1 "resources/input1"))
  ;;(depth-counter input nil [])