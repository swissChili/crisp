(ns crisp.core
  (:gen-class)
  (:require [crisp.parser :as p]
            [crisp.interpreter :as i]))

(def input
  " [let greet: (name): {
      [name: 1]
    }]
    [greet: (a): {
      [print: a]
    }]")

(defn -main
  "Parse some debug info."
  [& args]
  (println "return:" (i/interpret (p/parse-crisp input))))
