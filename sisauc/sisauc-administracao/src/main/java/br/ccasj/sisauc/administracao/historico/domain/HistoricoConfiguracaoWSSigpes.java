package br.ccasj.sisauc.administracao.historico.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoWSSigpes;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeHistoricoGenerica;


@Entity
@Table(name = "historico_configuracao_ws_sigpes", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class HistoricoConfiguracaoWSSigpes extends EntidadeHistoricoGenerica implements Serializable {

	private static final long serialVersionUID = -8800186037958875556L;
	private static final String SEQ_NAME = "historico_configuracao_ws_sigpes_id_seq";
	
	private String urlWSDL;
	private String urlServico;
	private String nomeServico;
	private String usuarioWebservice;
	private String senha;
	private Date horaSincronizacao;
	
	public HistoricoConfiguracaoWSSigpes() {
	}
	
	public HistoricoConfiguracaoWSSigpes(ConfiguracaoWSSigpes configuracaoWSSigpes){
		this.urlWSDL = configuracaoWSSigpes.getUrlWSDL();
		this.urlServico = configuracaoWSSigpes.getUrlServico();
		this.nomeServico = configuracaoWSSigpes.getNomeServico();
		this.usuarioWebservice = configuracaoWSSigpes.getUsuario();
		this.senha = configuracaoWSSigpes.getSenha();
		this.horaSincronizacao = configuracaoWSSigpes.getHoraSincronizacao();
	}
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
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

	@Column(name = "usuario_webservice")
	public String getUsuarioWebservice() {
		return usuarioWebservice;
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

	public void setUsuarioWebservice(String usuarioWebservice) {
		this.usuarioWebservice = usuarioWebservice;
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
