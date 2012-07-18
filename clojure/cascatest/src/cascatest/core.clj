(ns cascatest.core
  (:use cascalog.api
        [cascalog.ops :only (count)]
        cascalog.vars
        [cascalog.workflow :only (fields)])
  (:import [cascading.scheme.hadoop TextDelimited]
           [cascading.tuple Fields]))

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

(defn -main
  "Runs a pretty basic count of number of flights vs. unique carrier."
  [& args]
  (?<- (stdout) [?uniquecarrier ?count]
       ((hfs-delimited "hftp://127.0.0.1:50070/1987-truncated.tsv" :skip-header?
                       true) ?year ?month ?dayofmonth ?dayofweek ?deptime
                       ?crsdeptime ?arrtime ?crsarrtime ?uniquecarrier
                       ?flightnum ?tailnum ?actualelapsedtime ?crselapsedtime
                       ?airtime ?arrdelay ?depdelay ?origin ?dest ?distance
                       ?taxiin ?taxiout ?cancelled ?cancellationcode ?diverted
                       ?carrierdelay ?weatherdelay ?nasdelay ?securitydelay
                       ?lateaircraftdelay)
       (count ?count)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; testing code
(def gen-tuples
  [(range 3)
   (range 1 4)
   (range 2 5)])

(defn test-write
  "This doesn't seem to work.  Not sure why yet.  Also doesn't work over
  hdfs://localhost:9000."
  [] (?<- (hfs-delimited "hftp://localhost:50070/test-out.tsv"
                                   :outfields ["?a" "?b" "?c"]
                                   :outclasses (repeat 3 Integer))
                         [?a ?b ?c]
                         (gen-tuples :> ?a ?b ?c)))
