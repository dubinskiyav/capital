-- Database: capital
-- Author: Dubinskiy

CREATE ROLE "SYSDBA" WITH
	LOGIN
	SUPERUSER
	CREATEDB
	CREATEROLE
	INHERIT
	REPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'masterkey';



CREATE DATABASE capital_test
    WITH 
    OWNER = "SYSDBA"
    TEMPLATE = template0
    ENCODING = 'WIN1251'
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE capital_test
    IS 'Базовые сущности системы Капитал';