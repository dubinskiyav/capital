CREATE TABLE measureunit (
    id INTEGER NOT NULL,
    measure_id INTEGER NOT NULL,
    name varchar(100) NOT NULL,
    short_name varchar(20),
    PRIMARY KEY (id)
);
CREATE SEQUENCE measureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE measureunit_id_gen OWNED BY measureunit.id;
ALTER TABLE measureunit ADD UNIQUE (name);
ALTER TABLE measureunit ADD CONSTRAINT FK_measureunit__measure_id FOREIGN KEY (measure_id) REFERENCES measure(id);
CREATE INDEX ON measureunit (measure_id);
COMMENT ON TABLE measureunit IS 'Единица измерения';
COMMENT ON COLUMN measureunit.id IS 'ид';
COMMENT ON COLUMN measureunit.measure_id IS 'Единица измерения Ссылка';
COMMENT ON COLUMN measureunit.name IS 'Наименование';
COMMENT ON COLUMN measureunit.short_name IS 'Сокращение';

