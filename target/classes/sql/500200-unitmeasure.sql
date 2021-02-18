CREATE TABLE unitmeasure (
    unitmeasure_id INTEGER NOT NULL,
    unitmeasure_name varchar(100) NOT NULL,
    unitmeasure_shortname varchar(20),
    PRIMARY KEY (unitmeasure_id)
);
CREATE SEQUENCE unitmeasure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE unitmeasure_id_gen OWNED BY unitmeasure.unitmeasure_id;
ALTER TABLE unitmeasure ADD UNIQUE (unitmeasure_name);
COMMENT ON TABLE unitmeasure IS 'Единица измерения';
COMMENT ON COLUMN unitmeasure.unitmeasure_id IS 'ид';
COMMENT ON COLUMN unitmeasure.unitmeasure_name IS 'Наименование';
COMMENT ON COLUMN unitmeasure.unitmeasure_shortname IS 'Сокращение';

