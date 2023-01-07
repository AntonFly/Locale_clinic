psql -d Clinic --host=.sytes.net --port=5432 --username=rr -w -f init/init.sql &> results/init 
psql -d Clinic --host=.sytes.net --port=5432 --username=rr -w -f main.sql &> results/test 
psql -d Clinic --host=.sytes.net --port=5432 --username=rr -w -f clean/clean.sql &> results/cleanup 