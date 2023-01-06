create table passports
(
    id_person integer not null
        constraint passports_person_id_fk
            references person
            on update cascade on delete cascade,
    passport  integer not null
        constraint passports_pk
            primary key
        constraint passports_passport_check
            check ((passport > 999999999) AND (passport < '10000000000'::bigint))
);

alter table passports
    owner to "remoteUser";

