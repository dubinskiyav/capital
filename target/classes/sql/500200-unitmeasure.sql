CREATE TABLE unitmeasure (
    id INTEGER NOT NULL,
    name varchar(100) NOT NULL,
    short_name varchar(20),
    PRIMARY KEY (id)
);
CREATE SEQUENCE unitmeasure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE unitmeasure_id_gen OWNED BY unitmeasure.id;
ALTER TABLE unitmeasure ADD UNIQUE (name);
COMMENT ON TABLE unitmeasure IS 'Единица измерения';
COMMENT ON COLUMN unitmeasure.id IS 'ид';
COMMENT ON COLUMN unitmeasure.name IS 'Наименование';
COMMENT ON COLUMN unitmeasure.short_name IS 'Сокращение';

