CREATE TABLE post (
 id SERIAL PRIMARY KEY,
 name TEXT,
 description TEXT,
 created TIMESTAMP not null,
 city_id int references city(id)
);