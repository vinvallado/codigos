package br.ccasj.sisauc.emissaogab.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.emissaogab.dao.GuiaApresentacaoBeneficiarioDAO;
import br.ccasj.sisauc.emissaogab.domain.EstadoGAB;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.service.AcompanharGABService;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.domain.Perfil;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "acompanharGABService")
public class AcompanharGABServiceImpl implements AcompanharGABService {

	@Autowired
	private GuiaApresentacaoBeneficiarioDAO guiaApresentacaoBeneficiarioDAO;
	@Autowired
	private AuthenticationContext authenticationContext;

	@Override
	public void cancelar(Integer id, String justificativaCancelamentoGAB) {
		if (authenticationContext.getUsuarioLogado().getPerfis().contains(Perfil.PERFIL_CHEFE_FUNSA)) {
			if (justificativaCancelamentoGAB == null || "".equalsIgnoreCase(justificativaCancelamentoGAB.trim())) {
				throw new SystemRuntimeException("É necessário inserir uma justificativa ao cancelar uma GAB");
			}
			GuiaApresentacaoBeneficiario gab = guiaApresentacaoBeneficiarioDAO.findById(id);
			verificarGAB(gab);
			guiaApresentacaoBeneficiarioDAO.cancelarGAB(id, justificativaCancelamentoGAB);
		} else {
			throw new SystemRuntimeException("Somente usuários com o perfil 'Chefe FUNSA' podem cancelar GABs");
		}
	}

	private void verificarGAB(GuiaApresentacaoBeneficiario gab) {
		if (gab.getEstado().equals(EstadoGAB.CANCELADA) || gab.getEstado().equals(EstadoGAB.APRESENTADA)) {
			throw new SystemRuntimeException("Esta GAB não pode ser cancelada pois está APRESENTADA ou CANCELADA");
		}
	}

}
