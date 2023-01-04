create table person
(
    id            serial
        constraint "pearson _pk"
            primary key,
    name          varchar not null,
    surname       varchar not null,
    patronymic    varchar,
    date_of_birth date    not null
);

alter table person
    owner to "remoteUser";

