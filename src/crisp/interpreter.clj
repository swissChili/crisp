(ns crisp.interpreter
  (:gen-class)
  (:require [clojure.string :as str]))

;; TODO: write standard library
(def standard
  {  })

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
        (println "is last method! method" method "args" (empty-if-nil args))
        (let [m (get context (:value (first method)))]
          (do
            (println "About to call with" m "from" (first method))
            (apply
              (interpret-all
                (vifnt m)
                context
                first-eval)
              (empty-if-nil args)))))
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
              (map
                (fn [x]
                  (conj
                    x
                    { :type :context
                      :context context
                      :value x }))
                given)))
          first-eval)))))

;; A hashmap of functions
(def special
  { "let" let-fn
    "print" print-fn })

(defn rec-binary
  [items func]
  (do
    (println "In recurisve binary func")
    (cond
      (= (count items) 1)
        (first items)
      :default
        (func (first items) (rec-binary (rest items) func)))))

(defn op-fn
  [context items op]
  (let [w (map
              (fn [x]
                (interpret-all (vifnt x) context first-eval))
              items)]
        (op (first w) (rec-binary (rest w) op))))

(defn add-fn
  [context on with]
  (op-fn context with +))

(defn sub-fn
  [context on with]
  (op-fn context with -))

(defn div-fn
  [context on with]
  (op-fn context with /))

(defn mult-fn
  [context on with]
  (op-fn context with *))

(defn eq-fn
  [context on with]
  (op-fn context with =))

(defn lt-fn
  [context on with]
  (op-fn context with <))

(defn if-fn
  [context on with]
  ;; Alright, this function is a bit complicated.
  ;; Basically, it takes one `on` value, evals,
  ;; checks if it's true, if so, evaluates and
  ;; returns the first `with`, otherwise the 
  ;; second.
  (do
    (println "In if")
    (let [evaluated
        (interpret-all (vifnt (first with)) context first-eval)]
      (do
        (println "Checking Equality of" (vifnt (first with)))
        (if evaluated
          (do
            (println evaluated "Is True")
            (interpret-all (vifnt (nth with 1)) context first-eval))
          (do
            (println evaluated "Is False")
            (interpret-all (vifnt (nth with 2)) context first-eval)))))))

;; A hashmap of functions, but unlike special, these
;; can not modify the context. They do still have 
;; access to the AST though.
(def reserved
  { "add" add-fn
    "sub" sub-fn
    "div" div-fn
    "mult" mult-fn
    "eq" eq-fn
    "if" if-fn
    "lt" lt-fn })

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

        ;; Context given in AST
        (= (:type c) :context)
          (do
            (interpret-all
              (rest input)
              context
              (interpret-all 
                (vifnt (:value c))
                (:context c)
                last-eval)))

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
            (let [m (get context (:value c))]
              (if (:cache m)
                (interpret-all (rest input)
                                context
                                (:cache m))
                ;; Otherwise, evaluate lazily.
                (interpret-all (rest input)
                                context
                                ;; Interpret ident b/c lazy
                                (interpret-all
                                  (vifnt (get context (:value c)))
                                  context
                                  first-eval)))))

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
                ;; Or, try a reserved function
                (if-let [r (get reserved (:value (first on)))]
                  (do
                    (interpret-all (rest input)
                                    context
                                    (r context
                                      (rest on)
                                      (vifnt with))))
                  (interpret-all (rest input)
                    ;; Normal methods can not consume context
                    context
                    (eval-method-context context on with))))))
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
