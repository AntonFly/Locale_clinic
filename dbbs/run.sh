psql -d Clinic --host=fly.sytes.net --port=5432 --username=remoteUser -w -f init/init.sql &> results/init 
psql -d Clinic --host=fly.sytes.net --port=5432 --username=remoteUser -w -f main.sql &> results/test 
psql -d Clinic --host=fly.sytes.net --port=5432 --username=remoteUser -w -f clean/clean.sql &> results/cleanup 