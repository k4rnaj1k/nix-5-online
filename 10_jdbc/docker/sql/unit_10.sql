CREATE TABLE locations (
  id SERIAL PRIMARY KEY,
  name varchar NOT NULL
) ;

INSERT INTO locations (name) VALUES
('bydgoszcz'),
('gdansk'),
('torun'),
('warszawa');

CREATE TABLE problems (
  id int NOT NULL UNIQUE PRIMARY KEY,
  from_id int REFERENCES locations(id),
  to_id int REFERENCES locations(id)
) ;

CREATE TABLE routes (
  id SERIAL,
  from_id int REFERENCES locations(id),
  to_id int REFERENCES locations(id),
  cost int DEFAULT NULL
) ;

INSERT INTO routes (from_id, to_id, cost) VALUES
('1', '3', '1'),
('1', '4', '3'),
('2', '1', '1'),
('2', '3', '1'),
('2', '4', '4'),
('3', '1', '3'),
('3', '2', '1'),
('3', '4', '1'),
('4', '2', '4'),
('4', '3', '1');

CREATE TABLE solutions (
  problem_id int REFERENCES problems(id)
    ON DELETE CASCADE,
  cost int DEFAULT NULL
) ;

INSERT INTO problems (id, from_id, to_id) VALUES
(1, 1, 4),
(2, 2, 4);