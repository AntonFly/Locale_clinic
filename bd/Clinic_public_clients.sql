create table clients
(
    id        serial
        constraint clients_pk
            primary key,
    email     text    not null,
    comment   text,
    id_person integer not null
        constraint clients_person_id_fk
            references person
            on update cascade on delete cascade
);

alter table clients
    owner to "remoteUser";

