/* Единица измерения */
CREATE TABLE edizm (
	edizm_id	INTEGER NOT NULL,
	edizm_name	VARCHAR(50),
	edizm_notation	VARCHAR(15) NOT NULL,
	edizm_blockflag	INTEGER NOT NULL,
	edizm_code	VARCHAR(20) NOT NULL,
	CONSTRAINT edizm_pk PRIMARY KEY (edizm_id),
	CONSTRAINT edizm_ak1 UNIQUE (edizm_code)
);
CREATE SEQUENCE edizm_id_gen AS INTEGER START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE edizm_id_gen OWNED BY edizm.edizm_id;
