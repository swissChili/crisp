(ns crisp.core
  (:gen-class)
  (:require [crisp.parser :as p]
            [crisp.interpreter :as i]))

(def input
  "
    [let greet: (name to_print): {
      [name: to_print]
    }]
    
    [greet: (a): {
      [print: a]
    } 12345]")

(defn -main
  "Parse some debug info."
  [& args]
  (println "return:" (i/interpret (p/parse-crisp input))))
