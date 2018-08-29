package br.ccasj.sisauc.auditoriaretrospectiva.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarRegionalDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.NotaFiscalDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.Lote;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.NotaFiscal;
import br.ccasj.sisauc.auditoriaretrospectiva.service.GerenciarLoteService;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "gerenciarLoteServiceImpl")
public class GerenciarLoteServiceImpl implements GerenciarLoteService {

	@Autowired
	private GerenciarLoteDAO loteDao;
	@Autowired
	private OrganizacaoMilitarRegionalDAO organizacaoMilitarRegionalDAO;
	@Autowired
	private ItemGABDAO itemGABDAO;
	@Autowired
	private NotaFiscalDAO notaFiscalDao;
	@Autowired
	private GerenciarLoteDAO gerenciarLoteDao;

	@Override
	public void salvar(Lote lote, boolean conformidade) {
		if (lote.getItensGab() == null || lote.getItensGab().isEmpty()) {
			throw new SystemRuntimeException("É necessário escolher algum item");
		} else {
			verificarSeItensJahPossuemLote(lote.getItensGab());
			lote.setOrganizacaoMilitar(organizacaoMilitarRegionalDAO.obterRegionalSistema());
			lote.setConformidade(conformidade);
			verificarNumeracao(lote);
			lote.setValorTotal(SomarValorTotal(lote.getItensGab()));
			loteDao.merge(lote);
		}
	}

	private void verificarSeItensJahPossuemLote(Set<ItemGAB> itensGab) {
		List<String> codigosItensQuePossuemLote = itemGABDAO.obterCodigoItensQuePossuemLote(itensGab);
		if (codigosItensQuePossuemLote.size() > 0){
			StringBuilder sb = new StringBuilder();
			for (String codigoItem: codigosItensQuePossuemLote){
				if (sb.length() > 0){
					sb.append(", ");
				}
				sb.append(codigoItem);
			}
			sb.insert(0, codigosItensQuePossuemLote.size()==1 ? "O item " : "Os itens ");
			sb.append(codigosItensQuePossuemLote.size()==1 ? " já possui lote." : " já possuem lote.");
			throw new SystemRuntimeException(sb.toString());
		}
	}

	private void verificarNumeracao(Lote lote) {
		if (lote.getId() == null) {
			Calendar calendar = Calendar.getInstance();
			StringBuilder builder = new StringBuilder();
			Integer ano = Integer.valueOf(calendar.get(Calendar.YEAR));
			int quantidade = loteDao.obterQuantidadeLotesPorOrganizacaoMilitarEAno(
					lote.getOrganizacaoMilitar().getId(), ano, lote.isConformidade()) + 1;
			(lote.isConformidade() ? builder.append("LT") : builder.append("NC")).append(String.valueOf(ano))
					.append("/").append(lote.getOrganizacaoMilitar().getSigla()).append("/")
					.append(StringUtils.leftPad(String.valueOf(quantidade), 5, "0"));
			lote.setNumero(builder.toString());
			lote.setDataCriacao(new Date());
		}
	}

	private double SomarValorTotal(Set<ItemGAB> itensGab) {
		double valorFinal = 0;
		for (ItemGAB itemGAB : itensGab) {
			if (itemGAB.getAuditoriaRetrospectiva().getValorFinal() == 0) {
				valorFinal += itemGAB.getAuditoriaRetrospectiva().getValorAuditado();
			} else {
				valorFinal += itemGAB.getAuditoriaRetrospectiva().getValorFinal();
			}
		}
		return valorFinal;
	}

	@Override
	public void informarNotaFiscalLote(Lote lote) {
		validarNotaFiscal(lote.getNotaFiscal());
		if (StringUtils.isEmpty(lote.getNotaFiscal().getNumero())){
			lote.getNotaFiscal().setDataApresentacao(null);
		}
		notaFiscalDao.merge(lote.getNotaFiscal());
		gerenciarLoteDao.merge(lote);
	}

	private void validarNotaFiscal(NotaFiscal notaFiscal) {
		if (StringUtils.isNotEmpty(notaFiscal.getNumero()) && notaFiscal.getDataApresentacao() == null){
			throw new SystemRuntimeException("Data da Nota Fiscal: Campo obrigatório.");
		}
	}

}
