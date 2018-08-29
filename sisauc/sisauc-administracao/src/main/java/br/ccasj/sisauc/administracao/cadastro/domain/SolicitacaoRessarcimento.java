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
import javax.persistence.OneToOne;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.emissaoar.domain.ApresentacaoAutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.domain.Divisao;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

@Entity
@DiscriminatorValue("RESSARCIMENTO")
public class SolicitacaoRessarcimento extends SolicitacaoMedica {

	private static final long serialVersionUID = -5129812624903942866L;

	private boolean naoEletiva = false;
	private ApresentacaoAutorizacaoRessarcimento apresentacao;
	private Set<ProcedimentoPedidoSolicitacaoRessarcimento> procedimentos = new HashSet<ProcedimentoPedidoSolicitacaoRessarcimento>();
	private Medico medicoSolicitante;
	
	public SolicitacaoRessarcimento() {}
	
	public SolicitacaoRessarcimento(Integer id){
		super(id);
	}
	
	public SolicitacaoRessarcimento(Integer id, String numero) {
		super(id, numero);
	}

	public SolicitacaoRessarcimento(Integer id, Divisao divisao, String numero, Date dataSolicitacaoSistema, Date dataEnvioAuditoria, 
			Date dataInsercaoSistema, Date dataUltimaAlteracaoEstado, EstadoSolicitacaoProcedimento estado, 
			boolean naoEletiva,  String saramBeneficiario, String nomeBeneficiario, String nomeMedicoSolicitante){
		super(id, divisao,numero, dataSolicitacaoSistema, dataEnvioAuditoria,dataInsercaoSistema,dataUltimaAlteracaoEstado, estado,	saramBeneficiario, nomeBeneficiario);
		this.naoEletiva = naoEletiva;
		this.medicoSolicitante = new Medico(null, nomeMedicoSolicitante);
	}

	//PESQUISA DE SOLICITAÇÕES DE RESSARCIMENTOS
	public SolicitacaoRessarcimento(String numero, Divisao divisao, String nomeBeneficiario, String saramBeneficiario, boolean titular, Date dataNascimentoBeneficiario, String omBeneficiario, String omTitular,
			EstadoSolicitacaoProcedimento estadoSolicitacaoProcedimento, Date dataSolicitacaoSistema, LocalInternacao localInternacao, String siglaOM) {
		super();
		this.numero = numero;
		this.divisao = divisao;
		this.estado = estadoSolicitacaoProcedimento;
		this.dataSolicitacaoSistema = dataSolicitacaoSistema;
		this.localInternacao = localInternacao;
		
		this.organizacaoMilitarSolicitante = new OrganizacaoMilitar();
		this.organizacaoMilitarSolicitante.setSigla(siglaOM);
		
		
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

	@Column(name="nao_eletiva", nullable=false)
	public boolean isNaoEletiva() {
		return naoEletiva;
	}

	public void setNaoEletiva(boolean naoEletiva) {
		this.naoEletiva = naoEletiva;
	}

	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_apresentacao")
	public ApresentacaoAutorizacaoRessarcimento getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(ApresentacaoAutorizacaoRessarcimento apresentacao) {
		this.apresentacao = apresentacao;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "solicitacao_pedidos_procedimento_ressarcimento", schema = EntidadeGenerica.SCHEMA_SISAUC, 
		joinColumns =  @JoinColumn(name = "id_solicitacao"),
		inverseJoinColumns = @JoinColumn(name = "id_procedimento_pedido_solicitacao_ressarcimento")
	)
	public Set<ProcedimentoPedidoSolicitacaoRessarcimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(Set<ProcedimentoPedidoSolicitacaoRessarcimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	@ManyToOne
	@JoinColumn(name = "id_medico")
	public Medico getMedicoSolicitante() {
		return medicoSolicitante;
	}

	public void setMedicoSolicitante(Medico medicoSolicitante) {
		this.medicoSolicitante = medicoSolicitante;
	}

}
