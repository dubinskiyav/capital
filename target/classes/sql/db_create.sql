CREATE DATABASE capital2
    WITH 
    OWNER = "SYSDBA"
    TEMPLATE = template0
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE capital2
    IS 'Базовые сущности системы Капитал';
	
	
ОБЯЗАТЕЛЬНО соединиться с базой
	
-- Создание схемы
CREATE SCHEMA dbo;
-- Устанавливаем путь к схеме
//SET search_path TO dbo,public;
-- Проверяем
SHOW search_path;
-- Лучше так
ALTER DATABASE "capital2" SET search_path TO dbo
