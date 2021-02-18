CREATE TABLE dbo.measure (
    measure_id INTEGER NOT NULL,
    measure_name varchar(100) NOT NULL,
    PRIMARY KEY (measure_id)
);
CREATE SEQUENCE dbo.measure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.measure_id_gen OWNED BY dbo.measure.measure_id;
ALTER TABLE dbo.measure ADD UNIQUE (measure_name);
COMMENT ON TABLE dbo.measure IS 'Мера измерения';
COMMENT ON COLUMN dbo.measure.measure_id IS 'Мера измерения ИД';
COMMENT ON COLUMN dbo.measure.measure_name IS 'Наименование';

CREATE TABLE dbo.unitmeasure (
    unitmeasure_id INTEGER NOT NULL,
    unitmeasure_name varchar(100) NOT NULL,
    unitmeasure_shortname varchar(20),
    PRIMARY KEY (unitmeasure_id)
);
CREATE SEQUENCE dbo.unitmeasure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.unitmeasure_id_gen OWNED BY dbo.unitmeasure.unitmeasure_id;
ALTER TABLE dbo.unitmeasure ADD UNIQUE (unitmeasure_name);
COMMENT ON TABLE dbo.unitmeasure IS 'Единица измерения';
COMMENT ON COLUMN dbo.unitmeasure.unitmeasure_id IS 'ид';
COMMENT ON COLUMN dbo.unitmeasure.unitmeasure_name IS 'Наименование';
COMMENT ON COLUMN dbo.unitmeasure.unitmeasure_shortname IS 'Сокращение';

CREATE TABLE dbo.material (
    material_id INTEGER NOT NULL,
    materiallevel_id INTEGER NOT NULL,
    material_kind INTEGER NOT NULL,
    material_name varchar(255) NOT NULL,
    material_code varchar(100),
    PRIMARY KEY (material_id)
);
CREATE SEQUENCE dbo.material_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.material_id_gen OWNED BY dbo.material.material_id;
ALTER TABLE dbo.material ADD UNIQUE (materiallevel_id, material_name);
COMMENT ON TABLE dbo.material IS 'Материал';
COMMENT ON COLUMN dbo.material.material_id IS 'Материал ИД';
COMMENT ON COLUMN dbo.material.material_kind IS '1 - Материал, 2 - Услуга';
COMMENT ON COLUMN dbo.material.material_name IS 'Наименование';
COMMENT ON COLUMN dbo.material.material_code IS 'Код';

CREATE TABLE dbo.materiallevel (
    materiallevel_id INTEGER NOT NULL,
    materiallevel_idmaster INTEGER NOT NULL,
    materiallevel_name varchar(255) NOT NULL,
    materiallevel_code varchar(100),
    materiallevel_datebeg date NOT NULL,
    materiallevel_dateend date NOT NULL,
    PRIMARY KEY (materiallevel_id)
);
CREATE SEQUENCE dbo.materiallevel_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.materiallevel_id_gen OWNED BY dbo.materiallevel.materiallevel_id;
CREATE INDEX ON dbo.materiallevel (materiallevel_idmaster);
COMMENT ON TABLE dbo.materiallevel IS 'Уровень материала';
COMMENT ON COLUMN dbo.materiallevel.materiallevel_id IS 'Уровень материалов ИД';
COMMENT ON COLUMN dbo.materiallevel.materiallevel_idmaster IS 'Мастер уровень';
COMMENT ON COLUMN dbo.materiallevel.materiallevel_name IS 'Наименование';
COMMENT ON COLUMN dbo.materiallevel.materiallevel_code IS 'Код';
COMMENT ON COLUMN dbo.materiallevel.materiallevel_datebeg IS 'Начало действия';
COMMENT ON COLUMN dbo.materiallevel.materiallevel_dateend IS 'Окончание действия';

CREATE TABLE dbo.service (
    service_id INTEGER NOT NULL,
    PRIMARY KEY (service_id)
);
CREATE SEQUENCE dbo.service_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.service_id_gen OWNED BY dbo.service.service_id;
COMMENT ON TABLE dbo.service IS 'Услуга';

