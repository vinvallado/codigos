package br.ccasj.sisauc.emissaoar.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;
import br.ccasj.sisauc.auditoriaprospectiva.domain.RespostaRessarcimentoAuditoria;
import br.ccasj.sisauc.emissaoar.dao.ApresentacaoARDAO;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.ApresentacaoAutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.domain.EstadoItemAR;
import br.ccasj.sisauc.emissaoar.domain.ItemAR;
import br.ccasj.sisauc.emissaoar.service.GerarARService;
import br.ccasj.sisauc.framework.utils.EntidadeGenericaUtils;

@Transactional
@Service(value = "gerarARService")
public class GerarARServiceImpl implements GerarARService {

	@Autowired
	private AutorizacaoRessarcimentoDAO arDAO;
	@Autowired
	private AuditoriaProspectivaRessarcimentoDAO auditoriaProspectivaDAO;
	@Autowired
	private ApresentacaoARDAO apresentacaoARDAO;

	@Override
	public synchronized AutorizacaoRessarcimento salvar(AutorizacaoRessarcimento ar) {
		verificarNumeracao(ar);
		return arDAO.merge(ar);
	}
	
	private synchronized void verificarNumeracao(AutorizacaoRessarcimento ar) {
		if(ar.getId() == null){
			Calendar calendar = Calendar.getInstance();
			StringBuilder builder = new StringBuilder();
			builder.append("ARE");
			Integer ano = Integer.valueOf(calendar.get(Calendar.YEAR));
			int quantidade = arDAO.obterQuantidadeARsPorOrganizacaoMilitarEAno(ar.getOrganizacaoMilitar().getId(), ano) + 1;
			builder.append(String.valueOf(ano))
				.append("/").append(ar.getOrganizacaoMilitar().getSigla())
				.append("/").append(StringUtils.leftPad(String.valueOf(quantidade), 5, "0"));
			ar.setCodigo(builder.toString());
			ar.setDataGeracao(new Date());
			numerarItens(ar);
		}
	}
	
	private void numerarItens(AutorizacaoRessarcimento ar) {
		int i = 1;
		for (ItemAR item : ar.getItens()) {
			String sufix = StringUtils.leftPad(String.valueOf(i), 3, "0");
			item.setCodigo(ar.getCodigo() + "-" + sufix);
			i++;
		}
	}	

	@Override
	public AutorizacaoRessarcimento gerarAPartirAuditoriaProspectiva(Integer idAuditoriaProspectiva) {
		AuditoriaProspectivaRessarcimento auditoriaProspectiva = auditoriaProspectivaDAO.findById(idAuditoriaProspectiva);
		AutorizacaoRessarcimento ar = new AutorizacaoRessarcimento();
		ar.setBeneficiario(auditoriaProspectiva.getSolicitacao().getBeneficiario());
		ar.setIsento(auditoriaProspectiva.isIsento());
		ar.setEstado(EstadoAR.GERADA);
		ar.setAuditoriaProspectiva(auditoriaProspectiva);
		ar.setItens(new HashSet<ItemAR>());
		ar.setOrganizacaoMilitar(auditoriaProspectiva.getSolicitacao().getOrganizacaoMilitarSolicitante());
		ar.setDivisao(auditoriaProspectiva.getSolicitacao().getDivisao());
		for (RespostaRessarcimentoAuditoria resposta : auditoriaProspectiva.getProcedimentos()) {
			ar.getItens().add(criarItemPorProcedimento(ar, resposta));
		}
		ar = salvar(ar);
		
		if (auditoriaProspectiva.getSolicitacao().isNaoEletiva()){
			apresentarARNaoEletiva(ar, auditoriaProspectiva);
		}
			
		return ar;
	}

	private void apresentarARNaoEletiva(AutorizacaoRessarcimento ar, AuditoriaProspectivaRessarcimento auditoriaProspectiva) {
		ar.setEstado(EstadoAR.APRESENTADA);
		ar.setDataEmissao(ar.getDataGeracao());
		ar.setDataApresentacao(auditoriaProspectiva.getSolicitacao().getDataInsercaoSistema());
		ApresentacaoAutorizacaoRessarcimento apresentacao = auditoriaProspectiva.getSolicitacao().getApresentacao();
		apresentacao.setAr(ar);
		apresentacaoARDAO.merge(apresentacao);
	}

	private ItemAR criarItemPorProcedimento(AutorizacaoRessarcimento ar, RespostaRessarcimentoAuditoria resposta) {
		ItemAR item = new ItemAR();
		item.setId(EntidadeGenericaUtils.gerarIdNegativo());
		item.setEstadoItemAR(resposta.getAprovado() ? EstadoItemAR.APROVADO : EstadoItemAR.NAO_APROVADO);
		item.setAr(ar);
		item.setProcedimento(resposta.getProcedimento());
		item.setEspecialidade(resposta.getEspecialidade());
		item.setRespostaRessarcimentoAuditoria(resposta);
		item.setObservacaoARE(resposta.getObservacaoARE());
		item.setDente(resposta.getDente());
		item.setFaceDental(resposta.getFaceDental());
		return item;
	}
	



	
}
