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

import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;


@Entity
@Table(name="desfazer_cancelamento_are", schema=EntidadeGenerica.SCHEMA_SDGA)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class DesfazerCancelamentoARE extends AcaoSDGA {

	private static final long serialVersionUID = 2220883396890563099L;
	
	private static final String SEQ_NAME = "desfazer_cancelamento_are_seq";
	
	private AutorizacaoRessarcimento are;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SDGA)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return super.getId();
	}
	
	@ManyToOne
	@JoinColumn(name="id_are", nullable=false)
	public AutorizacaoRessarcimento getAre() {
		return are;
	}
	public void setAre(AutorizacaoRessarcimento are) {
		this.are = are;
	}

}
