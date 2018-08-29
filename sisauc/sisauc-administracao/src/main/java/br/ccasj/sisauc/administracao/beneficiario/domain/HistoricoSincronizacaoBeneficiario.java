package br.ccasj.sisauc.administracao.beneficiario.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

@Entity
@Table(name = "historico_sincronizacao_beneficiario", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class HistoricoSincronizacaoBeneficiario extends EntidadeGenerica  implements Serializable {
	
	private static final long serialVersionUID = -3616523401688135475L;

	public static final String SEQ_NAME = "historico_sincronizacao_beneficiario_id_seq";
	
	private Date dataHoraInicio;
	private Date dataHoraFim;
	private Integer numeroRegistrosAtualizados;
	private Integer numeroRegistrosErro;
	private EstadoSincronizacao estadoSincronizacao;
	private String resumo;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}
	
	@Column(name = "data_hora_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}
	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
	
	@Column(name = "data_hora_fim")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataHoraFim() {
		return dataHoraFim;
	}
	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}
	
	@Column(name = "numero_registros_atualizados")
	public Integer getNumeroRegistrosAtualizados() {
		return numeroRegistrosAtualizados;
	}
	public void setNumeroRegistrosAtualizados(Integer numeroRegistrosAtualizados) {
		this.numeroRegistrosAtualizados = numeroRegistrosAtualizados;
	}
	
	@Column(name = "numero_registros_erro")
	public Integer getNumeroRegistrosErro() {
		return numeroRegistrosErro;
	}
	
	public void setNumeroRegistrosErro(Integer numeroRegistrosErro) {
		this.numeroRegistrosErro = numeroRegistrosErro;
	}
	
	@Column(name = "estado_sincronizacao")
	@Enumerated(EnumType.STRING)
	public EstadoSincronizacao getEstadoSincronizacao() {
		return estadoSincronizacao;
	}
	public void setEstadoSincronizacao(EstadoSincronizacao estadoSincronizacao) {
		this.estadoSincronizacao = estadoSincronizacao;
	}
	
	@Column(name = "resumo")
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	
	
}
