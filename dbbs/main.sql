-- person
SELECT person_valid  (person_id => floor(313 + random( ) *10000 )::int);
SELECT person_invalid(person_id => floor(313 + random( ) *10000 )::int);

-- passports
SELECT passport_valid
    (
        person_id   => floor(313 + random( ) *10000 )::int,
        passport_id => 1234567890
    );
SELECT passport_invalid
    (
        person_id   => floor(313 + random( ) *10000 )::int,
        passport_id => 4
    );
SELECT passport_fk_invalid
    (
        person_id   => floor(313 + random( ) *10000 )::int,
        passport_id => 1234567890
    );

-- clients
SELECT client_valid
    (
        person_id => floor(313 + random( ) *10000 )::int,
        client_id => floor(313 + random( ) *10000 )::int
    );
SELECT client_fk_invalid
    (
        person_id => floor(313 + random( ) *10000 )::int,
        client_id => floor(313 + random( ) *10000 )::int
    );

-- user_role
SELECT user_role_valid(role_id => floor(313 + random( ) *10000 )::int);

SELECT user_role_unique
    (
        role_id     => floor(313 + random( ) *1000 )::int, 
        sec_role_id => floor(313 + random( ) *10000 )::int
    );

-- users
SELECT users_valid
    (
        person_id    => floor(313 + random( ) *10000 )::int, 
        role_id      => floor(313 + random( ) *10000 )::int, 
        test_user_id => floor(313 + random( ) *10000 )::int
    );
SELECT user_person_fk_invalid
    (
        person_id    => floor(1 + random( ) *10000 )::int, 
        role_id      => floor(313 + random( ) *10000 )::int,
        test_user_id => floor(313 + random( ) *10000 )::int
    );
SELECT user_role_fk_invalid
    (
        person_id    => floor(313 + random( ) *10000 )::int,
        role_id      => floor(1 + random( ) *10000 )::int,
        test_user_id => floor(313 + random( ) *10000 )::int
    );

-- pwd_drop_requests
SELECT pwd_drop_request_valid
    (
        person_id    => floor(313 + random( ) *10000 )::int,
        test_user_id => floor(313 + random( ) *10000 )::int,
        role_id      => floor(313 + random( ) *10000 )::int,
        drop_id      => floor(313 + random( ) *10000 )::int
    );

SELECT pwd_drop_request_fk_invalid
    (
        drop_id =>  floor(313 + random( ) *10000 )::int,
        fk      =>  floor(1 + random( ) *10000 )::int
    );

-- stock
SELECT stock_valid
    (
        person_id    => floor(313 + random( ) *10000 )::int,
        role_id      => floor(313 + random( ) *10000 )::int,
        test_user_id => floor(313 + random( ) *10000 )::int,
        stock_id     => floor(313 + random( ) *10000 )::int
    );
SELECT stock_user_fk_invalid
    (
        test_user_id  => floor(1 + random( ) *10000 )::int,
        stock_id      =>  floor(313 + random( ) *10000 )::int
    );
SELECT stock_vendor_invalid
    (
        person_id     => floor(313 + random( ) *10000 )::int,
        role_id       => floor(313 + random( ) *10000 )::int,
        test_user_id  => floor(313 + random( ) *10000 )::int,
        stock_id      => floor(313 + random( ) *10000 )::int
    );

-- implants
SELECT implants_valid(implant_id => floor(313 + random( ) *10000 )::int);
SELECT implants_invalid(implant_id => floor(313 + random( ) *10000 )::int);

-- client_implants
SELECT client_implant_valid
    (
        person_id  => floor(313 + random( ) *10000 )::int,
        client_id  => floor(313 + random( ) *10000 )::int,
        implant_id => floor(313 + random( ) *10000 )::int
    );
SELECT client_fk_implant_invalid
    (
        client_id  => floor(313 + random( ) *10000 )::int,
        implant_id => floor(313 + random( ) *10000 )::int
    );
SELECT client_implant_fk_invalid
    (
        person_id  => floor(313 + random( ) *10000 )::int,
        client_id  => floor(313 + random( ) *10000 )::int,
        implant_id => floor(313 + random( ) *10000 )::int
    );

-- accompaniment
SELECT accompaniment_valid(accompaniment_id => floor(313 + random( ) *10000 )::int);
SELECT accompaniment_invalid(accompaniment_id => floor(313 + random( ) *10000 )::int);

-- specializations
SELECT specialization_valid(spec_id => floor(313 + random( ) *10000 )::int);
SELECT specializations_invalid(spec_id => floor(313 + random( ) *10000 )::int);

-- client_specializations
SELECT client_mod_valid
    (
        person_id => floor(313 + random( ) *10000 )::int,
        client_id => floor(313 + random( ) *10000 )::int,
        mod_id   => floor(313 + random( ) *10000 )::int
    );
