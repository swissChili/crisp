(ns crisp.parser
  (:gen-class))

(use 'blancas.kern.core
     'blancas.kern.lexer.basic)

;;
;; Syntax:
;;
;; [obj method: arg0 arg1 arg2]
;; [obj method]
;; obj
;; "string literal"
;; 123   ; Int
;; 0.123 ; Float
;; (1 2 3) ; tuple
;; {a [b: c] d} ; syntax array
;; (a): {[print: "hello" a]} ; lambda

(declare single)
(declare crisp)

(def ident
  (bind [i identifier]
    (return { :type :ident
              :value i })))

(def str-lit
  (bind [s string-lit]
    (return { :type :literal
              :value s })))

(def boolean-lit
  (bind [b bool-lit]
    (return { :type :literal
              :value b })))

(def flt-lit
  (bind [f float-lit]
    (return { :type :literal
              :value f })))

(def num-lit
  (bind [n dec-lit]
    (return { :type :literal
              :value n})))

(def idents
  (>> trim
    (many ident)))

(def opt-c-c
  (optional
    (bind
      [_ colon p (many single)]
      (return p))))

(def map-lit
  (braces
    (bind [m (fwd crisp)]
      (return { :type :map
                :value m }))))

(def tupl
  (parens
    (bind [i idents]
      (return i))))

(def lambda
  ;; (args): {body}
  (bind [p tupl _ colon l (braces (many (fwd crisp)))]
    (return { :type :lambda
              :value
              { :args p
                :body l }})))

(def array
  (bind [t tupl]
    (return { :type :array
              :value t })))

(def nil-literal
  (bind [n nil-lit]
    (return { :type :literal
              :value nil })))

(def method
  (brackets
    (>> trim
      (bind
        [o idents p opt-c-c]
        (return { :type :method
                  :value
                  { :on o
                    :with p }})))))

(def single
  (>> trim
    (<|> method str-lit num-lit flt-lit boolean-lit nil-lit ident num-lit lambda map-lit)))

(def crisp
  (>> trim
    (many single)))

(defn parse-crisp
  [input]
  (:value (parse crisp input)))
