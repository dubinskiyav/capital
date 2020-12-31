CREATE TABLE materialmeasureunit (
    id INTEGER NOT NULL,
    material_id INTEGER NOT NULL,
    measureunit_id INTEGER NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE materialmeasureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE materialmeasureunit_id_gen OWNED BY materialmeasureunit.id;
ALTER TABLE materialmeasureunit ADD CONSTRAINT FK_materialmeasureunit__material_id FOREIGN KEY (material_id) REFERENCES material(id);
ALTER TABLE materialmeasureunit ADD CONSTRAINT FK_materialmeasureunit__measureunit_id FOREIGN KEY (measureunit_id) REFERENCES measureunit(id);
ALTER TABLE materialmeasureunit ADD UNIQUE (material_id, measureunit_id);
COMMENT ON TABLE materialmeasureunit IS 'Единица измерений материала';
COMMENT ON COLUMN materialmeasureunit.id IS 'Единица измерений материала ИД';
COMMENT ON COLUMN materialmeasureunit.material_id IS 'Материал ИД';
COMMENT ON COLUMN materialmeasureunit.measureunit_id IS 'Единица измерений ИД';

