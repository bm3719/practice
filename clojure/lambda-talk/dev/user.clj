(ns user
  (:require [clojure.tools.namespace.repl :as repl]))

;; From: https://gist.github.com/sunilnandihalli/745654
(defmacro def-curry-fn
  "creates a curried version of a function"
  [name args & body]
  {:pre [(not-any? #{'&} args)]}
  (if (empty? args)
    `(defn ~name ~args ~@body)
    (let [rec-funcs (reduce (fn [l v]
                              `(letfn [(helper#
                                         ([] helper#)
                                         ([x#] (let [~v x#] ~l))
                                         ([x# & rest#] (let [~v x#]
                                                         (apply (helper# x#) rest#))))]
                                 helper#))
                            `(do ~@body) (reverse args))]
      `(defn ~name [& args#]
         (let [helper# ~rec-funcs]
           (apply helper# args#))))))

;;; SKI

;; λx.x
(def I identity)

;; λxy.x
;; K x y = x
(def-curry-fn K [x y] x)

;; λxyz.xz(yz)
;; S f g x = f x (g x)
(def-curry-fn S [f g x] ((f x) (g x)))

;; (def x 1)
;; (= x (I x))
;; ((K 1) 2)
;; (K 1 2)
;; (= (I x) ((S K K) x))

;; succ function
(def succ (S (S (K +) (K 1)) I))

;; The y-combinator.
;; lambda notation: (λx.λy.xyx) (λy.λx.y(xyx))
;;              or: λf.(λx.f(xx))(λx.f(xx))
;; SKI calculus:    (def Y (S (K (S I I)) (S (S (K S) K) (K (S I I)))))
(def Y (fn [f]
         ((fn [x]
            (x x))
          (fn [x]
            (f (fn [y]
                 ((x x) y)))))))

(def fidget-spinner
  (fn [f]
    (fn [n]
      (if (zero? n) []
          (conj (f (dec n)) :spin)))))

(((fn [f]
    ((fn [x]
       (x x))
     (fn [x]
       (f (fn [y]
            ((x x) y))))))
  (fn [f]
    (fn [n]
      (if (zero? n) []
          (conj (f (dec n)) :spin))))) 7)
