CREATE OR REPLACE FUNCTION order_valid(order_id int, spec_id int, accompaniment_id int, person_id int, client_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    spec_res integer; acc_res integer; person_res integer; client_res integer;
    res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN                    
        -- spec
        INSERT INTO specializations (id, name) 
        VALUES (spec_id, 'test name');

        SELECT count(*) INTO spec_res
            FROM specializations
            WHERE id=spec_id;
                
        IF spec_res != 1 THEN            
            RAISE EXCEPTION 'spec error';            
        END IF;

        -- accomp
        INSERT INTO accompaniment_script (id, scenarios) 
        VALUES (accompaniment_id, 'test scen');

        SELECT count(*) INTO acc_res
            FROM accompaniment_script
            WHERE id=accompaniment_id;
                
        IF acc_res != 1 THEN            
            RAISE EXCEPTION 'acc error';            
        END IF;

        -- person
        INSERT INTO person (id, name, surname, patronymic, date_of_birth) VALUES (person_id, 'name', 'surname', 'patron', '2022-01-01');

        SELECT count(*) INTO person_res
            FROM person
            WHERE id=person_id;

        IF person_res != 1 THEN            
            RAISE EXCEPTION 'error setting up person';            
        END IF;
        
        -- client
        INSERT INTO clients (id, email, comment, id_person) VALUES (client_id, 'email', 'comment', person_id);

        SELECT count(*) INTO client_res
            FROM clients
            WHERE id=client_id;
                
        IF client_res != 1 THEN            
            RAISE EXCEPTION 'client error';            
        END IF;

        INSERT INTO orders (id, id_client, spec, comment, confirmation, accompaniment_script, genome)
            VALUES (order_id, client_id, spec_id, 'comment', 'confirmed', accompaniment_id, 'who?');

        SELECT count(*) INTO res
            FROM orders
            WHERE id=order_id;
                
        IF res != 1 THEN            
            RAISE EXCEPTION 'Record not found';            
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN        
        DELETE FROM orders WHERE id=order_id;
        DELETE FROM specializations WHERE id=spec_id;
        DELETE FROM accompaniment_script WHERE id=accompaniment_id;
        DELETE FROM clients WHERE id=client_id;
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM orders WHERE id=order_id;
    DELETE FROM specializations WHERE id=spec_id;
    DELETE FROM accompaniment_script WHERE id=accompaniment_id;
    DELETE FROM clients WHERE id=client_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;


CREATE OR REPLACE FUNCTION order_spec_fk_invalid(order_id int, spec_id int, accompaniment_id int, person_id int, client_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    test_name text;
    stack text;
    spec_res integer; acc_res integer; person_res integer; client_res integer;
    res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');        
    END IF;
    
    BEGIN
        -- accomp
        INSERT INTO accompaniment_script (id, scenarios) 
        VALUES (accompaniment_id, 'test scen');

        SELECT count(*) INTO acc_res
            FROM accompaniment_script
            WHERE id=accompaniment_id;
                
        IF acc_res != 1 THEN            
            RAISE EXCEPTION 'acc error';            
        END IF;

        -- person
        INSERT INTO person (id, name, surname, patronymic, date_of_birth) VALUES (person_id, 'name', 'surname', 'patron', '2022-01-01');

        SELECT count(*) INTO person_res
            FROM person
            WHERE id=person_id;

        IF person_res != 1 THEN            
            RAISE EXCEPTION 'error setting up person';            
        END IF;
        
        -- client
        INSERT INTO clients (id, email, comment, id_person) VALUES (client_id, 'email', 'comment', person_id);

        SELECT count(*) INTO client_res
            FROM clients
            WHERE id=client_id;
                
        IF client_res != 1 THEN            
            RAISE EXCEPTION 'client error';            
        END IF;

        INSERT INTO orders (id, id_client, spec, comment, confirmation, accompaniment_script, genome)
            VALUES (order_id, client_id, spec_id, 'comment', 'confirmed', accompaniment_id, 'who?');

        SELECT count(*) INTO res
            FROM orders
            WHERE id=order_id;
                
        IF res != 1 THEN            
            RAISE EXCEPTION 'Record not found';            
        END IF;

    EXCEPTION
    WHEN foreign_key_violation THEN
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM orders WHERE id=order_id;        
        DELETE FROM accompaniment_script WHERE id=accompaniment_id;
        DELETE FROM clients WHERE id=client_id;
        DELETE FROM person WHERE id=person_id;
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM orders WHERE id=order_id;    
    DELETE FROM accompaniment_script WHERE id=accompaniment_id;
    DELETE FROM clients WHERE id=client_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;


CREATE OR REPLACE FUNCTION order_acc_fk_invalid(order_id int, spec_id int, accompaniment_id int, person_id int, client_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    test_name text;
    stack text;
    spec_res integer; acc_res integer; person_res integer; client_res integer;
    res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');        
    END IF;
    
    BEGIN
        -- spec
        INSERT INTO specializations (id, name) 
        VALUES (spec_id, 'test name');

        SELECT count(*) INTO spec_res
            FROM specializations
            WHERE id=spec_id;
                
        IF spec_res != 1 THEN            
            RAISE EXCEPTION 'spec error';            
        END IF;        

        -- person
        INSERT INTO person (id, name, surname, patronymic, date_of_birth) VALUES (person_id, 'name', 'surname', 'patron', '2022-01-01');

        SELECT count(*) INTO person_res
            FROM person
            WHERE id=person_id;

        IF person_res != 1 THEN            
            RAISE EXCEPTION 'error setting up person';            
        END IF;
        
        -- client
        INSERT INTO clients (id, email, comment, id_person) VALUES (client_id, 'email', 'comment', person_id);

        SELECT count(*) INTO client_res
            FROM clients
            WHERE id=client_id;
                
        IF client_res != 1 THEN            
            RAISE EXCEPTION 'client error';            
        END IF;

        INSERT INTO orders (id, id_client, spec, comment, confirmation, accompaniment_script, genome)
            VALUES (order_id, client_id, spec_id, 'comment', 'confirmed', accompaniment_id, 'who?');

        SELECT count(*) INTO res
            FROM orders
            WHERE id=order_id;
                
        IF res != 1 THEN            
            RAISE EXCEPTION 'Record not found';            
        END IF;

    EXCEPTION
    WHEN foreign_key_violation THEN
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM orders WHERE id=order_id;
        DELETE FROM specializations WHERE id=spec_id;        
        DELETE FROM clients WHERE id=client_id;
        DELETE FROM person WHERE id=person_id;
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM orders WHERE id=order_id;
    DELETE FROM specializations WHERE id=spec_id;    
    DELETE FROM clients WHERE id=client_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;


CREATE OR REPLACE FUNCTION order_client_fk_invalid(order_id int, spec_id int, accompaniment_id int, client_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    test_name text;
    stack text;
    spec_res integer; acc_res integer; person_res integer; client_res integer;
    res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');        
    END IF;
    
    BEGIN
        -- spec
        INSERT INTO specializations (id, name) 
        VALUES (spec_id, 'test name');

        SELECT count(*) INTO spec_res
            FROM specializations
            WHERE id=spec_id;
                
        IF spec_res != 1 THEN            
            RAISE EXCEPTION 'spec error';            
        END IF;

        -- accomp
        INSERT INTO accompaniment_script (id, scenarios) 
        VALUES (accompaniment_id, 'test scen');

        SELECT count(*) INTO acc_res
            FROM accompaniment_script
            WHERE id=accompaniment_id;
                
        IF acc_res != 1 THEN            
            RAISE EXCEPTION 'acc error';            
        END IF;

        INSERT INTO orders (id, id_client, spec, comment, confirmation, accompaniment_script, genome)
            VALUES (order_id, client_id, spec_id, 'comment', 'confirmed', accompaniment_id, 'who?');

        SELECT count(*) INTO res
            FROM orders
            WHERE id=order_id;
                
        IF res != 1 THEN            
            RAISE EXCEPTION 'Record not found';            
        END IF;

    EXCEPTION
    WHEN foreign_key_violation THEN
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM orders WHERE id=order_id;
        DELETE FROM specializations WHERE id=spec_id;
        DELETE FROM accompaniment_script WHERE id=accompaniment_id;        
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM orders WHERE id=order_id;
    DELETE FROM specializations WHERE id=spec_id;
    DELETE FROM accompaniment_script WHERE id=accompaniment_id;    
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;
