package br.ccasj.sisauc.administracao.sistema.domain;


import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "configuracao_ws_sigpes", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class ConfiguracaoWSSigpes extends EntidadeGenerica {
	
	private static final long serialVersionUID = -8899751900086395834L;
	
	private String urlWSDL;
	private String urlServico;
	private String nomeServico;
	private String usuario;
	private String senha;
	private Date horaSincronizacao;
	
	public ConfiguracaoWSSigpes() {
		
	}
	
	@Id
	@Override
	public Integer getId() {
		return id;
	}

	@Column(name = "url_wsdl")
	public String getUrlWSDL() {
		return urlWSDL;
	}

	@Column(name = "url_servico")
	public String getUrlServico() {
		return urlServico;
	}

	@Column(name = "nome_servico")
	public String getNomeServico() {
		return nomeServico;
	}

	@Column(name = "usuario")
	public String getUsuario() {
		return usuario;
	}

	@Column(name = "senha")
	public String getSenha() {
		return senha;
	}

	public void setUrlWSDL(String urlWSDL) {
		this.urlWSDL = urlWSDL;
	}

	public void setUrlServico(String urlServico) {
		this.urlServico = urlServico;
	}

	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Column(name = "hora_diaria_sincronizacao")
	@Temporal(TemporalType.TIME)
	public Date getHoraSincronizacao() {
		return horaSincronizacao;
	}
	
	public void setHoraSincronizacao(Date hora) {
		this.horaSincronizacao = hora;
	}
	
}
