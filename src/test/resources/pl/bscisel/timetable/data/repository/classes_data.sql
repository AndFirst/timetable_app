INSERT INTO teachers_info (name, surname) VALUES ('Jan', 'Kowalski');
INSERT INTO teachers_info (name, surname) VALUES ('Adam', 'Nowak');

INSERT INTO organizational_units (is_top_level, name) VALUES (1, 'Wydział Informatyki');

INSERT INTO class_groups (name, organizational_unit_id) VALUES ('Grupa 1', 1);
INSERT INTO class_groups (name, organizational_unit_id) VALUES ('Grupa 2', 1);

INSERT INTO courses (code, name) VALUES ('INZ-123', 'Inżynieria oprogramowania');

INSERT INTO classes (day_of_week, start_time, end_time, frequency, class_group_id, course_id) VALUES (0, '8:00', '10:00', 'A', 1, 1);
INSERT INTO classes (day_of_week, start_time, end_time, frequency, class_group_id, course_id) VALUES (0, '10:00', '12:00', 'A', 2, 1);
INSERT INTO classes (day_of_week, start_time, end_time, frequency, class_group_id, course_id) VALUES (0, '12:00', '14:00', 'A', 2, 1);

INSERT INTO classes_teachers (class_id, teacher_id) VALUES (1, 1);
INSERT INTO classes_teachers (class_id, teacher_id) VALUES (2, 2);
INSERT INTO classes_teachers (class_id, teacher_id) VALUES (3, 2);
