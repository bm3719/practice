(ns malli-test.core
  (:require [malli.core :as m]
            [malli.error :as me]))

;;; Defining and validating schemas

(m/validate int? "1")
(m/validate int? 1)
(m/validate [:and int? [:> 6]] 1)
(m/validate [:and int? [:> 6]] 8)
(m/validate [:enum 1 2 3] 4)

(def valid?
  (m/validator
   [:map
    [:x boolean?]
    [:y {:optional true} int?]
    [:z string?]]))
(valid? {:x true, :z "kikka"})

;;; Schema properties

(def Age
  [:and
   {:title "Age"
    :description "It's an age"
    :json-schema/example 20}
   int? [:> 18]])
(m/properties Age)
(m/validate Age 49)

;;; Closing maps

(m/validate
 [:map [:x int?]]
 {:x 1, "extra" "key"})

(m/validate
 [:map {:closed true} [:x int?]]
 {:x 1, :extra "key"})

;;; Qualified keys and using registry references

(m/validate
 [:map {:registry {::id int?
                   ::country string?}}
  ::id
  [:name string?]
  [::country {:optional true}]]
 {::id 1
  :name "kikka"})

;;; Homogeneous maps (maps of the same k/v pair types)

(m/validate
 [:map-of :string [:map [:lat number?] [:long number?]]]
 {"oslo" {:lat 60 :long 11}
  "helsinki" {:lat 60 :long 24}})

;;; Sequences

(m/validate [:sequential any?] '())

(m/validate [:vector int?] [1 2 3])
(m/validate [:vector int?] [1 2 "sdf"])
(m/validate [:vector [:or int? string?]] [1 2 "sdf"])
(m/validate [:vector [:or int? nil?]] [1 2 nil])
(m/validate [:* int?] [1 2 3])
(m/validate [:? int?] [])

;; Has one or more.
(m/validate [:+ int?] [])
(m/validate [:+ int?] [1 2])

(m/validate [:repeat {:min 2, :max 4} int?] [1 2])

;;; Explain

(m/explain [:map [:lat int?] [:lon int?]] {:lat 1 :lon 2})
(m/explain [:map [:lat int?] [:lon int?]] {:lat 1})

(me/humanize (m/explain [:map [:lat int?] [:lon int?]]
                        {:lat 1}))
(me/humanize (m/explain
              [:map {:closed true} [:lat int?] [:lon int?]]
              {:lat 1 :lon 2 :a 3}))

;;; Custom error messages

;; Note: Requires sci as a project dependency for the :age example.
(->
 [:map
  [:id int?]
  [:size [:enum {:error/message "should be: S|M|L"}
          "S" "M" "L"]]
  [:age [:fn {:error/fn '(fn [{:keys [value]} _]
                           (str value ", should be > 18"))}
         (fn [x] (and (int? x) (> x 18)))]]]
 (m/explain {:size "XL", :age 10})
 (me/humanize))

;; Top-level custom errors.
(-> [:and [:map
           [:password string?]
           [:password2 string?]]
     [:fn {:error/message "passwords don't match"}
      '(fn [{:keys [password password2]}]
         (= password password2))]]
    (m/explain {:password "secret"
                :password2 "faarao"})
    (me/humanize))

;;; Strings

(m/validate string? "kikka")
(m/validate :string "kikka")
(m/validate [:string {:min 1, :max 4}] "")
(m/validate #"a+b+c+" "abbccc")

;;; Function schemas: Any predicate function allowed

(def my-schema
  [:and
   [:map
    [:x int?]
    [:y int?]]
   [:fn (fn [{:keys [x y]}] (> x y))]])
(m/validate my-schema {:x 3 :y 2})

(defn rev-str "" [s]
  (if (empty? s) ""
      (->> (butlast s)
           rev-str
           (list (last s))
           concat
           (apply str))))

(defn update-elem "" [i e coll]
  (concat (take i coll) (vector e) (rest (drop i coll))))
