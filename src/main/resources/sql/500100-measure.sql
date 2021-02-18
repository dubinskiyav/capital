CREATE TABLE measure (
    measure_id INTEGER NOT NULL,
    measure_name varchar(100) NOT NULL,
    PRIMARY KEY (measure_id)
);
CREATE SEQUENCE measure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE measure_id_gen OWNED BY measure.measure_id;
ALTER TABLE measure ADD UNIQUE (measure_name);
COMMENT ON TABLE measure IS 'Мера измерения';
COMMENT ON COLUMN measure.measure_id IS 'Мера измерения ИД';
COMMENT ON COLUMN measure.measure_name IS 'Наименование';

