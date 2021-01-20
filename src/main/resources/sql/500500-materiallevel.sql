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
ALTER TABLE materiallevel ADD CONSTRAINT FK_materiallevel__master_id FOREIGN KEY (master_id) REFERENCES materiallevel(id);
CREATE INDEX ON materiallevel (master_id);
COMMENT ON TABLE materiallevel IS 'Уровень материала';
COMMENT ON COLUMN materiallevel.id IS 'Уровень материалов ИД';
COMMENT ON COLUMN materiallevel.master_id IS 'Мастер уровень';
COMMENT ON COLUMN materiallevel.name IS 'Наименование';
COMMENT ON COLUMN materiallevel.code IS 'Код';
COMMENT ON COLUMN materiallevel.date_beg IS 'Начало действия';
COMMENT ON COLUMN materiallevel.date_end IS 'Окончание действия';

