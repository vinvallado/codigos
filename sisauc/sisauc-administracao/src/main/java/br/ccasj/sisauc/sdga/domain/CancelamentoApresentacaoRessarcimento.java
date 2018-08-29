package br.ccasj.sisauc.sdga.domain;

import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;

import javax.persistence.*;

/**
 * Created by douglasdpr on 14/10/2016.
 */

@Entity
@Table(name="cancelamento_apresentacao_ressarcimento", schema= EntidadeGenerica.SCHEMA_SDGA)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class CancelamentoApresentacaoRessarcimento extends AcaoSDGA {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6956642185082422570L;

	private static final String SEQ_NAME = "cancelamento_apresentacao_ar_seq";

	private AutorizacaoRessarcimento ar;

	@Id
    @SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SDGA)
    @GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
    @Override
    public Integer getId() {
        return super.getId();
    }

    @ManyToOne
    @JoinColumn(name="id_ar", nullable=false)
    public AutorizacaoRessarcimento getAr() {
        return ar;
    }

    public void setAr(AutorizacaoRessarcimento ar) {
        this.ar = ar;
    }
}
