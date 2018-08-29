package br.ccasj.sisauc.intendencia.domain.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarRegionalDAO;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.intendencia.dao.RelatorioDescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.EstadoEnvioFolhaPagamento;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;
import br.ccasj.sisauc.intendencia.domain.service.GerarRelatorioDescontoBeneficiarioService;

@Service("gerarRelatorioDescontoBeneficiarioService")
@Transactional
public class GerarRelatorioDescontoBeneficiarioServiceImpl implements GerarRelatorioDescontoBeneficiarioService {

	@Autowired
	private OrganizacaoMilitarRegionalDAO organizacaoMilitarRegionalDAO;
	@Autowired
	private RelatorioDescontoBeneficiarioDAO relatorioDescontoBeneficiarioDAO;
	@Autowired
	private ItemGABDAO itemGABDAO;
	@Autowired
	private AuthenticationContext authenticationContext;
	
	@Override
	public RelatorioDescontoBeneficiario gerarRelatorio(List<ItemGAB> itens) {
		RelatorioDescontoBeneficiario relatorio = new RelatorioDescontoBeneficiario();
		relatorio.setCodigo(obterCodigo());
		relatorio.setAutor(authenticationContext.getUsuarioLogado());
		relatorio.setDataGeracao(new Date());
		for (ItemGAB item: itens){
			if (item.getGab().isIsento()){
				relatorio.getItensIsentos().add(item);
			} else {
				RelatorioDescontoBeneficiarioItem itemRelat = new RelatorioDescontoBeneficiarioItem();
				itemRelat.setEstado(EstadoEnvioFolhaPagamento.NAO_ENVIADO);
				itemRelat.setValorDescontoEnviado(null);
				itemRelat.setItemGab(item);
				itemRelat.setRelatorioDescontoBeneficiario(relatorio);
				relatorio.getItens().add(itemRelat);
			}
		}
		List<ItemGAB> itensGABAtualizar = new ArrayList<ItemGAB>();
		for (ItemGAB itemGabAtualizar : itens) {
			itensGABAtualizar.add(itemGabAtualizar);
		}
		itemGABDAO.atualizarEstadoItens(itensGABAtualizar, EstadoItemGAB.ENVIADO_PARA_DESCONTO);
		return relatorioDescontoBeneficiarioDAO.merge(relatorio);
	}

	private String obterCodigo() {
		int ano = Calendar.getInstance().get(Calendar.YEAR);
		int quantidade = relatorioDescontoBeneficiarioDAO.obterNumeroDoUltimoRelatorioDoAno(ano) + 1;
		return new StringBuilder("RDB" + ano + "/" 
				+ organizacaoMilitarRegionalDAO.obterRegionalSistema().getSigla() + "/" 
				+ StringUtils.leftPad(String.valueOf(quantidade), 5, "0")).toString();
	}


}
