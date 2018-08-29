package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.mil.fab.consigext.util.CalculoUtil;

@SqlResultSetMapping(name = "findAllProcessosSaidaMapping", 
classes = @ConstructorResult
(targetClass = Processamento.class, columns = {
		@ColumnResult(name = "cdOrg",type=String.class), 
		@ColumnResult(name = "tpPessoa",type=String.class), 
		@ColumnResult(name = "nrOrdem",type=String.class),
		@ColumnResult(name = "cdPostoPgm",type=String.class), 
		@ColumnResult(name = "cdCaixaAcantus",type=String.class),
		@ColumnResult(name = "vlParcela",type=BigDecimal.class),
		@ColumnResult(name = "cdAnoMesFim",type=String.class),
		@ColumnResult(name = "nmPessoa",type=String.class), 
		@ColumnResult(name = "nrAde",type=String.class),
		@ColumnResult(name = "nrIndice",type=String.class) }))

@NamedNativeQuery(name = "Consignacao.findAllProcessosSaida", query = "select e.CD_ORG as cdOrg,"
		+ "hp.TP_PESSOA as tpPessoa,s.NR_ORDEM as nrOrdem,hp.cd_posto_pgm as cdPostoPgm,"
		+ "cp.cd_caixa_acantus as cdCaixaAcantus,c.VL_PRESTACAO as vlParcela,c.CD_ANOMES_FIM as cdAnoMesFim,"
		+ "p.NM_PESSOA as nmPessoa,c.NR_ADE as nrAde,c.NR_INDICE as nrIndice "
		+ "from consig.T_CONSIGNACAO c "
		+ "inner join consig.T_SERVIDOR_CONSIG s on s.ID_SERVIDOR_CONSIG = c.ID_SERVIDOR_CONSIG "
		+ "inner join consig.T_SERVICO_CONSIG sc on sc.ID_SERVICO_CONSIG = c.ID_SERVICO_CONSIG "
		+ "inner join consig.T_ENTIDADE_CONSIG e on sc.ID_ENTIDADE_CONSIG = e.ID_ENTIDADE_CONSIG "
		+ "inner join SIG.T_CAIXA_PAGAMENTO cp on sc.CD_CAIXA = cp.CD_CAIXA "
		+ "inner join SIG.T_PESFIS_COMGEP_DW p on p.NR_ORDEM = s.NR_ORDEM "
		+ "INNER JOIN sig.t_historico_pagamento hp ON hp.nr_ordem = s.NR_ORDEM AND hp.nr_versao = '1' "
		+ "INNER JOIN ( 	SELECT 	MAX(cf.cd_ano_mes) AS cd_ano_mes FROM "
		+ "	sig.t_cfg_basica_pgm cf	WHERE	cf.tp_versao = 'O' "
		+ "	) anomes ON hp.cd_ano_mes = anomes.cd_ano_mes "
		+ " where c.CD_ANOMES_FIM >= to_char(sysdate,'yyyyMM') and c.ID_STATUS_CONSIGNACAO = 8 "
		+ " ORDER BY  s.NR_ORDEM, c.ID_CONSIGNACAO, c.DT_RESERVA",resultSetMapping="findAllProcessosSaidaMapping")

