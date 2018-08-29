package br.ccasj.sisauc.sdga.service.impl;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.domain.CancelamentoLoteRessarcimento;
import br.ccasj.sisauc.sdga.service.CancelarLoteAREService;

@Transactional
@Service(value = "cancelarLoteAREServiceImpl")
public class CancelarLoteAREServiceImpl implements CancelarLoteAREService {

	@Autowired
	private AcaoSDGADAO acaoSdgaDao;
	@Autowired
	private GerenciarLoteRessarcimentoDAO loteArDao;

	@Override
	public void cancelarLoteAre(CancelamentoLoteRessarcimento cancelamentoARE) {
		LoteRessarcimento loteAR = loteArDao.findById(cancelamentoARE.getLoteRessarcimento().getId());
		cancelamentoARE.setItens(new HashSet<ItemAR>());
		cancelamentoARE.getItens().addAll(loteAR.getItensAr());
		loteAR.setItensAr(new HashSet<ItemAR>());
		loteAR.setValorTotalRessarcir(0);
		loteAR.setCancelado(true);
		acaoSdgaDao.merge(cancelamentoARE);
	}

	@Override
	public void validarCancelamento(int idLote) {
		LoteRessarcimento lote = loteArDao.findById(idLote);
		if (lote != null && lote.getCancelado()) {
			throw new SystemRuntimeException("Lote já foi cancelado.");
		}
	}
}
