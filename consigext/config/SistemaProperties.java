package br.mil.fab.consigext.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "sistema")
@Configuration("sistemaProperties")
public class SistemaProperties {
	
	/**
	 * CD_ORG da OM Gestora do Sistema
	 */
	String gestora;

	public String getGestora() {
		return gestora;
	}

	public void setGestora(String gestora) {
		this.gestora = gestora;
	}
	
}
