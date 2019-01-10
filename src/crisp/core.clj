(ns crisp.core
  (:gen-class)
  (:require [crisp.parser :as p]
            [crisp.interpreter :as i]))

(def input
  "
    [if: [eq: [add: 1.5 1.5] 3.0]
      [print: \"True\"]
      [print: \"False\"]
    ]")

(defn -main
  "Parse some debug info."
  [& args]
  (println "return:" (i/interpret (p/parse-crisp input))))
