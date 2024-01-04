-- Top level
INSERT INTO organizational_units (is_top_level, name) VALUES (1, 'Organizational Unit 1');
INSERT INTO organizational_units (is_top_level, name) VALUES (1, 'Organizational Unit 2');
INSERT INTO organizational_units (is_top_level, name) VALUES (1, 'Organizational Unit 3');
INSERT INTO organizational_units (is_top_level, name) VALUES (1, 'Organizational Unit 4');

-- Sub level
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Sub Unit 1', 1);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Sub Unit 2', 1);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Sub Unit 3', 1);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Sub Unit 4', 1);

INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Sub Unit 1', 2);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Sub Unit 2', 2);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Sub Unit 3', 2);
