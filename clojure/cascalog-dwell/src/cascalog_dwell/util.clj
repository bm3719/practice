(ns cascalog-dwell.util
  (:use cascalog.api
        cascalog.vars
        [cascalog.workflow :only (fields)]
        [clojure.contrib.math :only (expt)]
        clojure.contrib.generic.math-functions)
  (:import [cascading.scheme.hadoop TextDelimited]
           [cascading.tuple Fields]
           [cascalog.ops IdentityBuffer])
  (:gen-class))

(defn- delimited
  "Modified version of: https://www.refheap.com/paste/2638/raw"
  [field-seq delim & {:keys [classes skip-header?]}]
  (let [skip-header? (boolean skip-header?)
        field-seq    (fields field-seq)
        field-seq    (if (and classes (not (.isDefined field-seq)))
                       (fields (gen-nullable-vars (count classes)))
                       field-seq)]
    (if classes
      (TextDelimited. field-seq skip-header? delim (into-array classes))
      (TextDelimited. field-seq skip-header? delim))))

(defn hfs-delimited
  "Modified version of: https://www.refheap.com/paste/2638/raw"
  [path & opts]
  (let [{:keys [outfields delimiter]} (apply array-map opts)
        scheme (apply delimited
                      (or outfields Fields/ALL)
                      (or delimiter "\t")
                      opts)]
    (apply hfs-tap scheme path opts)))

(defn global-sort
  "Wrap a <- macro in this to sort without needing an aggregator.  The cost is
   an extra reduce step (which would happen anyway with aggregators).  From:
   https://github.com/nathanmarz/cascalog-workshop/blob/master/src/clj/workshop/dynamic.clj"
   [sq fields]
  (let [out-fields (get-out-fields sq)
        new-out-fields (cascalog.vars/gen-nullable-vars (count out-fields))]
    (<- new-out-fields
        (sq :>> out-fields)
        (:sort :<< fields)
        ((IdentityBuffer.) :<< out-fields :>> new-out-fields))))

;; Haversine in Clojure.  Ported this from the Common Lisp implementation at:
;; http://rosettacode.org/wiki/Haversine_formula#Common_Lisp
(let [earth-radius 6372.8
      rad-conv (/ (Math/PI) 180)]
  (defn haversine [x] (expt (sin (/ x 2)) 2))
  (defn dist-rad [lat1 lon1 lat2 lon2]
    (let [hlat (haversine (- lat2 lat1))
          hlon (haversine (- lon2 lon1))
          root (sqrt (+ hlat (* (cos lat1) (cos lat2) hlon)))]
      (* 2 earth-radius (asin root))))
  (defn dist-km
    "Call this to get the distance between two points in km."
    [lat1 lon1 lat2 lon2]
    (apply #'dist-rad (map #(* % rad-conv) (list lat1 lon1 lat2 lon2)))))

