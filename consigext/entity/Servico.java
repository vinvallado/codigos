package br.mil.fab.consigext.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


@Entity
@Table(name="T_SERVICO")
@SequenceGenerator(name="SQ_ID_SERVICO",sequenceName="SQ_ID_SERVICO",allocationSize=1)
public class Servico implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="SQ_ID_SERVICO",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_SERVICO")
	private long id;

	@Column(name="CD_SERVICO")
	private String cdServico;

	@Column(name="DS_SERVICO")
	private String dsServico;

	@Column(name="NR_PRIORIDADE")
	private long nrPrioridade;
	
	@Column(name="ST_SERVICO")
	private long stServico;

	public long getStServico() {
		return stServico;
	}


	public void setStServico(long stServico) {
		this.stServico = stServico;
	}

	@Column(name="ST_EXCLUIDO")
	private long stExcluido;
	
	//bi-directional many-to-one association to ParametroServico
	@OneToMany(mappedBy="servico", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<ParametroServico> parametroServicos;

	//bi-directional many-to-one association to NaturezaServico
	@ManyToOne
	@JoinColumn(name="ID_NATUREZA_SERVICO")
	@JsonIgnore
	private NaturezaServico naturezaServico;

	//bi-directional many-to-one association to ServicoConsig
	@OneToMany(mappedBy="servico", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<ServicoConsig> servicoConsigs;

	public Servico() {

	}
	
	
	public Servico(long id, String cdServico, String dsServico, long nrPrioridade, long stServico, long stExcluido) {
		super();
		this.id = id;
		this.cdServico = cdServico;
		this.dsServico = dsServico;
		this.nrPrioridade = nrPrioridade;
		this.stServico = stServico;
		this.stExcluido = stExcluido;
	}
	public Servico(long id, String cdServico, String dsServico, long nrPrioridade, long stServico) {
		super();
		this.id = id;
		this.cdServico = cdServico;
		this.dsServico = dsServico;
		this.nrPrioridade = nrPrioridade;
		this.stServico = stServico;
		this.stExcluido = (long) 0;
	}
	public Servico(long id, String cdServico, String dsServico, long nrPrioridade) {
		super();
		this.id = id;
		this.cdServico = cdServico;
		this.dsServico = dsServico;
		this.nrPrioridade = nrPrioridade;
		this.stServico = (long) 1;
		this.stExcluido = (long) 0;
	}

	
	public long getStExcluido() {
		return stExcluido;
	}


	public void setStExcluido(long stExcluido) {
		this.stExcluido = stExcluido;
	}


	public long getId() {
		return this.id;
	}

	public void setId(long idServico) {
		this.id = idServico;
	}

	public String getCdServico() {
		return this.cdServico;
	}

	public void setCdServico(String cdServico) {
		this.cdServico = cdServico;
	}

	public String getDsServico() {
		return this.dsServico;
	}

	public void setDsServico(String dsServico) {
		this.dsServico = dsServico;
	}

	public long getNrPrioridade() {
		return this.nrPrioridade;
	}

	public void setNrPrioridade(long nrPrioridade) {
		this.nrPrioridade = nrPrioridade;
	}

	public List<ParametroServico> getParametroServicos() {
		return this.parametroServicos;
	}

	public void setParametroServicos(List<ParametroServico> TParametroServicos) {
		this.parametroServicos = TParametroServicos;
	}

	public ParametroServico addTParametroServico(ParametroServico TParametroServico) {
		getParametroServicos().add(TParametroServico);
		TParametroServico.setServico(this);

		return TParametroServico;
	}

	public ParametroServico removeTParametroServico(ParametroServico TParametroServico) {
		getParametroServicos().remove(TParametroServico);
		TParametroServico.setServico(null);

		return TParametroServico;
	}

	public NaturezaServico getNaturezaServico() {
		return this.naturezaServico;
	}

	public void setNaturezaServico(NaturezaServico TNaturezaServico) {
		this.naturezaServico = TNaturezaServico;
	}

	public List<ServicoConsig> getServicoConsigs() {
		return this.servicoConsigs;
	}

	public void setServicoConsigs(List<ServicoConsig> TServicoConsigs) {
		this.servicoConsigs = TServicoConsigs;
	}

	public ServicoConsig addTServicoConsig(ServicoConsig TServicoConsig) {
		getServicoConsigs().add(TServicoConsig);
		TServicoConsig.setServico(this);

		return TServicoConsig;
	}

	public ServicoConsig removeTServicoConsig(ServicoConsig TServicoConsig) {
		getServicoConsigs().remove(TServicoConsig);
		TServicoConsig.setServico(null);

		return TServicoConsig;
	}

}