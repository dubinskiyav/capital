CREATE TABLE materialunitmeasure (
    materialunitmeasure_id INTEGER NOT NULL,
    material_id INTEGER NOT NULL,
    unitmeasure_id INTEGER NOT NULL,
    PRIMARY KEY (materialunitmeasure_id)
);
CREATE SEQUENCE materialunitmeasure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE materialunitmeasure_id_gen OWNED BY materialunitmeasure.materialunitmeasure_id;
ALTER TABLE materialunitmeasure ADD UNIQUE (material_id, unitmeasure_id);
COMMENT ON TABLE materialunitmeasure IS 'Единица измерений материала';
COMMENT ON COLUMN materialunitmeasure.materialunitmeasure_id IS 'Единица измерений материала ИД';
COMMENT ON COLUMN materialunitmeasure.material_id IS 'Материал ИД';
COMMENT ON COLUMN materialunitmeasure.unitmeasure_id IS 'Единица измерений ИД';

ALTER TABLE materialunitmeasure ADD CONSTRAINT FK_materialunitmeasure__material_id FOREIGN KEY (material_id) REFERENCES material(material_id);
ALTER TABLE materialunitmeasure ADD CONSTRAINT FK_materialunitmeasure__unitmeasure_id FOREIGN KEY (unitmeasure_id) REFERENCES unitmeasure(unitmeasure_id);
