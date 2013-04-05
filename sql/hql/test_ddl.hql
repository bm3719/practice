-- Clean up test tables.
drop table person;
drop table person_detailed;
drop table test_table;

-- Create a basic test table.
create table test_table(
       id             int,
       first_name     string,
       last_name      string,
       favorite_color string
);

-- Create a test table with a partition.  These are useful if the input data to
-- be loaded into the table includes something that would normally be used to
-- demarcate splits.
create table test_table2(
       id             int,
       first_name     string,
       last_name      string,
       date_of_birth  string
) partitioned by(dataset int);

-- Perform some table alters.
-- Add a column.
alter table test_table add columns (favorite_food string);
-- Add a column with comment.  These show up in the table describe, which can
-- make things easier to read.
alter table test_table2 add columns (last_update string comment
      'The last time this record was updated');
-- Rename tables.
alter table test_table rename to person;
alter table test_table2 rename to person_detailed;