@Entity
@Table(name = "T_CONSIGNACAO")
@SequenceGenerator(name = "SQ_ID_CONSIGNACAO", sequenceName = "SQ_ID_CONSIGNACAO", allocationSize = 1)
public class Consignacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SQ_ID_CONSIGNACAO", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_CONSIGNACAO")
	private long id;

	@Column(name = "CD_ANOMES_FIM")
	private String cdAnomesFim;
	
	@Column(name = "CD_ANOMES_FIM_REFERENCIA")
	private String cdAnomesFimReferencia;


	@Column(name = "CD_ANOMES_INICIO")
	private String cdAnomesInicio;

	@Column(name = "DS_IDENTIFICADOR", length = 50)
	private String dsIdentificador;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_RESERVA", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dtReserva;

	@Column(name = "NR_ADE")
	private long nrAde;

	@NotNull(message = "Agência inválida.")
	@Column(name = "NR_AGENCIA", length = 4)
	private String nrAgencia;

	@NotNull(message = "Número de banco inválido")
	@Column(name = "NR_BANCO", length = 4)
	private String nrBanco;

	@Column(name = "NR_CARENCIA", columnDefinition = "NUMBER(10) default 0")
	private Long nrCarencia = 0L;

	@Column(name = "NR_CONTA")
	private String nrConta;

	@Column(name = "NR_INDICE", length = 2)
	private String nrIndice;

	@Column(name = "NR_PRESTACOES")
	private Long nrPrestacoes;

	@Column(name = "VL_CET")
	private BigDecimal vlCet;

	@Column(name = "VL_LIQUIDO_LIBERADO")
	private BigDecimal vlLiquidoLiberado;

	@Column(name = "VL_PRESTACAO")
	private BigDecimal vlPrestacao;

	// bi-directional many-to-one association to OrigemConsignacao
	@ManyToOne
	@JoinColumn(name = "ID_ORIGEM_CONSIGNACAO")
	private OrigemConsignacao origemConsignacao;

	// bi-directional many-to-one association to ServicoConsig
	@ManyToOne
	@JoinColumn(name = "ID_SERVICO_CONSIG")
	private ServicoConsig servicoConsig;

	// bi-directional many-to-one association to ServidorConsig
	@ManyToOne
	@JoinColumn(name = "ID_SERVIDOR_CONSIG", insertable = true)
	private ServidorConsig servidorConsig;

	// bi-directional many-to-one association to StatusConsignacao
	@ManyToOne
	@JoinColumn(name = "ID_STATUS_CONSIGNACAO")
	private StatusConsignacao statusConsignacao;

	// bi-directional many-to-one association to TerminoConsignacao
	@ManyToOne
	@JoinColumn(name = "ID_TERMINO_CONSIGNACAO")
	private TerminoConsignacao terminoConsignacao;

	// bi-directional many-to-one association to HistoricoConsignacao
	@OneToMany(mappedBy = "consignacao", cascade = CascadeType.ALL)
	private List<HistoricoConsignacao> historicoConsignacaos;

	// bi-directional many-to-one association to ParcelaConsignacao
	@OneToMany(mappedBy = "consignacao", cascade = CascadeType.ALL)
	private List<ParcelaConsignacao> parcelaConsignacaos;

	// private List<ParcelaConsignacao> parcelasPagas;

	public Consignacao() {
	}

	public Consignacao(ServicoConsig servicoConsig, ServidorConsig servidorConsig) {
		this.servicoConsig = servicoConsig;
		this.servidorConsig = servidorConsig;
	}
	
	public Consignacao(ServicoConsig servicoConsig, ServidorConsig servidorConsig, BigDecimal vlLiquidoLiberado, 
						BigDecimal vlPrestacao,  BigDecimal vlCet, Long nrPrestacoes) {
		this.servicoConsig = servicoConsig;
		this.servidorConsig = servidorConsig;
		this.vlLiquidoLiberado = vlLiquidoLiberado;
		this.vlPrestacao = vlPrestacao;
		this.vlCet = vlCet;
		this.nrPrestacoes = nrPrestacoes;
	}

	public Consignacao(long id, String cdAnomesFim, String cdAnomesInicio, String dsIdentificador, Date dtReserva,
			long nrAde, String nrAgencia, String nrBanco, Long nrCarencia, String nrConta, String nrIndice,
			Long nrPrestacoes, BigDecimal vlCet, BigDecimal vlLiquidoLiberado, BigDecimal vlPrestacao,
			OrigemConsignacao origemConsignacao, ServicoConsig servicoConsig, ServidorConsig servidorConsig,
			StatusConsignacao statusConsignacao, TerminoConsignacao terminoConsignacao,
			List<HistoricoConsignacao> historicoConsignacaos, List<ParcelaConsignacao> parcelaConsignacaos) {
		super();
		this.id = id;
		this.cdAnomesFim = cdAnomesFim;
		this.cdAnomesInicio = cdAnomesInicio;
		this.dsIdentificador = dsIdentificador;
		this.dtReserva = dtReserva;
		this.nrAde = nrAde;
		this.nrAgencia = nrAgencia;
		this.nrBanco = nrBanco;
		this.nrCarencia = nrCarencia;
		this.nrConta = nrConta;
		this.nrIndice = nrIndice;
		this.nrPrestacoes = nrPrestacoes;
		this.vlCet = vlCet;
		this.vlLiquidoLiberado = vlLiquidoLiberado;
		this.vlPrestacao = vlPrestacao;
		this.origemConsignacao = origemConsignacao;
		this.servicoConsig = servicoConsig;
		this.servidorConsig = servidorConsig;
		this.statusConsignacao = statusConsignacao;
		this.terminoConsignacao = terminoConsignacao;
		this.historicoConsignacaos = historicoConsignacaos;
		this.parcelaConsignacaos = parcelaConsignacaos;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long idConsignacao) {
		this.id = idConsignacao;
	}

	public String getCdAnomesFim() {
		return this.cdAnomesFim;
	}

	public void setCdAnomesFim(String cdAnomesFim) {
		this.cdAnomesFim = cdAnomesFim;
	}

	public String getCdAnomesInicio() {
		return this.cdAnomesInicio;
	}

	public void setCdAnomesInicio(String cdAnomesInicio) {
		this.cdAnomesInicio = cdAnomesInicio;
	}

	public String getDsIdentificador() {
		return this.dsIdentificador;
	}

	public void setDsIdentificador(String dsIdentificador) {
		this.dsIdentificador = dsIdentificador;
	}

	public Date getDtReserva() {
		return this.dtReserva;
	}

	public void setDtReserva(Date dtReserva) {
		this.dtReserva = dtReserva;
	}

	public long getNrAde() {
		return this.nrAde;
	}

	public void setNrAde(long nrAde) {
		this.nrAde = nrAde;
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
	public EntidadeConsig getEntidadeConsig() {
		return servicoConsig.getEntidadeConsig();
	}
	public Long getNrCarencia() {
		return this.nrCarencia;
	}

	public void setNrCarencia(Long nrCarencia) {
		this.nrCarencia = nrCarencia;
	}

	public String getNrConta() {
		return this.nrConta;
	}

	public void setNrConta(String nrConta) {
		this.nrConta = nrConta;
	}

	public String getNrIndice() {
		return this.nrIndice;
	}

	public void setNrIndice(String nrIndice) {
		this.nrIndice = nrIndice;
	}

	public Long getNrPrestacoes() {
		return this.nrPrestacoes;
	}

	public void setNrPrestacoes(Long nrPrestacoes) {
		this.nrPrestacoes = nrPrestacoes;
	}

	public BigDecimal getVlCet() {
		return this.vlCet;
	}

	public void setVlCet(BigDecimal vlCet) {
		this.vlCet = vlCet;
	}

	public BigDecimal getVlLiquidoLiberado() {
		return this.vlLiquidoLiberado;
	}

	public void setVlLiquidoLiberado(BigDecimal vlLiquidoLiberado) {
		this.vlLiquidoLiberado = vlLiquidoLiberado;
	}

	public BigDecimal getVlPrestacao() {
		return this.vlPrestacao;
	}

	public void setVlPrestacao(BigDecimal vlPrestacao) {
		this.vlPrestacao = vlPrestacao;
	}

	public OrigemConsignacao getOrigemConsignacao() {
		return this.origemConsignacao;
	}

	public void setOrigemConsignacao(OrigemConsignacao TOrigemConsignacao) {
		this.origemConsignacao = TOrigemConsignacao;
	}

	public ServicoConsig getServicoConsig() {
		return this.servicoConsig;
	}

	public void setServicoConsig(ServicoConsig servicoConsig) {
		this.servicoConsig = servicoConsig;
	}

	public ServidorConsig getServidorConsig() {
		return this.servidorConsig;
	}

	public void setServidorConsig(ServidorConsig servidorConsig) {
		this.servidorConsig = servidorConsig;
	}

	public StatusConsignacao getStatusConsignacao() {
		return this.statusConsignacao;
	}

	public void setStatusConsignacao(StatusConsignacao TStatusConsignacao) {
		this.statusConsignacao = TStatusConsignacao;
	}

	public TerminoConsignacao getTerminoConsignacao() {
		return this.terminoConsignacao;
	}

	public void setTerminoConsignacao(TerminoConsignacao TTerminoConsignacao) {
		this.terminoConsignacao = TTerminoConsignacao;
	}

	public List<HistoricoConsignacao> getHistoricoConsignacaos() {
		List<HistoricoConsignacao> historicos = this.historicoConsignacaos;
		Collections.sort(historicos, new Comparator<HistoricoConsignacao>() {
			public int compare(HistoricoConsignacao self, HistoricoConsignacao other) {
				if (self.getId()>=other.getId())
					return -1;
				else 
					return 1;
			}
		});
		return historicos;
	}

	public void setHistoricoConsignacaos(List<HistoricoConsignacao> THistoricoConsignacaos) {
		this.historicoConsignacaos = THistoricoConsignacaos;
	}

	public HistoricoConsignacao addTHistoricoConsignacao(HistoricoConsignacao THistoricoConsignacao) {
		getHistoricoConsignacaos().add(THistoricoConsignacao);
		THistoricoConsignacao.setConsignacao(this);

		return THistoricoConsignacao;
	}

	public HistoricoConsignacao removeTHistoricoConsignacao(HistoricoConsignacao THistoricoConsignacao) {
		getHistoricoConsignacaos().remove(THistoricoConsignacao);
		THistoricoConsignacao.setConsignacao(null);

		return THistoricoConsignacao;
	}

	public List<ParcelaConsignacao> getParcelaConsignacaos() {
		List<ParcelaConsignacao> parcelas = this.parcelaConsignacaos;
		Collections.sort(parcelas, new Comparator<ParcelaConsignacao>() {
			public int compare(ParcelaConsignacao self, ParcelaConsignacao other) {
				if (self.getNrParcela() < other.getNrParcela())
					return 1;
				return -1;
			}
		});
		return parcelas;
	}

	public BigDecimal getValorTotal() {
		return this.vlPrestacao.multiply(new BigDecimal((this.nrPrestacoes)));
	}

	public BigDecimal getTotalEncargos() {
		return this.getValorTotal().subtract(this.vlLiquidoLiberado);
	}

	public void setParcelaConsignacaos(List<ParcelaConsignacao> TParcelaConsignacaos) {
		this.parcelaConsignacaos = TParcelaConsignacaos;
	}

	public ParcelaConsignacao addTParcelaConsignacao(ParcelaConsignacao TParcelaConsignacao) {
		getParcelaConsignacaos().add(TParcelaConsignacao);
		TParcelaConsignacao.setConsignacao(this);

		return TParcelaConsignacao;
	}
	
	public String getCdAnomesFimReferencia() {
		return cdAnomesFimReferencia;
	}

	public void setCdAnomesFimReferencia(String cdAnomesFimReferencia) {
		this.cdAnomesFimReferencia = cdAnomesFimReferencia;
	}

	public ParcelaConsignacao removeTParcelaConsignacao(ParcelaConsignacao TParcelaConsignacao) {
		getParcelaConsignacaos().remove(TParcelaConsignacao);
		TParcelaConsignacao.setConsignacao(null);

		return TParcelaConsignacao;
	}

	public List<ParcelaConsignacao> getParcelasPagas() {
		List<ParcelaConsignacao> parcelas = parcelaConsignacaos;
		List<ParcelaConsignacao> pagas = new ArrayList<ParcelaConsignacao>();
		for (ParcelaConsignacao parcela : parcelas) 
			if (parcela.getStatusParcela().getId() == 2 || parcela.getStatusParcela().getId() == 7 || parcela.getStatusParcela().getId() == 10) {
				pagas.add(parcela);
			}
		return pagas;
	}
	/**
	 * @TODO verificar em quais situacoes as prestacoes das consignacoes antigas aumentarao a nova margem e verificar como atualizar a margem 
	 * @author simaoags
	 */
	public boolean isRenegociavel() {
		boolean renegociavel = true;
		if (statusConsignacao.getId() ==7 || statusConsignacao.getId() == 15)
			renegociavel = false;		
		return renegociavel;
	}
	public List<ParcelaConsignacao> getParcelasNaoPagas() {
		List<ParcelaConsignacao> parcelas = parcelaConsignacaos;
		List<ParcelaConsignacao> naoPagas = new ArrayList<ParcelaConsignacao>();
		for (ParcelaConsignacao parcela : parcelas) 
			if (parcela.getStatusParcela().getId() != 2 && parcela.getStatusParcela().getId() != 7 && parcela.getStatusParcela().getId() != 10) {
				naoPagas.add(parcela);
			}
		return naoPagas;
	}
	public BigDecimal getCetAnual() {
		if (getVlCet() == null) return new BigDecimal(0);
		return CalculoUtil.cetMensalToAnual(this.getVlCet().doubleValue());
	}
	public String getNrOrdem() {
		return servidorConsig.getPesfis().getNrOrdem();
	}
}
