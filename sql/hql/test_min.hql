SET hive.merge.mapfiles=false;

DROP TABLE min_staging_1;
CREATE TABLE min_staging_1 (
       fname          STRING,
       lname          STRING
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';

LOAD DATA LOCAL INPATH './test_input_min.csv' OVERWRITE INTO TABLE min_staging_1;

ADD FILE ./test_min.py;

DROP TABLE min_staging_2;
CREATE TABLE min_staging_2 (
       fname          STRING,
       lname          STRING
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';

INSERT OVERWRITE TABLE min_staging_2
SELECT
  TRANSFORM(fname, lname)
  USING 'python test_min.py'
  AS (fname, lname)
FROM min_staging_1;



  
       

