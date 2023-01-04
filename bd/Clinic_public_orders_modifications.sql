create table orders_modifications
(
    id_order integer not null
        constraint orders_modifications_orders_id_fk
            references orders
            on delete cascade,
    id_mod   integer not null
        constraint orders_modifications_modifications_id_fk
            references modifications,
    constraint orders_modifications_pk
        primary key (id_order, id_mod)
);

alter table orders_modifications
    owner to "remoteUser";

