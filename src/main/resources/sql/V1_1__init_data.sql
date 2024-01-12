INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (1, null, 1, 'Architektury', null);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (2, null, 1, 'Elektroniki i Technik Informacyjnych', null);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (3, null, null, 'Diagnostyka i remonty obiektów', 1);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (4, null, null, 'Architektura wnętrz', 1);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (5, null, null, 'Projektowanie architektoniczne', 1);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (6, null, null, 'Projektowanie urbanistyczne', 1);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (7, null, null, 'Automatyka i robotyka', 2);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (8, null, null, 'Cyberbezpieczeństwo', 2);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (9, null, null, 'Elektronika', 2);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (10, null, null, 'Informatyka', 2);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (11, null, null, 'Inżynieria biomedyczna', 2);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (12, null, null, 'Inżynieria internetu rzeczy', 2);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (13, null, null, 'Telekomunikacja', 2);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (14, null, null, '1 stopień', 10);
INSERT INTO public.organizational_units (id, description, is_top_level, name, parent_unit_id) VALUES (15, null, null, '2 stopień', 10);

INSERT INTO public.class_groups (id, description, name, organizational_unit_id) VALUES (1, null, '1 sem', 14);
INSERT INTO public.class_groups (id, description, name, organizational_unit_id) VALUES (2, null, '2 sem', 14);
INSERT INTO public.class_groups (id, description, name, organizational_unit_id) VALUES (3, null, '3 sem', 14);
INSERT INTO public.class_groups (id, description, name, organizational_unit_id) VALUES (4, '', 'Przykładowy plan', 1);

INSERT INTO public.accounts (id, email_address, password) VALUES (1, 'admin@email.com', '$2y$10$eW2d3wfFMny6O2n72uCTiO9g8wke4jRqi87HcKej8Nm9JVkuh4c7q');
INSERT INTO public.accounts (id, email_address, password) VALUES (2, 'teacher@email.com', '$2a$10$rGOBbjlOen0tyy7yj0CgQ.h4Dibotla39jyTthP3H5nVPW6ope8fC');

INSERT INTO public.roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.roles (id, name) VALUES (2, 'ROLE_TEACHER');

INSERT INTO public.accounts_roles (account_id, role_id) VALUES (1, 1);
INSERT INTO public.accounts_roles (account_id, role_id) VALUES (2, 2);

INSERT INTO public.teachers_info (id, biography, degree, name, phone_number, surname, account_id) VALUES (1, '', 'prof.', 'Michał', '', 'Nowakowski', 2);
INSERT INTO public.teachers_info (id, biography, degree, name, phone_number, surname, account_id) VALUES (3, '', 'dr', 'Paweł', '', 'Kozłowski', null);
INSERT INTO public.teachers_info (id, biography, degree, name, phone_number, surname, account_id) VALUES (2, '', 'dr', 'Janusz', '', 'Nowicki', null);

INSERT INTO public.courses (id, code, description, name) VALUES (1, 'PP', '', 'Podstawy Programowania');
INSERT INTO public.courses (id, code, description, name) VALUES (2, 'ASD', '', 'Algorytmy i Struktury Danych');
INSERT INTO public.courses (id, code, description, name) VALUES (3, 'BD', '', 'Bazy Danych');
INSERT INTO public.courses (id, code, description, name) VALUES (4, 'IO', '', 'Inżynieria Oprogramowania');
INSERT INTO public.courses (id, code, description, name) VALUES (5, 'SK', '', 'Sieci Komputerowe');
INSERT INTO public.courses (id, code, description, name) VALUES (6, 'SI', '', 'Sztuczna Inteligencja');
INSERT INTO public.courses (id, code, description, name) VALUES (7, 'BI', '', 'Bezpieczeństwo Informatyczne');

INSERT INTO public.consultations (id, day_of_week, description, end_time, location, start_time, teacher_id) VALUES (1, 1, '', '11:30:00', '', '10:00:00', 3);
INSERT INTO public.consultations (id, day_of_week, description, end_time, location, start_time, teacher_id) VALUES (2, 4, '', '16:00:00', '', '14:00:00', 3);
INSERT INTO public.consultations (id, day_of_week, description, end_time, location, start_time, teacher_id) VALUES (3, 0, '', '14:30:00', '', '13:00:00', 2);
INSERT INTO public.consultations (id, day_of_week, description, end_time, location, start_time, teacher_id) VALUES (5, 4, '', '10:00:00', '', '08:00:00', 1);
INSERT INTO public.consultations (id, day_of_week, description, end_time, location, start_time, teacher_id) VALUES (4, 2, '', '11:30:00', '', '10:00:00', 2);

INSERT INTO public.classes (id, day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (1, 0, '', '12:00:00', '', '10:00:00', 'A', 'laboratorium', 4, 1);
INSERT INTO public.classes (id, day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (2, 1, '', '14:00:00', 'sala 20', '12:00:00', 'A', '', 4, 3);
INSERT INTO public.classes (id, day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (3, 2, '', '14:00:00', '', '10:00:00', 'A', 'wykład', 4, 6);
INSERT INTO public.classes (id, day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (4, 3, '', '16:00:00', '', '14:00:00', 'A', 'wykład', 4, 1);
INSERT INTO public.classes (id, day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (5, 0, '', '17:00:00', '', '15:00:00', 'A', '', 4, 7);
INSERT INTO public.classes (id, day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (6, 1, '', '19:00:00', '', '17:00:00', 'A', '', 4, 1);
INSERT INTO public.classes (id, day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (7, 1, '', '10:00:00', '', '08:00:00', 'A', '', 4, 5);
INSERT INTO public.classes (id, day_of_week, description, end_time, location, start_time, frequency, type, class_group_id, course_id) VALUES (8, 2, '', '19:00:00', '', '17:00:00', 'A', '', 4, 2);

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
