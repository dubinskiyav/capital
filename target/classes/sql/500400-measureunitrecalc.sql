CREATE TABLE measureunitrecalc (
    id INTEGER NOT NULL,
    mainmeasureunit_id INTEGER NOT NULL,
    measureunit_id INTEGER NOT NULL,
    conversion_factor real NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE measureunitrecalc_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE measureunitrecalc_id_gen OWNED BY measureunitrecalc.id;
ALTER TABLE measureunitrecalc ADD UNIQUE (mainmeasureunit_id, measureunit_id);
ALTER TABLE measureunitrecalc ADD CONSTRAINT FK_measureunitrecalc__mainmeasureunit_id FOREIGN KEY (mainmeasureunit_id) REFERENCES mainmeasureunit(measureunit_id);
ALTER TABLE measureunitrecalc ADD CONSTRAINT FK_measureunitrecalc__measureunit_id FOREIGN KEY (measureunit_id) REFERENCES measureunit(id);
COMMENT ON TABLE measureunitrecalc IS 'Пересчет единиц измерения';
COMMENT ON COLUMN measureunitrecalc.id IS 'Пересчет единиц измерения ИД';
COMMENT ON COLUMN measureunitrecalc.mainmeasureunit_id IS 'Главная единица измерения Ссылка';
COMMENT ON COLUMN measureunitrecalc.measureunit_id IS 'Единица измерения Ссылка';
COMMENT ON COLUMN measureunitrecalc.conversion_factor IS 'Коэффициент пересчета (сколько главных единиц в единице)';

