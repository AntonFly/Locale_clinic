create table modification_specialization
(
    spec varchar(100) not null
        constraint modification_specialization_specializations_name_fk
            references specializations,
    mod  varchar(100) not null
        constraint modification_specialization_modifications_name_fk
            references modifications
            on update cascade,
    constraint modification_specialization_pk
        primary key (spec, mod)
);

alter table modification_specialization
    owner to remoteuser;

INSERT INTO public.modification_specialization (spec, mod) VALUES ('Мастер-пилот', 'Дальномер в глазах');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Мастер-пилот', 'Опека над подопечными');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Мастер-пилот', 'Усиленный вестибюлярный аппарат');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Навигатор', 'Пособность построения математических моделей');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Навигатор', 'Нейро-интерфейс');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Мастер-пилот', 'Нейро-интерфейс');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Боец', 'Повышенная выносливость');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Боец', 'Презрение к другим');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Боец', 'Нейро-интерфейс');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Боец', 'Боевые навыки');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Боец', 'Ускорение');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Мастер-пилот', 'Усиленные нижние конечности');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Врач', 'Нейро-интерфейс');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Врач', 'Внедрение знания медицины');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Агент', 'Нейро-интерфейс');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Агент', 'Повышенная выносливость');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Агент', 'Ускорение');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Агент', 'Способность построения сложных логических моделей');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Агент', 'Навыки слежки');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Агент', 'Способность влюблять в себя');
INSERT INTO public.modification_specialization (spec, mod) VALUES ('Агент', 'Боевые навыки');
