CREATE OR REPLACE FUNCTION pwd_drop_request_valid(person_id int, test_user_id int, role_id int, drop_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    test_name text;
    stack text;
    person_res integer; role_res integer; users_res integer;
    res integer;
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

        INSERT INTO user_role (id, role) VALUES (role_id, 'test_role');

        SELECT count(*) INTO role_res
            FROM user_role
            WHERE id=role_id;

        IF role_res != 1 THEN            
            RAISE EXCEPTION 'error setting up role';            
        END IF;
        
        INSERT INTO users (id, role, password, email, id_person) VALUES (test_user_id, role_id, 'pass', 'email', person_id);

        SELECT count(*) INTO users_res
            FROM users
            WHERE id=test_user_id;
                
        IF users_res != 1 THEN            
            RAISE EXCEPTION 'error setting up user';            
        END IF;

        -- test

        INSERT INTO pwd_drop_requests (id, user_id, request_date, dropped, drop_date) 
            VALUES (drop_id, test_user_id, now(), false, null);

        SELECT count(*) INTO res
            FROM pwd_drop_requests
            WHERE id=drop_id;
                
        IF res != 1 THEN            
            RAISE EXCEPTION 'record not found';            
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN
        DELETE FROM pwd_drop_requests WHERE id=drop_id;
        DELETE FROM users WHERE id=test_user_id;
        DELETE FROM user_role WHERE id=role_id;
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;

    DELETE FROM pwd_drop_requests WHERE id=drop_id;
    DELETE FROM users WHERE id=test_user_id;
    DELETE FROM user_role WHERE id=role_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;


CREATE OR REPLACE FUNCTION pwd_drop_request_fk_invalid(drop_id int, fk int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    test_name text;
    stack text;
    res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');        
    END IF;
    
    BEGIN            
        -- test

        INSERT INTO pwd_drop_requests (id, user_id, request_date, dropped, drop_date) 
            VALUES (drop_id, fk, now(), false, null);

        SELECT count(*) INTO res
            FROM pwd_drop_requests
            WHERE id=drop_id;
                
        IF res != 0 THEN            
            RAISE EXCEPTION 'found record';            
        END IF;
        
    EXCEPTION 
    WHEN raise_exception THEN    
        DELETE FROM pwd_drop_requests WHERE id=drop_id;
                 
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    WHEN foreign_key_violation THEN    
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN    
        DELETE FROM pwd_drop_requests WHERE id=drop_id;
                 
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;

    DELETE FROM pwd_drop_requests WHERE id=drop_id;

    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;


