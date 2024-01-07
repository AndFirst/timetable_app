create table public.accounts
(
    id            bigserial
        primary key,
    email_address varchar(255) not null
        constraint uk_iifbg4pgeau1kopq0mk2s0j37
            unique,
    password      varchar(128) not null
);

alter table public.accounts
    owner to "user";

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

create table public.accounts_roles
(
    account_id bigint not null
        constraint fkt44duw96d6v8xrapfo4ff2up6
            references public.accounts,
    role_id    bigint not null
        constraint fkpwest19ib22ux5gk54esw9qve
            references public.roles,
    primary key (account_id, role_id)
);

alter table public.accounts_roles
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
    account_id   bigint
        constraint uk_tkr50aatqx4hfgbpw35mydtpg
            unique
        constraint fkhf70c79mrmq0usp6vs5o053x2
            references public.accounts
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

