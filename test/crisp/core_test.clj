(ns crisp.core-test
  (:require [clojure.test :refer :all]
            [crisp.core :refer :all]
            [crisp.parser :as p]
            [crisp.interpreter :as i]))

(def tests
  [ "1"
    "1.0"
    "2.1"
    "true"
    "false"
    "[print: 1]"
    "[let a: 1] [add: a 2]"
    "[mult: 12 10]" ])

(def results
  [ 1
    1.0
    2.1
    true
    false
    nil
    3
    120 ])

(deftest literal
  (testing "Test some literals"
    (is
      (=
        (map (fn [x]
          (i/interpret (p/parse-crisp x)))
          tests)
        results))))
