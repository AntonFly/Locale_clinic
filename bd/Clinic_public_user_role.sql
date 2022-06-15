create table user_role
(
    id   serial
        constraint user_role_pk
            primary key,
    role varchar(50) not null
);

alter table user_role
    owner to remoteuser;

create unique index user_role_role_uindex
    on user_role (role);

INSERT INTO public.user_role (id, role) VALUES (2, 'ROLE_MANAGER');
INSERT INTO public.user_role (id, role) VALUES (5, 'ROLE_ENGINEER');
INSERT INTO public.user_role (id, role) VALUES (4, 'ROLE_MEDIC');
INSERT INTO public.user_role (id, role) VALUES (3, 'ROLE_SCIENTIST');
INSERT INTO public.user_role (id, role) VALUES (1, 'ROLE_ADMIN');
