CREATE TABLE mainmeasureunit (
    measureunit_id INTEGER NOT NULL,
    PRIMARY KEY (measureunit_id)
);
ALTER TABLE mainmeasureunit ADD CONSTRAINT FK_mainmeasureunit__measureunit_id FOREIGN KEY (measureunit_id) REFERENCES measureunit(id);
CREATE SEQUENCE mainmeasureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE mainmeasureunit_id_gen OWNED BY mainmeasureunit.measureunit_id;
COMMENT ON TABLE mainmeasureunit IS 'Главная единица для меры';
COMMENT ON COLUMN mainmeasureunit.measureunit_id IS 'Единица измерения Ссылка';

