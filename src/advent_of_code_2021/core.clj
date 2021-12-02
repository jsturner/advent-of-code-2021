(ns advent-of-code-2021.core
  (:gen-class))

(def testdata [199 200 208 210 200 207 240 269 260 263])

(defn input1 [path] (map #(Integer/parseInt %)
                         (-> (slurp path)
                             (clojure.string/split-lines))))

(defn- sums [coll]
  (reduce + (take 3 coll)))

(defn depth-counter [coll prev acc]
  (if (empty? coll)
    (count acc)
    (recur (rest coll)
           (first coll)
           (if (nil? prev) acc (if (> (first coll) prev) (cons prev acc) acc)))))

(defn depth-counter2 [coll prev acc]
  (if (empty? coll)
    (count acc)
    (recur (rest coll)
           (sums coll)
           (if (nil? prev) acc (if (> (sums coll) prev) (cons prev acc) acc)))))

(defn -main
  [& args]
  (println "The answer to part 1 is" (depth-counter (input1 "resources/input1") nil []))
  (println "The answer to part 1 is" (depth-counter2 (input1 "resources/input1") nil [])))