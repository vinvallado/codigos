package br.ccasj.sisauc.administracao.cadastro.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "procedimento_base", schema = EntidadeGenerica.SCHEMA_SISAUC)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tabela", discriminatorType = DiscriminatorType.STRING)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public abstract class ProcedimentoBase extends EntidadeGenerica {

	private static final long serialVersionUID = 8191166482757225289L;

	private static final String SEQ_NAME = "procedimento_base_id_seq";

	public static final String PROCEDIMENTO_MAPPING = "procedimentoMapping";
	
	protected Tabela tabela;
	protected String codigo;
	protected String descricao;

	public ProcedimentoBase() {
		super();
	}

	public ProcedimentoBase(Integer id) {
		super(id);
	}
	
	public ProcedimentoBase(Integer id, String codigo, String descricao) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public ProcedimentoBase(String codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public ProcedimentoBase(Tabela tabela, String codigo, String descricao) {
		super();
		this.tabela = tabela;
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public ProcedimentoBase(Integer id, String codigo, String descricao, Tabela tabela) {
		super(id);
		this.codigo = codigo;
		this.descricao = descricao;
		this.tabela = tabela;
	}	
	
	public static ProcedimentoBase criarProcedimento(Tabela tabela){
		switch (tabela) {
		case CBHPM2012:
			return new ProcedimentoCBHPM2012();
		case CISSFA:
			return new ProcedimentoCISSFA();
		case TRS:
			return new ProcedimentoTRS();
		default:
			return null;
		}
	}
	
	public static ProcedimentoBase criarProcedimento(Tabela tabela, String codigo, String descricao){
		ProcedimentoBase procedimentoBase = criarProcedimento(tabela);
		procedimentoBase.setCodigo(codigo);
		procedimentoBase.setDescricao(descricao);
		return procedimentoBase;
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}	

	@NotNull
	@Enumerated(EnumType.STRING)
	public Tabela getTabela() {
		//Forma de garantir que a tabela nunca virá nula
		if(this.tabela == null){
			if(this instanceof ProcedimentoCBHPM2012){
				tabela = Tabela.CBHPM2012;
			}
			if(this instanceof ProcedimentoTRS){
				tabela = Tabela.TRS;
			}
			if (this instanceof ProcedimentoCISSFA){
				tabela = Tabela.CISSFA;
			}
		}
		return tabela;
	}

	public void setTabela(Tabela tabela) {
		this.tabela = tabela;
	}

	@NotNull
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@NotNull
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
