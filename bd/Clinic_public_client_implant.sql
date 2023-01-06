create table client_implant
(
    id_client  integer not null
        constraint client_implant_clients_id_fk
            references clients,
    id_implant integer not null
        constraint client_implant_implants_id_fk
            references implants,
    constraint client_implant_pk
        primary key (id_client, id_implant)
);

alter table client_implant
    owner to "remoteUser";

