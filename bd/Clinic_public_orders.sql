create table orders
(
    id        serial
        constraint orders_pk
            primary key,
    id_client integer      not null
        constraint orders_clients_id_fk
            references clients,
    spec      varchar(100) not null
        constraint orders_specializations_name_fk
            references specializations,
    comment   text
);

alter table orders
    owner to remoteuser;

INSERT INTO public.orders (id, id_client, spec, comment) VALUES (13, 14, 'Мастер-пилот', 'Заявка для демо');
INSERT INTO public.orders (id, id_client, spec, comment) VALUES (14, 13, 'Врач', 'Заявка для демо');
INSERT INTO public.orders (id, id_client, spec, comment) VALUES (15, 15, 'Мастер-пилот', '');
