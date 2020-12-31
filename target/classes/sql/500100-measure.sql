CREATE TABLE measure (
    id INTEGER NOT NULL,
    name varchar(100) NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE measure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE measure_id_gen OWNED BY measure.id;
ALTER TABLE measure ADD UNIQUE (name);
COMMENT ON TABLE measure IS 'Мера измерения';
COMMENT ON COLUMN measure.id IS 'Мера измерения ИД';
COMMENT ON COLUMN measure.name IS 'Наименование';

