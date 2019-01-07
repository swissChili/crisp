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
;; (group)

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
      [_ colon p single]
      (return p))))

(def lambda
  (bind [l (braces (fwd crisp))]
    (return { :type :lambda
              :value l })))

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
    (<|> method ident str-lit flt-lit num-lit lambda)))

(def crisp
  (>> trim
    (many single)))

(defn parse-crisp
  [input]
  (:value (parse crisp input)))
