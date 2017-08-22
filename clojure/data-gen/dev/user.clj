(ns user)

(def next-sequel-numeral
  (let [counter (atom 0)
        sequel-numerals (apply concat
                               (repeat ["III" "IV" "V" "VI" "VII" "VIII"]))]
    (fn [] (swap! counter inc)
      (nth sequel-numerals @counter))))

(def next-sequel-suffix
  (let [counter (atom 0)
        sequel-suffixes
        (apply concat
               (repeat ["Retribution" "Attack of the Clones" "Extinction"
                        "Salvation" "Apocalypse" "Resurrection" "Endgame"
                        "Simba's Pride" "Reloaded" "The Revenge" "Oblivion"
                        "Age of Extinction" "New Moon" "The Final Chapter"
                        "The College Years"]))]
    (fn [] (swap! counter inc)
      (nth sequel-suffixes @counter))))

(def movies-base
  ["Weekend at Bernie's" "Revenge of the Nerds" "The Karate Kid"
   "Look Who's Talking" "Resident Evil" "The Parent Trap" "Star Trek"
   "Transformers" "Police Academy" "Terminator"])

(def directors
  ["Michael Bay" "JJ Abrams" "Uwe Boll"])

(defn gen-movies [sequels-per-series]
  (for [movie movies-base
        _ (range sequels-per-series)]
    {:name (str movie " "
                (next-sequel-numeral) ": "
                (next-sequel-suffix))
     :director (rand-nth directors)
     :release-date (str (+ 1980 (rand-int 38)) "-"
                        (rand-int 2) (inc (rand-int 2)) "-"
                        (rand-int 2) (inc (rand-int 9)))
     :box-office (str (* 10000000 (int (rand-int 120))))}))

(def first-names
  ["Bronk" "Bric" "Bzzt" "Brz" "Brunk" "Bonk" "Bryce" "Biff" "Brno" "Bro"
   "Brian" "Bron" "Bref"])

(def last-names
  ["Moo" "Mahr" "Mmf" "Mnu" "Monroe" "Mull" "Mueller" "Moof" "Mannlicher"
   "Mott" "Mann" "Mabel" "Moz"])

(def features
  ["explosions" "vampires" "zombies" "male nudity"])

(def positive-reviews
  ["Gratuitous quantities of $; just the way I like it."
   "Gives America what it wants: lots of $."
   "Beautiful non-stop $... often in slow motion."
   "Finally a movie that combines werewolves and $."
   "More than enough $ for the whole family."])

(def neutral-reviews
  ["Has a reasonably satisfying quantities of $."
   "Only way to improve would be more $."
   "Okay but hopefully the forthcoming prequel has more $."
   "+1 for having $. -1 for no close-ups of $."
   "Had just enough $ to keep me awake."])

(def negative-reviews
  ["Not nearly enough $."
   "When I pay good money for $. I expect lots of $."
   "Guess I'll wait for the next reboot to see the $ I expect."
   "I don't think Hollywood understands my insatiable appetite for $."
   "Stop making me think and give me the $ I want."])

(defn gen-reviews [num-reviewers avg-reviews-per movie-names]
  (let [next-movie (let [counter (atom 0)]
                     (fn [] (swap! counter inc)
                       (nth (apply concat (repeat movie-names)) @counter)))]
    (for [reviewer (for [_ (range num-reviewers)]
                     {:name (str (rand-nth first-names) " " (rand-nth last-names))
                      :review-count (- avg-reviews-per (rand-int avg-reviews-per))})
          _        (range (:review-count reviewer))
          :let [rating (rand-int 6)]]
      {:movie-name (next-movie)
       :name (:name reviewer)
       :rating rating
       :review (clojure.string/replace
                (cond (< rating 2) (rand-nth negative-reviews)
                      (< rating 4) (rand-nth neutral-reviews)
                      :else (rand-nth positive-reviews))
                #"\$" (rand-nth features))})))

(defn gen-files []
  (let [movies-keys  [:name :director :release-date :box-office]
        reviews-keys [:movie-name :name :rating :review]
        movies-data  (gen-movies 2)
        reviews-data (gen-reviews 30 5 (map :name movies-data))]
    (spit "movies.csv"
          (->> (map #((apply juxt movies-keys) %) movies-data)
               (into [(map name movies-keys)])
               (map #(clojure.string/join "," %))
               (clojure.string/join "\n")))
    (spit "reviews.csv"
          (->> (map #((apply juxt reviews-keys) %) reviews-data)
               (into [(map name reviews-keys)])
               (map #(clojure.string/join "," %))
               (clojure.string/join "\n")))))
