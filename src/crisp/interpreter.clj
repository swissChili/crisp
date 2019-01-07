(ns crisp.interpreter
  (:gen-class))

;; Value to use as initial evaluation. May be useful
;; for things like system error returns in the future.
(def first-eval nil)

;; Forward-declare this function as it is used by most
;; special funcs
(declare interpret-all)

;; A function that adds something to the context.
(defn let-fn
  "On is a vector of the subobjects of let,
  with is a vector of the params.
  Context is the current context."
  [context on with]
  ;; Add this to the context
  (do
    (println "In lambda")
    (conj context 
        { (:value (first on)) 
          (interpret-all (first with) context first-eval) })))

;; A hashmap of functions
(def special
  { "let" let-fn })

;; The main interpret function. Recursively evaluates
;; data and passes the updated context on.
(defn interpret-all
  [input context last-eval]
  (let [c (first input)]
    (do
      (println "Interpreting token" (:value c))
      (cond
        (= (count input) 0)
          ;; Input is empty, return last item evaluated
          context

        (= (:type c) :literal)
          ;; Literal value, push to stack  and recurse
          (interpret-all (rest input) context (:value c))

        (= (:type c) :ident)
          ;; Identifier, look up, push to stack, recurse
          (interpret-all (rest input) 
                          context 
                          (get context (:value c)))
        (= (:type c) :method)
          ;; Method call, look up in context, evaluate
          (let [v (:value c)
                with (:with v)
                on (:on v)]
            ;; try to find a special function from the first
            ;; object
            (do
              (println "v" v "with" with "on" on)
              (if-let [s (get special (:value (first on)))]
                ;; Recurs with context returned from func
                (interpret-all (rest input)
                                (s context
                                  (rest on)
                                  (rest with))
                                ;; Special functions return
                                ;; nothing
                                last-eval))))))))

(defn interpret
  "Just a wrapper for interpret-all"
  [input]
  (interpret-all input {} first-eval))