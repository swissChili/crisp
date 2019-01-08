(ns crisp.core
  (:gen-class)
  (:require [crisp.parser :as p]
            [crisp.interpreter :as i]))

(def input 
  " [let a: (b): {
      b
    }]
    [a: 123]")

(defn -main
  "Parse some debug info."
  [& args]
  (println "return:" (i/interpret (p/parse-crisp input))))
