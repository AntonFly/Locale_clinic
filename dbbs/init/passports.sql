CREATE OR REPLACE FUNCTION passport_valid(person_id int, passport_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; person_res integer;
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
            RAISE EXCEPTION 'failed with person';
        END IF;

    -- test
        INSERT INTO passports (passport, id_person) VALUES (passport_id, person_id);

        SELECT count(*) INTO res
            FROM passports
            WHERE passport=passport_id;

        IF res != 1 THEN            
            RAISE EXCEPTION 'valid record not found';
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN                
        DELETE FROM passports WHERE passport=passport_id;
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
        
    DELETE FROM passports WHERE passport=passport_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;

CREATE OR REPLACE FUNCTION passport_invalid(person_id int, passport_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; person_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN        
        IF passport_id > 999999999 AND passport_id < 10000000000 THEN
            RAISE EXCEPTION 'valid passport given';
        END IF;
    -- prepare
        INSERT INTO person (id, name, surname, patronymic, date_of_birth) VALUES (person_id, 'name', 'surname', 'patron', '2022-01-01');

        SELECT count(*) INTO person_res
            FROM person
            WHERE id=person_id;

        IF person_res != 1 THEN            
            RAISE EXCEPTION 'failed with person';
        END IF;

    -- test
        INSERT INTO passports (passport, id_person) VALUES (passport_id, person_id);

        SELECT count(*) INTO res
            FROM passports
            WHERE passport=passport_id;

        IF res != 0 THEN            
            RAISE EXCEPTION 'valid record found';
        END IF;
        
    EXCEPTION 
    WHEN check_violation THEN
        DELETE FROM person WHERE id=person_id;

        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        RAISE NOTICE 'PASSPORT INVALID % % ', SQLSTATE, SQLERRM;
        DELETE FROM passports WHERE passport=passport_id;
        DELETE FROM person WHERE id=person_id;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
        
    DELETE FROM passports WHERE passport=passport_id;
    DELETE FROM person WHERE id=person_id;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;

CREATE OR REPLACE FUNCTION passport_fk_invalid(person_id int, passport_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; person_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    test_name := SUBSTRING(test_name,0, POSITION('(' in test_name));
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN            

    -- test
        INSERT INTO passports (passport, id_person)
            VALUES (passport_id, person_id);

        SELECT count(*) INTO res
            FROM person
            WHERE id=person_id;

        IF res != 0 THEN
            RAISE EXCEPTION 'found invalid record';
        END IF;
        
    EXCEPTION
    WHEN foreign_key_violation THEN
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM passports WHERE passport=passport_id;        
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM passports WHERE passport=passport_id;
    
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'no error occured');
    RETURN false;
END;
$$;

