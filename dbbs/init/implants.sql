CREATE OR REPLACE FUNCTION implants_valid(implant_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer;     
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN                    
        -- test
        INSERT INTO implants (id, name, description) 
        VALUES (implant_id, 'test name', 'test desc');

        SELECT count(*) INTO res
            FROM implants
            WHERE id=implant_id;
                
        IF res != 1 THEN            
            RAISE EXCEPTION 'Record not found';            
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN        
        DELETE FROM implants WHERE id=implant_id;        
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM implants WHERE id=implant_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;

CREATE OR REPLACE FUNCTION implants_invalid(implant_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer;     
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN                    
        -- test
        INSERT INTO implants (id, description) 
        VALUES (implant_id, 'test desc');

        SELECT count(*) INTO res
            FROM implants
            WHERE id=implant_id;
                
        IF res != 0 THEN            
            RAISE EXCEPTION 'found record';            
        END IF;
        
    EXCEPTION 
    WHEN not_null_violation THEN
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN        
        DELETE FROM implants WHERE id=implant_id;        
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM implants WHERE id=implant_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'no error occured');
    RETURN false;
END;
$$;