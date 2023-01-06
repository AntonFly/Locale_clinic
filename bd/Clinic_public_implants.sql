create table implants
(
    id          serial
        constraint implants_pk
            primary key,
    name        text not null,
    description integer
);

alter table implants
    owner to "remoteUser";

create unique index implants_name_uindex
    on implants (name);

INSERT INTO public.implants (id, name, description) VALUES (1, 'Почка П63', null);
