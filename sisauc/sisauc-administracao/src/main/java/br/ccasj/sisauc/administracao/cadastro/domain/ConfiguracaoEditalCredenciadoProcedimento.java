package br.ccasj.sisauc.administracao.cadastro.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.Cidade;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "configuracao_edital_credenciado_procedimento", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class ConfiguracaoEditalCredenciadoProcedimento extends EntidadeGenerica {

	private static final long serialVersionUID = -5826106141899532258L;

	private static final String SEQ_NAME = "configuracao_edital_credenciado_procedimento_id_seq";
	
	private boolean ativo = true;
	private Date dataInsercao;
	private Date dataExclusao;
	private Double valor;
	private TipoCobrancaCredenciadoProcedimento tipo;
	private ConfiguracaoEditalCredenciado configuracaoEditalCredenciado;
	private ProcedimentoBase procedimento;
	private Especialidade especialidade;

	public ConfiguracaoEditalCredenciadoProcedimento() {
		super();
	}
	
	public ConfiguracaoEditalCredenciadoProcedimento(Integer id) {
		super(id);
	}

	public ConfiguracaoEditalCredenciadoProcedimento(Integer id, boolean ativo, Double valor, TipoCobrancaCredenciadoProcedimento tipo, String nomeFantasiaCredenciado, Tabela tabela, String codigoProcedimento, String descricaoProcedimento, String nomeEspecialidade, String siglaEspecialidade){
		super(id);
		this.ativo = ativo;
		this.valor = valor;
		this.tipo = tipo;
		this.configuracaoEditalCredenciado = new ConfiguracaoEditalCredenciado();
		this.configuracaoEditalCredenciado.setCredenciado(new Credenciado(nomeFantasiaCredenciado));
		this.procedimento = ProcedimentoBase.criarProcedimento(tabela, codigoProcedimento, descricaoProcedimento);
		this.especialidade = new Especialidade(nomeEspecialidade, siglaEspecialidade);
	}
	
	public ConfiguracaoEditalCredenciadoProcedimento(Integer id, boolean ativo, Double valor, TipoCobrancaCredenciadoProcedimento tipo, String nomeFantasiaCredenciado, Cidade cidadeCredenciado, Tabela tabela, String codigoProcedimento, String descricaoProcedimento, String nomeEspecialidade, String siglaEspecialidade){
		super(id);
		this.ativo = ativo;
		this.valor = valor;
		this.tipo = tipo;
		this.configuracaoEditalCredenciado = new ConfiguracaoEditalCredenciado();
		this.configuracaoEditalCredenciado.setCredenciado(new Credenciado(nomeFantasiaCredenciado, cidadeCredenciado));
		this.procedimento = ProcedimentoBase.criarProcedimento(tabela, codigoProcedimento, descricaoProcedimento);
		this.especialidade = new Especialidade(nomeEspecialidade, siglaEspecialidade);
	}
	
	public ConfiguracaoEditalCredenciadoProcedimento(Integer id, boolean ativo, Double valor, TipoCobrancaCredenciadoProcedimento tipo, String nomeFantasiaCredenciado, Cidade cidadeCredenciado, String bairro, Tabela tabela, String codigoProcedimento, String descricaoProcedimento, String nomeEspecialidade, String siglaEspecialidade){
		super(id);
		this.ativo = ativo;
		this.valor = valor;
		this.tipo = tipo;
		this.configuracaoEditalCredenciado = new ConfiguracaoEditalCredenciado();
		this.configuracaoEditalCredenciado.setCredenciado(new Credenciado(nomeFantasiaCredenciado, cidadeCredenciado, bairro));
		this.procedimento = ProcedimentoBase.criarProcedimento(tabela, codigoProcedimento, descricaoProcedimento);
		this.especialidade = new Especialidade(nomeEspecialidade, siglaEspecialidade);
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_insercao")
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_exclusao")
	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "id_tipo_cobranca")
	public TipoCobrancaCredenciadoProcedimento getTipo() {
		return tipo;
	}

	public void setTipo(TipoCobrancaCredenciadoProcedimento tipo) {
		this.tipo = tipo;
	}

	@ManyToOne
	@JoinColumn(name = "id_configuracao_edital_credenciado")
	public ConfiguracaoEditalCredenciado getConfiguracaoEditalCredenciado() {
		return configuracaoEditalCredenciado;
	}

	public void setConfiguracaoEditalCredenciado(ConfiguracaoEditalCredenciado configuracaoEditalCredenciado) {
		this.configuracaoEditalCredenciado = configuracaoEditalCredenciado;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_procedimento")
	public ProcedimentoBase getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(ProcedimentoBase procedimento) {
		this.procedimento = procedimento;
	}

	@ManyToOne
	@JoinColumn(name = "id_especialidade")
	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	
	
}
