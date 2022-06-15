create table specializations
(
    name varchar(100) not null
        constraint specializations_pk
            primary key
);

alter table specializations
    owner to remoteuser;

INSERT INTO public.specializations (name) VALUES ('Мастер-пилот');
INSERT INTO public.specializations (name) VALUES ('Навигатор');
INSERT INTO public.specializations (name) VALUES ('Боец');
INSERT INTO public.specializations (name) VALUES ('Врач');
INSERT INTO public.specializations (name) VALUES ('Агент');
