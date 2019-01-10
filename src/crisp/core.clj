(ns crisp.core
  (:gen-class)
  (:require [crisp.parser :as p]
            [crisp.interpreter :as i]))

(def input
  "
    [let greet: (name to_print): {
      [name: to_print]
    }]

    [greet: (a b): {
      [print: a]
    } 1 true]")

(defn -main
  "Parse some debug info."
  [& args]
  (println "return:" (i/interpret (p/parse-crisp input))))
