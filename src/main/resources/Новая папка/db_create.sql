-- Database: capital
-- Author: Dubinskiy
CREATE DATABASE capital
    WITH OWNER = postgres
        ENCODING = 'UTF8'
        TABLESPACE = pg_default
        LC_COLLATE = 'undefined'
        LC_CTYPE = 'undefined'
        CONNECTION LIMIT = -1;

COMMENT ON DATABASE capital
    IS 'Базовые сущности системы Капитал';