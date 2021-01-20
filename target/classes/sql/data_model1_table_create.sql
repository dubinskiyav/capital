CREATE TABLE dbo.measure (
    id INTEGER NOT NULL,
    name varchar(100) NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.measure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.measure_id_gen OWNED BY dbo.measure.id;
ALTER TABLE dbo.measure ADD UNIQUE (name);
COMMENT ON TABLE dbo.measure IS 'Мера измерения';
COMMENT ON COLUMN dbo.measure.id IS 'Мера измерения ИД';
COMMENT ON COLUMN dbo.measure.name IS 'Наименование';

CREATE TABLE dbo.unitmeasure (
    id INTEGER NOT NULL,
    name varchar(100) NOT NULL,
    short_name varchar(20),
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.unitmeasure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.unitmeasure_id_gen OWNED BY dbo.unitmeasure.id;
ALTER TABLE dbo.unitmeasure ADD UNIQUE (name);
COMMENT ON TABLE dbo.unitmeasure IS 'Единица измерения';
COMMENT ON COLUMN dbo.unitmeasure.id IS 'ид';
COMMENT ON COLUMN dbo.unitmeasure.name IS 'Наименование';
COMMENT ON COLUMN dbo.unitmeasure.short_name IS 'Сокращение';

CREATE TABLE dbo.material (
    id INTEGER NOT NULL,
    materiallevel_id INTEGER NOT NULL,
    kind INTEGER NOT NULL,
    name varchar(255) NOT NULL,
    code varchar(100),
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.material_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.material_id_gen OWNED BY dbo.material.id;
ALTER TABLE dbo.material ADD UNIQUE (materiallevel_id, name);
COMMENT ON TABLE dbo.material IS 'Материал';
COMMENT ON COLUMN dbo.material.id IS 'Материал ИД';
COMMENT ON COLUMN dbo.material.kind IS '1 - Материал, 2 - Услуга';
COMMENT ON COLUMN dbo.material.name IS 'Наименование';
COMMENT ON COLUMN dbo.material.code IS 'Код';

CREATE TABLE dbo.materiallevel (
    id INTEGER NOT NULL,
    master_id INTEGER NOT NULL,
    name varchar(255) NOT NULL,
    code varchar(100),
    date_beg date NOT NULL,
    date_end date NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.materiallevel_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.materiallevel_id_gen OWNED BY dbo.materiallevel.id;
CREATE INDEX ON dbo.materiallevel (master_id);
COMMENT ON TABLE dbo.materiallevel IS 'Уровень материала';
COMMENT ON COLUMN dbo.materiallevel.id IS 'Уровень материалов ИД';
COMMENT ON COLUMN dbo.materiallevel.master_id IS 'Мастер уровень';
COMMENT ON COLUMN dbo.materiallevel.name IS 'Наименование';
COMMENT ON COLUMN dbo.materiallevel.code IS 'Код';
COMMENT ON COLUMN dbo.materiallevel.date_beg IS 'Начало действия';
COMMENT ON COLUMN dbo.materiallevel.date_end IS 'Окончание действия';

CREATE TABLE dbo.service (
    service_id INTEGER NOT NULL,
    PRIMARY KEY (service_id)
);
CREATE SEQUENCE dbo.service_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.service_id_gen OWNED BY dbo.service.service_id;
COMMENT ON TABLE dbo.service IS 'Услуга';

CREATE TABLE dbo.materialunitmeasure (
    id INTEGER NOT NULL,
    material_id INTEGER NOT NULL,
    unitmeasure_id INTEGER NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.materialunitmeasure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.materialunitmeasure_id_gen OWNED BY dbo.materialunitmeasure.id;
ALTER TABLE dbo.materialunitmeasure ADD UNIQUE (material_id, unitmeasure_id);
COMMENT ON TABLE dbo.materialunitmeasure IS 'Единица измерений материала';
COMMENT ON COLUMN dbo.materialunitmeasure.id IS 'Единица измерений материала ИД';
COMMENT ON COLUMN dbo.materialunitmeasure.material_id IS 'Материал ИД';
COMMENT ON COLUMN dbo.materialunitmeasure.unitmeasure_id IS 'Единица измерений ИД';

CREATE TABLE dbo.measureunit (
    id INTEGER NOT NULL,
    measure_id INTEGER NOT NULL,
    unitmeasure_id INTEGER NOT NULL,
    priority INTEGER NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.measureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.measureunit_id_gen OWNED BY dbo.measureunit.id;
ALTER TABLE dbo.measureunit ADD UNIQUE (measure_id, unitmeasure_id);
COMMENT ON TABLE dbo.measureunit IS 'Единица измерения меры';
COMMENT ON COLUMN dbo.measureunit.id IS 'Единица измерения меры ИД';
COMMENT ON COLUMN dbo.measureunit.priority IS 'Приоритет';

CREATE TABLE dbo.unitmeasurerecalc (
    id INTEGER NOT NULL,
    unitmeasurefrom_id INTEGER NOT NULL,
    unitmeasureto_id INTEGER NOT NULL,
    factor real NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.unitmeasurerecalc_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.unitmeasurerecalc_id_gen OWNED BY dbo.unitmeasurerecalc.id;
ALTER TABLE dbo.unitmeasurerecalc ADD UNIQUE (unitmeasurefrom_id, unitmeasureto_id);
COMMENT ON TABLE dbo.unitmeasurerecalc IS 'Пересчет единиц измерения';
COMMENT ON COLUMN dbo.unitmeasurerecalc.id IS 'Пересчет единиц измерения ИД';
COMMENT ON COLUMN dbo.unitmeasurerecalc.unitmeasurefrom_id IS 'Единица измерения из ИД';
COMMENT ON COLUMN dbo.unitmeasurerecalc.unitmeasureto_id IS 'Единица измерения в ИД';
COMMENT ON COLUMN dbo.unitmeasurerecalc.factor IS 'Коэффициент';

ALTER TABLE dbo.material ADD CONSTRAINT FK_material__materiallevel_id FOREIGN KEY (materiallevel_id) REFERENCES dbo.materiallevel(id);
ALTER TABLE dbo.materiallevel ADD CONSTRAINT FK_materiallevel__master_id FOREIGN KEY (master_id) REFERENCES dbo.materiallevel(id);
ALTER TABLE dbo.service ADD CONSTRAINT FK_service__service_id FOREIGN KEY (service_id) REFERENCES dbo.material(id);
ALTER TABLE dbo.materialunitmeasure ADD CONSTRAINT FK_materialunitmeasure__material_id FOREIGN KEY (material_id) REFERENCES dbo.material(id);
ALTER TABLE dbo.materialunitmeasure ADD CONSTRAINT FK_materialunitmeasure__unitmeasure_id FOREIGN KEY (unitmeasure_id) REFERENCES dbo.unitmeasure(id);
ALTER TABLE dbo.measureunit ADD CONSTRAINT FK_measureunit__measure_id FOREIGN KEY (measure_id) REFERENCES dbo.measure(id);
ALTER TABLE dbo.measureunit ADD CONSTRAINT FK_measureunit__unitmeasure_id FOREIGN KEY (unitmeasure_id) REFERENCES dbo.unitmeasure(id);
ALTER TABLE dbo.unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasurefrom_id FOREIGN KEY (unitmeasurefrom_id) REFERENCES dbo.unitmeasure(id);
ALTER TABLE dbo.unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasureto_id FOREIGN KEY (unitmeasureto_id) REFERENCES dbo.measure(id);