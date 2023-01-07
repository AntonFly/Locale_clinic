CREATE OR REPLACE FUNCTION mod_scen_valid(id_mod int, scen_id int, id_spec int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    mod_res integer;spec_res integer; scen_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN        

    -- prepare
        INSERT INTO modifications (id, name, price, currency, risk, chance) 
        VALUES (id_mod, 'test name', 200.0, 'USD', 'high', 0.2);

        SELECT count(*) INTO mod_res
            FROM modifications
            WHERE id=id_mod;
                
        IF mod_res != 1 THEN
            RAISE EXCEPTION 'error prep mod';
        END IF;

        INSERT INTO specializations (id, name)
            VALUES (id_spec, 'test name');

        SELECT count(*) INTO spec_res
            FROM specializations
            WHERE id=id_spec;
                
        IF spec_res != 1 THEN
            RAISE EXCEPTION 'spec error';
        END IF;
        
        INSERT INTO scenario (id, spec_id)
            VALUES (scen_id, id_spec);

        SELECT count(*) INTO scen_res
            FROM scenario
            WHERE id=scen_id;
                
        IF scen_res != 1 THEN
            RAISE EXCEPTION 'scene error';
        END IF;        

        -- test
        INSERT INTO modification_scenario (scanario_id, mod_id)
            VALUES (scen_id, id_mod);

        SELECT count(*) INTO res
            FROM scenario
            WHERE id=scen_id;

        IF res != 1 THEN            
            RAISE EXCEPTION 'Record not found';
        END IF;
        
    EXCEPTION WHEN OTHERS
    THEN
        DELETE FROM modification_scenario WHERE scanario_id=scen_id AND mod_id=id_mod;
        DELETE FROM modifications WHERE id=id_mod;
        DELETE FROM scenario WHERE id=scen_id;
        DELETE FROM specializations WHERE id=id_spec;
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM modification_scenario WHERE scanario_id=scen_id AND mod_id=id_mod;
    DELETE FROM modifications WHERE id=id_mod;
    DELETE FROM scenario WHERE id=scen_id;
    DELETE FROM specializations WHERE id=id_spec;
    INSERT INTO tests (name, status, comment) VALUES (test_name, true, '-');
    RETURN true;
END;
$$;

CREATE OR REPLACE FUNCTION mod_fk_scen_invalid(id_mod int, scen_id int, id_spec int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    spec_res integer; scen_res integer;
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
        
        INSERT INTO scenario (id, spec_id)
            VALUES (scen_id, id_spec);

        SELECT count(*) INTO scen_res
            FROM scenario
            WHERE id=scen_id;
                
        IF scen_res != 1 THEN
            RAISE EXCEPTION 'scene error';
        END IF;        

        -- test
        INSERT INTO modification_scenario (scanario_id, mod_id)
            VALUES (scen_id, id_mod);

        SELECT count(*) INTO res
            FROM scenario
            WHERE id=scen_id;

        IF res != 0 THEN            
            RAISE EXCEPTION 'found invalid record';
        END IF;
        
    EXCEPTION 
    WHEN foreign_key_violation THEN         
        DELETE FROM modification_scenario WHERE scanario_id=scen_id AND mod_id=id_mod;        
        DELETE FROM scenario WHERE id=scen_id;
        DELETE FROM specializations WHERE id=id_spec;

        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM modification_scenario WHERE scanario_id=scen_id AND mod_id=id_mod;        
        DELETE FROM scenario WHERE id=scen_id;
        DELETE FROM specializations WHERE id=id_spec;
        
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM modification_scenario WHERE scanario_id=scen_id AND mod_id=id_mod;
    DELETE FROM scenario WHERE id=scen_id;
    DELETE FROM specializations WHERE id=id_spec;

    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;

CREATE OR REPLACE FUNCTION mod_scen_fk_invalid(id_mod int, scen_id int)
RETURNS boolean
LANGUAGE plpgsql
as
$$
DECLARE    
    stack text; test_name text;
    res integer; 
    mod_res integer;spec_res integer; scen_res integer;
BEGIN
    GET DIAGNOSTICS stack = PG_CONTEXT;
    test_name := substring(stack from 'function (.*?) line')::regprocedure::text;    
    IF test_name IS NULL THEN        
        test_name := substring(stack from 'SQL (.*?), строка');
    END IF;
    
    BEGIN        
-- prepare
        INSERT INTO modifications (id, name, price, currency, risk, chance) 
        VALUES (id_mod, 'test name', 200.0, 'USD', 'high', 0.2);

        SELECT count(*) INTO mod_res
            FROM modifications
            WHERE id=id_mod;
                
        IF mod_res != 1 THEN
            RAISE EXCEPTION 'error prep mod';
        END IF;        

        -- test
        INSERT INTO modification_scenario (scanario_id, mod_id)
            VALUES (scen_id, id_mod);

        SELECT count(*) INTO res
            FROM scenario
            WHERE id=scen_id;

        IF res != 0 THEN            
            RAISE EXCEPTION 'found invalid record';
        END IF;
        
    EXCEPTION 
    WHEN foreign_key_violation THEN         
        DELETE FROM modification_scenario WHERE scanario_id=scen_id AND mod_id=id_mod;
        DELETE FROM modifications WHERE id=id_mod;        

        INSERT INTO tests (name, status, comment) VALUES (test_name, true, SQLERRM);
        RETURN true;
    WHEN OTHERS THEN
        DELETE FROM modification_scenario WHERE scanario_id=scen_id AND mod_id=id_mod;
        DELETE FROM modifications WHERE id=id_mod;        
         
        INSERT INTO tests (name, status, comment) VALUES (test_name, false, SQLERRM);
        RETURN false;
    END;
    
    DELETE FROM modification_scenario WHERE scanario_id=scen_id AND mod_id=id_mod;
    DELETE FROM modifications WHERE id=id_mod;
    
    INSERT INTO tests (name, status, comment) VALUES (test_name, false, 'No error occured');
    RETURN false;
END;
$$;