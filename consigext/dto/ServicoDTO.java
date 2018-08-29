package br.mil.fab.consigext.dto;

import java.io.Serializable;
import java.util.List;

import br.mil.fab.consigext.entity.ParametroServico;


public class ServicoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	String id = "0";
	String dsServico;
	String cdServico;
	long idNaturezaServico;
	List<ParametroServico> parametrosServico;
	long nrPrioridade;
	
	
	public ServicoDTO() {
		super();
	}
	
	public ServicoDTO(String id, String dsServico, String cdServico, long idNaturezaServico, long nrPrioridade, List<ParametroServico> parametrosServico) {
		super();
		this.dsServico = dsServico;
		this.cdServico = cdServico;
		this.idNaturezaServico = idNaturezaServico;
		this.nrPrioridade = nrPrioridade;
		this.parametrosServico = parametrosServico;
		this.id = id;
	}

	public ServicoDTO(String dsServico, String cdServico, long idNaturezaServico, long nrPrioridade, List<ParametroServico> parametrosServico) {
		super();
		this.dsServico = dsServico;
		this.cdServico = cdServico;
		this.idNaturezaServico = idNaturezaServico;
		this.nrPrioridade = nrPrioridade;
		this.parametrosServico = parametrosServico;
	}

	public String getDsServico() {
		return dsServico;
	}


	public void setDsServico(String dsServico) {
		this.dsServico = dsServico;
	}


	public String getCdServico() {
		return cdServico;
	}


	public void setCdServico(String cdServico) {
		this.cdServico = cdServico;
	}


	public long getIdNaturezaServico() {
		return idNaturezaServico;
	}


	public void setIdNaturezaServico(long idNaturezaServico) {
		this.idNaturezaServico = idNaturezaServico;
	}


	public long getNrPrioridade() {
		return nrPrioridade;
	}

	public void setNrPrioridade(long nrPrioridade) {
		this.nrPrioridade = nrPrioridade;
	}

	public List<ParametroServico> getParametrosServico() {
		return parametrosServico;
	}

	public void setParametrosServico(List<ParametroServico> parametrosServico) {
		this.parametrosServico = parametrosServico;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
