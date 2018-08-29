package br.ccasj.sisauc.auditoriaprospectiva.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.LocalInternacao;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoRessarcimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "auditoria_prospectiva_ressarcimento", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false) )
public class AuditoriaProspectivaRessarcimento extends AuditoriaProspectivaBase {

	private static final long serialVersionUID = 8120946103940540774L;

	private static final String SEQ_NAME = "auditoria_prospectiva_ressarcimento_id_seq";

	private SolicitacaoRessarcimento solicitacao;
	private boolean internacao;
	private LocalInternacao localInternacao;
	private Set<RespostaRessarcimentoAuditoria> procedimentos = new HashSet<RespostaRessarcimentoAuditoria>();
	
	
	public AuditoriaProspectivaRessarcimento() {
		super();
	}
	
	public AuditoriaProspectivaRessarcimento(Integer id) {
		super(id);
	}
		
	public AuditoriaProspectivaRessarcimento(Integer id, EstadoAuditoriaProspectiva estado, Date dataFinalAuditoria,
			Integer idSolicitacao, String numero,
			Integer idResposta, String justificativa, Boolean aprovado, String observacaoARE,
			Integer idProcedimento, Tabela tabela, String codigoProcedimento, String descricaoProcedimento,
			Integer idEspecialidade, String siglaEspecialidade){
		this.id = id;
		this.estado = estado;
		this.dataFinalAuditoria = dataFinalAuditoria;
		this.solicitacao = new SolicitacaoRessarcimento(idSolicitacao, numero);
		RespostaRessarcimentoAuditoria resposta = new RespostaRessarcimentoAuditoria();
		resposta.setId(idResposta);
		resposta.setJustificativa(justificativa);
		resposta.setAprovado(aprovado);
		resposta.setObservacaoARE(observacaoARE);
		resposta.setProcedimento(ProcedimentoBase.criarProcedimento(tabela, codigoProcedimento, descricaoProcedimento));
		resposta.getProcedimento().setId(idProcedimento);
		resposta.setEspecialidade(new Especialidade(idEspecialidade));
		resposta.getEspecialidade().setSigla(siglaEspecialidade);
		this.procedimentos = new HashSet<RespostaRessarcimentoAuditoria>();
		this.procedimentos.add(resposta);
	}
	
	public AuditoriaProspectivaRessarcimento(Integer id, EstadoAuditoriaProspectiva estado, Date dataFinalAuditoria,
			Integer idSolicitacao, String numero,
			Integer idResposta, String justificativa, Boolean aprovado,
			Integer idProcedimento, Tabela tabela, String codigoProcedimento, String descricaoProcedimento,
			Integer idEspecialidade, String siglaEspecialidade, ItemAR itemAR){
		this.id = id;
		this.estado = estado;
		this.dataFinalAuditoria = dataFinalAuditoria;
		this.solicitacao = new SolicitacaoRessarcimento(idSolicitacao, numero);
		RespostaRessarcimentoAuditoria resposta = new RespostaRessarcimentoAuditoria();
		resposta.setId(idResposta);
		resposta.setJustificativa(justificativa);
		resposta.setAprovado(aprovado);
		resposta.setProcedimento(ProcedimentoBase.criarProcedimento(tabela, codigoProcedimento, descricaoProcedimento));
		resposta.getProcedimento().setId(idProcedimento);
		resposta.setEspecialidade(new Especialidade(idEspecialidade));
		resposta.getEspecialidade().setSigla(siglaEspecialidade);
		this.procedimentos = new HashSet<RespostaRessarcimentoAuditoria>();
		this.procedimentos.add(resposta);
		itemAR.getRespostaRessarcimentoAuditoria().setId(idResposta);
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "id_solicitacao_ressarcimento", nullable = false)
	public SolicitacaoRessarcimento getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoRessarcimento solicitacao) {
		this.solicitacao = solicitacao;
	}

	@Column
	public boolean isInternacao() {
		return internacao;
	}

	public void setInternacao(boolean internacao) {
		this.internacao = internacao;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "local_internacao")
	public LocalInternacao getLocalInternacao() {
		return localInternacao;
	}

	public void setLocalInternacao(LocalInternacao localInternacao) {
		this.localInternacao = localInternacao;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_auditoria_prospectiva", nullable = false)
	public Set<RespostaRessarcimentoAuditoria> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(Set<RespostaRessarcimentoAuditoria> procedimentos) {
		this.procedimentos = procedimentos;
	}
}
