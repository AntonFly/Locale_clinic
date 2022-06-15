create table clients
(
    id        serial
        constraint clients_pk
            primary key,
    email     text    not null,
    comment   text,
    id_person integer not null
        constraint clients_person_id_fk
            references person
            on update cascade on delete cascade
);

alter table clients
    owner to remoteuser;

INSERT INTO public.clients (id, email, comment, id_person) VALUES (10, 'INA@mail.com', 'Для МПИ демо', 1);
INSERT INTO public.clients (id, email, comment, id_person) VALUES (11, 'stepa99@ya.ru', 'Для МПИ демо', 2);
INSERT INTO public.clients (id, email, comment, id_person) VALUES (12, 'test@email.mail', 'Client comment', 3);
INSERT INTO public.clients (id, email, comment, id_person) VALUES (13, 'demon@mail.xm', '', 666);
INSERT INTO public.clients (id, email, comment, id_person) VALUES (14, 'test@gmail.mail', '', 123);
INSERT INTO public.clients (id, email, comment, id_person) VALUES (15, 'mail@mail.ru', 'MPI', 9889);
