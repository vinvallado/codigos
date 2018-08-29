package br.ccasj.sisauc.emissaoar.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ccasj.sisauc.administracao.cadastro.domain.Arquivo;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name="apresentacao_ar", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class ApresentacaoAutorizacaoRessarcimento extends EntidadeGenerica {

	private static final long serialVersionUID = -3585351483235754044L;

	private static final String SEQ_NAME = "apresentacao_ar_id_seq";
	
	private AutorizacaoRessarcimento ar;
	private String numeroNotaFiscal;
	private Date dataNotaFiscal;
	private String cpfCnpjPrestador;
	private String nomePrestador;
	private Arquivo arquivoRequerimento;
	private Arquivo arquivoNotaFiscal;

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}	
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_ar", unique=true)
	public AutorizacaoRessarcimento getAr() {
		return ar;
	}
	
	public void setAr(AutorizacaoRessarcimento ar) {
		this.ar = ar;
	}

	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_arquivo_requerimento", unique=true)
	public Arquivo getArquivoRequerimento() {
		return arquivoRequerimento;
	}
	
	public void setArquivoRequerimento(Arquivo arquivoRequerimento) {
		this.arquivoRequerimento = arquivoRequerimento;
	}

	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_arquivo_nota_fiscal", unique=true)
	public Arquivo getArquivoNotaFiscal() {
		return arquivoNotaFiscal;
	}
	
	public void setArquivoNotaFiscal(Arquivo arquivoNotaFiscal) {
		this.arquivoNotaFiscal = arquivoNotaFiscal;
	}

	@Column(name="numero_nota_fiscal")
	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}
	
	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	@Column(name="data_nota_fiscal")
	@Temporal(TemporalType.DATE)
	public Date getDataNotaFiscal() {
		return dataNotaFiscal;
	}
	
	public void setDataNotaFiscal(Date dataNotaFiscal) {
		this.dataNotaFiscal = dataNotaFiscal;
	}
	
	@Column(name="cpf_cnpj_prestador")
	public String getCpfCnpjPrestador() {
		return cpfCnpjPrestador;
	}
	
	public void setCpfCnpjPrestador(String cpfCnpjPrestador) {
		this.cpfCnpjPrestador = cpfCnpjPrestador;
	}

	@Column(name="nome_prestador")
	public String getNomePrestador() {
		return nomePrestador;
	}
	
	public void setNomePrestador(String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}
	
}