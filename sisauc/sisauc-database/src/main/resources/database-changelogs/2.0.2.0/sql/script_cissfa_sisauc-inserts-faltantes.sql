-- INSERTS faltantes da tabela procedimento CISSFA

-- segundo o Cel Aguiar da DIRSA, é para desconsiderar pois não existe na CBHPM
--insert into sch_sisauc.procedimento_base values ((select nextval('sch_sisauc.procedimento_base_id_seq')),'20104057','Cauterização química vesical','CISSFA');
insert into sch_sisauc.procedimento_base values ((select nextval('sch_sisauc.procedimento_base_id_seq')),'40404030','Antigenemia para diagnóstico de CMV pós transplante','CISSFA');
insert into sch_sisauc.procedimento_base values ((select nextval('sch_sisauc.procedimento_base_id_seq')),'40503151','Análise de DNA por MLPA, por sonda de DNA utilizada, por amostra','CISSFA');

-- segundo o Cel Aguiar da DIRSA, é para desconsiderar pois não existe na CBHPM
--insert into sch_sisauc.procedimento_cissfa values ((select nextval('sch_sisauc.procedimento_cissfa_id_seq')),null,null,226);
insert into sch_sisauc.procedimento_cissfa values ((select nextval('sch_sisauc.procedimento_cissfa_id_seq')),806,null,391);
insert into sch_sisauc.procedimento_cissfa values ((select nextval('sch_sisauc.procedimento_cissfa_id_seq')),411,null,394);