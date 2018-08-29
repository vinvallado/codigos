package br.ccasj.sisauc.auditoriaretrospectiva.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "metadado_valor_auditoria_retrospectiva_ressarcimento", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class MetadadoValorAuditoriaRetrospectivaRessarcimento extends EntidadeGenerica {

	private static final long serialVersionUID = 4552257312902631551L;

	private static final String SEQ_NAME = "metadado_valor_auditoria_retrospectiva_ressarcimento_id_seq";

	private Double valor;
	private String chave;
	private String descricao;
	
	public MetadadoValorAuditoriaRetrospectivaRessarcimento() {
		super();
	}
	
	public MetadadoValorAuditoriaRetrospectivaRessarcimento(Integer id, Double valor, String chave, String descricao) {
		super(id);
		this.valor = valor;
		this.chave = chave;
		this.descricao = descricao;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}	

	@NotNull
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@NotEmpty
	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
