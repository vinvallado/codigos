package br.ccasj.sisauc.administracao.historico.domain;

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

import br.ccasj.sisauc.administracao.cadastro.domain.PorteAnestesico;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.EntidadeHistoricoGenerica;

@Entity
@Table(name = "historico_valor_porte_anestesico", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class HistoricoValorPorteAnestesico extends EntidadeHistoricoGenerica{
	
	private static final long serialVersionUID = -3509536193636135397L;
	
	private double valor;
	private PorteAnestesico porteAnestesico;

	public HistoricoValorPorteAnestesico() {
		super();
	}

	public HistoricoValorPorteAnestesico(double valor, PorteAnestesico porteAnestesico) {
		super();
		this.valor = valor;
		this.porteAnestesico = porteAnestesico;
	}

	private static final String SEQ_NAME = "historico_valor_porte_anestesico_id_seq";

	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@ManyToOne
	@JoinColumn(name = "id_porte_anestesico")
	public PorteAnestesico getPorteAnestesico() {
		return porteAnestesico;
	}

	public void setPorteAnestesico(PorteAnestesico porteAnestesico) {
		this.porteAnestesico = porteAnestesico;
	}
	
}
