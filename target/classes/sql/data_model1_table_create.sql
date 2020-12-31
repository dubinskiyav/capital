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

CREATE TABLE dbo.measureunit (
    id INTEGER NOT NULL,
    measure_id INTEGER NOT NULL,
    name varchar(100) NOT NULL,
    short_name varchar(20),
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.measureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.measureunit_id_gen OWNED BY dbo.measureunit.id;
ALTER TABLE dbo.measureunit ADD UNIQUE (name);
CREATE INDEX ON dbo.measureunit (measure_id);
COMMENT ON TABLE dbo.measureunit IS 'Единица измерения';
COMMENT ON COLUMN dbo.measureunit.id IS 'ид';
COMMENT ON COLUMN dbo.measureunit.measure_id IS 'Единица измерения Ссылка';
COMMENT ON COLUMN dbo.measureunit.name IS 'Наименование';
COMMENT ON COLUMN dbo.measureunit.short_name IS 'Сокращение';

CREATE TABLE dbo.mainmeasureunit (
    measureunit_id INTEGER NOT NULL,
    PRIMARY KEY (measureunit_id)
);
CREATE SEQUENCE dbo.mainmeasureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.mainmeasureunit_id_gen OWNED BY dbo.mainmeasureunit.measureunit_id;
COMMENT ON TABLE dbo.mainmeasureunit IS 'Главная единица для меры';
COMMENT ON COLUMN dbo.mainmeasureunit.measureunit_id IS 'Единица измерения Ссылка';

CREATE TABLE dbo.measureunitrecalc (
    id INTEGER NOT NULL,
    mainmeasureunit_id INTEGER NOT NULL,
    measureunit_id INTEGER NOT NULL,
    conversion_factor real NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.measureunitrecalc_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.measureunitrecalc_id_gen OWNED BY dbo.measureunitrecalc.id;
ALTER TABLE dbo.measureunitrecalc ADD UNIQUE (mainmeasureunit_id, measureunit_id);
COMMENT ON TABLE dbo.measureunitrecalc IS 'Пересчет единиц измерения';
COMMENT ON COLUMN dbo.measureunitrecalc.id IS 'Пересчет единиц измерения ИД';
COMMENT ON COLUMN dbo.measureunitrecalc.mainmeasureunit_id IS 'Главная единица измерения Ссылка';
COMMENT ON COLUMN dbo.measureunitrecalc.measureunit_id IS 'Единица измерения Ссылка';
COMMENT ON COLUMN dbo.measureunitrecalc.conversion_factor IS 'Коэффициент пересчета (сколько главных единиц в единице)';

CREATE TABLE dbo.material (
    id INTEGER NOT NULL,
    name varchar(255) NOT NULL,
    code varchar(100),
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.material_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.material_id_gen OWNED BY dbo.material.id;
ALTER TABLE dbo.material ADD UNIQUE (name);
COMMENT ON TABLE dbo.material IS 'Материал';
COMMENT ON COLUMN dbo.material.id IS 'Материал ИД';
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

CREATE TABLE dbo.materialmeasureunit (
    id INTEGER NOT NULL,
    material_id INTEGER NOT NULL,
    measureunit_id INTEGER NOT NULL,
    PRIMARY KEY (id)
);
CREATE SEQUENCE dbo.materialmeasureunit_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE dbo.materialmeasureunit_id_gen OWNED BY dbo.materialmeasureunit.id;
ALTER TABLE dbo.materialmeasureunit ADD UNIQUE (material_id, measureunit_id);
COMMENT ON TABLE dbo.materialmeasureunit IS 'Единица измерений материала';
COMMENT ON COLUMN dbo.materialmeasureunit.id IS 'Единица измерений материала ИД';
COMMENT ON COLUMN dbo.materialmeasureunit.material_id IS 'Материал ИД';
COMMENT ON COLUMN dbo.materialmeasureunit.measureunit_id IS 'Единица измерений ИД';

ALTER TABLE dbo.measureunit ADD CONSTRAINT FK_measureunit__measure_id FOREIGN KEY (measure_id) REFERENCES dbo.measure(id);
ALTER TABLE dbo.measureunitrecalc ADD CONSTRAINT FK_measureunitrecalc__mainmeasureunit_id FOREIGN KEY (mainmeasureunit_id) REFERENCES dbo.mainmeasureunit(measureunit_id);
ALTER TABLE dbo.measureunitrecalc ADD CONSTRAINT FK_measureunitrecalc__measureunit_id FOREIGN KEY (measureunit_id) REFERENCES dbo.measureunit(id);
ALTER TABLE dbo.materiallevel ADD CONSTRAINT FK_materiallevel__master_id FOREIGN KEY (master_id) REFERENCES dbo.materiallevel(id);
ALTER TABLE dbo.service ADD CONSTRAINT FK_service__service_id FOREIGN KEY (service_id) REFERENCES dbo.material(id);
ALTER TABLE dbo.materialmeasureunit ADD CONSTRAINT FK_materialmeasureunit__material_id FOREIGN KEY (material_id) REFERENCES dbo.material(id);
ALTER TABLE dbo.materialmeasureunit ADD CONSTRAINT FK_materialmeasureunit__measureunit_id FOREIGN KEY (measureunit_id) REFERENCES dbo.measureunit(id);