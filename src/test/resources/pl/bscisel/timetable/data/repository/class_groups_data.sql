INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Organizational Unit 1', 1, NULL);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Organizational Unit 2', 1, NULL);

INSERT INTO class_groups (name, organizational_unit_id) VALUES ('Class Group 2', 1);
INSERT INTO class_groups (name, organizational_unit_id) VALUES ('Class Group 1', 1);
INSERT INTO class_groups (name, organizational_unit_id) VALUES ('Class Group 3', 1);

INSERT INTO class_groups (name, organizational_unit_id) VALUES ('Class Group 1', 2);
