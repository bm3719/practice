SET hive.merge.mapfiles=false;

-- Full script including DDL and DML.  This will aggregate some input data that
-- includes the various net worth components.
-- Create a table with delimiters defined.
DROP TABLE net_worth_staging_1;
CREATE TABLE net_worth_staging_1(
       id             INT,
       name           STRING,
       job            STRING,
       cash           DOUBLE,
       checking       DOUBLE,
       savings        DOUBLE,
       investments    DOUBLE,
       equity         DOUBLE,
       debt           DOUBLE,
       mortgage       DOUBLE
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';

LOAD DATA LOCAL INPATH './test_input_full.csv' OVERWRITE INTO TABLE net_worth_staging_1;

ADD FILE ./test_full.py;

-- Create the table with all fields cleaned up and converted to useful
-- aggregates.
DROP TABLE net_worth_staging_2;
CREATE TABLE net_worth_staging_2(
       name           STRING,
       job            STRING,
       assets         DOUBLE,
       liabilities    DOUBLE
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';

-- Populate the table with output from the Python script.
INSERT OVERWRITE TABLE net_worth_staging_2
SELECT
  TRANSFORM(id, name, job, cash, checking, savings, investments,
            equity, debt, mortgage)
  USING 'python test_full.py'
  AS (name, job, assets, liabilities)
FROM net_worth_staging_1;

-- Create final table.  This combines the data load with the creation.
DROP TABLE net_worth;
CREATE TABLE net_worth AS
  SELECT name, job, (assets - liabilities)
  FROM
    net_worth_staging_2;
