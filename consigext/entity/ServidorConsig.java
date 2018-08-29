package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import br.mil.fab.consigext.enums.CapacidadeServidor;
import br.mil.fab.consigext.enums.StatusEstabilizado;


/**
 * The persistent class for the T_SERVIDOR_CONSIG database table.
 * 
 */
@Entity
@SequenceGenerator(name="SQ_ID_SERVIDOR_CONSIG",sequenceName="SQ_ID_SERVIDOR_CONSIG",allocationSize=1 )
@Table(name="T_SERVIDOR_CONSIG")
@DynamicUpdate
public class ServidorConsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_SERVIDOR_CONSIG",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_SERVIDOR_CONSIG")
	private long id;

	@Column(name="DS_CATEGORIA")
	private String dsCategoria;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_REENGANJAMENTO")
	private Date dtReenganjamento;

	@Column(name="ST_CAPACIDADE")
	@Enumerated(EnumType.STRING)
	private CapacidadeServidor stCapacidade;

	@Column(name="ST_ESTABILIZADO")
	@Enumerated(EnumType.STRING)
	private StatusEstabilizado stEstabilizado;

//	@Column(name="VL_MARGEM30")
//	private BigDecimal vlMargem30;
//
//	@Column(name="VL_MARGEM70")
//	private BigDecimal vlMargem70;

	//bi-directional many-to-one association to CodigoUnico
	@OneToMany(mappedBy="servidorConsig")
	private List<CodigoUnico> codigoUnicos;

	//bi-directional many-to-one association to Consignacao
	@OneToMany(mappedBy="servidorConsig", fetch = FetchType.LAZY)
	private Set<Consignacao> consignacaos;

	@Column(name="ST_SERVIDOR")
	private Long stServidor;
	
	//bi-directional many-to-one association to SituacaoServidor
	@ManyToOne
	@JoinColumn(name="ID_SITUACAO_SERVIDOR",insertable=false,updatable=false)
	private SituacaoServidor situacaoServidor;
	
	@OneToOne
	@JoinColumn(name="NR_ORDEM", nullable = false,unique=true)
	private PesfisComgep pesfis;

	public ServidorConsig() {
	}
	

	public ServidorConsig(long id, PesfisComgep pesfis, //BigDecimal vlMargem30, BigDecimal vlMargem70,
			SituacaoServidor situacaoServidor, String dsCategoria, StatusEstabilizado stEstabilizado,
			Date dtReenganjamento, CapacidadeServidor stCapacidade) {
		
		super();
		this.id = id;
		this.pesfis = pesfis;
//		this.vlMargem30 = vlMargem30;
//		this.vlMargem70 = vlMargem70;
		this.situacaoServidor = situacaoServidor;
		this.dsCategoria = dsCategoria;
		this.stEstabilizado = stEstabilizado;
		this.dtReenganjamento = dtReenganjamento;
		this.stCapacidade = stCapacidade;
		this.stServidor = Long.parseLong("1");
	}
	
	public ServidorConsig(PesfisComgep pesfis) {
		super();
		this.pesfis = pesfis;
	}


	public ServidorConsig(Long id) {
		this.id = id;
	}


	public long getId() {
		return this.id;
	}

	public void setId(long idServidorConsig) {
		this.id = idServidorConsig;
	}

	public String getDsCategoria() {
		return this.dsCategoria;
	}

	public void setDsCategoria(String dsCategoria) {
		this.dsCategoria = dsCategoria;
	}

	public Date getDtReenganjamento() {
		return this.dtReenganjamento;
	}

	public void setDtReenganjamento(Date dtReenganjamento) {
		this.dtReenganjamento = dtReenganjamento;
	}

	public CapacidadeServidor getStCapacidade() {
		return this.stCapacidade;
	}

	public void setStCapacidade(CapacidadeServidor stCapacidade) {
		this.stCapacidade = stCapacidade;
	}

	public StatusEstabilizado getStEstabilizado() {
		return this.stEstabilizado;
	}

	public void setStEstabilizado(StatusEstabilizado stEstabilizado) {
		this.stEstabilizado = stEstabilizado;
	}

//	public BigDecimal getVlMargem30() {
//		return this.vlMargem30;
//	}
//
//	public void setVlMargem30(BigDecimal vlMargem30) {
//		this.vlMargem30 = vlMargem30;
//	}
//
//	public BigDecimal getVlMargem70() {
//		return this.vlMargem70;
//	}
//
//	public void setVlMargem70(BigDecimal vlMargem70) {
//		this.vlMargem70 = vlMargem70;
//	}

	public List<CodigoUnico> getCodigoUnicos() {
		return this.codigoUnicos;
	}

	public void setCodigoUnicos(List<CodigoUnico> TCodigoUnicos) {
		this.codigoUnicos = TCodigoUnicos;
	}

	public CodigoUnico addTCodigoUnico(CodigoUnico TCodigoUnico) {
		getCodigoUnicos().add(TCodigoUnico);
		TCodigoUnico.setServidorConsig(this);

		return TCodigoUnico;
	}

	public CodigoUnico removeTCodigoUnico(CodigoUnico TCodigoUnico) {
		getCodigoUnicos().remove(TCodigoUnico);
		TCodigoUnico.setServidorConsig(null);

		return TCodigoUnico;
	}

	public Set<Consignacao> getConsignacaos() {
		return this.consignacaos;
	}

	public void setConsignacaos(Set<Consignacao> TConsignacaos) {
		this.consignacaos = TConsignacaos;
	}

	public Consignacao addTConsignacao(Consignacao TConsignacao) {
		getConsignacaos().add(TConsignacao);
		TConsignacao.setServidorConsig(this);

		return TConsignacao;
	}

	public Consignacao removeTConsignacao(Consignacao TConsignacao) {
		getConsignacaos().remove(TConsignacao);
		TConsignacao.setServidorConsig(null);

		return TConsignacao;
	}
	
	public Long getStServidor() {
		return this.stServidor;
	}

	public void setStServidor(Long stServidor) {
		this.stServidor = stServidor;
	}
	
	public SituacaoServidor getSituacaoServidor() {
		return this.situacaoServidor;
	}

	public void setSituacaoServidor(SituacaoServidor TSituacaoServidor) {
		this.situacaoServidor = TSituacaoServidor;
	}

	public PesfisComgep getPesfis() {
		return pesfis;
	}

	public void setPesfis(PesfisComgep pesfis) {
		this.pesfis = pesfis;
	}

	
	public List<Consignacao> getConsignacoesEc (EntidadeConsig ec){
		
		List<Consignacao> consignacoesEc = new ArrayList<Consignacao>();
		
		for (Consignacao consig: this.getConsignacaos()) {
			String cdOrgConsig = consig.getEntidadeConsig().getOrganizacao().getCdOrg();
			String cdOrgEc = ec.getOrganizacao().getCdOrg();
			
			if (cdOrgConsig.equals(cdOrgEc)) {
				consignacoesEc.add(consig);
			}
		}
		return consignacoesEc;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && 
				obj instanceof ServidorConsig && 
				this.getId() == ((ServidorConsig) obj).getId();
	}

	
}