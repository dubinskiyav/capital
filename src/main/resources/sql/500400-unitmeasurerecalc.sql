CREATE TABLE unitmeasurerecalc (
    id INTEGER NOT NULL,
    unitmeasurefrom_id INTEGER NOT NULL,
    unitmeasureto_id INTEGER NOT NULL,
    factor real NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE unitmeasurerecalc_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE unitmeasurerecalc_id_gen OWNED BY unitmeasurerecalc.id;
ALTER TABLE unitmeasurerecalc ADD UNIQUE (unitmeasurefrom_id, unitmeasureto_id);
COMMENT ON TABLE unitmeasurerecalc IS 'Пересчет единиц измерения';
COMMENT ON COLUMN unitmeasurerecalc.id IS 'Пересчет единиц измерения ИД';
COMMENT ON COLUMN unitmeasurerecalc.unitmeasurefrom_id IS 'Единица измерения из ИД';
COMMENT ON COLUMN unitmeasurerecalc.unitmeasureto_id IS 'Единица измерения в ИД';
COMMENT ON COLUMN unitmeasurerecalc.factor IS 'Коэффициент';
ALTER TABLE unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasurefrom_id FOREIGN KEY (unitmeasurefrom_id) REFERENCES unitmeasure(id);
ALTER TABLE unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasureto_id FOREIGN KEY (unitmeasureto_id) REFERENCES measure(id);

