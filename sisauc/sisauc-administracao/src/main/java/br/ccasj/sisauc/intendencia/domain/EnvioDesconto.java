package br.ccasj.sisauc.intendencia.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "desconto_beneficiario_envio", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class EnvioDesconto extends EntidadeGenerica {
	
	private static final long serialVersionUID = -6698268487906945061L;

	private static final String SEQ_NAME = "desconto_beneficiario_envio_id_seq";

	private EstadoEnvioFolhaPagamento estadoEnvioDesconto;
	private String codigoMensagem;
	private String descricaoMensagem;
	private DescontoBeneficiario descontoBeneficiario;
	private Date dataHoraInicioEnvio;
	private Date dataHoraFimEnvio;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}

	@Column(name="estado_envio")
	@Enumerated(EnumType.STRING)
	public EstadoEnvioFolhaPagamento getEstadoEnvioDesconto() {
		return estadoEnvioDesconto;
	}

	public void setEstadoEnvioDesconto(EstadoEnvioFolhaPagamento estadoEnvioDesconto) {
		this.estadoEnvioDesconto = estadoEnvioDesconto;
	}
	
	@Column(name="codigo_mensagem")
	public String getCodigoMensagem() {
		return codigoMensagem;
	}

	public void setCodigoMensagem(String codigoMensagem) {
		this.codigoMensagem = codigoMensagem;
	}

	@Column(name="descricao_mensagem")
	public String getDescricaoMensagem() {
		return descricaoMensagem;
	}

	public void setDescricaoMensagem(String descricaoMensagem) {
		this.descricaoMensagem = descricaoMensagem;
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_desconto_beneficiario")
	public DescontoBeneficiario getDescontoBeneficiario() {
		return descontoBeneficiario;
	}

	public void setDescontoBeneficiario(DescontoBeneficiario descontoBeneficiario) {
		this.descontoBeneficiario = descontoBeneficiario;
	}

	@Column(name="data_hora_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataHoraInicioEnvio() {
		return dataHoraInicioEnvio;
	}

	public void setDataHoraInicioEnvio(Date dataHoraInicioEnvio) {
		this.dataHoraInicioEnvio = dataHoraInicioEnvio;
	}

	@Column(name="data_hora_fim")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataHoraFimEnvio() {
		return dataHoraFimEnvio;
	}

	public void setDataHoraFimEnvio(Date dataHoraFimEnvio) {
		this.dataHoraFimEnvio = dataHoraFimEnvio;
	}
	
	
}
