package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_CAIXA_ORGANIZACAO database table.
 * 
 */
@Entity
@Table(name="SIG.T_CAIXA_ORGANIZACAO")
public class CaixaOrganizacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_CAIXA")
	private String cdCaixa;

	//bi-directional one-to-one association to CaixaPagamento
	@OneToOne
	@JoinColumn(name="CD_CAIXA")
	private CaixaPagamento TCaixaPagamento;

	//bi-directional many-to-one association to Organizacao
	@ManyToOne
	@JoinColumn(name="CD_ORG")
	private Organizacao TOrganizacao;

	public CaixaOrganizacao() {
	}

	public String getCdCaixa() {
		return this.cdCaixa;
	}

	public void setCdCaixa(String cdCaixa) {
		this.cdCaixa = cdCaixa;
	}

	public CaixaPagamento getTCaixaPagamento() {
		return this.TCaixaPagamento;
	}

	public void setTCaixaPagamento(CaixaPagamento TCaixaPagamento) {
		this.TCaixaPagamento = TCaixaPagamento;
	}

	public Organizacao getTOrganizacao() {
		return this.TOrganizacao;
	}

	public void setTOrganizacao(Organizacao TOrganizacao) {
		this.TOrganizacao = TOrganizacao;
	}

}