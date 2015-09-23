;;; Google Code Jam: Reverse Words
;;; http://code.google.com/codejam/contest/351101/dashboard#s=p1

(ns practice.reverse-words)

(def test-input "3
this is a test
foobar
all your base")

(defn format-output
  "Generate the output string." [case-num col]
  (str "Case #" case-num ": " (clojure.string/join " " col)))

(defn solve-reverse-words
  "Solve the reverse words problem" []
  (doseq [[n output] (->> (rest (clojure.string/split test-input #"\n"))
                          (map #(clojure.string/split % #" "))
                          (map reverse)
                          (map (fn [n s] [n s]) (map inc (range))))]
    (println (format-output n output))))
