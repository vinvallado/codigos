package br.ccasj.sisauc.framework.utils.managedbean;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.formatter.BeneficiarioFormatter;
import br.ccasj.sisauc.administracao.formatter.CodigoInternacionalDoencasFormatter;
import br.ccasj.sisauc.administracao.formatter.ConfiguracaoEditalCredenciadoProcedimentoFormatter;
import br.ccasj.sisauc.administracao.formatter.CredenciadoFormatter;
import br.ccasj.sisauc.administracao.formatter.EspecialidadeFormatter;
import br.ccasj.sisauc.administracao.formatter.GABFormatter;
import br.ccasj.sisauc.administracao.formatter.GrupoFormatter;
import br.ccasj.sisauc.administracao.formatter.ItemGABFormatter;
import br.ccasj.sisauc.administracao.formatter.MedicoFormatter;
import br.ccasj.sisauc.administracao.formatter.ProcedimentoFormatter;
import br.ccasj.sisauc.administracao.formatter.SubgrupoFormatter;

@Scope(value = "view")
@Service(value = "formatterBean")
public class FormatterBean implements Serializable {

	private static final long serialVersionUID = 8908148665410971287L;

	private BeneficiarioFormatter beneficiarioFormatter = new BeneficiarioFormatter();
	private ProcedimentoFormatter procedimentoFormatter = new ProcedimentoFormatter();
	private MedicoFormatter medicoFormatter = new MedicoFormatter();
	private CodigoInternacionalDoencasFormatter cidFormatter = new CodigoInternacionalDoencasFormatter();
	@Autowired
	private ConfiguracaoEditalCredenciadoProcedimentoFormatter configuracaoProcedimentoFormatter = new ConfiguracaoEditalCredenciadoProcedimentoFormatter();
	private CredenciadoFormatter credenciadoFormatter = new CredenciadoFormatter();
	private GrupoFormatter grupoFormatter = new GrupoFormatter();
	private SubgrupoFormatter subgrupoFormatter = new SubgrupoFormatter();
	private ItemGABFormatter itemGABFormatter = new ItemGABFormatter();
	private GABFormatter gabFormatter = new GABFormatter();
	private EspecialidadeFormatter especialidadeFormatter = new EspecialidadeFormatter();

	public GrupoFormatter getGrupoFormatter() {
		return grupoFormatter;
	}

	public void setGrupoFormatter(GrupoFormatter grupoFormatter) {
		this.grupoFormatter = grupoFormatter;
	}

	public SubgrupoFormatter getSubgrupoFormatter() {
		return subgrupoFormatter;
	}

	public void setSubgrupoFormatter(SubgrupoFormatter subgrupoFormatter) {
		this.subgrupoFormatter = subgrupoFormatter;
	}

	public BeneficiarioFormatter getBeneficiarioFormatter() {
		return beneficiarioFormatter;
	}

	public void setBeneficiarioFormatter(BeneficiarioFormatter beneficiarioFormatter) {
		this.beneficiarioFormatter = beneficiarioFormatter;
	}

	public ProcedimentoFormatter getProcedimentoFormatter() {
		return procedimentoFormatter;
	}

	public void setProcedimentoFormatter(ProcedimentoFormatter procedimentoFormatter) {
		this.procedimentoFormatter = procedimentoFormatter;
	}

	public MedicoFormatter getMedicoFormatter() {
		return medicoFormatter;
	}

	public void setMedicoFormatter(MedicoFormatter medicoFormatter) {
		this.medicoFormatter = medicoFormatter;
	}

	public CodigoInternacionalDoencasFormatter getCidFormatter() {
		return cidFormatter;
	}

	public void setCidFormatter(CodigoInternacionalDoencasFormatter cidFormatter) {
		this.cidFormatter = cidFormatter;
	}

	/**
	 * @return the configuracaoProcedimentoFormatter
	 */
	public ConfiguracaoEditalCredenciadoProcedimentoFormatter getConfiguracaoProcedimentoFormatter() {
		return configuracaoProcedimentoFormatter;
	}

	/**
	 * @param configuracaoProcedimentoFormatter
	 *            the configuracaoProcedimentoFormatter to set
	 */
	public void setConfiguracaoProcedimentoFormatter(
			ConfiguracaoEditalCredenciadoProcedimentoFormatter configuracaoProcedimentoFormatter) {
		this.configuracaoProcedimentoFormatter = configuracaoProcedimentoFormatter;
	}

	public CredenciadoFormatter getCredenciadoFormatter() {
		return credenciadoFormatter;
	}

	public void setCredenciadoFormatter(CredenciadoFormatter credenciadoFormatter) {
		this.credenciadoFormatter = credenciadoFormatter;
	}

	public ItemGABFormatter getItemGABFormatter() {
		return itemGABFormatter;
	}

	public void setItemGABFormatter(ItemGABFormatter itemGABFormatter) {
		this.itemGABFormatter = itemGABFormatter;
	}

	public GABFormatter getGabFormatter() {
		return gabFormatter;
	}

	public void setGabFormatter(GABFormatter gabFormatter) {
		this.gabFormatter = gabFormatter;
	}

	public EspecialidadeFormatter getEspecialidadeFormatter() {
		return especialidadeFormatter;
	}

	public void setEspecialidadeFormatter(EspecialidadeFormatter especialidadeFormatter) {
		this.especialidadeFormatter = especialidadeFormatter;
	}

}