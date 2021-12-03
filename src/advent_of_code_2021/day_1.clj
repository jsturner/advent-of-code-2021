(ns advent-of-code-2021.core)

(defn input [path] (map #(Integer/parseInt %)
                         (-> (slurp path)
                             (clojure.string/split-lines))))

(defn- sums [coll]
  (reduce + (take 3 coll)))

(defn depth-counter
  ([coll measurement-fn]
   (depth-counter coll nil [] measurement-fn))
  ([coll prev acc measurement-fn]
   (if (empty? coll)
     (count acc)
     (recur (rest coll)
            (measurement-fn coll)
            (if (nil? prev) acc (if (> (measurement-fn coll) prev) (cons prev acc) acc))
            measurement-fn))))

  (println "The answer to part 1 is" (depth-counter (input "resources/input1") first))
  (println "The answer to part 2 is" (depth-counter (input "resources/input1") sums))