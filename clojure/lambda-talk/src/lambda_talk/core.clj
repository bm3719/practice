(ns lambda-talk.core)

;; The y-combinator.
(def y (fn [f]
         ((fn [x]
            (x x))
          (fn [x]
            (f (fn [y]
                 ((x x) y)))))))

(def spin (fn [f]
            (fn [n]
              (if (zero? n) [:spin]
                  (conj (f (dec n)) :spin)))))
