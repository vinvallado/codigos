package br.ccasj.sisauc.auditoriaretrospectiva.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.dao.AuditoriaRetrospectivaDAO;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.historico.domain.HistoricoAuditoriaRetrospectiva;
import br.ccasj.sisauc.auditoriaretrospectiva.service.AuditoriaRetrospectivaService;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoItemGAB;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.dao.GenericEntityHistoricoDAO;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "auditoriaRetrospectivaServiceImpl")
public class AuditoriaRetrospectivaServiceImpl implements AuditoriaRetrospectivaService {

	private static final String MENSAGEM_AUTOMATICA_REPLICAR_VALOR = "Automático. Valor Estimado e Apresentado iguais.";
	
	private static final String MENSAGEM_ERRO_NECESSARIO_VALOR_FINAL = "Para Finalizar a auditoria de um Item de GAB é necessário inserir o Valor Final!";
	
	private static final String MENSAGEM_ERRO_DIVERGENCIA_QUATRO_VALORES = "Caso haja uma divergência entre o Valor Estimado, Valor Apresentado, Valor Auditado e Valor Final"
			+ ", para Finalizar a auditoria de um Item de GAB é necessário inserir a justificativa do Valor Final!";
	
	private static final String MENSAGEM_ERRO_NECESSARIO_VALOR_AUDITADO = "Para marcar um Item de GAB como Não Conforme é necessário inserir o valor auditado!";
	
	private static final String MENSAGEM_ERRO_NECESSARIO_JUSTIFICATIVA_VALOR_AUDITADO = "Para marcar um Item de GAB como Não Conforme é necessário inserir a justificativa do valor auditado!";
	
	@Autowired
	private AuthenticationContext authenticationContext;
	
	@Autowired
	private AuditoriaRetrospectivaDAO auditoriaRetrospectivaDAO;
	@Autowired
	private ItemGABDAO itemGabDao;
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDAO;

	@Autowired
	private GenericEntityHistoricoDAO<HistoricoAuditoriaRetrospectiva> genericEntityHistoricoDAO;

	@Override
	public AuditoriaRetrospectiva salvar(AuditoriaRetrospectiva auditoriaRetrospectiva, ItemGAB itemGAB) {
		AuditoriaRetrospectiva auditoriaSalva = this.auditoriaRetrospectivaDAO.merge(auditoriaRetrospectiva);
		genericEntityHistoricoDAO.merge(new HistoricoAuditoriaRetrospectiva(auditoriaSalva, itemGAB));
		return auditoriaSalva;
	}
	
	@Override
	public AuditoriaRetrospectiva salvarSemFinalizar(AuditoriaRetrospectiva auditoriaRetrospectiva, ItemGAB itemGAB) {
		AuditoriaRetrospectiva auditoriaSalva = auditoriaRetrospectivaDAO.merge(auditoriaRetrospectiva);
		itemGAB.setAuditoriaRetrospectiva(auditoriaSalva);
		if (EstadoItemGAB.AGUARDANDO_AUDITORIA.equals(itemGAB.getEstadoItemGAB())) {
			itemGAB.setEstadoItemGAB(EstadoItemGAB.AUDITORIA_INICIADA);			
		}
		itemGabDao.merge(itemGAB);
		genericEntityHistoricoDAO.merge(new HistoricoAuditoriaRetrospectiva(auditoriaSalva, itemGAB));
		gabDAO.atualizarEstadoGABAoRealizarAuditoriaRetrospectiva(itemGAB.getGab().getId());
		return auditoriaSalva;
	}
	
	@Override
	public AuditoriaRetrospectiva finalizarAuditoriaConforme(AuditoriaRetrospectiva auditoriaRetrospectiva, ItemGAB itemGab) {
		validarFinalizarAuditoriaConforme(itemGab.getValorTotal(), auditoriaRetrospectiva);
		auditoriaRetrospectiva.setAuditorRetrospectivo(authenticationContext.getUsuarioLogado());
		AuditoriaRetrospectiva auditoriaSalva = auditoriaRetrospectivaDAO.merge(auditoriaRetrospectiva);
		itemGab.setAuditoriaRetrospectiva(auditoriaSalva);
		itemGab.setEstadoItemGAB(EstadoItemGAB.CONFORME);
		itemGabDao.merge(itemGab);
		genericEntityHistoricoDAO.merge(new HistoricoAuditoriaRetrospectiva(auditoriaSalva, itemGab));
		gabDAO.atualizarEstadoGABAoRealizarAuditoriaRetrospectiva(itemGab.getGab().getId());
		return auditoriaSalva;
	}

