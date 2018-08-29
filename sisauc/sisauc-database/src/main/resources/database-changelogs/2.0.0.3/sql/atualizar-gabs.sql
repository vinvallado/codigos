			--FOI COLOCADO EM UM ARQUIVO SEPARADO POIS O LIQUIBASE NAO CONSEGUIA EXECUTAR ESTE SCRIPT NA TAG <SQL>
			DROP FUNCTION IF EXISTS atualizarGABs();
			
			CREATE OR REPLACE FUNCTION atualizarGABs() RETURNS SETOF integer AS 
			$BODY$
			DECLARE
			   r sch_sisauc.gab%rowtype;
			BEGIN
			    FOR r IN SELECT id FROM sch_sisauc.gab
			    LOOP
					update sch_sisauc.gab set estado = 'APRESENTADA' where id = r.id and
						(select count(*) from sch_sisauc.item_gab where id_gab = r.id ) = 
						(select count(*) from sch_sisauc.item_gab where id_gab = r.id and estado in ('NAO_REALIZADO','AGUARDANDO_AUDITORIA'));
				
					update sch_sisauc.gab set estado = 'AUDITADA' where id = r.id and
						(select count(*) from sch_sisauc.item_gab where id_gab = r.id ) = 
						(select count(*) from sch_sisauc.item_gab where id_gab = r.id and estado in ('CONFORME','NAO_REALIZADO'));
				
					update sch_sisauc.gab set estado = 'EM_AUDITORIA' where id = r.id and
						(select count(*) from sch_sisauc.item_gab where id_gab = r.id and estado in ('AUDITORIA_INICIADA', 'NAO_CONFORME', 'CONFORME')) > 0 and
						(select count(*) from sch_sisauc.item_gab where id_gab = r.id ) >
						(select count(*) from sch_sisauc.item_gab where id_gab = r.id and estado in ('NAO_REALIZADO','CONFORME'));
			
			        RETURN NEXT r.id; -- return current row of SELECT
			    END LOOP;
			    RETURN;
			END
			$BODY$
			LANGUAGE plpgsql;
			
			SELECT * FROM atualizarGABs();
			
			DROP FUNCTION IF EXISTS atualizarGABs();
