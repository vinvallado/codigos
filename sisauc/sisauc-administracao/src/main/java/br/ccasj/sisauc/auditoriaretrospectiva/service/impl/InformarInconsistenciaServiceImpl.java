package br.ccasj.sisauc.auditoriaretrospectiva.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.service.InformarInconsistenciaService;
import br.ccasj.sisauc.emissaoar.dao.AutorizacaoRessarcimentoDAO;
import br.ccasj.sisauc.emissaoar.domain.AutorizacaoRessarcimento;
import br.ccasj.sisauc.emissaoar.domain.EstadoAR;

@Service("informarInconsistenciaService")
@Transactional
public class InformarInconsistenciaServiceImpl implements InformarInconsistenciaService {

	@Autowired
	private AutorizacaoRessarcimentoDAO autorizacaoRessarcimentoDAO;
	
	@Override
	public void informarInconsistencia(AutorizacaoRessarcimento ar) {
		ar.setEstado(EstadoAR.INCONSISTENTE);
		autorizacaoRessarcimentoDAO.merge(ar);
	}

}
