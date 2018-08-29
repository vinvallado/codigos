package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the T_ENTIDADE_CONSIG database table.
 * 
 */
@Entity
@SequenceGenerator(name="SQ_ID_ENTIDADE_CONSIG",sequenceName="SQ_ID_ENTIDADE_CONSIG",allocationSize=1)
@Table(name="T_ENTIDADE_CONSIG")
public class EntidadeConsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_ENTIDADE_CONSIG",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_ENTIDADE_CONSIG")
	private long id;

	@Column(name="CD_ENTIDADE")
	private String cdEntidade;

	@Column(name="DS_CARGO_RESP1")
	private String dsCargoResp1;

	@Column(name="DS_CARGO_RESP2")
	private String dsCargoResp2;

	@Column(name="DS_CARGO_RESP3")
	private String dsCargoResp3;

	@Column(name="DS_CONTATO_RESP1")
	private String dsContatoResp1;

	@Column(name="DS_CONTATO_RESP2")
	private String dsContatoResp2;

	@Column(name="DS_CONTATO_RESP3")
	private String dsContatoResp3;

	@Column(name="DS_CONTATOEC")
	private String dsContatoec;

	@Column(name="DS_CONTRATO")
	private String dsContrato;

	@Column(name="DS_EMAIL_EC")
	private String dsEmailEc;

	@Column(name="DS_EMAIL_NOTIF_EXP")
	private String dsEmailNotifExp;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_EXPIRACAO_CADASTRAL")
	private Date dtExpiracaoCadastral;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_EXPIRACAO_CONTRATUAL")
	private Date dtExpiracaoContratual;

	@Column(name="NM_RESP1")
	private String nmResp1;

	@Column(name="NM_RESP2")
	private String nmResp2;

	@Column(name="NM_RESP3")
	private String nmResp3;

	@Column(name="NR_AGENCIA")
	private String nrAgencia;

	@Column(name="NR_BANCO")
	private String nrBanco;

	@Column(name="NR_CONTA")
	private String nrConta;

	@Column(name="NR_DIGITOCONTA")
	private String nrDigitoconta;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="CD_ORG")
	private Organizacao organizacao;
	
	@Column(name="ST_ENTIDADE")
	private Long stEntidade;

	@Column(name="TX_INSTRUCAO_CONTATO")
	private String txInstrucaoContato;

	//bi-directional many-to-one association to NaturezaConsig
	@ManyToOne
	@JoinColumn(name="ID_NATUREZA")
	private NaturezaConsig naturezaConsig;

	//bi-directional many-to-one association to HistoricoEc
	@OneToMany(mappedBy="entidadeConsig")
	private List<HistoricoEc> historicoEcs;

	//bi-directional many-to-one association to ListaIp
	@OneToMany(mappedBy="entidadeConsig")
	private List<ListaIP> listaIps;

	//bi-directional many-to-one association to ParametroEc
	
	@OneToMany(mappedBy="entidadeConsig")
	private List<ParametroEc> parametroEcs;

	//bi-directional many-to-one association to ServicoConsig
	@OneToMany(mappedBy="entidadeConsig")
	private List<ServicoConsig> servicoConsigs;

	//bi-directional many-to-one association to UsuarioEc
	@OneToMany(mappedBy="entidadeConsig")
	private List<UsuarioConsig> usuariosConsig;
	
	//bi-directional many-to-one association to HistoricoConsignacao
	@OneToMany(mappedBy="entidadeConsig")
	private List<Perfil> perfil;

	public EntidadeConsig() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idEntidadeConsig) {
		this.id = idEntidadeConsig;
	}

	public String getCdEntidade() {
		return this.cdEntidade;
	}

	public void setCdEntidade(String cdEntidade) {
		this.cdEntidade = cdEntidade;
	}

	public String getDsCargoResp1() {
		return this.dsCargoResp1;
	}

	public void setDsCargoResp1(String dsCargoResp1) {
		this.dsCargoResp1 = dsCargoResp1;
	}

	public String getDsCargoResp2() {
		return this.dsCargoResp2;
	}

	public void setDsCargoResp2(String dsCargoResp2) {
		this.dsCargoResp2 = dsCargoResp2;
	}

	public String getDsCargoResp3() {
		return this.dsCargoResp3;
	}

	public void setDsCargoResp3(String dsCargoResp3) {
		this.dsCargoResp3 = dsCargoResp3;
	}

	public String getDsContatoResp1() {
		return this.dsContatoResp1;
	}

	public void setDsContatoResp1(String dsContatoResp1) {
		this.dsContatoResp1 = dsContatoResp1;
	}

	public String getDsContatoResp2() {
		return this.dsContatoResp2;
	}

	public void setDsContatoResp2(String dsContatoResp2) {
		this.dsContatoResp2 = dsContatoResp2;
	}

	public String getDsContatoResp3() {
		return this.dsContatoResp3;
	}

	public void setDsContatoResp3(String dsContatoResp3) {
		this.dsContatoResp3 = dsContatoResp3;
	}

	public String getDsContatoec() {
		return this.dsContatoec;
	}

	public void setDsContatoec(String dsContatoec) {
		this.dsContatoec = dsContatoec;
	}

	public String getDsContrato() {
		return this.dsContrato;
	}

	public void setDsContrato(String dsContrato) {
		this.dsContrato = dsContrato;
	}

	public String getDsEmailEc() {
		return this.dsEmailEc;
	}

	public void setDsEmailEc(String dsEmailEc) {
		this.dsEmailEc = dsEmailEc;
	}

	public String getDsEmailNotifExp() {
		return this.dsEmailNotifExp;
	}

	public void setDsEmailNotifExp(String dsEmailNotifExp) {
		this.dsEmailNotifExp = dsEmailNotifExp;
	}

	public Date getDtExpiracaoCadastral() {
		return this.dtExpiracaoCadastral;
	}

	public void setDtExpiracaoCadastral(Date dtExpiracaoCadastral) {
		this.dtExpiracaoCadastral = dtExpiracaoCadastral;
	}

	public Date getDtExpiracaoContratual() {
		return this.dtExpiracaoContratual;
	}

	public void setDtExpiracaoContratual(Date dtExpiracaoContratual) {
		this.dtExpiracaoContratual = dtExpiracaoContratual;
	}

	public String getNmResp1() {
		return this.nmResp1;
	}

	public void setNmResp1(String nmResp1) {
		this.nmResp1 = nmResp1;
	}

	public String getNmResp2() {
		return this.nmResp2;
	}

	public void setNmResp2(String nmResp2) {
		this.nmResp2 = nmResp2;
	}

	public String getNmResp3() {
		return this.nmResp3;
	}

	public void setNmResp3(String nmResp3) {
		this.nmResp3 = nmResp3;
	}

	public String getNrAgencia() {
		return this.nrAgencia;
	}

	public void setNrAgencia(String nrAgencia) {
		this.nrAgencia = nrAgencia;
	}

	public String getNrBanco() {
		return this.nrBanco;
	}

	public void setNrBanco(String nrBanco) {
		this.nrBanco = nrBanco;
	}

	public String getNrConta() {
		return this.nrConta;
	}

	public void setNrConta(String nrConta) {
		this.nrConta = nrConta;
	}

	public String getNrDigitoconta() {
		return this.nrDigitoconta;
	}

	public void setNrDigitoconta(String nrDigitoconta) {
		this.nrDigitoconta = nrDigitoconta;
	}

	public Long getStEntidade() {
		return this.stEntidade;
	}

	public void setStEntidade(Long stEntidade) {
		this.stEntidade = stEntidade;
	}

	public String getTxInstrucaoContato() {
		return this.txInstrucaoContato;
	}

	public void setTxInstrucaoContato(String txInstrucaoContato) {
		this.txInstrucaoContato = txInstrucaoContato;
	}

	public NaturezaConsig getNaturezaConsig() {
		return this.naturezaConsig;
	}

	public void setNaturezaConsig(NaturezaConsig TNaturezaConsig) {
		this.naturezaConsig = TNaturezaConsig;
	}

	public List<HistoricoEc> getHistoricoEcs() {
		return this.historicoEcs;
	}

	public void setHistoricoEcs(List<HistoricoEc> THistoricoEcs) {
		this.historicoEcs = THistoricoEcs;
	}

	public HistoricoEc addTHistoricoEc(HistoricoEc THistoricoEc) {
		getHistoricoEcs().add(THistoricoEc);
		THistoricoEc.setEntidadeConsig(this);

		return THistoricoEc;
	}

	public HistoricoEc removeTHistoricoEc(HistoricoEc THistoricoEc) {
		getHistoricoEcs().remove(THistoricoEc);
		THistoricoEc.setEntidadeConsig(null);

		return THistoricoEc;
	}

	public List<ListaIP> getListaIps() {
		return this.listaIps;
	}

	public void setListaIps(List<ListaIP> TListaIps) {
		this.listaIps = TListaIps;
	}

	public ListaIP addTListaIp(ListaIP TListaIp) {
		getListaIps().add(TListaIp);
		TListaIp.setEntidadeConsig(this);

		return TListaIp;
	}

	public ListaIP removeTListaIp(ListaIP TListaIp) {
		getListaIps().remove(TListaIp);
		TListaIp.setEntidadeConsig(null);

		return TListaIp;
	}

	public List<ParametroEc> getParametroEcs() {
		return this.parametroEcs;
	}

	public void setParametroEcs(List<ParametroEc> TParametroEcs) {
		this.parametroEcs = TParametroEcs;
	}

	public ParametroEc addTParametroEc(ParametroEc TParametroEc) {
		getParametroEcs().add(TParametroEc);
		TParametroEc.setEntidadeConsig(this);

		return TParametroEc;
	}

	public ParametroEc removeTParametroEc(ParametroEc TParametroEc) {
		getParametroEcs().remove(TParametroEc);
		TParametroEc.setEntidadeConsig(null);

		return TParametroEc;
	}

	public List<ServicoConsig> getServicoConsigs() {
		return this.servicoConsigs;
	}

	public void setServicoConsigs(List<ServicoConsig> TServicoConsigs) {
		this.servicoConsigs = TServicoConsigs;
	}

	public ServicoConsig addTServicoConsig(ServicoConsig TServicoConsig) {
		getServicoConsigs().add(TServicoConsig);
		TServicoConsig.setEntidadeConsig(this);

		return TServicoConsig;
	}

	public ServicoConsig removeTServicoConsig(ServicoConsig TServicoConsig) {
		getServicoConsigs().remove(TServicoConsig);
		TServicoConsig.setEntidadeConsig(null);

		return TServicoConsig;
	}

	public Organizacao getOrganizacao() {
		return organizacao;
	}

	public void setOrganizacao(Organizacao organizacao) {
		this.organizacao = organizacao;
	}

	public List<UsuarioConsig> getUsuarioEcs() {
		return usuariosConsig;
	}

	public void setUsuarioEcs(List<UsuarioConsig> usuarioEcs) {
		this.usuariosConsig = usuarioEcs;
	}

	public List<Perfil> getPerfil() {
		return perfil;
	}

	public void setPerfil(List<Perfil> perfil) {
		this.perfil = perfil;
	}
	
}