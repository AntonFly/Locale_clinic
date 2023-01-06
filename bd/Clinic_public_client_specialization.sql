create table client_specialization
(
    id_client integer not null
        constraint client_specialization_clients_id_fk
            references clients,
    id_spec   integer not null
        constraint client_specialization_specializations_id_fk
            references specializations,
    constraint client_specialization_pk
        primary key (id_client, id_spec)
);

alter table client_specialization
    owner to "remoteUser";

