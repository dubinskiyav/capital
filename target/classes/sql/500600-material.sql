CREATE TABLE material (
    id INTEGER NOT NULL,
    materiallevel_id INTEGER NOT NULL,
    kind INTEGER NOT NULL,
    name varchar(255) NOT NULL,
    code varchar(100),
    PRIMARY KEY (id)
);
CREATE SEQUENCE material_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE material_id_gen OWNED BY material.id;
ALTER TABLE dbo.material ADD UNIQUE (materiallevel_id, name);
ALTER TABLE material ADD CONSTRAINT FK_material__materiallevel_id FOREIGN KEY (materiallevel_id) REFERENCES materiallevel(id);
CREATE INDEX ON material (materiallevel_id);
COMMENT ON TABLE material IS 'Материал';
COMMENT ON COLUMN material.id IS 'Материал ИД';
COMMENT ON COLUMN material.kind IS '1 - Материал, 2 - Услуга';
COMMENT ON COLUMN material.name IS 'Наименование';
COMMENT ON COLUMN material.code IS 'Код';

