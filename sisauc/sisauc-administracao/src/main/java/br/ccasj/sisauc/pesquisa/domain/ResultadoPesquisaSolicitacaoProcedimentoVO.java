package br.ccasj.sisauc.pesquisa.domain;

import java.util.Date;

import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.framework.domain.Divisao;

public class ResultadoPesquisaSolicitacaoProcedimentoVO {

	private SolicitacaoProcedimento solicitacao;
	private EstadoAuditoriaProspectiva estadoAuditoriaProspectiva;

	public ResultadoPesquisaSolicitacaoProcedimentoVO() {
		super();
	}

	public ResultadoPesquisaSolicitacaoProcedimentoVO(String numero, String nomeBeneficiario, String saramBeneficiario, boolean urgente, Divisao divisao, boolean titular, Date dataNascimentoBeneficiario, String omBeneficiario, String omTitular,
			EstadoSolicitacaoProcedimento estadoSolicitacaoProcedimento, Date dataSolicitacaoSistema, String nomeProfissional, LocalInternacao localInternacao, String siglaOM, EstadoAuditoriaProspectiva estadoAuditoria){
		this.solicitacao = new SolicitacaoProcedimento(numero, nomeBeneficiario, saramBeneficiario, urgente, divisao, titular, dataNascimentoBeneficiario, omBeneficiario, omTitular, estadoSolicitacaoProcedimento, dataSolicitacaoSistema, nomeProfissional, localInternacao, siglaOM);
		this.estadoAuditoriaProspectiva = estadoAuditoria;
	}
	
	public SolicitacaoProcedimento getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoProcedimento solicitacao) {
		this.solicitacao = solicitacao;
	}

	public EstadoAuditoriaProspectiva getEstadoAuditoriaProspectiva() {
		return estadoAuditoriaProspectiva;
	}

	public void setEstadoAuditoriaProspectiva(
			EstadoAuditoriaProspectiva estadoAuditoriaProspectiva) {
		this.estadoAuditoriaProspectiva = estadoAuditoriaProspectiva;
	}

}
