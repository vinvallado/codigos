package br.mil.fab.boletim.api.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the T_MOTIVO_UTILIZADO database table.
 * 
 */
@Entity
@Table(name="T_MOTIVO_UTILIZADO")
@NamedQuery(name="MotivoUtilizado.findAll", query="SELECT m FROM MotivoUtilizado m")
@SequenceGenerator(name="sq_motivo_utilizado",sequenceName="sig.sq_motivo_utilizado",allocationSize=1)
public class MotivoUtilizado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator="sq_motivo_utilizado",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_LANCAMENTO")
	private long idLancamento;

	@ManyToOne
	@JoinColumn(name="CD_MOTIVO")
	private MotivoBoletim motivo;

	@Column(name="CD_ORG")
	private String cdOrg;

	@Column(name="CD_USUARIO")
	private String cdUsuario;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_REALIZACAO")
	private Date dtRealizacao;

	@Column(name="ID_BOLETIM")
	private BigDecimal idBoletim;

	@Column(name="ID_MODULO")
	private BigDecimal idModulo;

	@Column(name="ID_PROCESSO")
	private BigDecimal idProcesso;

	@Column(name="NR_IP_USUARIO")
	private String nrIpUsuario;

	@Column(name="NR_ITEM_BOLETIM")
	private BigDecimal nrItemBoletim;

	@Column(name="NR_MAC_USUARIO")
	private String nrMacUsuario;

	@Column(name="NR_ORDEM_UTILIZADO")
	private String nrOrdemUtilizado;

	@Column(name="ST_AUTORIZACAO")
	private String stAutorizacao;

	@Column(name="ST_CONFORMIDADE")
	private String stConformidade;


	@OneToMany(mappedBy="id.motivo",cascade=CascadeType.PERSIST)
	private List<ComponenteGeral> componentes;

	public MotivoUtilizado() {
	}

	public long getIdLancamento() {
		return this.idLancamento;
	}

	public void setIdLancamento(long idLancamento) {
		this.idLancamento = idLancamento;
	}

	
	public MotivoBoletim getMotivo() {
		return this.motivo;
	}

	public void setMotivo(MotivoBoletim cdMotivo) {
		this.motivo = cdMotivo;
	}

	public String getCdOrg() {
		return this.cdOrg;
	}

	public void setCdOrg(String cdOrg) {
		this.cdOrg = cdOrg;
	}

	public String getCdUsuario() {
		return this.cdUsuario;
	}

	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}

	

	public Date getDtRealizacao() {
		return this.dtRealizacao;
	}

	public void setDtRealizacao(Date dtRealizacao) {
		this.dtRealizacao = dtRealizacao;
	}

	public BigDecimal getIdBoletim() {
		return this.idBoletim;
	}

	public void setIdBoletim(BigDecimal idBoletim) {
		this.idBoletim = idBoletim;
	}

	
	public BigDecimal getIdModulo() {
		return this.idModulo;
	}

	public void setIdModulo(BigDecimal idModulo) {
		this.idModulo = idModulo;
	}

	public BigDecimal getIdProcesso() {
		return this.idProcesso;
	}

	public void setIdProcesso(BigDecimal idProcesso) {
		this.idProcesso = idProcesso;
	}


	public String getNrIpUsuario() {
		return this.nrIpUsuario;
	}

	public void setNrIpUsuario(String nrIpUsuario) {
		this.nrIpUsuario = nrIpUsuario;
	}

	public BigDecimal getNrItemBoletim() {
		return this.nrItemBoletim;
	}

	public void setNrItemBoletim(BigDecimal nrItemBoletim) {
		this.nrItemBoletim = nrItemBoletim;
	}

	public String getNrMacUsuario() {
		return this.nrMacUsuario;
	}

	public void setNrMacUsuario(String nrMacUsuario) {
		this.nrMacUsuario = nrMacUsuario;
	}

	public String getNrOrdemUtilizado() {
		return this.nrOrdemUtilizado;
	}

	public void setNrOrdemUtilizado(String nrOrdemUtilizado) {
		this.nrOrdemUtilizado = nrOrdemUtilizado;
	}

	public String getStAutorizacao() {
		return this.stAutorizacao;
	}

	public void setStAutorizacao(String stAutorizacao) {
		this.stAutorizacao = stAutorizacao;
	}

	public String getStConformidade() {
		return this.stConformidade;
	}

	public void setStConformidade(String stConformidade) {
		this.stConformidade = stConformidade;
	}

	public List<ComponenteGeral> getComponentes() {
		return this.componentes;
	}

	public void setComponentes(List<ComponenteGeral> componentes) {
		this.componentes = componentes;
	}

}