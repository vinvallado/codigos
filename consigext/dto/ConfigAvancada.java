package br.mil.fab.consigext.dto;

import java.math.BigDecimal;
import java.util.List;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.Servico;
import br.mil.fab.consigext.entity.ServicoConsig;
import br.mil.fab.consigext.entity.ServidorConsig;

public class ConfigAvancada {


	//Consignacao
	private Consignacao consignacao;
	
	//Valores
	private Long nrCarencia = 0L;
	
	private Long nrPrestacoes;
	private Long nrPrestResestantes;
	
	private BigDecimal vlCet;
	private BigDecimal vlLiquidoLiberado;
	private BigDecimal vlPrestacao;
	
	private String dsIdentificador;
	
	private ServidorConsig servidorConsig;
	private EntidadeConsig entidadeConsig;
	private Servico servico;
	private ServicoConsig servicoConsig;
	
	private String matricula;
	
	
	//Configurações Avançadas
	private List<String> parametros;

	
	public ConfigAvancada() {}
	
	public ConfigAvancada(Consignacao consignacao, Long nrCarencia, Long nrPrestacoes, Long nrPrestResestantes,
			BigDecimal vlCet, BigDecimal vlLiquidoLiberado, BigDecimal vlPrestacao, String dsIdentificador,
			ServidorConsig servidorConsig, EntidadeConsig entidadeConsig, Servico servico, String matricula,
			List<String> parametros) {
		super();
		this.consignacao = consignacao;
		this.nrCarencia = nrCarencia;
		this.nrPrestacoes = nrPrestacoes;
		this.nrPrestResestantes = nrPrestResestantes;
		this.vlCet = vlCet;
		this.vlLiquidoLiberado = vlLiquidoLiberado;
		this.vlPrestacao = vlPrestacao;
		this.dsIdentificador = dsIdentificador;
		this.servidorConsig = servidorConsig;
		this.entidadeConsig = entidadeConsig;
		this.servico = servico;
		this.matricula = matricula;
		this.parametros = parametros;
	}
	

	public ConfigAvancada(Consignacao consignacao, Long nrCarencia, Long nrPrestacoes, Long nrPrestResestantes,
			BigDecimal vlCet, BigDecimal vlLiquidoLiberado, BigDecimal vlPrestacao, String dsIdentificador,
			ServidorConsig servidorConsig, EntidadeConsig entidadeConsig, ServicoConsig servicoConsig,
			List<String> parametros) {
		super();
		this.consignacao = consignacao;
		this.nrCarencia = nrCarencia;
		this.nrPrestacoes = nrPrestacoes;
		this.nrPrestResestantes = nrPrestResestantes;
		this.vlCet = vlCet;
		this.vlLiquidoLiberado = vlLiquidoLiberado;
		this.vlPrestacao = vlPrestacao;
		this.dsIdentificador = dsIdentificador;
		this.servidorConsig = servidorConsig;
		this.entidadeConsig = entidadeConsig;
		this.servicoConsig = servicoConsig;
		this.parametros = parametros;
	}

	public ConfigAvancada(Consignacao consignacao, Long nrCarencia, Long nrPrestacoes, Long nrPrestResestantes,
			BigDecimal vlCet, BigDecimal vlLiquidoLiberado, BigDecimal vlPrestacao, String dsIdentificador,
			ServidorConsig servidorConsig, EntidadeConsig entidadeConsig, ServicoConsig servicoConsig,
			String matricula, List<String> parametros) {
		super();
		this.consignacao = consignacao;
		this.nrCarencia = nrCarencia;
		this.nrPrestacoes = nrPrestacoes;
		this.nrPrestResestantes = nrPrestResestantes;
		this.vlCet = vlCet;
		this.vlLiquidoLiberado = vlLiquidoLiberado;
		this.vlPrestacao = vlPrestacao;
		this.dsIdentificador = dsIdentificador;
		this.servidorConsig = servidorConsig;
		this.entidadeConsig = entidadeConsig;
		this.servicoConsig = servicoConsig;
		this.matricula = matricula;
		this.parametros = parametros;
	}
	
	public Consignacao getConsignacao() {
		return consignacao;
	}

	public void setConsignacao(Consignacao consignacao) {
		this.consignacao = consignacao;
	}

	public Long getNrCarencia() {
		return nrCarencia;
	}

	public void setNrCarencia(Long nrCarencia) {
		this.nrCarencia = nrCarencia;
	}

	public Long getNrPrestacoes() {
		return nrPrestacoes;
	}

	public void setNrPrestacoes(Long nrPrestacoes) {
		this.nrPrestacoes = nrPrestacoes;
	}

	public Long getNrPrestResestantes() {
		return nrPrestResestantes;
	}

	public void setNrPrestResestantes(Long nrPrestResestantes) {
		this.nrPrestResestantes = nrPrestResestantes;
	}

	public BigDecimal getVlCet() {
		return vlCet;
	}

	public void setVlCet(BigDecimal vlCet) {
		this.vlCet = vlCet;
	}

	public BigDecimal getVlLiquidoLiberado() {
		return vlLiquidoLiberado;
	}

	public void setVlLiquidoLiberado(BigDecimal vlLiquidoLiberado) {
		this.vlLiquidoLiberado = vlLiquidoLiberado;
	}

	public BigDecimal getVlPrestacao() {
		return vlPrestacao;
	}

	public void setVlPrestacao(BigDecimal vlPrestacao) {
		this.vlPrestacao = vlPrestacao;
	}

	public String getDsIdentificador() {
		return dsIdentificador;
	}

	public void setDsIdentificador(String dsIdentificador) {
		this.dsIdentificador = dsIdentificador;
	}

	public ServidorConsig getServidorConsig() {
		return servidorConsig;
	}

	public void setServidorConsig(ServidorConsig servidorConsig) {
		this.servidorConsig = servidorConsig;
	}

	public EntidadeConsig getEntidadeConsig() {
		return entidadeConsig;
	}

	public void setEntidadeConsig(EntidadeConsig entidadeConsig) {
		this.entidadeConsig = entidadeConsig;
	}

	public ServicoConsig getServicoConsig() {
		return servicoConsig;
	}

	public void setServicoConsig(ServicoConsig servicoConsig) {
		this.servicoConsig = servicoConsig;
	}
	

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public List<String> getParametros() {
		return parametros;
	}

	public void setParametros(List<String> parametros) {
		this.parametros = parametros;
	}

	public Servico getServico() {
		return servico;
	}
	
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	
}
