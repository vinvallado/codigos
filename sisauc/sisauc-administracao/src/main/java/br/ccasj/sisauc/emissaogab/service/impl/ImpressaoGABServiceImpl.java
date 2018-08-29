package br.ccasj.sisauc.emissaogab.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoSistemaDao;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.service.ImpressaoGABService;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "impressaoGABService")
public class ImpressaoGABServiceImpl implements ImpressaoGABService {

	@Autowired
	private ConfiguracaoSistemaDao configuracaoSistemaDao;
	@Autowired
	private GuiaApresentacaoBeneficiarioDAO gabDao;
	@Autowired
	private AuthenticationContext authenticationContext;

	public ConfiguracaoSistema configuracaoSistema;

	@Override
	public void imprimir(GuiaApresentacaoBeneficiario gab) {
		configuracaoSistema = configuracaoSistemaDao.obterConfiguracaoSistema();
		if (configuracaoSistema != null) {

			if (configuracaoSistema.isBloquearEmissaoGab()) {
				throw new SystemRuntimeException(
						"A emissão de GAB do sistema está bloqueada. Entre em contato com o Administrador.");
			}
		}
		if (gab.getDataEmissao() == null && gab.getEstado() == EstadoGAB.GERADA) {
			if (!authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_CHEFE_FUNSA)
					&& !authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_ATENDENTE_FUNSA)) {
				throw new SystemRuntimeException("Apenas usuários com o perfil 'Chefe do FUNSA' podem emitir GAB's.");
			}
			if(gab.getUsuarioEmissorGab() == null && gab.getEstado() == EstadoGAB.GERADA){
				gab.setUsuarioEmissorGab(authenticationContext.getUsuarioLogado());
				gabDao.atualizarUsuarioEmissor(gab.getUsuarioEmissorGab(), gab.getId());
			}
			gab.setEstado(EstadoGAB.EMITIDA);
			gabDao.atualizarDataGAB(new Date(), gab.getId());
			gabDao.atualizarEstadoGAB(EstadoGAB.EMITIDA, gab.getId());
		}
	}

}
