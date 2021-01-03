CREATE TABLE measureunit (
    id INTEGER NOT NULL,
    measure_id INTEGER NOT NULL,
    unitmeasure_id INTEGER NOT NULL,
    priority INTEGER NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE measureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE measureunit_id_gen OWNED BY measureunit.id;
ALTER TABLE measureunit ADD UNIQUE (measure_id, unitmeasure_id);
COMMENT ON TABLE measureunit IS 'Единица измерения меры';
COMMENT ON COLUMN measureunit.id IS 'Единица измерения меры ИД';
COMMENT ON COLUMN measureunit.priority IS 'Приоритет';

ALTER TABLE measureunit ADD CONSTRAINT FK_measureunit__measure_id FOREIGN KEY (measure_id) REFERENCES measure(id);
ALTER TABLE measureunit ADD CONSTRAINT FK_measureunit__unitmeasure_id FOREIGN KEY (unitmeasure_id) REFERENCES unitmeasure(id);
