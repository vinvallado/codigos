package br.ccasj.sisauc.administracao.sistema.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "funsa", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Funsa extends EntidadeGenerica { 

	private static final long serialVersionUID = 5819327084992243218L;

	private static final String SEQ_NAME = "funsa_id_seq";
	
	private Integer id;
	
	private String sigla;
	
	private String nome;
	
	private Integer numeroGabsMes;

	public Funsa(){
		super();
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@Column(name="sigla")
	public String getSigla() {
		return sigla;
	}

	@Column(name="nome")
	public String getNome() {
		return nome;
	}

	/**
	 * Essa coluna deve ser retirada quando as bases forem integradas.
	 * 
	 * @return O número de GABs estimadas por mês, para calculo da prioridade do Job de envio
	 * para o SISCONSIG, evitando colisão de envios entre FUNSAs 
	 */
	@Column(name="numero_gabs_mes")
	public Integer getNumeroGabsMes() {
		return numeroGabsMes;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNumeroGabsMes(Integer numeroGabsMes) {
		this.numeroGabsMes = numeroGabsMes;
	}
	
}
