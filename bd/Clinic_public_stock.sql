create table stock
(
    id               serial,
    vendor_code      integer     not null,
    name             varchar(30) not null,
    amount           integer     not null,
    description      text,
    min_amount       integer,
    last_update      integer     not null
        constraint stock_users_id_fk
            references users,
    last_update_time timestamp default CURRENT_TIMESTAMP,
    constraint stock_pk
        primary key (vendor_code, id)
);

alter table stock
    owner to "remoteUser";

