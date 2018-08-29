package br.ccasj.sisauc.sdga.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.Usuario;

@MappedSuperclass
public abstract class AcaoSDGA extends EntidadeGenerica {

	private static final long serialVersionUID = -5104094815333021761L;

	protected String nomeGuerraAutor;
	protected String justificativa;
	protected Date dataExecucao;
	protected String ip;
	protected String host;
	protected Usuario autor;

	public AcaoSDGA() {
		super();
	}

	public AcaoSDGA(Integer id) {
		super(id);
	}

	public AcaoSDGA(String nomeGuerraAutor, String justificativa,
			Date dataExecucao, String ip, String host) {
		super();
		this.nomeGuerraAutor = nomeGuerraAutor;
		this.justificativa = justificativa;
		this.dataExecucao = dataExecucao;
		this.ip = ip;
		this.host = host;
	}

	public AcaoSDGA(Integer id, String nomeGuerraAutor, String justificativa,
			Date dataExecucao, String ip, String host) {
		super(id);
		this.nomeGuerraAutor = nomeGuerraAutor;
		this.justificativa = justificativa;
		this.dataExecucao = dataExecucao;
		this.ip = ip;
		this.host = host;
	}

	@Column(name="nome_guerra_autor", nullable=true)
	public String getNomeGuerraAutor() {
		return nomeGuerraAutor;
	}

	public void setNomeGuerraAutor(String nomeGuerraAutor) {
		this.nomeGuerraAutor = nomeGuerraAutor;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@Column(name = "data_execucao")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataExecucao() {
		return dataExecucao;
	}

	public void setDataExecucao(Date dataExecucao) {
		this.dataExecucao = dataExecucao;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	@ManyToOne
	@JoinColumn(name="id_autor")
	public Usuario getAutor(){
		return autor;
	}
	
	public void setAutor(Usuario autor){
		this.autor = autor;
	}

}
