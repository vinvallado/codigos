package br.mil.fab.consigext.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Unique;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
@Table(name="T_USUARIO_CONSIG")
@SequenceGenerator(name="SQ_ID_USUARIO_CONSIG",sequenceName="SQ_ID_USUARIO_CONSIG",allocationSize=1)
public class UsuarioConsig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SQ_ID_USUARIO_CONSIG",strategy=GenerationType.SEQUENCE)
	@Column(name="ID_USUARIO_CONSIG")
	private long id;

	@ManyToOne
	@JoinColumn(name="ID_ENTIDADE_CONSIG")
	private EntidadeConsig entidadeConsig;

	@ManyToOne
	@JoinColumn(name="ID_PERFIL")
	@NotNull
	private Perfil perfil;

	@ManyToOne
	@JoinColumn(name="ID_USUARIO", nullable = true,unique=true)
	private Usuario usuario;
	
	@Column(name="NR_CPF")
	@NotEmpty(message="CPF não pode estar em branco")
	private String nrCpf;

	@ManyToOne
	//@JoinColumn(name="ID_USUARIO_EXTERNO", nullable = true,unique=true)
	//@Column(name="ID_USUARIO_EXTERNO", nullable = true,unique=true)
	@JoinColumn(name="ID_USUARIO_EXTERNO", referencedColumnName="ID_USUARIO")
	private UsuarioExterno usuarioExterno;
	
	@Column(name="NM_USUARIO_CONSIG")
	@Unique
	@NotEmpty(message="Nome do Usuário (apelido) não pode estar em branco")
	private String nmUsuarioConsig;

	@Column(name="ST_USUARIO")
	private long stUsuario;

	@Column(name="ST_EXCLUIDO")
	private long stExcluido;
	
	@Column(name="TP_USUARIO")
	private long tpUsuario;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_VALIDADE")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dtValidade;
	
	//TODO: NAO MEXER
	@Transient
	private PesfisComgep pesfis;

		
	public UsuarioConsig() {
	}
	
	public UsuarioConsig(String nmUsuarioConsig) {
		this.nmUsuarioConsig = nmUsuarioConsig;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	
	public UsuarioExterno getUsuarioExterno() {
		return usuarioExterno;
	}

	public void setUsuarioExterno(UsuarioExterno usuarioExterno) {
		this.usuarioExterno = usuarioExterno;
	}

	public String getNmUsuarioConsig() {
		return nmUsuarioConsig;
	}

	public void setNmUsuarioConsig(String nmUsuarioConsig) {
		this.nmUsuarioConsig = nmUsuarioConsig;
	}

	public long getStUsuario() {
		return stUsuario;
	}

	public void setStUsuario(long stUsuario) {
		this.stUsuario = stUsuario;
	}
	
	public long getStExcluido() {
		return stExcluido;
	}

	public void setStExcluido(long stExcluido) {
		this.stExcluido = stExcluido;
	}

	public long getTpUsuario() {
		return tpUsuario;
	}

	public void setTpUsuario(long tpUsuario) {
		this.tpUsuario = tpUsuario;
	}

	public EntidadeConsig getEntidadeConsig() {
		return entidadeConsig;
	}

	public void setEntidadeConsig(EntidadeConsig entidadeConsig) {
		this.entidadeConsig = entidadeConsig;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNrCpf() {
		return nrCpf;
	}

	public void setNrCpf(String nrCpf) {
		this.nrCpf = nrCpf;
	}

	public PesfisComgep getPesfis() {
		return pesfis;
	}

	public void setPesfis(PesfisComgep pesfis) {
		this.pesfis = pesfis;
	}
	
	public String getNmPessoa() {
		/*if(this.tpUsuario==0){
			return pesfis.getNmPessoa();			
		}
		return this.getUsuarioExterno().getNmPessoa();*/
		return null;
	}

	public Date getDtValidade() {
		return dtValidade;
	}

	public void setDtValidade(Date dtValidade) {
		this.dtValidade = dtValidade;
	}
	
	public boolean isUsuarioFab() {
		return getUsuario() != null;
	}
	
	public boolean isUsuarioExterno() {
		return getUsuarioExterno() != null;
	}


}