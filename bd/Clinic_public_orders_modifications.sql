create table orders_modifications
(
    id_order integer      not null
        constraint orders_modifications_orders_id_fk
            references orders
            on delete cascade,
    mod      varchar(100) not null
        constraint orders_modifications_modifications_name_fk
            references modifications,
    constraint orders_modifications_pk
        primary key (id_order, mod)
);

alter table orders_modifications
    owner to remoteuser;

INSERT INTO public.orders_modifications (id_order, mod) VALUES (13, 'Усиленные нижние конечности');
INSERT INTO public.orders_modifications (id_order, mod) VALUES (13, 'Нейро-интерфейс');
INSERT INTO public.orders_modifications (id_order, mod) VALUES (13, 'Усиленный вестибюлярный аппарат');
INSERT INTO public.orders_modifications (id_order, mod) VALUES (14, 'Нейро-интерфейс');
INSERT INTO public.orders_modifications (id_order, mod) VALUES (14, 'Внедрение знания медицины');
INSERT INTO public.orders_modifications (id_order, mod) VALUES (15, 'Нейро-интерфейс');
INSERT INTO public.orders_modifications (id_order, mod) VALUES (15, 'Опека над подопечными');
INSERT INTO public.orders_modifications (id_order, mod) VALUES (15, 'Дальномер в глазах');
