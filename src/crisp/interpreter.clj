(ns crisp.interpreter
  (:gen-class)
  (:require [clojure.string :as str]))

;; TODO: write standard library
(def standard
  { })

(defn vifnt
  "Make into a vector if it's not one"
  [input]
  (if (= (class input) clojure.lang.PersistentVector)
    input
    [input]))

(defn empty-if-nil
  [thing]
  (do
    (println "is nil?" thing "w/vifnt" (vifnt thing))
    (if thing
      (vifnt thing)
      [])))

;; Value to use as initial evaluation. May be useful
;; for things like system error returns in the future.
;; I would use 0 but sublime throughs a fit.
(def first-eval nil)

;; Forward-declare this function as it is used by most
;; special funcs
(declare interpret-all)

(defn eval-method-context
  [context method args]
  ;; last iteration
  (do
    (println "Evaling method context")
    (if (= (count method) 1)
      ;; Call the function
      (do
        (println "is true! method" method "args" (empty-if-nil args))
        (apply
          (interpret-all
            (vifnt (get context (:value (first method))))
            context
            first-eval)
          (empty-if-nil args)))
      ;; Recurse
      (eval-method-context
        (get context (first method))
        args))))

;; A function that adds something to the context.
(defn let-fn
  "On is a vector of the subobjects of let,
  with is a vector of the params.
  Context is the current context."
  [context on with]
  ;; Add this to the context
  (do
    (println "In let with" (first with) "on" on)
    (conj context 
        { (:value (first on))
          with })))

(defn print-fn
  [context on with]
  (do
    (println
      (str/join " "
        ;; Interpret all arguments
        (map
          (fn [x]
            (interpret-all (vifnt x) context first-eval))
          with)))))

(defn lambda-fn
  [context args body]
  (do
    (println "args" args)
    (fn [& given]
      (do
        (println "Given" given)
        (interpret-all
          (vifnt (first body))
          (conj context
            ;; This beautiful function joins two vectors
            ;; eg: [a b c] [1 2 3]
            ;; and constructs a hashmap
            ;; eg: {a 1, b 2, c 3}
            (zipmap
              ;; Get the values of all the identifiers 
              (map :value args)
              given))
          first-eval)))))

;; A hashmap of functions
(def special
  { "let" let-fn
    "print" print-fn })

;; The main interpret function. Recursively evaluates
;; data and passes the updated context on.
(defn interpret-all
  [input context last-eval]
  (let [c (first input)]
    (do
      (println "\n\n\n=========================\ninterpreting" c)
      (println "\nof" input)
      (println "\nrest" (rest input))
      (cond
        (= (count input) 0)
          ;; Input is empty, return last item evaluated
          (do
            (println "returning" last-eval)
            last-eval)

        (= (:type c) :literal)
          ;; Literal value, push to stack  and recurse
          (interpret-all (rest input) context (:value c))

        (= (:type c) :tuple)
          (interpret-all (rest input)
                          context
                          (:value c))

        (= (:type c) :ident)
          ;; Identifier, look up, push to stack, recurse
          (do
            (println "looking up identifier")
            (println "found" (get context (:value c)))
            (interpret-all (rest input)
                            context
                            ;; Interpret ident b/c lazy
                            (interpret-all
                              (vifnt (get context (:value c)))
                              context
                              first-eval)))
        (= (:type c) :method)
          ;; Method call, look up in context, evaluate
          (let [v (:value c)
                with (vifnt (:with v))
                on (vifnt (:on v))]
            ;; try to find a special function from the first
            ;; object
            (do
              (if-let [s (get special (:value (first on)))]
                ;; Recurs with context returned from func
                (interpret-all (rest input)
                                (s context
                                  (rest on)
                                  (vifnt with))
                                ;; Special functions return
                                ;; nothing
                                last-eval)
                (interpret-all (rest input)
                  ;; Normal methods can not consume context
                  context
                  (eval-method-context context on with)))))
        (= (:type c) :lambda)
          (let [v (:value c)
                a (:args v)
                b (:body v)]
            (do
              (interpret-all
                (rest input)
                context
                ;; Call the lambda generator and add it to the stack
                (lambda-fn context a b))))))))

(defn interpret
  "Just a wrapper for interpret-all"
  [input]
  (interpret-all input standard first-eval))
