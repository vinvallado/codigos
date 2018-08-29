package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the T_SERVICO_CONSIG database table.
 * 
 */
@Entity
@SequenceGenerator(name="SQ_ID_SERVICO_CONSIG",sequenceName="SQ_ID_SERVICO_CONSIG",allocationSize=1 )
@Table(name="T_SERVICO_CONSIG")
public class ServicoConsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_SERVICO_CONSIG",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_SERVICO_CONSIG")
	private long id;

	@Column(name="ST_SERVICO")
	private long stServico;
	
	//bi-directional many-to-one association to Cet
	@OneToMany(mappedBy="servicoConsig")
	private List<Cet> cets;
	
	@OneToMany(mappedBy="servicoConsig", fetch=FetchType.LAZY)
	private List<IndiceServicoConsig> indices;

	//bi-directional many-to-one association to Consignacao
	@OneToMany(mappedBy="servicoConsig", fetch=FetchType.EAGER)
	private List<Consignacao> consignacaos;

	//bi-directional many-to-one association to ParametroServicoConsig
	@OneToMany(mappedBy="servicoConsig")
	@JsonIgnore
	private List<ParametroServicoConsig> parametroServicoConsigs;

	//bi-directional many-to-one association to EntidadeConsig
	@ManyToOne
	@JoinColumn(name="ID_ENTIDADE_CONSIG")
	@JsonIgnore
	private EntidadeConsig entidadeConsig;

	//bi-directional many-to-one association to Servico
	@ManyToOne
	@JoinColumn(name="ID_SERVICO")
	@JsonIgnore
	private Servico servico;
	
	@ManyToOne
	@JoinColumn(name="CD_CAIXA", nullable = true)
	@JsonIgnore
	private CaixaPagamento caixaPagamento;
	
	/*@Column(name="CD_CAIXA")
	private String cdCaixa;*/

	public ServicoConsig() {
	}
	
	public ServicoConsig(EntidadeConsig entidadeConsig, Servico servico) {
		super();
		this.entidadeConsig = entidadeConsig;
		this.servico = servico;
	}

	
	public ServicoConsig(List<Consignacao> consignacaos, EntidadeConsig entidadeConsig, Servico servico) {
		super();
		this.consignacaos = consignacaos;
		this.entidadeConsig = entidadeConsig;
		this.servico = servico;
	}


	public ServicoConsig(long id, long stServico, List<Cet> cets, List<Consignacao> consignacaos,
			List<ParametroServicoConsig> parametroServicoConsigs, EntidadeConsig entidadeConsig, Servico servico,
			CaixaPagamento caixaPagamento) {
		super();
		this.id = id;
		this.stServico = stServico;
		this.cets = cets;
		this.consignacaos = consignacaos;
		this.parametroServicoConsigs = parametroServicoConsigs;
		this.entidadeConsig = entidadeConsig;
		this.servico = servico;
		this.caixaPagamento = caixaPagamento;
	}
	
	public ServicoConsig(long stServico, List<Cet> cets, List<Consignacao> consignacaos,
			List<ParametroServicoConsig> parametroServicoConsigs, EntidadeConsig entidadeConsig, Servico servico,
			CaixaPagamento caixaPagamento) {
		super();
		this.stServico = stServico;
		this.cets = cets;
		this.consignacaos = consignacaos;
		this.parametroServicoConsigs = parametroServicoConsigs;
		this.entidadeConsig = entidadeConsig;
		this.servico = servico;
		this.caixaPagamento = caixaPagamento;
	}


	public long getId() {
		return this.id;
	}

	public void setId(long idServicoConsig) {
		this.id = idServicoConsig;
	}

	public long getStServico() {
		return this.stServico;
	}

	public void setStServico(long stServico) {
		this.stServico = stServico;
	}

	public List<Cet> getCets() {
		return this.cets;
	}

	public void setCets(List<Cet> TCets) {
		this.cets = TCets;
	}

	public Cet addTCet(Cet TCet) {
		getCets().add(TCet);
		TCet.setTServicoConsig(this);

		return TCet;
	}

	public Cet removeTCet(Cet TCet) {
		getCets().remove(TCet);
		TCet.setTServicoConsig(null);

		return TCet;
	}

	public List<Consignacao> getConsignacaos() {
		return this.consignacaos;
	}

	public void setConsignacaos(List<Consignacao> TConsignacaos) {
		this.consignacaos = TConsignacaos;
	}

	public Consignacao addTConsignacao(Consignacao TConsignacao) {
		if(getConsignacaos() == null) {
			this.setConsignacaos(new ArrayList<>());
		}
		getConsignacaos().add(TConsignacao);
		TConsignacao.setServicoConsig(this);

		return TConsignacao;
	}

	public Consignacao removeTConsignacao(Consignacao TConsignacao) {
		getConsignacaos().remove(TConsignacao);
		TConsignacao.setServicoConsig(null);

		return TConsignacao;
	}

	public List<ParametroServicoConsig> getParametroServicoConsigs() {
		return this.parametroServicoConsigs;
	}

	public void setParametroServicoConsigs(List<ParametroServicoConsig> TParametroServicoConsigs) {
		this.parametroServicoConsigs = TParametroServicoConsigs;
	}

	public ParametroServicoConsig addTParametroServicoConsig(ParametroServicoConsig TParametroServicoConsig) {
		getParametroServicoConsigs().add(TParametroServicoConsig);
		TParametroServicoConsig.setServicoConsig(this);

		return TParametroServicoConsig;
	}

	public ParametroServicoConsig removeTParametroServicoConsig(ParametroServicoConsig TParametroServicoConsig) {
		getParametroServicoConsigs().remove(TParametroServicoConsig);
		TParametroServicoConsig.setServicoConsig(null);

		return TParametroServicoConsig;
	}

	public EntidadeConsig getEntidadeConsig() {
		return this.entidadeConsig;
	}

	public void setEntidadeConsig(EntidadeConsig TEntidadeConsig) {
		this.entidadeConsig = TEntidadeConsig;
	}

	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico TServico) {
		this.servico = TServico;
	}

	/*public String getCdCaixa() {
		return cdCaixa;
	}

	public void setCdCaixa(String cdCaixa) {
		this.cdCaixa = cdCaixa;
	}*/

	public CaixaPagamento getCaixaPagamento() {
		return caixaPagamento;
	}

	public void setCaixaPagamento(CaixaPagamento caixaPagamento) {
		this.caixaPagamento = caixaPagamento;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && 
				obj instanceof ServicoConsig && 
				this.getId() == ((ServicoConsig) obj).getId();
	}

	public List<IndiceServicoConsig> getIndices() {
		return indices;
	}

	public void setIndices(List<IndiceServicoConsig> indices) {
		this.indices = indices;
	}


}