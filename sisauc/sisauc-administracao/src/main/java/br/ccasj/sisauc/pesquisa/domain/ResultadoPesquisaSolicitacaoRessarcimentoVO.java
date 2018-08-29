package br.ccasj.sisauc.pesquisa.domain;

import java.util.Date;

import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.EstadoAuditoriaProspectiva;
import br.ccasj.sisauc.framework.domain.Divisao;

public class ResultadoPesquisaSolicitacaoRessarcimentoVO {

	private SolicitacaoRessarcimento solicitacao;
	private EstadoAuditoriaProspectiva estadoAuditoriaProspectiva;

	public ResultadoPesquisaSolicitacaoRessarcimentoVO() {
		super();
	}

	public ResultadoPesquisaSolicitacaoRessarcimentoVO(String numero, Divisao divisao, String nomeBeneficiario, String saramBeneficiario, boolean titular, Date dataNascimentoBeneficiario, String omBeneficiario, String omTitular,
			EstadoSolicitacaoProcedimento estadoSolicitacaoProcedimento, Date dataSolicitacaoSistema, LocalInternacao localInternacao, String siglaOM, EstadoAuditoriaProspectiva estadoAuditoria){
		this.solicitacao = new SolicitacaoRessarcimento(numero, divisao, nomeBeneficiario, saramBeneficiario, titular, dataNascimentoBeneficiario, omBeneficiario, omTitular, estadoSolicitacaoProcedimento, dataSolicitacaoSistema, localInternacao, siglaOM);
		this.estadoAuditoriaProspectiva = estadoAuditoria;
	}
	
	public SolicitacaoRessarcimento getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoRessarcimento solicitacao) {
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
