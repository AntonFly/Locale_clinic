CREATE OR REPLACE FUNCTION client_implant_valid(person_id int, client_id int, implant_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    person_res integer; client_res integer; implants_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
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

        INSERT INTO implants (id, name, description) 
        VALUES (implant_id, 'test name','test desc');

        SELECT count(*) INTO implants_res
            FROM implants
            WHERE id=implant_id;
                
        IF implants_res != 1 THEN            
            RAISE EXCEPTION 'error setting up implants';            
        END IF;

        
        -- test
        INSERT INTO client_implant (id_client, id_implant) VALUES (client_id, implant_id);

        SELECT count(*) INTO res
            FROM client_implant
            WHERE id_client=client_id AND id_implant=implant_id;
                
        IF res != 1 THEN            
            RAISE EXCEPTION 'Record not found';
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN
        DELETE FROM client_implant WHERE id_client=client_id AND id_implant=implant_id;
        DELETE FROM implants WHERE id=implant_id;
        DELETE FROM clients WHERE id=client_id;
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM client_implant WHERE id_client=client_id AND id_implant=implant_id;
    DELETE FROM implants WHERE id=implant_id;
    DELETE FROM clients WHERE id=client_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;

CREATE OR REPLACE FUNCTION client_fk_implant_invalid(client_id int, implant_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    person_res integer; client_res integer; implants_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN        

    -- prepare        

        INSERT INTO implants (id, name, description) 
        VALUES (implant_id, 'test name','test desc');

        SELECT count(*) INTO implants_res
            FROM implants
            WHERE id=implant_id;
                
        IF implants_res != 1 THEN            
            RAISE EXCEPTION 'error setting up implants';            
        END IF;

        
        -- test
        INSERT INTO client_implant (id_client, id_implant) VALUES (client_id, implant_id);

        SELECT count(*) INTO res
            FROM client_implant
            WHERE id_client=client_id AND id_implant=implant_id;
                
        IF res != 0 THEN            
            RAISE EXCEPTION 'found record';
        END IF;
        
    EXCEPTION 
    WHEN foreign_key_violation THEN         
        DELETE FROM client_implant WHERE id_client=client_id AND id_implant=implant_id;
        DELETE FROM implants WHERE id=implant_id;

        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM client_implant WHERE id_client=client_id AND id_implant=implant_id;
        DELETE FROM implants WHERE id=implant_id;
        
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM client_implant WHERE id_client=client_id AND id_implant=implant_id;
    DELETE FROM implants WHERE id=implant_id;

    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;

CREATE OR REPLACE FUNCTION client_implant_fk_invalid(person_id int, client_id int, implant_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    person_res integer; client_res integer; implants_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
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
        INSERT INTO client_implant (id_client, id_implant) VALUES (client_id, implant_id);

        SELECT count(*) INTO res
            FROM client_implant
            WHERE id_client=client_id AND id_implant=implant_id;
                
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
        DELETE FROM client_implant WHERE id_client=client_id AND id_implant=implant_id;        
        DELETE FROM clients WHERE id=client_id;
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM client_implant WHERE id_client=client_id AND id_implant=implant_id;    
    DELETE FROM clients WHERE id=client_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;