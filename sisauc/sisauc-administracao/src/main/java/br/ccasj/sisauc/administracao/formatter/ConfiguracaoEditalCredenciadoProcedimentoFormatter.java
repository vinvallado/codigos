package br.ccasj.sisauc.administracao.formatter;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.emissaogab.domain.ValoresGuiaApresentacaoBeneficiariosDTO;
import br.ccasj.sisauc.emissaogab.service.GerarGABService;

@Component("configuracaoEditalCredenciadoProcedimentoFormatter")
public class ConfiguracaoEditalCredenciadoProcedimentoFormatter implements SisaucFormatter<ConfiguracaoEditalCredenciadoProcedimento> {
	
	@Autowired
	private GerarGABService gerarGABService;
	@Autowired
	private ConfiguracaoEditalCredenciadoProcedimentoDAO configuracaoDao;

	public String getDescricaoConfiguracao(ConfiguracaoEditalCredenciadoProcedimento configuracao){
		if(configuracao == null){
			return "";
		}
		StringBuilder builder = new StringBuilder();
        NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));  
		builder.append(configuracao.getConfiguracaoEditalCredenciado().getCredenciado().getNomeFantasia());
		builder.append(" - Proc. ");
		builder.append(configuracao.getProcedimento().getCodigo());
		builder.append(" - ");
		builder.append(currency.format(obterValorProcedimento(configuracao)));
		builder.append(" (").append(configuracao.getTipo().getLabel()).append(")");
		return builder.toString();
	}
	
	public String getCodigoNomeSiglaEspecialidade(ConfiguracaoEditalCredenciadoProcedimento configuracao){
		if(configuracao == null){
			return "";
		}
		StringBuilder builder = new StringBuilder(configuracao.getProcedimento().getCodigo())
			.append(" - ").append(configuracao.getProcedimento().getDescricao());
		if(configuracao.getEspecialidade() != null && configuracao.getEspecialidade().getSigla() != null){
			builder.append(" (").append(configuracao.getEspecialidade().getSigla()).append(")");
		}
		return builder.toString();
	}
	
	public String getDescricaoCodigoProcedimentoConfiguracao(ConfiguracaoEditalCredenciadoProcedimento configuracao){
		StringBuilder builder = new StringBuilder();
		builder.append(configuracao.getProcedimento().getCodigo());
		builder.append(" - ");
		builder.append(configuracao.getProcedimento().getDescricao());
		builder.append(" - ");
		builder.append(configuracao.getEspecialidade().getNome());
		builder.append(" - ");
		builder.append(configuracao.getEspecialidade().getSigla());
		builder.append(" - ");
		builder.append(configuracao.getConfiguracaoEditalCredenciado().getCredenciado().getNomeFantasia());
		return builder.toString();
	}
	
	public String getNomeCredenciadoValor(ConfiguracaoEditalCredenciadoProcedimento configuracao){
		StringBuilder builder = new StringBuilder();
        NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));  
		builder.append(configuracao.getConfiguracaoEditalCredenciado().getCredenciado().getNomeFantasia());
		builder.append(" - ");
		builder.append(configuracao.getConfiguracaoEditalCredenciado().getCredenciado().getCidade().getNome());
		builder.append("/");
		builder.append(configuracao.getConfiguracaoEditalCredenciado().getCredenciado().getCidade().getEstado().getSigla());
		builder.append(" - ");
		builder.append(currency.format(obterValorProcedimento(configuracao)));
		return builder.toString();
	}
	
	public String getCredenciadoValor(ConfiguracaoEditalCredenciadoProcedimento configuracao){
		StringBuilder builder = new StringBuilder();
        NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));  
		builder.append(currency.format(obterValorProcedimento(configuracao)));
		return builder.toString();
	}
	
	public double obterValorProcedimento(ConfiguracaoEditalCredenciadoProcedimento configuracao){
		configuracao = configuracaoDao.findById(configuracao.getId());
		ValoresGuiaApresentacaoBeneficiariosDTO dto = gerarGABService.calcularValorTotalProcedimento(configuracao);
		return dto.getValorTotal();
	}
	
	@Override
	public String getAutocompleteLabel(ConfiguracaoEditalCredenciadoProcedimento object) {
		return getDescricaoCodigoProcedimentoConfiguracao(object);
	}
	
}
