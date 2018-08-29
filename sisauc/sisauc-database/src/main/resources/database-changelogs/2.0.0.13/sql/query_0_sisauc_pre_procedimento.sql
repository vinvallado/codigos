ALTER SEQUENCE sch_sisauc.procedimento_base_id_seq RESTART WITH 5000;

CREATE SEQUENCE sch_sisauc.procedimento_trs_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 5000
  CACHE 1;

ALTER TABLE sch_sisauc.procedimento_trs_id_seq
	OWNER TO sisauc;