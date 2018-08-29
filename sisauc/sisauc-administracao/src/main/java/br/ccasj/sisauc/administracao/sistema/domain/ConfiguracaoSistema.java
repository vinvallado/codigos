package br.ccasj.sisauc.administracao.sistema.domain;

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
import javax.validation.constraints.NotNull;

import br.ccasj.sisauc.framework.domain.Cidade;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "configuracao_sistema", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class ConfiguracaoSistema extends EntidadeGenerica {


	private static final long serialVersionUID = -5965946604798195485L;
	
	private static final String SEQ_NAME = "configuracao_sistema_id_seq";

	private boolean bloquearEmissaoGab = false;
	private boolean permitirSolicitacoesSemUpload = true;
	private Cidade cidadeRegional;
	
	public ConfiguracaoSistema(boolean bloquearEmissaoGab) {
		super();
		this.bloquearEmissaoGab = bloquearEmissaoGab;
	}
	
	public ConfiguracaoSistema() {
		super();
	}
	
	public ConfiguracaoSistema(Integer id, boolean bloquearEmissaoGab, boolean permitirSolicitacoesSemUpload, Integer idCidade, String nomeCidade) {
		super(id);
		this.bloquearEmissaoGab = bloquearEmissaoGab;
		this.permitirSolicitacoesSemUpload = permitirSolicitacoesSemUpload;
		this.cidadeRegional = new Cidade();
		this.cidadeRegional.setId(idCidade);
		this.cidadeRegional.setNome(nomeCidade);
	}

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	@NotNull
	@Column(name = "bloquear_emissao_gab")
	public boolean isBloquearEmissaoGab() {
		return bloquearEmissaoGab;
	}

	public void setBloquearEmissaoGab(boolean bloquearEmissaoGab) {
		this.bloquearEmissaoGab = bloquearEmissaoGab;
	}

	@NotNull
	@Column(name = "permitir_solicitacoes_sem_upload")
	public boolean isPermitirSolicitacoesSemUpload() {
		return permitirSolicitacoesSemUpload;
	}

	public void setPermitirSolicitacoesSemUpload(boolean permitirSolicitacoesSemUpload) {
		this.permitirSolicitacoesSemUpload = permitirSolicitacoesSemUpload;
	}

	@ManyToOne
	@JoinColumn(name="id_cidade")
	public Cidade getCidadeRegional() {
		return cidadeRegional;
	}

	public void setCidadeRegional(Cidade cidadeRegional) {
		this.cidadeRegional = cidadeRegional;
	}
	
}
