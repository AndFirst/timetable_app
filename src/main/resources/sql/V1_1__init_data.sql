INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Administracji i Nauk Społecznych', 1, Null);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Architektury', 1, Null);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Chemiczny', 1, Null);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Elektroniki i Technik Informacyjnych', 1, Null);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Elektryczny', 1, Null);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Fizyki', 1, Null);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Geodezji i Kartografii', 1, Null);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Instalacji Budowlanych, Hydrotechniki i Inżynierii Środowiska', 1, Null);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Inżynierii Chemicznej i Procesowej', 1, Null);
INSERT INTO organizational_units (name, is_top_level, parent_unit_id) VALUES ('Inżynierii Lądowej', 1, Null);

INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Diagnostyka i remonty obiektów', 2);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Architektura wnętrz', 2);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Projektowanie architektoniczne', 2);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Projektowanie urbanistyczne', 2);

INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Automatyka i robotyka', 4);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Cyberbezpieczeństwo', 4);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Elektronika', 4);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Informatyka', 4);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Inżynieria biomedyczna', 4);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Inżynieria internetu rzeczy', 4);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('Telekomunikacja', 4);

INSERT INTO organizational_units (name, parent_unit_id) VALUES ('1 stopień', 18);
INSERT INTO organizational_units (name, parent_unit_id) VALUES ('2 stopień', 18);

INSERT INTO class_groups (name, organizational_unit_id) VALUES ('1 sem', 22);
INSERT INTO class_groups (name, organizational_unit_id) VALUES ('2 sem', 22);
INSERT INTO class_groups (name, organizational_unit_id) VALUES ('3 sem', 22);
INSERT INTO class_groups (name, organizational_unit_id) VALUES ('4 sem', 22);
INSERT INTO class_groups (name, organizational_unit_id) VALUES ('5 sem', 22);
INSERT INTO class_groups (name, organizational_unit_id) VALUES ('6 sem', 22);
INSERT INTO class_groups (name, organizational_unit_id) VALUES ('7 sem', 22);

INSERT INTO users (email_address, password) VALUES ('admin@email.com', '$2y$10$eW2d3wfFMny6O2n72uCTiO9g8wke4jRqi87HcKej8Nm9JVkuh4c7q');
INSERT INTO users (email_address, password) VALUES ('teacher@email.com', '$2a$10$rGOBbjlOen0tyy7yj0CgQ.h4Dibotla39jyTthP3H5nVPW6ope8fC');

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_TEACHER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
