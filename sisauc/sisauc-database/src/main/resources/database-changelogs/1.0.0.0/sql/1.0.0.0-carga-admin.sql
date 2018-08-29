insert into sch_sisauc.organizacao_militar(id, sigla, nome, ativo, id_cidade, tipo_om) 
	values(-1, 'SISAUC','Sistema de Saúde Complementar', TRUE, 1, 'REGIONAL');
insert into sch_sisauc.perfil(id, nome, ativo) values('PERFIL_ADMINISTRADOR_SISTEMA', 'Administrador do Sistema', TRUE);
insert into sch_sisauc.perfil(id, nome, ativo) values('PERFIL_ATENDENTE_FUNSA', 'Atendente FUNSA', TRUE);
insert into sch_sisauc.perfil(id, nome, ativo) values('PERFIL_AUDITOR_FUNSA', 'Auditor FUNSA', TRUE);
insert into sch_sisauc.perfil(id, nome, ativo) values('PERFIL_AUDITOR_FINANCEIRO', 'Auditor Financeiro', TRUE);
insert into sch_sisauc.perfil(id, nome, ativo) values('PERFIL_CHEFE_FUNSA', 'Chefe FUNSA', TRUE);
insert into sch_sisauc.perfil(id, nome, ativo) values('PERFIL_DIRETOR', 'Diretor', TRUE);

insert into sch_sisauc.usuario(nome, email, ativo, login, senha, id_organizacao_militar) 
	values('Administrador','admin@admin.intraer',TRUE,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 
	-1);
insert into sch_sisauc.usuario_perfil values 
	((select max(id) from sch_sisauc.usuario) , 'PERFIL_ADMINISTRADOR_SISTEMA');

insert into sch_sisauc.tipo_cobranca_credenciado_procedimento(id) values('TAXA');
insert into sch_sisauc.tipo_cobranca_credenciado_procedimento(id) values('PACOTE');
INSERT INTO sch_sisauc.configuracao_ws_sigameh (id, url_wsdl, url_servico, nome_servico, usuario, senha) 
	VALUES (1, 'http://192.168.172.92:8080/sisauc-sigameh-ws/services/sigamehWS?wsdl', 'http://impl.service.webservices.sisauc.ccasj.br/', 'SigamehWSImplService', 'ccasj', 'Password01');
