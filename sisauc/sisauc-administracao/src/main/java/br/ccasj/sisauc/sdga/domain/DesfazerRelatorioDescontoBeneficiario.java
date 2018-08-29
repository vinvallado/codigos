package br.ccasj.sisauc.sdga.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;


@Entity
@Table(name="desfazer_relatorio_desconto_beneficiario", schema=EntidadeGenerica.SCHEMA_SDGA)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class DesfazerRelatorioDescontoBeneficiario extends AcaoSDGA {

	private static final long serialVersionUID = -3858138210767216101L;

	private static final String SEQ_NAME = "desfazer_relatorio_desconto_beneficiario_seq";
	
	private RelatorioDescontoBeneficiario relatorio;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SDGA)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}

	@ManyToOne
	@JoinColumn(name="id_relatorio", nullable=false)
	public RelatorioDescontoBeneficiario getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(RelatorioDescontoBeneficiario relatorio) {
		this.relatorio = relatorio;
	}
	
	

}
