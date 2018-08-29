package br.ccasj.sisauc.administracao.cadastro.domain;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "procedimento_pedido_solicitacao", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class ProcedimentoPedidoSolicitacao extends EntidadeGenerica {

	private static final long serialVersionUID = 950012338626488367L;

	private static final String SEQ_NAME = "procedimento_pedido_solicitacao_id_seq";

	private ConfiguracaoEditalCredenciadoProcedimento credenciado;
	private int quantidade = 1;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "id_configuracao_edital_credenciado_procedimento")
	public ConfiguracaoEditalCredenciadoProcedimento getCredenciado() {
		return credenciado;
	}

	public void setCredenciado(ConfiguracaoEditalCredenciadoProcedimento credenciado) {
		this.credenciado = credenciado;
	}

	@NotNull
	@Min(value = 1)
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	

}
