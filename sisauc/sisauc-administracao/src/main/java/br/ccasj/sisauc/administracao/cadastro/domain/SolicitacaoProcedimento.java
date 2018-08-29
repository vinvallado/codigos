package br.ccasj.sisauc.administracao.cadastro.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

@Entity
@DiscriminatorValue("GAB")
public class SolicitacaoProcedimento extends SolicitacaoMedica {

	private static final long serialVersionUID = 4692053285770761647L;

	/*	
	 * criado no início do projeto e atualmente 
	 * usado como motivo de isenção (EspecificacaoIsencaoCobranca.java)
	 */	
	@Deprecated
	private boolean inspecaoSaude = false;

	private boolean urgente = false;
	private Medico medicoSolicitante;
	private Set<ProcedimentoPedidoSolicitacao> procedimentos = new HashSet<ProcedimentoPedidoSolicitacao>();
	
	public SolicitacaoProcedimento() {
		super();
	}

	public SolicitacaoProcedimento(Integer id) {
		super(id);
	}
	
	public SolicitacaoProcedimento(Integer id, Date dataSolicitacaoSistema, Date dataEnvioAuditoria, Date dataInsercaoSistema,
			Date dataUltimaAlteracaoEstado, EstadoSolicitacaoProcedimento estado, String nomeMedicoSolicitante, 
			String saramBeneficiario, String nomeBeneficiario) {
		super(id, dataSolicitacaoSistema, dataEnvioAuditoria,dataInsercaoSistema,dataUltimaAlteracaoEstado, estado,	saramBeneficiario, nomeBeneficiario); 
		this.medicoSolicitante = new Medico(null, nomeMedicoSolicitante);
	}
	
	public SolicitacaoProcedimento(Integer id, Divisao divisao, String numero, Date dataSolicitacaoSistema, Date dataEnvioAuditoria, Date dataInsercaoSistema,
			Date dataUltimaAlteracaoEstado, EstadoSolicitacaoProcedimento estado, String nomeMedicoSolicitante, 
			String saramBeneficiario, String nomeBeneficiario) {
		super(id, divisao, numero, dataSolicitacaoSistema, dataEnvioAuditoria,dataInsercaoSistema,dataUltimaAlteracaoEstado, estado,	saramBeneficiario, nomeBeneficiario); 
		this.medicoSolicitante = new Medico(null, nomeMedicoSolicitante);
	}
	
	public SolicitacaoProcedimento(Integer id, Divisao divisao, String numero, Date dataSolicitacaoSistema, Date dataEnvioAuditoria, Date dataInsercaoSistema,
			Date dataUltimaAlteracaoEstado, EstadoSolicitacaoProcedimento estado, boolean urgente, String nomeMedicoSolicitante, 
			String saramBeneficiario, String nomeBeneficiario) {
		super(id, divisao, numero, dataSolicitacaoSistema, dataEnvioAuditoria,dataInsercaoSistema,dataUltimaAlteracaoEstado, estado,	saramBeneficiario, nomeBeneficiario); 
		this.urgente = urgente;
		this.medicoSolicitante = new Medico(null, nomeMedicoSolicitante);
	}
	
	//TODO verificar nas pesquisas dos relatórios os impactos
	//PESQUISA DE SOLICITAÇÕES DE PROCEDIMENTOS
	public SolicitacaoProcedimento(String numero, String nomeBeneficiario, String saramBeneficiario, boolean urgente, Divisao divisao, boolean titular, Date dataNascimentoBeneficiario, String omBeneficiario, String omTitular,
			EstadoSolicitacaoProcedimento estadoSolicitacaoProcedimento, Date dataSolicitacaoSistema, String nomeProfissional, LocalInternacao localInternacao, String siglaOM) {
		super();
		this.numero = numero;
		this.urgente = urgente;
		this.divisao = divisao;
		this.estado = estadoSolicitacaoProcedimento;
		this.dataSolicitacaoSistema = dataSolicitacaoSistema;
		this.localInternacao = localInternacao;
		
		this.organizacaoMilitarSolicitante = new OrganizacaoMilitar();
		this.organizacaoMilitarSolicitante.setSigla(siglaOM);
		
		this.medicoSolicitante = new Medico();
		this.medicoSolicitante.setNome(nomeProfissional);

		this.beneficiario = new Beneficiario();
		this.beneficiario.setNome(nomeBeneficiario);
		this.beneficiario.setSaram(saramBeneficiario);
		this.beneficiario.setDataNascimento(dataNascimentoBeneficiario);
		
		this.beneficiario.setTitular(titular);
		if (beneficiario.isTitular()) {
			this.beneficiario.setOrganizacaoMilitar(new OrganizacaoMilitar(omBeneficiario));			
		} else {
			this.beneficiario.setBeneficiarioTitular(new Beneficiario());
			this.beneficiario.getBeneficiarioTitular().setOrganizacaoMilitar(new OrganizacaoMilitar(omTitular));
		}
		
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_medico")
	public Medico getMedicoSolicitante() {
		return medicoSolicitante;
	}

	public void setMedicoSolicitante(Medico medicoSolicitante) {
		this.medicoSolicitante = medicoSolicitante;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "solicitacao_pedidos_procedimento", schema = EntidadeGenerica.SCHEMA_SISAUC, 
		joinColumns =  @JoinColumn(name = "id_solicitacao"),
		inverseJoinColumns = @JoinColumn(name = "id_procedimento_pedido_solicitacao")
	)
	public Set<ProcedimentoPedidoSolicitacao> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(Set<ProcedimentoPedidoSolicitacao> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	@Column(name = "inspecao_saude")
	public boolean isInspecaoSaude() {
		return inspecaoSaude;
	}

	public void setInspecaoSaude(boolean inspecaoSaude) {
		this.inspecaoSaude = inspecaoSaude;
	}
	
}
