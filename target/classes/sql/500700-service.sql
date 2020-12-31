CREATE TABLE service (
    service_id INTEGER NOT NULL,
    PRIMARY KEY (service_id)
);
ALTER TABLE service ADD CONSTRAINT FK_service__service_id FOREIGN KEY (service_id) REFERENCES material(id);
CREATE SEQUENCE service_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE service_id_gen OWNED BY service.service_id;
COMMENT ON TABLE service IS 'Услуга';

