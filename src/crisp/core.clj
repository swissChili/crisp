(ns crisp.core
  (:gen-class)
  (:require [crisp.parser :as p]
            [crisp.interpreter :as i]))

(def input"
  [let a: 1]")

(defn -main
  "Parse some debug info."
  [& args]
  (println "Context:" (i/interpret (p/parse-crisp input))))
