create table modifications
(
    name     varchar(100) not null,
    price    numeric,
    currency varchar(3),
    id       serial
        constraint modifications_pk
            primary key,
    risk     text
);

alter table modifications
    owner to "remoteUser";

INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Ускорение', 25673, 'RUB', 1, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Усиленный вестибюлярный аппарат', 23798, 'RUB', 2, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Усиленные нижние конечности', 67980, 'RUB', 3, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Опека над подопечными', 15623, 'RUB', 4, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Способность мобилизации ресурсов мышц ', 109358, 'RUB', 5, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Боевые навыки', 19872, 'RUB', 6, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Повышенная выносливость', 54691, 'RUB', 7, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Способность построения сложных логических моделей', 151876, 'RUB', 8, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Пособность построения математических моделей', 16324, 'RUB', 9, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Искренняя любовь ', 14583, 'RUB', 10, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Внедрение знания медицины', 54679, 'RUB', 11, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Презрение к другим', 19156, 'RUB', 12, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Дальномер в глазах', 13245, 'RUB', 13, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Способность влюблять в себя', 123654, 'RUB', 14, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Нейро-интерфейс', 51678, 'RUB', 15, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Навыки слежки', 254631, 'RUB', 16, null);
INSERT INTO public.modifications (name, price, currency, id, risk) VALUES ('Отключение синтиметов', 53467, 'RUB', 17, null);
