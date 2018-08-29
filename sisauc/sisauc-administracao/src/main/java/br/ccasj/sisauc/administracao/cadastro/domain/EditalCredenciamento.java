package br.ccasj.sisauc.administracao.cadastro.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "edital_credenciamento", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class EditalCredenciamento extends EntidadeGenerica {

	private static final long serialVersionUID = -66648807402438796L;
	private static final String SEQ_NAME = "edital_credenciamento_id_seq";
	
	private String numero;
	private String descricao;
	private boolean ativo = true;
	private Date inicio;
	private Date fim;
	private Tabela tabela;
	private Set<ConfiguracaoEditalCredenciado> credenciados = new HashSet<ConfiguracaoEditalCredenciado>();
	
	public EditalCredenciamento() {
		super();
	}

	public EditalCredenciamento(Integer id) {
		super(id);
	}

	public EditalCredenciamento(Integer id, String numero, String descricao, boolean ativo, Date inicio, Date fim) {
		super(id);
		this.numero = numero;
		this.descricao = descricao;
		this.ativo = ativo;
		this.inicio = inicio;
		this.fim = fim;
	}

	public EditalCredenciamento(Integer id, String numero, Tabela tabela, String descricao, boolean ativo, Date inicio, Date fim) {
		super(id);
		this.numero = numero;
		this.tabela = tabela;
		this.descricao = descricao;
		this.ativo = ativo;
		this.inicio = inicio;
		this.fim = fim;
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}	
	
	@NotEmpty(message = "O campo número é obrigatório")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@NotNull(message = "Campo início obrigatório")
	@Temporal(TemporalType.DATE)
	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	@NotNull(message = "Campo fim obrigatório")
	@Temporal(TemporalType.DATE)
	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	public Tabela getTabela() {
		return tabela;
	}

	public void setTabela(Tabela tabela) {
		this.tabela = tabela;
	}

	@OneToMany(mappedBy = "edital")
	public Set<ConfiguracaoEditalCredenciado> getCredenciados() {
		return credenciados;
	}

	public void setCredenciados(Set<ConfiguracaoEditalCredenciado> credenciados) {
		this.credenciados = credenciados;
	}

}
