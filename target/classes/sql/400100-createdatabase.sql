-- Создать пользователя SYSDBA средствами DBeaver
CREATE ROLE "SYSDBA" WITH
	LOGIN
	SUPERUSER
	CREATEDB
	CREATEROLE
	INHERIT
	REPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'masterkey';
-- Создаем базу срудствами DBeaver
CREATE DATABASE capital
    WITH 
    OWNER = "SYSDBA"
    TEMPLATE = template0  -- Чтобы создать чистую пустую бд указывать это
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
-- Обязательно переконнектиться к новой бд
-- Создание схемы
CREATE SCHEMA dbo;
-- Устанавливаем путь к схеме
SET search_path TO dbo,public; -- Работает не только для текущего сеанса
-- Проверяем
SHOW search_path;
ALTER DATABASE "capital" SET search_path TO 'dbo';
