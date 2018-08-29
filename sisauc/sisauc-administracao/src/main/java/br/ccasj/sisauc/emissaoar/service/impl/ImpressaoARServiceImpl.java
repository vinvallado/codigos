package br.ccasj.sisauc.emissaoar.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.service.ImpressaoARService;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "impressaoARService")
public class ImpressaoARServiceImpl implements ImpressaoARService {

	@Autowired
	private AutorizacaoRessarcimentoDAO arDao;
	@Autowired
	private AuthenticationContext authenticationContext;

	public ConfiguracaoSistema configuracaoSistema;

	@Override
	public void imprimir(AutorizacaoRessarcimento ar) {
//		configuracaoSistema = configuracaoSistemaDao.obterConfiguracaoSistema();
//		if (configuracaoSistema != null) {
//
//			if (configuracaoSistema.isBloquearEmissaoGab()) {
//				throw new SystemRuntimeException(
//						"A emissão de AR do sistema está bloqueada. Entre em contato com o Administrador.");
//			}
//		}
		if (ar.getDataEmissao() == null && ar.getEstado() == EstadoAR.GERADA) {
			if (!authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_CHEFE_FUNSA)
					&& !authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_ATENDENTE_FUNSA)) {
				throw new SystemRuntimeException("Apenas usuários com o perfil 'Chefe do FUNSA' podem emitir ARE.");
			}
			if(ar.getUsuarioEmissorAr() == null && ar.getEstado() == EstadoAR.GERADA){
				ar.setUsuarioEmissorAr(authenticationContext.getUsuarioLogado());
				arDao.atualizarUsuarioEmissor(ar.getUsuarioEmissorAr(), ar.getId());
			}
			ar.setEstado(EstadoAR.EMITIDA);
			arDao.atualizarDataAR(new Date(), ar.getId());
			arDao.atualizarEstadoAR(EstadoAR.EMITIDA, ar.getId());
		}
	}

}
