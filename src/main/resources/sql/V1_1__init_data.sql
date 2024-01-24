INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, 1, 'Architektury', null);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, 1, 'Elektroniki i Technik Informacyjnych', null);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Diagnostyka i remonty obiektów', 1);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Architektura wnętrz', 1);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Projektowanie architektoniczne', 1);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Projektowanie urbanistyczne', 1);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Automatyka i robotyka', 2);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Cyberbezpieczeństwo', 2);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Elektronika', 2);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Informatyka', 2);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Inżynieria biomedyczna', 2);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Inżynieria internetu rzeczy', 2);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, 'Telekomunikacja', 2);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, '1 stopień', 10);
INSERT INTO public.organizational_units (description, is_top_level, name, parent_unit_id) VALUES (null, null, '2 stopień', 10);

INSERT INTO public.class_groups (description, name, organizational_unit_id) VALUES (null, '1 sem', 14);
INSERT INTO public.class_groups (description, name, organizational_unit_id) VALUES (null, '2 sem', 14);
INSERT INTO public.class_groups (description, name, organizational_unit_id) VALUES (null, '3 sem', 14);
INSERT INTO public.class_groups (description, name, organizational_unit_id) VALUES ('', 'Przykładowy plan', 1);

INSERT INTO public.accounts (email_address, password) VALUES ('admin@email.com', '$2y$10$eW2d3wfFMny6O2n72uCTiO9g8wke4jRqi87HcKej8Nm9JVkuh4c7q');
INSERT INTO public.accounts (email_address, password) VALUES ('teacher@email.com', '$2a$10$rGOBbjlOen0tyy7yj0CgQ.h4Dibotla39jyTthP3H5nVPW6ope8fC');

INSERT INTO public.roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.roles (name) VALUES ('ROLE_TEACHER');

INSERT INTO public.accounts_roles (account_id, role_id) VALUES (1, 1);
INSERT INTO public.accounts_roles (account_id, role_id) VALUES (2, 2);

INSERT INTO public.teachers_info (biography, degree, name, phone_number, surname, account_id) VALUES ('', 'prof.', 'Michał', '', 'Nowakowski', 2);
INSERT INTO public.teachers_info (biography, degree, name, phone_number, surname, account_id) VALUES ('', 'dr', 'Paweł', '', 'Kozłowski', null);
INSERT INTO public.teachers_info (biography, degree, name, phone_number, surname, account_id) VALUES ('', 'dr', 'Janusz', '', 'Nowicki', null);

INSERT INTO public.courses (code, description, name) VALUES ('PP', '', 'Podstawy Programowania');
INSERT INTO public.courses (code, description, name) VALUES ('ASD', '', 'Algorytmy i Struktury Danych');
INSERT INTO public.courses (code, description, name) VALUES ('BD', '', 'Bazy Danych');
INSERT INTO public.courses (code, description, name) VALUES ('IO', '', 'Inżynieria Oprogramowania');
INSERT INTO public.courses (code, description, name) VALUES ('SK', '', 'Sieci Komputerowe');
INSERT INTO public.courses (code, description, name) VALUES ('SI', '', 'Sztuczna Inteligencja');
INSERT INTO public.courses (code, description, name) VALUES ('BI', '', 'Bezpieczeństwo Informatyczne');

INSERT INTO public.consultations (day_of_week, description, end_time, location, start_time, teacher_id) VALUES (1, '', '11:30:00', '', '10:00:00', 3);
INSERT INTO public.consultations (day_of_week, description, end_time, location, start_time, teacher_id) VALUES (4, '', '16:00:00', '', '14:00:00', 3);
INSERT INTO public.consultations (day_of_week, description, end_time, location, start_time, teacher_id) VALUES (0, '', '14:30:00', '', '13:00:00', 2);
INSERT INTO public.consultations (day_of_week, description, end_time, location, start_time, teacher_id) VALUES (4, '', '10:00:00', '', '08:00:00', 1);
INSERT INTO public.consultations (day_of_week, description, end_time, location, start_time, teacher_id) VALUES (2, '', '11:30:00', '', '10:00:00', 2);

INSERT INTO public.classes (day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (0, '', '12:00:00', '', '10:00:00', 'A', 'laboratorium', 4, 1);
INSERT INTO public.classes (day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (1, '', '14:00:00', 'sala 20', '12:00:00', 'A', '', 4, 3);
INSERT INTO public.classes (day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (2, '', '14:00:00', '', '10:00:00', 'A', 'wykład', 4, 6);
INSERT INTO public.classes (day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (3, '', '16:00:00', '', '14:00:00', 'A', 'wykład', 4, 1);
INSERT INTO public.classes (day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (0, '', '17:00:00', '', '15:00:00', 'A', '', 4, 7);
INSERT INTO public.classes (day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (1, '', '19:00:00', '', '17:00:00', 'A', '', 4, 1);
INSERT INTO public.classes (day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (1, '', '10:00:00', '', '08:00:00', 'A', '', 4, 5);
INSERT INTO public.classes (day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (2, '', '19:00:00', '', '17:00:00', 'A', '', 4, 2);

INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (1, 1);
INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (2, 2);
INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (3, 3);
INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (4, 3);
INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (4, 2);
INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (5, 2);
INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (6, 1);
INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (7, 1);
INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (7, 2);
INSERT INTO public.classes_teachers (class_id, teacher_id) VALUES (8, 3);
