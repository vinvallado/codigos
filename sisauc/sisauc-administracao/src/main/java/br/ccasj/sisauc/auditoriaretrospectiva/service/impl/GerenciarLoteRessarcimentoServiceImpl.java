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
import br.ccasj.sisauc.auditoriaretrospectiva.dao.GerenciarLoteRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.LoteRessarcimento;
import br.ccasj.sisauc.auditoriaretrospectiva.service.GerenciarLoteRessarcimentoService;
import br.ccasj.sisauc.emissaoar.dao.ItemARDAO;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "gerenciarLoteRessarcimentoServiceImpl")
public class GerenciarLoteRessarcimentoServiceImpl implements GerenciarLoteRessarcimentoService {

	@Autowired
	private GerenciarLoteRessarcimentoDAO loteRessarcimentoDao;
	@Autowired
	private OrganizacaoMilitarRegionalDAO organizacaoMilitarRegionalDAO;
	@Autowired
	private ItemARDAO itemARDAO;

	@Override
	public void salvar(LoteRessarcimento loteRessarcimento) {
		if (loteRessarcimento.getItensAr() == null || loteRessarcimento.getItensAr().isEmpty()) {
			throw new SystemRuntimeException("É necessário escolher algum item");
		} else {
			verificarSeItensJahPossuemLoteRessarcimento(loteRessarcimento.getItensAr());
			loteRessarcimento.setOrganizacaoMilitar(organizacaoMilitarRegionalDAO.obterRegionalSistema());
			verificarNumeracao(loteRessarcimento);
			loteRessarcimento.setValorTotalRessarcir(SomarValorTotal(loteRessarcimento.getItensAr()));
			loteRessarcimentoDao.merge(loteRessarcimento);
		}
	}

	private void verificarSeItensJahPossuemLoteRessarcimento(Set<ItemAR> itensAr) {
		List<String> codigosItensQuePossuemLoteRessarcimento = itemARDAO.obterCodigoItensQuePossuemLoteRessarcimento(itensAr);
		if (codigosItensQuePossuemLoteRessarcimento.size() > 0){
			StringBuilder sb = new StringBuilder();
			for (String codigoItem: codigosItensQuePossuemLoteRessarcimento){
				if (sb.length() > 0){
					sb.append(", ");
				}
				sb.append(codigoItem);
			}
			sb.insert(0, codigosItensQuePossuemLoteRessarcimento.size()==1 ? "O item " : "Os itens ");
			sb.append(codigosItensQuePossuemLoteRessarcimento.size()==1 ? " já possui lote de ressarcimento." : " já possuem lote de ressarcimento.");
			throw new SystemRuntimeException(sb.toString());
		}
	}

	private void verificarNumeracao(LoteRessarcimento loteRessarcimento) {
		if (loteRessarcimento.getId() == null) {
			Calendar calendar = Calendar.getInstance();
			StringBuilder builder = new StringBuilder();
			Integer ano = Integer.valueOf(calendar.get(Calendar.YEAR));
			int quantidade = loteRessarcimentoDao.obterQuantidadeLotesRessarcimentoPorOrganizacaoMilitarEAno(
					loteRessarcimento.getOrganizacaoMilitar().getId(), ano) + 1;
			(builder.append("LR")).append(String.valueOf(ano))
					.append("/").append(loteRessarcimento.getOrganizacaoMilitar().getSigla()).append("/")
					.append(StringUtils.leftPad(String.valueOf(quantidade), 5, "0"));
			loteRessarcimento.setNumero(builder.toString());
			loteRessarcimento.setDataCriacao(new Date());
		}
	}

	private double SomarValorTotal(Set<ItemAR> itensAr) {
		double valorFinal = 0;
		for (ItemAR itemAR : itensAr) {
			valorFinal += itemAR.getAuditoriaRetrospectiva().getValorRessarcimento();
		}
		return valorFinal;
	}

}
