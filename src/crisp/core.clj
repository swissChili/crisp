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

(defn repl
  []
  (do
    (print "\033[32m> \033[0m")
    (flush)
    (i/interpret (p/parse-crisp (read-line)))
    (repl)))

(defn -main
  "Parse some debug info."
  [& args]
  (if (> (count args) 0)
    (i/interpret (p/parse-crisp (slurp (first args))))
    (repl)))
  ;(println "return:" (i/interpret (p/parse-crisp input))))
