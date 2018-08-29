package br.mil.fab.consigext.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "SIG", name="T_CFG_BASICA_PGM")
public class CfgBasicaPgm {
	
	@EmbeddedId
	private KeyOfCfgBasicaPgm keyOfCfgBasicaPgm;
	
	
	public KeyOfCfgBasicaPgm getKeyOfCfgBasicaPgm() {
		return keyOfCfgBasicaPgm;
	}

	public void setKeyOfCfgBasicaPgm(KeyOfCfgBasicaPgm keyOfCfgBasicaPgm) {
		this.keyOfCfgBasicaPgm = keyOfCfgBasicaPgm;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_PROCESSAMENTO", nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dtProcessamento;

	public Date getDtProcessamento() {
		return dtProcessamento;
	}

	public void setDtProcessamento(Date dtProcessamento) {
		this.dtProcessamento = dtProcessamento;
	}

	
}



