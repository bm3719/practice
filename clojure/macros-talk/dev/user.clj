(ns user)

;; Macro infection
(defmacro fidget-stuff [s]
  `(str "Fidget " ~s))

(def strings ["Spinner" "for Lyfe" "Slumber Party" "Convention" "Pride"])

;; Combining reader macros
(defmacro make-spinners [lst]
  `(map (fn [s] (str s " Spinner")) ~lst))

(def spinner-types ["Executive" "Luxury" "Weaponized" "Gravy-dispensing"])

;; gensym examples
(defmacro defspinner
  "Return a function that makes an 3-armed spinner of the specified
  type." [arm]
  `(fn [~'stuff] {:arms [~arm ~arm ~arm]
                  :center ~'stuff}))
