CREATE OR REPLACE FUNCTION users_valid(person_id int, role_id int, test_user_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    person_res integer; role_res integer; users_res integer;
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

        INSERT INTO user_role (id, name) VALUES (role_id, 'test_role');

        SELECT count(*) INTO role_res
            FROM user_role
            WHERE id=role_id;

        IF role_res != 1 THEN            
            RAISE EXCEPTION 'error setting up role';            
        END IF;
        
        -- test
        INSERT INTO users (id, role, password, email, id_person) VALUES (test_user_id, role_id, 'pass', 'email', person_id);

        SELECT count(*) INTO users_res
            FROM users
            WHERE id=test_user_id;
                
        IF users_res != 1 THEN            
            RAISE EXCEPTION 'Record not found';            
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN        
        DELETE FROM users WHERE id=test_user_id;
        DELETE FROM user_role WHERE id=role_id;
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM users WHERE id=test_user_id;
    DELETE FROM user_role WHERE id=role_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;

CREATE OR REPLACE FUNCTION user_person_fk_invalid(person_id int, role_id int, test_user_id int )
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    test_name text;
    stack text;
    role_res integer; users_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');        
    END IF;
    
    BEGIN
        INSERT INTO user_role (id, name) VALUES (role_id, 'test_role');

        SELECT count(*) INTO role_res
            FROM user_role
            WHERE id=role_id;

        IF role_res != 1 THEN            
            RAISE EXCEPTION 'error setting up role';            
        END IF;
        
        -- test
        INSERT INTO users (id, role, password, email, id_person) VALUES (test_user_id, role_id, 'pass', 'email', person_id);

        SELECT count(*) INTO users_res
            FROM users
            WHERE id=test_user_id;

        IF users_res != 0 THEN            
            RAISE EXCEPTION 'found unexpected record';
        END IF;

    EXCEPTION    
    WHEN foreign_key_violation THEN
        DELETE FROM user_role WHERE id=role_id;
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM users WHERE id=test_user_id;
        DELETE FROM user_role WHERE id=role_id;        
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);    
        RETURN false;
    END;
    
    DELETE FROM users WHERE id=test_user_id;
    DELETE FROM user_role WHERE id=role_id;    
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;


CREATE OR REPLACE FUNCTION user_role_fk_invalid(person_id int, role_id int, test_user_id int  )
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    test_name text;
    stack text;
    res integer;
    person_res integer; users_res integer; 
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
        
        -- test
        INSERT INTO users (id, role, password, email, id_person) VALUES (test_user_id, role_id, 'pass', 'email', person_id);

        SELECT count(*) INTO users_res
            FROM users
            WHERE id=test_user_id;
                
        IF users_res != 0 THEN            
            RAISE EXCEPTION 'found unexpected record';            
        END IF;
    
    EXCEPTION    
    WHEN foreign_key_violation THEN            
        DELETE FROM person WHERE id=person_id;
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM users WHERE id=test_user_id;
        DELETE FROM person WHERE id=person_id;
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);    
        RETURN false;
    END;
        
    DELETE FROM users WHERE id=test_user_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;