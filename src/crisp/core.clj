(ns crisp.core
  (:gen-class)
  (:require [crisp.parser :as p]
            [crisp.interpreter :as i]))

(def input
  " [let fact: (n): {
      [if: [lt: n 2]
           1
           [mult: [fact: [sub: n 1]] n ]]}]

    [fact: 20]")

(defn -main
  "Parse some debug info."
  [& args]
  (println "return:" (i/interpret (p/parse-crisp input))))
