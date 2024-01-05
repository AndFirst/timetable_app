create table public.courses
(
    id          bigserial
        primary key,
    code        varchar(50)  not null
        constraint uk_61og8rbqdd2y28rx2et5fdnxd
            unique,
    description varchar(500),
    name        varchar(100) not null
);

alter table public.courses
    owner to "user";

create table public.organizational_units
(
    id             bigserial
        primary key,
    description    varchar(1000),
    is_top_level   smallint,
    name           varchar(100) not null,
    parent_unit_id bigint
        constraint fkj71ivjwq9de1p47olljmv42qg
            references public.organizational_units,
    constraint uklwq558k9844gspeawxh05xbdg
        unique (parent_unit_id, name),
    constraint uk9n3xlu3iwjulxsgc2u45brhd6
        unique (is_top_level, name),
    constraint organizational_units_check
        check (((is_top_level IS NOT NULL) AND (is_top_level = 1) AND (parent_unit_id IS NULL)) OR
               ((is_top_level IS NULL) AND (parent_unit_id IS NOT NULL)))
);

alter table public.organizational_units
    owner to "user";

create table public.class_groups
(
    id                     bigserial
        primary key,
    description            varchar(500),
    name                   varchar(50) not null,
    organizational_unit_id bigint      not null
        constraint fka0se31sep3qkjevn7a1hsanoq
            references public.organizational_units,
    constraint ukkimi6d4gdnqok7dsd11b4gjqu
        unique (name, organizational_unit_id)
);

alter table public.class_groups
    owner to "user";

create table public.classes
(
    id             bigserial
        primary key,
    day_of_week    smallint not null
        constraint classes_day_of_week_check
            check ((day_of_week >= 0) AND (day_of_week <= 6)),
    description    varchar(500),
    end_time       time(6)  not null,
    location       varchar(50),
    start_time     time(6)  not null,
    frequency      char     not null,
    type           varchar(50),
    class_group_id bigint   not null
        constraint fkt1bxboa51c7jxkde2ylestd8y
            references public.class_groups,
    course_id      bigint   not null
        constraint fk9v6ijeybapa0ontdtd4o4rycs
            references public.courses
);

alter table public.classes
    owner to "user";

create table public.roles
(
    id   bigserial
        primary key,
    name varchar(20)
        constraint uk_ofx66keruapi6vyqpv6f2or37
            unique
);

alter table public.roles
    owner to "user";

create table public.tests
(
    id   bigserial
        primary key,
    name varchar(100) not null
);

alter table public.tests
    owner to "user";

create table public.users
(
    id            bigserial
        primary key,
    email_address varchar(255) not null
        constraint uk_1ar956vx8jufbghpyi09yr16l
            unique,
    password      varchar(128) not null
);

alter table public.users
    owner to "user";

create table public.teacher_info
(
    id           bigserial
        primary key,
    biography    varchar(1000),
    degree       varchar(20),
    name         varchar(50),
    phone_number varchar(15),
    surname      varchar(50),
    user_id      bigint
        constraint uk_i292wwbo89d4lb0w9q5cnmgm9
            unique
        constraint fk9h463l6cankpruiqrn2bbwngs
            references public.users
);

alter table public.teacher_info
    owner to "user";

create table public.class_teacher
(
    class_id   bigint not null
        constraint fkfh695osd6ugijlg3hb6nd50
            references public.classes,
    teacher_id bigint not null
        constraint fk7lu4ju9y3ee9qvnfy3y50pn9y
            references public.teacher_info,
    primary key (class_id, teacher_id)
);

alter table public.class_teacher
    owner to "user";

create table public.consultations
(
    id          bigserial
        primary key,
    day_of_week smallint not null
        constraint consultations_day_of_week_check
            check ((day_of_week >= 0) AND (day_of_week <= 6)),
    description varchar(500),
    end_time    time(6)  not null,
    location    varchar(50),
    start_time  time(6)  not null,
    teacher_id  bigint   not null
        constraint fklgw42msgqq218ngp0y9hfumqk
            references public.teacher_info
);

alter table public.consultations
    owner to "user";

create table public.users_roles
(
    user_id bigint not null
        constraint fk2o0jvgh89lemvvo17cbqvdxaa
            references public.users,
    role_id bigint not null
        constraint fkj6m8fwv7oqv74fcehir1a9ffy
            references public.roles,
    primary key (user_id, role_id)
);

alter table public.users_roles
    owner to "user";

