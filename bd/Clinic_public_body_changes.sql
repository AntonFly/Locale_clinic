create table body_changes
(
    "order" integer not null
        constraint body_changes_orders_id_fk
            references orders
            on update cascade on delete cascade,
    change  text    not null,
    id      serial
        constraint body_changes_pk
            primary key
);

alter table body_changes
    owner to "remoteUser";

