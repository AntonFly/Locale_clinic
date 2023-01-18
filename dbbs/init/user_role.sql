CREATE OR REPLACE FUNCTION user_role_valid(role_id int)
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
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));

    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');    
    END IF;
    
    BEGIN        
        INSERT INTO user_role (id, name) VALUES (role_id, 'test_role');

        SELECT count(*) INTO res
        FROM user_role
        WHERE id=role_id;
                
        IF res != 1 THEN            
            RAISE EXCEPTION 'valid record not found';            
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN
        DELETE FROM user_role WHERE id=role_id;        
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;

    DELETE FROM user_role WHERE id=role_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;


CREATE OR REPLACE FUNCTION user_role_unique(role_id int, sec_role_id int )
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    test_name text;
    stack text;
    res integer;
    test_role text := 'test_role';
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));

    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');    
    END IF;
    
    BEGIN
        
        INSERT INTO user_role (id, name) VALUES (role_id, test_role);
        INSERT INTO user_role (id, name) VALUES (sec_role_id, test_role);
        
        SELECT count(*) INTO res
        FROM user_role
        WHERE name=test_role;    
                
        IF res = 0 THEN            
            RAISE EXCEPTION 'found no roles';   
        END IF;
        
        IF res > 1 THEN            
            RAISE EXCEPTION 'found several roles';   
        END IF;
        
    EXCEPTION 
    WHEN raise_exception THEN        
        DELETE FROM user_role WHERE id=role_id;
        DELETE FROM user_role WHERE id=sec_role_id;
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    WHEN unique_violation THEN        
        DELETE FROM user_role WHERE id=role_id;
        DELETE FROM user_role WHERE id=sec_role_id;
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN            
        DELETE FROM user_role WHERE id=role_id;
        DELETE FROM user_role WHERE id=sec_role_id;
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLSTATE || ' ' || SQLERRM);
        RETURN false;
    END;

    DELETE FROM user_role WHERE id=role_id;
    DELETE FROM user_role WHERE id=sec_role_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;