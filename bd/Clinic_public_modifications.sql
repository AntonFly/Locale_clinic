create table modifications
(
    name     varchar(100) not null
        constraint modifications_pk
            primary key,
    price    numeric,
    currency varchar(3)
);

alter table modifications
    owner to remoteuser;

INSERT INTO public.modifications (name, price, currency) VALUES ('Ускорение', 25673, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Усиленный вестибюлярный аппарат', 23798, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Усиленные нижние конечности', 67980, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Опека над подопечными', 15623, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Способность мобилизации ресурсов мышц ', 109358, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Боевые навыки', 19872, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Повышенная выносливость', 54691, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Способность построения сложных логических моделей', 151876, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Пособность построения математических моделей', 16324, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Искренняя любовь ', 14583, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Внедрение знания медицины', 54679, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Презрение к другим', 19156, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Дальномер в глазах', 13245, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Способность влюблять в себя', 123654, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Нейро-интерфейс', 51678, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Навыки слежки', 254631, 'RUB');
INSERT INTO public.modifications (name, price, currency) VALUES ('Отключение синтиметов', 53467, 'RUB');
