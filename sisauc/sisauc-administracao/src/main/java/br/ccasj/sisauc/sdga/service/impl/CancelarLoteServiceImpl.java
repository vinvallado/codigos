package br.ccasj.sisauc.sdga.service.impl;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.sdga.dao.AcaoSDGADAO;
import br.ccasj.sisauc.sdga.domain.CancelamentoLote;
import br.ccasj.sisauc.sdga.service.CancelarLoteService;

@Transactional
@Service(value = "cancelarLoteServiceImpl")
public class CancelarLoteServiceImpl implements CancelarLoteService {

	@Autowired
	private AcaoSDGADAO acaoSdgaDao;
	@Autowired
	private GerenciarLoteDAO loteDao;

	@Override
	public void cancelarLote(CancelamentoLote cancelamento) {
		Lote lote = loteDao.findById(cancelamento.getLote().getId());
		validarCancelamento(lote.getId());
		cancelamento.setItens(new HashSet<ItemGAB>());
		cancelamento.getItens().addAll(lote.getItensGab());
		lote.setItensGab(new HashSet<ItemGAB>());
		lote.setValorTotal(0);
		lote.setCancelado(true);
		acaoSdgaDao.merge(cancelamento);
	}

	@Override
	public void validarCancelamento(int idLote) {
		Lote lote = loteDao.findById(idLote);
		if (lote.getNotaFiscal().getNumero() != null && !lote.getNotaFiscal().getNumero().isEmpty()) {
			throw new SystemRuntimeException("Somente lotes sem nota fiscal poderão ser cancelados.");
		}
	}
}
