package br.ccasj.sisauc.emissaoar.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.domain.Arquivo;
import br.ccasj.sisauc.administracao.cadastro.service.ArquivoService;
import br.ccasj.sisauc.emissaoar.dao.ApresentacaoARDAO;
import br.ccasj.sisauc.emissaoar.domain.ApresentacaoAutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.service.ApresentarARService;

@Service("apresentarARService")
@Transactional
public class ApresentarARServiceImpl implements ApresentarARService {

	@Autowired
	private ApresentacaoARDAO apresentacaoARDAO;
	@Autowired
	private ArquivoService arquivoService;

	@Override
	public void apresentar(ApresentacaoAutorizacaoRessarcimento apresentacao) {
		apresentacao.getAr().setEstado(EstadoAR.APRESENTADA);
		apresentacao.getAr().setDataApresentacao(new Date());
		apresentacao.getAr().setMotivoInconsistencia(null);
		configurarArquivos(apresentacao);
		apresentacaoARDAO.merge(apresentacao);
	}

	@Override
	public void configurarArquivos(ApresentacaoAutorizacaoRessarcimento apresentacao) {
		if(apresentacao.getArquivoNotaFiscal() != null && apresentacao.getArquivoNotaFiscal().getId() == null){
			apresentacao.getArquivoNotaFiscal().setDataInsercao(new Date());
			Arquivo arquivoNotaFiscal = arquivoService.inserirArquivoNoSistema(apresentacao.getArquivoNotaFiscal(), 
					apresentacao.getAr().getOrganizacaoMilitar().getId(), 
					apresentacao.getArquivoNotaFiscal().getDataInsercao());
			apresentacao.setArquivoNotaFiscal(arquivoNotaFiscal);
		}
		
		if(apresentacao.getArquivoRequerimento() != null && apresentacao.getArquivoRequerimento().getId() == null){
			apresentacao.getArquivoRequerimento().setDataInsercao(new Date());
			Arquivo arquivoRequerimento = arquivoService.inserirArquivoNoSistema(apresentacao.getArquivoRequerimento(), 
					apresentacao.getAr().getOrganizacaoMilitar().getId(), 
					apresentacao.getArquivoRequerimento().getDataInsercao());
			apresentacao.setArquivoRequerimento(arquivoRequerimento);
		}
	}
}