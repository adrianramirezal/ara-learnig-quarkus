CREATE KEYSPACE lil WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE TABLE lil.rooms(
  name TEXT,
  room_number TEXT,
  bed_info TEXT,
  PRIMARY KEY((room_number), bed_info)
);

CREATE TABLE lil.employees(
  id UUID,
  first_name text,
  last_name text,
  position text,
  PRIMARY KEY ((id), position)
);

CREATE INDEX bed_info_index on lil.rooms(bed_info);
