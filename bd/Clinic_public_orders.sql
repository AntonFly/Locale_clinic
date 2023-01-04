create table orders
(
    id                   serial
        constraint orders_pk
            primary key,
    id_client            integer not null
        constraint orders_clients_id_fk
            references clients,
    spec                 integer not null
        constraint orders_specializations_id_fk
            references specializations,
    comment              text,
    confirmation         text,
    accompaniment_script integer
        constraint orders_accompaniment_script_id_fk
            references accompaniment_script
            on delete cascade,
    genome               text
);

alter table orders
    owner to "remoteUser";

