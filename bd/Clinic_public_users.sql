create table users
(
    id        serial
        constraint users_pk
            primary key,
    role      integer not null
        constraint users_userrole_id_fk
            references user_role,
    password  text    not null,
    email     text    not null,
    id_person integer not null
        constraint users_person_id_fk
            references person
            on update cascade on delete cascade
);

alter table users
    owner to remoteuser;

INSERT INTO public.users (id, role, password, email, id_person) VALUES (35, 1, 'TEST_PSWD', 'fsdodsz@shitmail.me', 313);
INSERT INTO public.users (id, role, password, email, id_person) VALUES (36, 4, 'PSWD', 'anton99910@outlook.com', 9999);
