create table person
(
    id            integer not null
        constraint "pearson _pk"
            primary key,
    name          varchar not null,
    surname       varchar not null,
    patronymic    varchar,
    date_of_birth date    not null
);

alter table person
    owner to remoteuser;

INSERT INTO public.person (id, name, surname, patronymic, date_of_birth) VALUES (313, 'Иван', 'Давыдов', 'Денисович', '1999-12-12');
INSERT INTO public.person (id, name, surname, patronymic, date_of_birth) VALUES (1, 'Никита', 'И', 'Антонович', '1983-12-05');
INSERT INTO public.person (id, name, surname, patronymic, date_of_birth) VALUES (2, 'Степан', 'Носков', 'Олегович', '2000-01-12');
INSERT INTO public.person (id, name, surname, patronymic, date_of_birth) VALUES (3, 'Имя', 'Фамилия', 'Отчество', '1962-06-01');
INSERT INTO public.person (id, name, surname, patronymic, date_of_birth) VALUES (666, 'Димон', 'Огурчиков', 'Алексеевич', '1970-07-02');
INSERT INTO public.person (id, name, surname, patronymic, date_of_birth) VALUES (123, 'Name', 'Surname', 'Otchestvo', '1974-01-04');
INSERT INTO public.person (id, name, surname, patronymic, date_of_birth) VALUES (9999, 'Tony', 'Avr', 'Dmitr', '2022-06-03');
INSERT INTO public.person (id, name, surname, patronymic, date_of_birth) VALUES (9889, 'Test', 'TEST', 'TEST', '2022-06-02');
