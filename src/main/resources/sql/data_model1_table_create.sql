CREATE TABLE measure (
    id INTEGER NOT NULL,
    name varchar(100) NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE measure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE measure_id_gen OWNED BY measure.id;
ALTER TABLE measure ADD UNIQUE (name);
COMMENT ON TABLE measure IS 'Мера измерения';
COMMENT ON COLUMN measure.id IS 'Мера измерения ИД';
COMMENT ON COLUMN measure.name IS 'Наименование';

CREATE TABLE unitmeasure (
    id INTEGER NOT NULL,
    name varchar(100) NOT NULL,
    short_name varchar(20),
    PRIMARY KEY (id)
);
CREATE SEQUENCE unitmeasure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE unitmeasure_id_gen OWNED BY unitmeasure.id;
ALTER TABLE unitmeasure ADD UNIQUE (name);
COMMENT ON TABLE unitmeasure IS 'Единица измерения';
COMMENT ON COLUMN unitmeasure.id IS 'ид';
COMMENT ON COLUMN unitmeasure.name IS 'Наименование';
COMMENT ON COLUMN unitmeasure.short_name IS 'Сокращение';

CREATE TABLE material (
    id INTEGER NOT NULL,
    materiallevel_id INTEGER NOT NULL,
    name varchar(255) NOT NULL,
    code varchar(100),
    PRIMARY KEY (id)
);
CREATE SEQUENCE material_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE material_id_gen OWNED BY material.id;
ALTER TABLE material ADD UNIQUE (name);
CREATE INDEX ON material (materiallevel_id);
COMMENT ON TABLE material IS 'Материал';
COMMENT ON COLUMN material.id IS 'Материал ИД';
COMMENT ON COLUMN material.name IS 'Наименование';
COMMENT ON COLUMN material.code IS 'Код';

CREATE TABLE materiallevel (
    id INTEGER NOT NULL,
    master_id INTEGER NOT NULL,
    name varchar(255) NOT NULL,
    code varchar(100),
    date_beg date NOT NULL,
    date_end date NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE materiallevel_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE materiallevel_id_gen OWNED BY materiallevel.id;
CREATE INDEX ON materiallevel (master_id);
COMMENT ON TABLE materiallevel IS 'Уровень материала';
COMMENT ON COLUMN materiallevel.id IS 'Уровень материалов ИД';
COMMENT ON COLUMN materiallevel.master_id IS 'Мастер уровень';
COMMENT ON COLUMN materiallevel.name IS 'Наименование';
COMMENT ON COLUMN materiallevel.code IS 'Код';
COMMENT ON COLUMN materiallevel.date_beg IS 'Начало действия';
COMMENT ON COLUMN materiallevel.date_end IS 'Окончание действия';

CREATE TABLE service (
    service_id INTEGER NOT NULL,
    PRIMARY KEY (service_id)
);
CREATE SEQUENCE service_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE service_id_gen OWNED BY service.service_id;
COMMENT ON TABLE service IS 'Услуга';

CREATE TABLE measureunit (
    id INTEGER NOT NULL,
    measure_id INTEGER NOT NULL,
    unitmeasure_id INTEGER NOT NULL,
    priority INTEGER NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE measureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE measureunit_id_gen OWNED BY measureunit.id;
ALTER TABLE measureunit ADD UNIQUE (measure_id, unitmeasure_id);
COMMENT ON TABLE measureunit IS 'Единица измерения меры';
COMMENT ON COLUMN measureunit.id IS 'Единица измерения меры ИД';
COMMENT ON COLUMN measureunit.priority IS 'Приоритет';

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

ALTER TABLE material ADD CONSTRAINT FK_material__materiallevel_id FOREIGN KEY (materiallevel_id) REFERENCES materiallevel(id);
ALTER TABLE materiallevel ADD CONSTRAINT FK_materiallevel__master_id FOREIGN KEY (master_id) REFERENCES materiallevel(id);
ALTER TABLE service ADD CONSTRAINT FK_service__service_id FOREIGN KEY (service_id) REFERENCES material(id);
ALTER TABLE measureunit ADD CONSTRAINT FK_measureunit__measure_id FOREIGN KEY (measure_id) REFERENCES measure(id);
ALTER TABLE measureunit ADD CONSTRAINT FK_measureunit__unitmeasure_id FOREIGN KEY (unitmeasure_id) REFERENCES unitmeasure(id);
ALTER TABLE unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasurefrom_id FOREIGN KEY (unitmeasurefrom_id) REFERENCES unitmeasure(id);
ALTER TABLE unitmeasurerecalc ADD CONSTRAINT FK_unitmeasurerecalc__unitmeasureto_id FOREIGN KEY (unitmeasureto_id) REFERENCES measure(id);