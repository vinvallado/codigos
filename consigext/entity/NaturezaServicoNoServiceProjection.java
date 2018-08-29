package br.mil.fab.consigext.entity;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="naturezaServicoNoService", types= {NaturezaServico.class})
public interface NaturezaServicoNoServiceProjection {
	
	long getId();
	String getSgNatureza();
}
