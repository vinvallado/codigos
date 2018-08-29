package br.ccasj.sisauc.auditoriaprospectiva.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.Usuario;

@Entity
@Table(name = "auditoria_prospectiva", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class AuditoriaProspectiva extends AuditoriaProspectivaBase {

	private static final long serialVersionUID = 132255549205282940L;

	private static final String SEQ_NAME = "auditoria_prospectiva_id_seq";

	private SolicitacaoProcedimento solicitacao;
	private Set<RespostaProcedimentoAuditoria> procedimentos = new HashSet<RespostaProcedimentoAuditoria>();
	
	public AuditoriaProspectiva() {
		super();
	}

	public AuditoriaProspectiva(Integer id) {
		super(id);
	}
	
	public AuditoriaProspectiva(Integer id, Date dataFinalAuditoria, EstadoAuditoriaProspectiva estado, String numero) {
		super(id);
		this.dataFinalAuditoria = dataFinalAuditoria;
		this.auditor = new Usuario();
		this.estado = estado;
		this.solicitacao = new SolicitacaoProcedimento();
		this.solicitacao.setNumero(numero);
	}
	
	public AuditoriaProspectiva(Integer idAuditoria, EstadoAuditoriaProspectiva estadoAuditoria, Date dataFinalAuditoria, Integer idSolicitacao, String numeroSolicitacao, 
			Integer idResposta, String justificativaResposta, boolean aprovado, Integer idCredenciado, 
			Integer idProcedimento, Tabela tabela, String codigoProcedimento, String descricaoProcedimento, 
			Integer idEspecialidade, String siglaEspecialidade){
		this.id = idAuditoria;
		this.estado = estadoAuditoria;
		this.dataFinalAuditoria = dataFinalAuditoria;
		this.solicitacao = new SolicitacaoProcedimento();
		this.solicitacao.setId(idSolicitacao);
		this.solicitacao.setNumero(numeroSolicitacao);
		
		RespostaProcedimentoAuditoria resposta = new RespostaProcedimentoAuditoria();
		resposta.setId(idResposta);
		resposta.setJustificativa(justificativaResposta);
		resposta.setAprovado(aprovado);
		resposta.setCredenciado(new ConfiguracaoEditalCredenciadoProcedimento());
		resposta.getCredenciado().setId(idCredenciado);
		resposta.getCredenciado().setProcedimento(ProcedimentoBase.criarProcedimento(tabela));
		resposta.getCredenciado().getProcedimento().setId(idProcedimento);
		resposta.getCredenciado().getProcedimento().setCodigo(codigoProcedimento);
		resposta.getCredenciado().getProcedimento().setDescricao(descricaoProcedimento);
		resposta.getCredenciado().setEspecialidade(new Especialidade());
		resposta.getCredenciado().getEspecialidade().setId(idEspecialidade);
		resposta.getCredenciado().getEspecialidade().setSigla(siglaEspecialidade);
		
		this.procedimentos = new HashSet<RespostaProcedimentoAuditoria>();
		this.procedimentos.add(resposta);
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_solicitacao_procedimento")
	public SolicitacaoProcedimento getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(SolicitacaoProcedimento solicitacao) {
		this.solicitacao = solicitacao;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "auditoria_resultado_procedimento_auditoria", schema = EntidadeGenerica.SCHEMA_SISAUC, 
		joinColumns =  @JoinColumn(name = "id_auditoria_prospectiva"),
		inverseJoinColumns = @JoinColumn(name = "id_resposta_procedimento_auditoria")
	)
	public Set<RespostaProcedimentoAuditoria> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(Set<RespostaProcedimentoAuditoria> procedimentos) {
		this.procedimentos = procedimentos;
	}


}
