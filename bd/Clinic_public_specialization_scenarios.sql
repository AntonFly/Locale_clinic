create table specialization_scenarios
(
    scenarios      text,
    specialization varchar(100)
        constraint specialization_scenarios_specializations_name_fk
            references specializations,
    id             serial
        constraint specialization_scenarios_pk
            primary key
);

alter table specialization_scenarios
    owner to remoteuser;

INSERT INTO public.specialization_scenarios (scenarios, specialization, id) VALUES ('Проверить температуру
Восстановить кислотнощелочной баланс
Напоить
Проверить пульс
Проверить артериальное давление
Охладить
Снять экг
Накормить
Дать электролиты', 'Навигатор', 2);
INSERT INTO public.specialization_scenarios (scenarios, specialization, id) VALUES ('Проверить пульс
Ущипнуть за сосок
Проверить серцебиение
Проверить давление
Проверить температуру
Восстановить щелочной баланс
Дать электролиты
Охладить
Снять ЭКГ
Проверить глазное давление
', 'Мастер-пилот', 1);
INSERT INTO public.specialization_scenarios (scenarios, specialization, id) VALUES ('Проверить серцебиение в центе грудной клетки на уровне солнечного сплетения
Проверить кандицию мышц
Проверить объем легких
Проверить способность виспринимать раздрожители
Ущипнуть за сосок
Проверить серцебиение
Проверить давление
Проверить температуру
Охладить
Снять экг
Накормить
Дать электролиты', 'Боец', 3);
INSERT INTO public.specialization_scenarios (scenarios, specialization, id) VALUES ('Проверить когнетивные функции
Проверить температуру головы
Проверить ЧСС
Если нужно охладить', 'Врач', 4);
INSERT INTO public.specialization_scenarios (scenarios, specialization, id) VALUES ('Проверить серцебиение в центе грудной клетки на уровне солнечного сплетения
Проверить кандицию мышц
Проверить объем легких
Проверить способность виспринимать раздрожители
Охладить
Снять экг
Накормить
Дать электролиты
Ущипнуть за сосок
Проверить серцебиение
Проверить давление
Проверить температуру
Проверить свойство кожи приспосабливать температуру под окружающую среду
Проверить свойство кожи менять цвет', 'Агент', 5);
