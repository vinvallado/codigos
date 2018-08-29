package br.mil.fab.consigext.entity;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="ServicoComboProjection", types= Servico.class)
public interface ServicoComboProjection {
	
	long getId();
	String getDsServico();
}
