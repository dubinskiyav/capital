CREATE TABLE unitmeasurerecalc (
    unitmeasurerecalc_id INTEGER NOT NULL,
    unitmeasure_idfrom INTEGER NOT NULL,
    unitmeasure_idto INTEGER NOT NULL,
    unitmeasurerecalc_factor real NOT NULL,
    PRIMARY KEY (unitmeasurerecalc_id)
);
CREATE SEQUENCE unitmeasurerecalc_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE unitmeasurerecalc_id_gen OWNED BY unitmeasurerecalc.unitmeasurerecalc_id;
ALTER TABLE unitmeasurerecalc ADD UNIQUE (unitmeasure_idfrom, unitmeasure_idto);
COMMENT ON TABLE unitmeasurerecalc IS 'Пересчет единиц измерения (from = factor to)';
COMMENT ON COLUMN unitmeasurerecalc.unitmeasurerecalc_id IS 'Пересчет единиц измерения ИД';
COMMENT ON COLUMN unitmeasurerecalc.unitmeasure_idfrom IS 'Единица измерения из ИД';
COMMENT ON COLUMN unitmeasurerecalc.unitmeasure_idto IS 'Единица измерения в ИД';
COMMENT ON COLUMN unitmeasurerecalc.unitmeasurerecalc_factor IS 'Коэффициент';

ALTER TABLE unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasure_idfrom FOREIGN KEY (unitmeasure_idfrom) REFERENCES unitmeasure(unitmeasure_id);
ALTER TABLE unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasure_idto FOREIGN KEY (unitmeasure_idto) REFERENCES measure(measure_id);
