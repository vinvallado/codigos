insert into sch_sisauc.organizacao_militar (sigla, nome, ativo, tipo_om, regional_sistema) values ('GAP AN','GRUPO DE APOIO DE ANAPOLIS','t','REGIONAL', false);

update sch_sisauc.ar set id_organizacao_militar = (select max(id) from sch_sisauc.organizacao_militar) where id_organizacao_militar = 16;
update sch_sisauc.gab set id_organizacao_militar = (select max(id) from sch_sisauc.organizacao_militar) where id_organizacao_militar = 16;
update sch_sisauc.lote set id_organizacao_militar = (select max(id) from sch_sisauc.organizacao_militar) where id_organizacao_militar = 16;
update sch_sisauc.medico set id_organizacao_militar = (select max(id) from sch_sisauc.organizacao_militar) where id_organizacao_militar = 16;
update sch_sisauc.solicitacao set id_organizacao_militar = (select max(id) from sch_sisauc.organizacao_militar) where id_organizacao_militar = 16;
update sch_sisauc.usuario set id_organizacao_militar = (select max(id) from sch_sisauc.organizacao_militar) where id_organizacao_militar = 16;

update sch_sisauc.organizacao_militar set tipo_om = 'SUBORDINADA', regional_sistema = false  where id = 16;

update sch_sisauc.organizacao_militar set id_regional = (select max(id) from sch_sisauc.organizacao_militar) where id_regional = 16;

update sch_sisauc.organizacao_militar set regional_sistema = true where (select count(*) from sch_sisauc.organizacao_militar where regional_sistema is true) = 0 and id = (select max(id) from sch_sisauc.organizacao_militar);