package br.mil.fab.consigext.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;


@SqlResultSetMapping(name = "EnderecoAtualCompletoMapping", 
classes = @ConstructorResult
(targetClass = EnderecoPessoaFisica.class, columns = {
		@ColumnResult(name = "cdEndPes",type=String.class), 
		@ColumnResult(name = "nmEnd",type=String.class),
		@ColumnResult(name = "nmBairro",type=String.class),
		@ColumnResult(name = "nrEnd",type=String.class), 
		@ColumnResult(name = "dsComplemento",type=String.class) }))

@NamedNativeQuery(name = "EnderecoPessoaFisica.findByEnderecoAtualCompleto", query = " SELECT PF.CD_END_PES as cdEndPes, " + 
		" PF.NM_END as nmEnd, " + 
		" PF.NM_BAIRRO as nmBairro, " + 
		" PF.NR_END as nrEnd, " +
		" PF.DS_COMPLEMENTO as dsComplemento" + 
		" FROM T_ENDERECO_PESSOA_FISICA PF " + 
		" INNER JOIN  T_LOCALIDADE L ON PF.CD_LOCALD = L.CD_LOCALD " + 
		" WHERE PF.NR_ORDEM = ? " + 
		" AND PF.ST_ATUAL = 'S'",resultSetMapping="EnderecoAtualCompletoMapping")

@Entity
@Table(schema = "SIG", name="T_ENDERECO_PESSOA_FISICA")
@IdClass(EnderecoPessoaFisicaPk.class)
public class EnderecoPessoaFisica implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CD_END_PES")
	private String cdEndPes;
	@Id
	@Column(name="NR_ORDEM")
	private String nrOrdem;
	@Column(name="NM_END")
	private String nmEnd;
	@Column(name="NM_BAIRRO")
	private String nmBairro;
	@Column(name="NR_END")
	private String nrEnd;
	@Column(name="DS_COMPLEMENTO")
	private String dsComplemento;
	@Column(name="ST_ATUAL")
	private String stAtual;
	
	public EnderecoPessoaFisica() {
		
	}
	
	public EnderecoPessoaFisica(String cdEndPes, String pesfisComgep, String nmEnd, String nmBairro, String nrEnd,
			String dsComplemento) {
		super();
		this.cdEndPes = cdEndPes;
		this.nmEnd = nmEnd;
		this.nmBairro = nmBairro;
		this.nrEnd = nrEnd;
		this.dsComplemento = dsComplemento;
	}
	
	public EnderecoPessoaFisica(String cdEndPes, String nmEnd, String nmBairro, String nrEnd,
			String dsComplemento) {
		super();
		this.cdEndPes = cdEndPes;
		this.nmEnd = nmEnd;
		this.nmBairro = nmBairro;
		this.nrEnd = nrEnd;
		this.dsComplemento = dsComplemento;
	}
	
	public String getCdEndPes() {
		return cdEndPes;
	}

	public void setCdEndPes(String cdEndPes) {
		this.cdEndPes = cdEndPes;
	}

	public String getNmEnd() {
		return nmEnd;
	}

	public void setNmEnd(String nmEnd) {
		this.nmEnd = nmEnd;
	}

	public String getNmBairro() {
		return nmBairro;
	}

	public void setNmBairro(String nmBairro) {
		this.nmBairro = nmBairro;
	}

	public String getNrEnd() {
		return nrEnd;
	}

	public void setNrEnd(String nrEnd) {
		this.nrEnd = nrEnd;
	}

	public String getDsComplemento() {
		return dsComplemento;
	}

	public void setDsComplemento(String dsComplemento) {
		this.dsComplemento = dsComplemento;
	}
	
	public String getStAtual() {
		return stAtual;
	}

	public void setStAtual(String stAtual) {
		this.stAtual = stAtual;
	}
	
	public String getNrOrdem() {
		return nrOrdem;
	}

	public void setNrOrdem(String nrOrdem) {
		this.nrOrdem = nrOrdem;
	}


	
}
