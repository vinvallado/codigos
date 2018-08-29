package br.ccasj.sisauc.administracao.historico.domain;

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

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoCobrancaCredenciadoProcedimento;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeHistoricoGenerica;

@Entity
@Table(name = "historico_valor_configuracao_edital_procedimento", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class HistoricoValorCadastroEditalConfiguracao extends EntidadeHistoricoGenerica {

	private static final long serialVersionUID = -1458856469298274777L;

	private static final String SEQ_NAME = "historico_valor_configuracao_edital_procedimento_id_seq";

	private Double valor;
	private TipoCobrancaCredenciadoProcedimento tipo;
	private ProcedimentoBase procedimento;
	private Especialidade especialidade;
	private ConfiguracaoEditalCredenciadoProcedimento configuracao;	
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	public HistoricoValorCadastroEditalConfiguracao(Double valor, TipoCobrancaCredenciadoProcedimento tipo,
			ProcedimentoBase procedimento, Especialidade especialidade,
			ConfiguracaoEditalCredenciadoProcedimento configuracao) {
		super();
		this.valor = valor;
		this.tipo = tipo;
		this.procedimento = procedimento;
		this.especialidade = especialidade;
		this.configuracao = configuracao;
	}
	
	public HistoricoValorCadastroEditalConfiguracao() {
		super();
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

	@ManyToOne
	@JoinColumn(name = "id_configuracao_edital_credenciado_procedimento")
	public ConfiguracaoEditalCredenciadoProcedimento getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(ConfiguracaoEditalCredenciadoProcedimento configuracao) {
		this.configuracao = configuracao;
	}

}