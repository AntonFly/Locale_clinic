create table scenario
(
    id      serial
        constraint scenario_pk
            primary key,
    spec_id integer
        constraint scenario_specializations_id_fk
            references specializations
);

alter table scenario
    owner to "remoteUser";

INSERT INTO public.scenario (id, spec_id) VALUES (1, 1);
INSERT INTO public.scenario (id, spec_id) VALUES (2, 2);
INSERT INTO public.scenario (id, spec_id) VALUES (3, 3);
INSERT INTO public.scenario (id, spec_id) VALUES (4, 4);
INSERT INTO public.scenario (id, spec_id) VALUES (5, 5);
