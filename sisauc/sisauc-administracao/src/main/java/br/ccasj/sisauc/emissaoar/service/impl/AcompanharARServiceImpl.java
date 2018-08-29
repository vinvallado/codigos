package br.ccasj.sisauc.emissaoar.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;
import br.ccasj.sisauc.emissaoar.service.AcompanharARService;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "acompanharARService")
public class AcompanharARServiceImpl implements AcompanharARService {

	@Autowired
	private AutorizacaoRessarcimentoDAO arDAO;
	@Autowired
	private AuthenticationContext authenticationContext;

	@Override
	public void cancelar(Integer id, String justificativaCancelamentoAr) {
		if (authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_CHEFE_FUNSA)) {
			if (justificativaCancelamentoAr == null || "".equalsIgnoreCase(justificativaCancelamentoAr.trim())) {
				throw new SystemRuntimeException("É necessário inserir uma justificativa ao cancelar uma ARE");
			}
			AutorizacaoRessarcimento ar = arDAO.findById(id);
			verificarAr(ar);
			arDAO.cancelarAR(id, justificativaCancelamentoAr);
		} else {
			throw new SystemRuntimeException("Somente usuários com o perfil 'Chefe FUNSA' podem cancelar ARE");
		}
	}

	private void verificarAr(AutorizacaoRessarcimento ar) {
		if (ar.getEstado().equals(EstadoAR.CANCELADA) || ar.getEstado().equals(EstadoAR.APRESENTADA)) {
			throw new SystemRuntimeException("Esta ARE não pode ser cancelada pois está APRESENTADA ou CANCELADA");
		}
	}

}
