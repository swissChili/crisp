(ns crisp.core
  (:gen-class)
  (:require [crisp.parser :as p]
            [crisp.interpreter :as i]))

(def input"
  1 2 3")

(defn -main
  "Parse some debug info."
  [& args]
  (println (i/interpret (p/parse-crisp input))))
