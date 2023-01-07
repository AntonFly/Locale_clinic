DROP TABLE tests;

DROP FUNCTION person_valid;
DROP FUNCTION person_invalid;

DROP FUNCTION passport_valid;
DROP FUNCTION passport_invalid;
DROP FUNCTION passport_fk_invalid;

DROP FUNCTION user_role_valid;
DROP FUNCTION user_role_unique;

DROP FUNCTION users_valid;
DROP FUNCTION user_person_fk_invalid;
DROP FUNCTION user_role_fk_invalid;

DROP FUNCTION pwd_drop_request_valid;
DROP FUNCTION pwd_drop_request_fk_invalid;

DROP FUNCTION stock_valid;
DROP FUNCTION stock_user_fk_invalid;
DROP FUNCTION stock_vendor_invalid;

DROP FUNCTION implants_valid;
DROP FUNCTION implants_invalid;

DROP FUNCTION client_implant_valid;
DROP FUNCTION client_fk_implant_invalid;
DROP FUNCTION client_implant_fk_invalid;

DROP FUNCTION accompaniment_valid;
DROP FUNCTION accompaniment_invalid;

DROP FUNCTION specialization_valid;
DROP FUNCTION specializations_invalid;

DROP FUNCTION client_spec_valid;
DROP FUNCTION client_fk_spec_invalid;
DROP FUNCTION client_spec_fk_invalid;

DROP FUNCTION scenario_valid;
DROP FUNCTION scenario_fk_invalid;

DROP FUNCTION modification_valid;
DROP FUNCTION modification_invalid;

DROP FUNCTION mod_scen_valid;
DROP FUNCTION mod_fk_scen_invalid;
DROP FUNCTION mod_scen_fk_invalid;

DROP FUNCTION order_valid;
DROP FUNCTION order_spec_fk_invalid;
DROP FUNCTION order_acc_fk_invalid;
DROP FUNCTION order_client_fk_invalid;

DROP FUNCTION body_valid;
DROP FUNCTION body_fk_invalid;

DROP FUNCTION order_mod_valid;
DROP FUNCTION order_fk_mod_invalid;
DROP FUNCTION order_mod_fk_invalid;