SELECT client_fk_mod_invalid
    (
        client_id => floor(313 + random( ) *10000 )::int,
        mod_id   => floor(313 + random( ) *10000 )::int
    );
-- SELECT client_mod_fk_invalid
--     (
--         person_id => floor(313 + random( ) *100000 )::int,
--         client_id => floor(313 + random( ) *100000 )::int,
--         mod_id   => floor(313 + random( ) *100000 )::int
--     );

-- scenario
SELECT scenario_valid
    (
        id_spec => floor(313 + random( ) *10000 )::int, 
        scen_id => floor(313 + random( ) *10000 )::int
    );
SELECT scenario_fk_invalid
    (
        id_spec => floor(313 + random( ) *10000 )::int, 
        scen_id => floor(313 + random( ) *10000 )::int
    );


-- mods
SELECT modification_valid(floor(313 + random( ) *10000 )::int);
SELECT modification_invalid(floor(313 + random( ) *10000 )::int);

-- mods_scens
SELECT mod_scen_valid
    (
        id_mod  => floor(313 + random( ) *10000 )::int, 
        scen_id => floor(313 + random( ) *10000 )::int, 
        id_spec => floor(313 + random( ) *10000 )::int
    );
SELECT mod_fk_scen_invalid
    (
        id_mod  => floor(313 + random( ) *10000 )::int, 
        scen_id => floor(313 + random( ) *10000 )::int, 
        id_spec => floor(313 + random( ) *10000 )::int
    );
SELECT mod_scen_fk_invalid
    (
        id_mod  => floor(313 + random( ) *10000 )::int, 
        scen_id => floor(313 + random( ) *10000 )::int
    );

-- orders
SELECT order_valid
    (
        order_id         => floor(313 + random( ) *10000 )::int,
        spec_id          => floor(313 + random( ) *10000 )::int,
        accompaniment_id => floor(313 + random( ) *10000 )::int,
        person_id        => floor(313 + random( ) *10000 )::int,
        client_id        => floor(313 + random( ) *10000 )::int
    );
SELECT order_spec_fk_invalid
    (
        order_id         => floor(313 + random( ) *10000 )::int,
        spec_id          => floor(313 + random( ) *10000 )::int,
        accompaniment_id => floor(313 + random( ) *10000 )::int,
        person_id        => floor(313 + random( ) *10000 )::int,
        client_id        => floor(313 + random( ) *10000 )::int
    );
SELECT order_acc_fk_invalid
    (
        order_id         => floor(313 + random( ) *10000 )::int,
        spec_id          => floor(313 + random( ) *10000 )::int,
        accompaniment_id => floor(313 + random( ) *10000 )::int,
        person_id        => floor(313 + random( ) *10000 )::int,
        client_id        => floor(313 + random( ) *10000 )::int
    );
SELECT order_client_fk_invalid
    (
        order_id         => floor(313 + random( ) *10000 )::int,
        spec_id          => floor(313 + random( ) *10000 )::int,
        accompaniment_id => floor(313 + random( ) *10000 )::int,        
        client_id        => floor(313 + random( ) *10000 )::int
    );

-- body changes
SELECT body_valid 
    (
        floor(313 + random( ) *10000 )::int,
        floor(313 + random( ) *10000 )::int,
        floor(313 + random( ) *10000 )::int,
        floor(313 + random( ) *10000 )::int,
        floor(313 + random( ) *10000 )::int,
        floor(313 + random( ) *10000 )::int
    );
SELECT body_fk_invalid(floor(313 + random( ) *10000 )::int,floor(313 + random( ) *10000 )::int);

-- orders_modifications
SELECT order_mod_valid(floor(313 + random( ) *10000 )::int,floor(313 + random( ) *10000 )::int,floor(313 + random( ) *10000 )::int,floor(313 + random( ) *10000 )::int,floor(313 + random( ) *10000 )::int,floor(313 + random( ) *10000 )::int);
SELECT order_fk_mod_invalid(floor(313 + random( ) *10000 )::int, floor(313 + random( ) *10000 )::int);
SELECT order_mod_fk_invalid(floor(313 + random( ) *10000 )::int, floor(313 + random( ) *10000 )::int,floor(313 + random( ) *10000 )::int ,floor(313 + random( ) *10000 )::int,floor(313 + random( ) *10000 )::int,floor(313 + random( ) *10000 )::int);

-- done
SELECT * FROM tests;

SELECT count(*) AS AMOUNT, 
    (
        SELECT count(*) AS OK FROM tests WHERE status=true
    ) AS OK,
    (
        SELECT count(*) AS OK FROM tests WHERE status=false
    ) AS NOT_OK
FROM tests;
    