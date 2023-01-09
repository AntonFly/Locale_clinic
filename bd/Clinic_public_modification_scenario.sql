create table modification_scenario
(
    scanario_id integer not null
        constraint modification_specialization_scenario_id_fk
            references scenario,
    mod_id      integer not null
        constraint modification_specialization_modifications_id_fk
            references modifications,
    constraint modification_specialization_pk
        primary key (scanario_id, mod_id)
);

alter table modification_scenario
    owner to "remoteUser";

INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (3, 7);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (5, 6);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (5, 14);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (5, 8);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (1, 3);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (5, 7);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (4, 11);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (5, 1);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (3, 1);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (2, 9);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (3, 15);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (5, 15);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (5, 16);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (1, 15);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (2, 15);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (1, 13);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (4, 15);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (1, 4);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (3, 6);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (1, 2);
INSERT INTO public.modification_scenario (scanario_id, modification_id) VALUES (3, 12);
