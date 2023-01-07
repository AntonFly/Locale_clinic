CREATE OR REPLACE FUNCTION scenario_valid(id_spec int, scen_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    stack text; test_name text;
    res integer; spec_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;
    IF test_name IS NULL THEN
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN
        -- prepare
        INSERT INTO specializations (id, name)
            VALUES (id_spec, 'test name');

        SELECT count(*) INTO spec_res
            FROM specializations
            WHERE id=id_spec;
                
        IF spec_res != 1 THEN
            RAISE EXCEPTION 'spec error';
        END IF;

        -- test
        INSERT INTO scenario (id, spec_id)
            VALUES (scen_id, id_spec);

        SELECT count(*) INTO res
            FROM scenario
            WHERE id=scen_id;
                
        IF res != 1 THEN
            RAISE EXCEPTION 'Valid record not found';
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN
        DELETE FROM scenario WHERE id=scen_id;
        DELETE FROM specializations WHERE id=id_spec;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM scenario WHERE id=scen_id;
    DELETE FROM specializations WHERE id=id_spec;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;


CREATE OR REPLACE FUNCTION scenario_fk_invalid(id_spec int, scen_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE
    stack text; test_name text;
    res integer; spec_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;
    IF test_name IS NULL THEN
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN
        -- test
        INSERT INTO scenario (id, spec_id)
            VALUES (scen_id, id_spec);

        SELECT count(*) INTO res
            FROM scenario
            WHERE id=scen_id;
                
        IF res != 0 THEN
            RAISE EXCEPTION 'found invalid record';
        END IF;
        
    EXCEPTION 
    WHEN foreign_key_violation THEN
        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM scenario WHERE id=scen_id;
        DELETE FROM specializations WHERE id=id_spec;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM scenario WHERE id=scen_id;
    DELETE FROM specializations WHERE id=id_spec;
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'no error occured');
    RETURN false;
END;
$$;