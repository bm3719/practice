-- Load some data from a text file.  The default delimiter is ^A.  "overwrite"
-- will replace all existing data in the table.  Behind the scenes, this
-- actually copies the file (either from the local FS or HDFS.  Make sure
-- there's no blank lines in the input file.
load data local inpath './test_input.txt' overwrite into table person;

-- Load into person_detailed using a partition.  This is a column that's
-- specified at load, so multiple files could be specified with different
-- partition settings, and all go to the same table.
load data local inpath './test_input2.txt' overwrite into table person_detailed
     partition (dataset=2);
load data local inpath './test_input3.txt' into table person_detailed
     partition (dataset=1);
