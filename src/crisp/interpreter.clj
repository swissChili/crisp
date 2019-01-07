(ns crisp.interpreter
  (:gen-class))

(defn interpret-all
  [input context stack]
  (let [c (first input)]
    (do
      (cond
        (= (count input) 0)
          ;; Input is empty, return last item evaluated
          (last stack)

        (= (:type c) :literal)
          ;; Literal value, push to stack  and recurse
          (interpret-all (rest input) context (conj stack (:value c)))

        (= (:type c) :ident)
          ;; Identifier, look up, push to stack, recurse
          (interpret-all (rest input) 
                          context 
                          (conj stack
                            (get context (:value c))))))))

(defn interpret
  "Just a wrapper for interpret-all"
  [input]
  (interpret-all input {} []))