CREATE OR REPLACE FUNCTION client_mod_valid(person_id int, client_id int, mod_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    person_res integer; client_res integer; spec_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));   
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN        

    -- prepare
        INSERT INTO person (id, name, surname, patronymic, date_of_birth) VALUES (person_id, 'name', 'surname', 'patron', '2022-01-01');

        SELECT count(*) INTO person_res
            FROM person
            WHERE id=person_id;

        IF person_res != 1 THEN            
            RAISE EXCEPTION 'error setting up person';            
        END IF;

        INSERT INTO clients (id, email, comment, id_person)
        VALUES (client_id, 'email@email.com', 'coommtent', person_id);

        SELECT count(*) INTO client_res
            FROM clients
            WHERE id=client_id;
                
        IF client_res != 1 THEN            
            RAISE EXCEPTION 'error setting up client';            
        END IF;

        INSERT INTO modifications (id, name, price, currency, risk, chance, accompaniment) 
        VALUES (mod_id, 'test name', 313,'RUB', 'aaa', 100, 'acccccc');

        SELECT count(*) INTO spec_res
            FROM modifications
            WHERE id=mod_id;
                
        IF spec_res != 1 THEN            
            RAISE EXCEPTION 'Record not found';            
        END IF;

        
        -- test
        INSERT INTO client_modification (id_client, id_mod) VALUES (client_id, mod_id);

        SELECT count(*) INTO res
            FROM client_modification
            WHERE id_client=client_id AND id_mod=mod_id;
                
        IF res != 1 THEN            
            RAISE EXCEPTION 'Record not found';
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN
        DELETE FROM client_modification WHERE id_client=client_id AND id_mod=mod_id;    
        DELETE FROM modifications WHERE id=mod_id;
        DELETE FROM clients WHERE id=client_id;
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM client_modification WHERE id_client=client_id AND id_spec=spec_id;
    DELETE FROM modifications WHERE id=mod_id;
    DELETE FROM clients WHERE id=client_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;

CREATE OR REPLACE FUNCTION client_fk_mod_invalid(client_id int, mod_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    spec_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN        

    -- prepare        

        INSERT INTO modifications (id, name, price, currency, risk, chance, accompaniment) 
        VALUES (mod_id, 'test name', 313,'RUB', 'aaa', 100, 'acccccc');

        SELECT count(*) INTO spec_res
            FROM modifications
            WHERE id=mod_id;
                
        IF spec_res != 1 THEN            
            RAISE EXCEPTION 'Record not found';            
        END IF;

        
        -- test
        INSERT INTO client_modification (id_client, id_mod) VALUES (client_id, mod_id);

        SELECT count(*) INTO res
            FROM client_modification
            WHERE id_client=client_id AND id_mod=mod_id;
                
        IF res != 0 THEN            
            RAISE EXCEPTION 'found record';
        END IF;
        
    EXCEPTION 
    WHEN foreign_key_violation THEN         
        DELETE FROM client_modification WHERE id_client=client_id AND id_mod=mod_id;
        DELETE FROM modifications WHERE id=mod_id;

        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM client_modification WHERE id_client=client_id AND id_mod=mod_id;
        DELETE FROM modifications WHERE id=mod_id;
        
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM client_modification WHERE id_client=client_id AND id_mod=mod_id;
    DELETE FROM modifications WHERE id=mod_id;

    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;

CREATE OR REPLACE FUNCTION client_mod_fk_invalid(person_id int, client_id int, mod_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    person_res integer; client_res integer; spec_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN        

    -- prepare
        INSERT INTO person (id, name, surname, patronymic, date_of_birth) VALUES (person_id, 'name', 'surname', 'patron', '2022-01-01');

        SELECT count(*) INTO person_res
            FROM person
            WHERE id=person_id;

        IF person_res != 1 THEN            
            RAISE EXCEPTION 'error setting up person';            
        END IF;

        INSERT INTO clients (id, email, comment, id_person)
        VALUES (client_id, 'email@email.com', 'coommtent', person_id);

        SELECT count(*) INTO client_res
            FROM clients
            WHERE id=client_id;
                
        IF client_res != 1 THEN            
            RAISE EXCEPTION 'error setting up client';            
        END IF;

        -- test
        INSERT INTO client_modification (id_client, id_mod) VALUES (client_id, mod_id);

        SELECT count(*) INTO res
            FROM client_modification
            WHERE id_client=client_id AND id_mod=mod_id;
                
        IF res != 0 THEN            
            RAISE EXCEPTION 'found record';
        END IF;
        
    EXCEPTION 
    WHEN foreign_key_violation THEN         
        DELETE FROM clients WHERE id=client_id;
        DELETE FROM person WHERE id=person_id;

        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM client_modification WHERE id_client=client_id AND id_mod=mod_id;
        DELETE FROM clients WHERE id=client_id;
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM client_modification WHERE id_client=client_id AND id_mod=mod_id;
    DELETE FROM clients WHERE id=client_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;