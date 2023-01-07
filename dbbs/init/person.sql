CREATE OR REPLACE FUNCTION person_valid(person_id int)
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

        INSERT INTO person (id, name, surname, patronymic, date_of_birth) VALUES (person_id, 'name', 'surname', 'patron', '2022-01-01');

        SELECT count(*) INTO res
            FROM person
            WHERE id=person_id;

        IF res != 1 THEN            
            RAISE EXCEPTION 'record not found';            
        END IF;        
        
    EXCEPTION WHEN OTHERS
    THEN                
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
        
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;

CREATE OR REPLACE FUNCTION person_invalid(person_id int)
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
        INSERT INTO person (id, surname, patronymic, date_of_birth) 
            VALUES (person_id, 'surname', 'patron', '2022-01-01');

        SELECT count(*) INTO res
            FROM person
            WHERE id=person_id;

        IF res != 0 THEN            
            RAISE EXCEPTION 'invalid record found';            
        END IF;        
        
    EXCEPTION
    WHEN not_null_violation THEN
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
        
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'no error occured');
    RETURN false;
END;
$$;

