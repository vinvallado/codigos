package br.ccasj.sisauc.emissaogab.service;

import java.util.List;

import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaProcedimentoAuditoria;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ValoresGuiaApresentacaoBeneficiariosDTO;

public interface GerarGABService {
	
	public GuiaApresentacaoBeneficiario salvar(GuiaApresentacaoBeneficiario gab);
	public List<GuiaApresentacaoBeneficiario> gerarAPartirAuditoriaProspectiva(Integer idAuditoriaProspectiva);
	public ValoresGuiaApresentacaoBeneficiariosDTO calcularValorTotalRespostaProcedimento(RespostaProcedimentoAuditoria resposta);
	public ValoresGuiaApresentacaoBeneficiariosDTO calcularValorTotalProcedimento(ConfiguracaoEditalCredenciadoProcedimento configuracao);

}
