CREATE TEMP TABLE ar AS
	SELECT id_auditoria_retrospectiva,id_usuario FROM sch_sisauc.historico_auditoria_retrospectiva
			WHERE data_alteracao IN (SELECT MAX(data_alteracao) 
					       FROM sch_sisauc.historico_auditoria_retrospectiva
					       WHERE id_auditoria_retrospectiva IN (SELECT id_auditoria_retrospectiva FROM sch_sisauc.item_gab WHERE estado = 'CONFORME' or estado = 'ENVIADO_PARA_DESCONTO') GROUP BY id_auditoria_retrospectiva)
					       order by id_auditoria_retrospectiva;

select * from ar;

DROP FUNCTION IF EXISTS atualizarARs();
			
CREATE OR REPLACE FUNCTION atualizarARs() RETURNS SETOF integer AS 
$BODY$
DECLARE
	r ar%rowtype;
BEGIN
	FOR r IN SELECT id_auditoria_retrospectiva, id_usuario FROM ar
	LOOP
		UPDATE sch_sisauc.auditoria_retrospectiva set id_auditor = r.id_usuario where id = r.id_auditoria_retrospectiva;
		RETURN NEXT r.id_auditoria_retrospectiva;
	END LOOP;
	RETURN;
END
$BODY$
LANGUAGE plpgsql;
			
SELECT * FROM atualizarARs();

DROP FUNCTION IF EXISTS atualizarARs();
DROP TABLE IF EXISTS ar;