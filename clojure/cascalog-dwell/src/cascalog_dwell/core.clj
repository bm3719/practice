(ns cascalog-dwell.core
  (:use cascalog.api
        cascalog.vars
        [cascalog-dwell.util :as util]
        clj-time.format
        [clj-time.core :only (interval in-minutes after? sec)]
        clojure.contrib.generic.math-functions)
  (:gen-class))

(defn dt-parse
  "Parse a datetime from the AIS data format." [dt-str]
  (parse (formatter "yyyy-MM-dd'T'HH:mm:ss'Z'") dt-str))
(defn min-diff
  "Return the difference in minutes between two datetimes." [dt1 dt2]
  (in-minutes (if (after? dt1 dt2)
                (interval dt2 dt1)
                (interval dt1 dt2))))

(defaggregateop loc-dt-agg
  "Roll up lat/lon/dt points."
  ([] [])
  ([result lat lon dt]
     (if (empty? result)
       [(vec (list lat lon dt))]
       (conj result (vec (list lat lon dt)))))
  ([result] [[result]]))

(let [plat (ref 0)
      plon (ref 0)
      pdt (ref 0)]
  (defn update-p [clat clon cdt]
    (dosync (ref-set plat clat))
    (dosync (ref-set plon clon))
    (dosync (ref-set pdt cdt)))
  (defn d-point [plat2 plon2 clat clon pdt2 cdt]
    (let [dist (dist-km plat2 plon2 clat clon)
          secs (+ (* 60 (min-diff pdt2 cdt)) (abs (- (sec pdt2) (sec cdt))))]
      (if (and (< dist 1) (> secs (* 60 15)))
        [clat clon secs]
        [0 0 0])))   ; This equates to no dwell time.
  (defn calc-points [lst]
    (let [[clat clon cdt] lst]
      (if (= @plat 0)
        (update-p clat clon cdt)
        (let [ret-val (d-point @plat @plon clat clon @pdt cdt)]
          (update-p clat clon cdt)
          ret-val)))))

(defn dwell-calc
  "Takes a delimited path string, sorts it by datetime, and computes dwell
  points.  Returns a list of 3-vecs of lat lon dwell_time." [path]
  (defn event-split [e-list]
    (let [[lat lon dt] e-list]
      [(double (read-string lat))
       (double (read-string lon))
       (dt-parse dt)]))
  (let [points (sort-by #(nth % 2) (apply list (map event-split path)))]
    (update-p 0 0 0)
    (rest (map #'calc-points points))))

(defaggregateop dwell-collect
  ([] [])
  ([result list-by-id] (reduce conj (dwell-calc list-by-id) result))
  ([result] result))

(defaggregateop dosum
  ([] 0)
  ([result count] (+ count result))
  ([result] [result]))

(defn dwell
  "Dwell time analytic over AIS data."
  [hfs-input-file hfs-output-file]
  (let [in-tap (util/hfs-delimited hfs-input-file :skip-header? false :delimiter "\t")
        collapse-by-id (<- [?id ?list]
                           (in-tap :> ?dt ?id ?shipname ?type ?lat ?lon ?heading ?speed)
                           (loc-dt-agg ?lat ?lon ?dt :> ?list))
        collapse-lists (<- [?lat ?lon ?secs]
                           (collapse-by-id :> _ ?list)
                           (dwell-collect ?list :> ?lat ?lon ?secs)
                           (> ?lat 0))]
    (?<- (hfs-textline hfs-output-file)
         [?lat ?lon ?sum]
         (collapse-lists :> ?lat ?lon ?secs)
         (dosum ?secs :> ?sum))))

(defn -main
  [& args]
  (dwell "final_output.csv" "ais-dwell-out"))
