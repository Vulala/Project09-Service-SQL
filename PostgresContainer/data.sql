psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
CREATE SCHEMA IF NOT EXISTS abernathyclinic_mediscreen;

CREATE TABLE abernathyclinic_mediscreen.patients
(
    uuid uuid NOT NULL,
    last_name character varying(32),
    first_name character varying(32),
    date_of_birth character varying(16),
    gender character varying(32),
    home_address character varying(128),
    phone_number character varying(16),
    PRIMARY KEY (uuid)
);

INSERT INTO abernathyclinic_mediscreen.patients(
	uuid, last_name, first_name, date_of_birth, gender, home_address, phone_number)
VALUES ('b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9', 'lastName', 'firstName', '00/00/0000', 'Binary', 'homeAddress', '123.456.789');
SELECT * FROM abernathyclinic_mediscreen.patients;
EOSQL