package br.ccasj.sisauc.administracao.historico.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeHistoricoGenerica;

@Entity
@Table(name = "historico_valor_configuracao_edital_credenciado", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class HistoricoValorCadastroEditalCredenciado extends EntidadeHistoricoGenerica {

	private static final long serialVersionUID = -8347543803277404553L;
	private static final String SEQ_NAME = "historico_valor_configuracao_edital_credenciado_id_seq";
	
	private Double indiceReajustePorte;
	private Double indiceReajustePorteAnestesico;
	private Double indiceReajusteCustoOperacional;
	//TODO add indiceReajusteAuxiliares
	private ConfiguracaoEditalCredenciado configuracaoEditalCredenciamento;
	private Credenciado credenciado;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	public HistoricoValorCadastroEditalCredenciado(Double indiceReajustePorte, Double indiceReajustePorteAnestesico,
			Double indiceReajusteCustoOperacional, Credenciado credenciado,
			ConfiguracaoEditalCredenciado configuracaoEditalCredenciamento) {
		super();
		this.indiceReajustePorte = indiceReajustePorte;
		this.indiceReajustePorteAnestesico = indiceReajustePorteAnestesico;
		this.indiceReajusteCustoOperacional = indiceReajusteCustoOperacional;
		this.configuracaoEditalCredenciamento = configuracaoEditalCredenciamento;
		this.credenciado = credenciado;
	}

	public HistoricoValorCadastroEditalCredenciado() {
		super();
	}
	
	@Column(name="indice_reajuste_porte")
	public Double getIndiceReajustePorte() {
		return indiceReajustePorte;
	}
	
	public void setIndiceReajustePorte(Double indiceReajustePorte) {
		this.indiceReajustePorte = indiceReajustePorte;
	}
	
	@Column(name="indice_reajuste_porte_anestesico")
	public Double getIndiceReajustePorteAnestesico() {
		return indiceReajustePorteAnestesico;
	}
	
	public void setIndiceReajustePorteAnestesico(Double indiceReajustePorteAnestesico) {
		this.indiceReajustePorteAnestesico = indiceReajustePorteAnestesico;
	}
	
	@Column(name="indice_reajuste_custo_operacional")
	public Double getIndiceReajusteCustoOperacional() {
		return indiceReajusteCustoOperacional;
	}
	
	public void setIndiceReajusteCustoOperacional(Double indiceReajusteCustoOperacional) {
		this.indiceReajusteCustoOperacional = indiceReajusteCustoOperacional;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_configuracao_edital_credenciamento")
	public ConfiguracaoEditalCredenciado getConfiguracaoEditalCredenciamento() {
		return configuracaoEditalCredenciamento;
	}
	public void setConfiguracaoEditalCredenciamento(ConfiguracaoEditalCredenciado configuracaoEditalCredenciamento) {
		this.configuracaoEditalCredenciamento = configuracaoEditalCredenciamento;
	}

	@ManyToOne
	@JoinColumn(name = "id_credenciado")
	public Credenciado getCredenciado() {
		return credenciado;
	}


	public void setCredenciado(Credenciado credenciado) {
		this.credenciado = credenciado;
	}
	
}
