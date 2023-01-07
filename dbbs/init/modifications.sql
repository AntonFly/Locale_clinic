CREATE OR REPLACE FUNCTION modification_valid(mod_id int)
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
    IF test_name IS NULL THEN
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN
        -- test
        INSERT INTO modifications (id, name, price, currency, risk, chance) 
        VALUES (mod_id, 'test name', 200.0, 'USD', 'high', 0.2);

        SELECT count(*) INTO res
            FROM modifications
            WHERE id=mod_id;
                
        IF res != 1 THEN
            RAISE EXCEPTION 'Record not found';
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN
        DELETE FROM modifications WHERE id=mod_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM modifications WHERE id=mod_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;

CREATE OR REPLACE FUNCTION modification_invalid(mod_id int)
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
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN                    
        -- test
        INSERT INTO modifications (id, price, currency, risk, chance) 
        VALUES (mod_id, 200.0, 'USD', 'high', 0.2);

        SELECT count(*) INTO res
            FROM modifications
            WHERE id=mod_id;
                
        IF res != 0 THEN
            RAISE EXCEPTION 'found invalid record';
        END IF;
        
    EXCEPTION 
    WHEN not_null_violation THEN
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN        
        DELETE FROM specializations WHERE id=mod_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM specializations WHERE id=mod_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'no error occured');
    RETURN false;
END;
$$;