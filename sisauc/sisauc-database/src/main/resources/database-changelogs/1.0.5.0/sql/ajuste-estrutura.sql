--Apagar as constraints da tabela beneficiario:

alter table sch_sisauc.beneficiario drop constraint fk_beneficiario_beneficiario_titular;
alter table sch_sisauc.solicitacao drop constraint fk_solicitacao_beneficiario;
alter table sch_sisauc.gab drop constraint fk_gab_beneficiario;
alter table sch_sisauc.desconto_beneficiario drop constraint fk_desconto_beneficiario_beneficiario;
alter table sch_sisauc.beneficiario drop constraint pk_beneficiario ;

-- Ataulizar os titulares/dependentes para o novo padrão atribuindo o 00 ao final em todas as entidades referenciadas:

update sch_sisauc.beneficiario set id = id*100 where titular = true;
update sch_sisauc.beneficiario set id_beneficiario_titular = id_beneficiario_titular*100 where titular = false; 
update sch_sisauc.gab set id_beneficiario = id_beneficiario*100 where titular_beneficiario = true;
update sch_sisauc.solicitacao set id_beneficiario = id_beneficiario*100 where titular_beneficiario = true;
update sch_sisauc.desconto_beneficiario set id_beneficiario = id_beneficiario*100 where titular_beneficiario = true;

-- Recriar as constraints:

alter table sch_sisauc.beneficiario add constraint pk_beneficiario primary key (id);
	
alter table sch_sisauc.beneficiario add constraint fk_beneficiario_beneficiario_titular 
	foreign key (id_beneficiario_titular) references sch_sisauc.beneficiario (id);
	
alter table sch_sisauc.desconto_beneficiario add constraint fk_desconto_beneficiario_beneficiario
	foreign key (id_beneficiario) references sch_sisauc.beneficiario (id);

alter table sch_sisauc.gab add constraint fk_gab_beneficiario
	foreign key (id_beneficiario) references sch_sisauc.beneficiario (id);
	
alter table sch_sisauc.solicitacao add constraint fk_solicitacao_beneficiario
	foreign key (id_beneficiario) references sch_sisauc.beneficiario (id);


-- Apagar as colunas de tipo BOOLEAN anteriormente criadas:

alter table sch_sisauc.beneficiario drop column fk_beneficiario_titular;
alter table sch_sisauc.desconto_beneficiario drop column titular_beneficiario;
alter table sch_sisauc.gab drop column titular_beneficiario;
alter table sch_sisauc.solicitacao drop column titular_beneficiario;

