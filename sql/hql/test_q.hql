-- Various queries.

-- Basic query.
select pd.first_name,
       pd.last_name
from person_detailed pd
where pd.dataset = 2;

-- Get output to a file in HDFS, do this.  This will create a file called
-- /tmp/names-2/attempt-<attempt_id> for each map job.
insert overwrite directory '/tmp/names-2'
select pd.first_name,
       pd.last_name
from person_detailed pd
where pd.dataset = 2;

-- Do the same, but to a local directory.  It's also possible to output to
-- another table using something like `insert overwrite table <table_name>
-- select ...'.  Have multiple inserts after the from clause to do multi-table
-- inserts.
insert overwrite local directory './names-2'
select pd.first_name,
       pd.last_name
from person_detailed pd
where pd.dataset = 2;

-- Group by example.  
from person p
select p.first_name,
       p.last_name,
       p.favorite_food
where p.favorite_color <> 'blue'
group by p.favorite_food,
         p.first_name,
         p.last_name;

-- Join example.  Joining by ID column.
from person p
join person_detailed pd on (p.first_name = pd.first_name)
select p.id,
       p.first_name,
       p.last_name,
       p.favorite_color,
       pd.last_update;

