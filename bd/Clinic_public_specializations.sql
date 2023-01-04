create table specializations
(
    name varchar(100) not null,
    id   serial
        constraint specializations_pk
            primary key
);

alter table specializations
    owner to "remoteUser";

INSERT INTO public.specializations (name, id) VALUES ('Боец', 3);
INSERT INTO public.specializations (name, id) VALUES ('Агент', 5);
INSERT INTO public.specializations (name, id) VALUES ('Мастер-пилот', 1);
INSERT INTO public.specializations (name, id) VALUES ('Навигатор', 2);
INSERT INTO public.specializations (name, id) VALUES ('Врач', 4);
