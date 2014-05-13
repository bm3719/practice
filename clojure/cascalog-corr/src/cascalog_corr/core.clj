(ns cascalog-corr.core
  (:use cascalog.api
        cascalog.vars
        [cascalog.workflow :only (fields)])
  (:import [cascading.scheme.hadoop TextDelimited]
           [cascading.tuple Fields]
           [org.apache.commons.math.stat.correlation PearsonsCorrelation]
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

(def ^:dynamic *correlate-cutoff* 0.9)

(defn split [strvector]
  (double-array
   (map (fn [x] (double (read-string x)))
        (.split strvector ","))))

(defn corr [x y]
  (. (new PearsonsCorrelation) correlation (split x) (split y)))

(defn corr-filter [x]
  (> (Math/abs x) *correlate-cutoff*))

(defn correlation-analytic
  "Self join some data and deliver results from PearsonsCorrelation in
  commons-math, sorted by IP addresses."
  [hfs-input-file hfs-output-file]
  (let [vts-tap (hfs-delimited hfs-input-file :skip-header? false)]
    (?- (hfs-textline hfs-output-file)
        (global-sort
         (<- [?ip1 ?ip2 ?corr]
             (vts-tap :> ?ip1 ?ts1)
             (vts-tap :> ?ip2 ?ts2)
             (corr ?ts1 ?ts2 :> ?corr)
             (corr-filter ?corr)
             (cross-join))
         ["?ip1" "?ip2"]))))

(defn -main
  "Call the main analytic function."
  [& args]
  ;; Adjust the number of mappers according to data input size.
  (with-job-conf{"mapred.map.tasks" 10}
  (with-job-conf{"mapred.reduce.tasks" 10}  (correlation-analytic
"vast_time_series_data.tsv" "corr-out"))))
