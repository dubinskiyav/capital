CREATE TABLE materiallevel (
    materiallevel_id INTEGER NOT NULL,
    materiallevel_idmaster INTEGER NOT NULL,
    materiallevel_name varchar(255) NOT NULL,
    materiallevel_code varchar(100),
    materiallevel_datebeg date NOT NULL,
    materiallevel_dateend date NOT NULL,
    PRIMARY KEY (materiallevel_id)
);
CREATE SEQUENCE materiallevel_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE materiallevel_id_gen OWNED BY materiallevel.materiallevel_id;
CREATE INDEX ON materiallevel (materiallevel_idmaster);
COMMENT ON TABLE materiallevel IS 'Уровень материала';
COMMENT ON COLUMN materiallevel.materiallevel_id IS 'Уровень материалов ИД';
COMMENT ON COLUMN materiallevel.materiallevel_idmaster IS 'Мастер уровень';
COMMENT ON COLUMN materiallevel.materiallevel_name IS 'Наименование';
COMMENT ON COLUMN materiallevel.materiallevel_code IS 'Код';
COMMENT ON COLUMN materiallevel.materiallevel_datebeg IS 'Начало действия';
COMMENT ON COLUMN materiallevel.materiallevel_dateend IS 'Окончание действия';

ALTER TABLE materiallevel ADD CONSTRAINT FK_materiallevel__materiallevel_idmaster FOREIGN KEY (materiallevel_idmaster) REFERENCES materiallevel(materiallevel_id);
