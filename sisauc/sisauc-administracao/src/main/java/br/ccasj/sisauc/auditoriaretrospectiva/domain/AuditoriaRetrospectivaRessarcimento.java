package br.ccasj.sisauc.auditoriaretrospectiva.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.ccasj.sisauc.emissaoar.domain.EspecificacaoItemARE;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.Usuario;

@Entity
@Table(name = "auditoria_retrospectiva_ressarcimento", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class AuditoriaRetrospectivaRessarcimento extends EntidadeGenerica{

	private static final long serialVersionUID = -3069015918000439461L;

	private static final String SEQ_NAME = "auditoria_retrospectiva_ressarcimento_id_seq";

	private Double valorEstimado;
	private Double valorApresentado;
	private Double valorRessarcimento;
	private Boolean naoRealizado = false;
	private String justificativa;
	private List<EspecificacaoItemARE> especificacoes = new ArrayList<EspecificacaoItemARE>();
	private Usuario auditorRetrospectivo;
	private Set<MetadadoValorAuditoriaRetrospectivaRessarcimento> valores;
	
	@Id
	@SequenceGenerator(allocationSize = 1, name = SEQ_NAME, sequenceName = SEQ_NAME, schema = EntidadeGenerica.SCHEMA_SISAUC)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Override
	public Integer getId() {
		return id;
	}	
	
	@Column(name="valor_estimado")
	public Double getValorEstimado() {
		return valorEstimado;
	}

	public void setValorEstimado(Double valorEstimado) {
		this.valorEstimado = valorEstimado;
	}
	
	@Column(name="valor_apresentado")
	public Double getValorApresentado() {
		return valorApresentado;
	}

	public void setValorApresentado(Double valorApresentado) {
		this.valorApresentado = valorApresentado;
	}

	@Column(name="valor_ressarcimento")
	public Double getValorRessarcimento() {
		return valorRessarcimento;
	}

	public void setValorRessarcimento(Double valorRessarcimento) {
		this.valorRessarcimento = valorRessarcimento;
	}

	@Column(name="nao_realizado", nullable=false)
	public Boolean getNaoRealizado() {
		return naoRealizado;
	}

	public void setNaoRealizado(Boolean naoRealizado) {
		this.naoRealizado = naoRealizado;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name= "id_auditoria_retrospectiva_ressarcimento", nullable = false)
	public List<EspecificacaoItemARE> getEspecificacoes() {
		return especificacoes;
	}

	public void setEspecificacoes(List<EspecificacaoItemARE> especificacoes) {
		this.especificacoes = especificacoes;
	}
	
	@Column
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@ManyToOne
	@JoinColumn(name = "id_auditor")
	public Usuario getAuditorRetrospectivo() {
		return auditorRetrospectivo;
	}

	public void setAuditorRetrospectivo(Usuario auditorRetrospectivo) {
		this.auditorRetrospectivo = auditorRetrospectivo;
	}

	@Transient
	public Double getValorCalculado(){
		Double valorCalculado = 0.0d;
		for(EspecificacaoItemARE valoEspecificacaoItemARE : especificacoes){
			valorCalculado += valoEspecificacaoItemARE.getValorCalculado();
		}
		return valorCalculado;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "auditoria_retrospectiva_metadado_valor_auditoria_retrospectiva", schema = EntidadeGenerica.SCHEMA_SISAUC, 
		joinColumns =  @JoinColumn(name = "id_auditoria_retrospectiva"),
		inverseJoinColumns = @JoinColumn(name = "id_metadado_valor_auditoria_retrospectiva")
	)
	public Set<MetadadoValorAuditoriaRetrospectivaRessarcimento> getValores() {
		return valores;
	}

	public void setValores(Set<MetadadoValorAuditoriaRetrospectivaRessarcimento> valores) {
		this.valores = valores;
	}
	
	public double somaValoresMetadados(){
		double resultado = 0;
		for (MetadadoValorAuditoriaRetrospectivaRessarcimento metadadoValorAuditoriaRetrospectivaRessarcimento : valores) {
			resultado += metadadoValorAuditoriaRetrospectivaRessarcimento.getValor();
		}
		return resultado;
	}

}