	@Override
	public AuditoriaRetrospectiva finalizarAuditoriaNaoConforme(AuditoriaRetrospectiva auditoriaRetrospectiva, ItemGAB itemGAB) {
		Double valorAuditado = auditoriaRetrospectiva.getValorAuditado();
		if ((valorAuditado==null) || (valorAuditado <= 0)) {
			throw new SystemRuntimeException(MENSAGEM_ERRO_NECESSARIO_VALOR_AUDITADO);
		}
		if (auditoriaRetrospectiva.getMotivo() == null){
			throw new SystemRuntimeException(MENSAGEM_ERRO_NECESSARIO_JUSTIFICATIVA_VALOR_AUDITADO);
		}
		AuditoriaRetrospectiva auditoriaSalva = auditoriaRetrospectivaDAO.merge(auditoriaRetrospectiva);
		itemGAB.setAuditoriaRetrospectiva(auditoriaSalva);
		itemGAB.setEstadoItemGAB(EstadoItemGAB.NAO_CONFORME);
		itemGabDao.merge(itemGAB);
		genericEntityHistoricoDAO.merge(new HistoricoAuditoriaRetrospectiva(auditoriaSalva, itemGAB));
		gabDAO.atualizarEstadoGABAoRealizarAuditoriaRetrospectiva(itemGAB.getGab().getId());
		return auditoriaSalva;
	}

	public void validarFinalizarAuditoriaConforme(Double valorEstimado, AuditoriaRetrospectiva auditoriaRetrospectiva) {
		
		if (valorEstimado != null && auditoriaRetrospectiva.getValorApresentado() != null 
				&& (auditoriaRetrospectiva.getValorApresentado().compareTo(valorEstimado) == 0
				&& auditoriaRetrospectiva.getValorAuditado().compareTo(0.00d) == 0
				&& auditoriaRetrospectiva.getValorFinal().compareTo(0.00d) == 0)) {
			
			//Valor apresentado e estimado são iguais e valor auditado e final são zero.
			auditoriaRetrospectiva.setValorAuditado(auditoriaRetrospectiva.getValorApresentado());
			auditoriaRetrospectiva.setValorFinal(auditoriaRetrospectiva.getValorApresentado());
			auditoriaRetrospectiva.setJustificativaValorAuditado(MENSAGEM_AUTOMATICA_REPLICAR_VALOR);
			auditoriaRetrospectiva.setJustificativaValorFinal(MENSAGEM_AUTOMATICA_REPLICAR_VALOR);
			return;
			}
	
		if (auditoriaRetrospectiva.getValorFinal() != null && auditoriaRetrospectiva.getValorFinal()<=0) {
			throw new SystemRuntimeException(MENSAGEM_ERRO_NECESSARIO_VALOR_FINAL);
		}
		
		//Caso os 4 valores não sejam iguais é necessário justificar o valor final
		if (!(	(auditoriaRetrospectiva.getValorFinal().compareTo(auditoriaRetrospectiva.getValorApresentado()) == 0) && 
				(auditoriaRetrospectiva.getValorApresentado().compareTo(auditoriaRetrospectiva.getValorAuditado()) == 0) &&
				(auditoriaRetrospectiva.getValorAuditado().compareTo(valorEstimado) == 0)
				)){
			String justificativaValorFinal = auditoriaRetrospectiva.getJustificativaValorFinal();
			if (justificativaValorFinal == null || justificativaValorFinal.isEmpty()){
				throw new SystemRuntimeException(MENSAGEM_ERRO_DIVERGENCIA_QUATRO_VALORES);
			}
		}
	}

	
	
}
