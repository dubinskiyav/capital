CREATE TABLE material (
    material_id INTEGER NOT NULL,
    materiallevel_id INTEGER NOT NULL,
    material_kind INTEGER NOT NULL,
    material_name varchar(255) NOT NULL,
    material_code varchar(100),
    PRIMARY KEY (material_id)
);
CREATE SEQUENCE material_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE material_id_gen OWNED BY material.material_id;
ALTER TABLE material ADD UNIQUE (materiallevel_id, material_name);
COMMENT ON TABLE material IS 'Материал';
COMMENT ON COLUMN material.material_id IS 'Материал ИД';
COMMENT ON COLUMN material.material_kind IS '1 - Материал, 2 - Услуга';
COMMENT ON COLUMN material.material_name IS 'Наименование';
COMMENT ON COLUMN material.material_code IS 'Код';

ALTER TABLE material ADD CONSTRAINT FK_material__materiallevel_id FOREIGN KEY (materiallevel_id) REFERENCES materiallevel(materiallevel_id);