CREATE TABLE dbo.materialunitmeasure (
    materialunitmeasure_id INTEGER NOT NULL,
    material_id INTEGER NOT NULL,
    unitmeasure_id INTEGER NOT NULL,
    PRIMARY KEY (materialunitmeasure_id)
);
CREATE SEQUENCE dbo.materialunitmeasure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.materialunitmeasure_id_gen OWNED BY dbo.materialunitmeasure.materialunitmeasure_id;
ALTER TABLE dbo.materialunitmeasure ADD UNIQUE (material_id, unitmeasure_id);
COMMENT ON TABLE dbo.materialunitmeasure IS 'Единица измерений материала';
COMMENT ON COLUMN dbo.materialunitmeasure.materialunitmeasure_id IS 'Единица измерений материала ИД';
COMMENT ON COLUMN dbo.materialunitmeasure.material_id IS 'Материал ИД';
COMMENT ON COLUMN dbo.materialunitmeasure.unitmeasure_id IS 'Единица измерений ИД';

CREATE TABLE dbo.measureunit (
    measureunit_id INTEGER NOT NULL,
    measure_id INTEGER NOT NULL,
    unitmeasure_id INTEGER NOT NULL,
    measureunit_priority INTEGER NOT NULL,
    PRIMARY KEY (measureunit_id)
);
CREATE SEQUENCE dbo.measureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.measureunit_id_gen OWNED BY dbo.measureunit.measureunit_id;
ALTER TABLE dbo.measureunit ADD UNIQUE (measure_id, unitmeasure_id);
COMMENT ON TABLE dbo.measureunit IS 'Единица измерения меры';
COMMENT ON COLUMN dbo.measureunit.measureunit_id IS 'Единица измерения меры ИД';
COMMENT ON COLUMN dbo.measureunit.measureunit_priority IS 'Приоритет';

CREATE TABLE dbo.unitmeasurerecalc (
    unitmeasurerecalc_id INTEGER NOT NULL,
    unitmeasure_idfrom INTEGER NOT NULL,
    unitmeasure_idto INTEGER NOT NULL,
    unitmeasurerecalc_factor real NOT NULL,
    PRIMARY KEY (unitmeasurerecalc_id)
);
CREATE SEQUENCE dbo.unitmeasurerecalc_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.unitmeasurerecalc_id_gen OWNED BY dbo.unitmeasurerecalc.unitmeasurerecalc_id;
ALTER TABLE dbo.unitmeasurerecalc ADD UNIQUE (unitmeasure_idfrom, unitmeasure_idto);
COMMENT ON TABLE dbo.unitmeasurerecalc IS 'Пересчет единиц измерения (from = factor to)';
COMMENT ON COLUMN dbo.unitmeasurerecalc.unitmeasurerecalc_id IS 'Пересчет единиц измерения ИД';
COMMENT ON COLUMN dbo.unitmeasurerecalc.unitmeasure_idfrom IS 'Единица измерения из ИД';
COMMENT ON COLUMN dbo.unitmeasurerecalc.unitmeasure_idto IS 'Единица измерения в ИД';
COMMENT ON COLUMN dbo.unitmeasurerecalc.unitmeasurerecalc_factor IS 'Коэффициент';

ALTER TABLE dbo.material ADD CONSTRAINT FK_material__materiallevel_id FOREIGN KEY (materiallevel_id) REFERENCES dbo.materiallevel(materiallevel_id);
ALTER TABLE dbo.materiallevel ADD CONSTRAINT FK_materiallevel__materiallevel_idmaster FOREIGN KEY (materiallevel_idmaster) REFERENCES dbo.materiallevel(materiallevel_id);
ALTER TABLE dbo.service ADD CONSTRAINT FK_service__service_id FOREIGN KEY (service_id) REFERENCES dbo.material(material_id);
ALTER TABLE dbo.materialunitmeasure ADD CONSTRAINT FK_materialunitmeasure__material_id FOREIGN KEY (material_id) REFERENCES dbo.material(material_id);
ALTER TABLE dbo.materialunitmeasure ADD CONSTRAINT FK_materialunitmeasure__unitmeasure_id FOREIGN KEY (unitmeasure_id) REFERENCES dbo.unitmeasure(unitmeasure_id);
ALTER TABLE dbo.measureunit ADD CONSTRAINT FK_measureunit__measure_id FOREIGN KEY (measure_id) REFERENCES dbo.measure(measure_id);
ALTER TABLE dbo.measureunit ADD CONSTRAINT FK_measureunit__unitmeasure_id FOREIGN KEY (unitmeasure_id) REFERENCES dbo.unitmeasure(unitmeasure_id);
ALTER TABLE dbo.unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasure_idfrom FOREIGN KEY (unitmeasure_idfrom) REFERENCES dbo.unitmeasure(unitmeasure_id);
ALTER TABLE dbo.unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasure_idto FOREIGN KEY (unitmeasure_idto) REFERENCES dbo.measure(measure_id);