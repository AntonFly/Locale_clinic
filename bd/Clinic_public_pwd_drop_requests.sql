create table pwd_drop_requests
(
    user_id      integer               not null
        constraint pwd_reset_requests_users_id_fk
            references users,
    dropped      boolean default false not null,
    request_date timestamp             not null,
    drop_date    timestamp,
    id           serial
        constraint pwd_drop_requests_pk
            primary key
);

alter table pwd_drop_requests
    owner to "remoteUser";

