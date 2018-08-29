package br.ccasj.sisauc.administracao.cadastro.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.PorteDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Porte;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroPorteService;
import br.ccasj.sisauc.administracao.historico.domain.HistoricoValorPorte;
import br.ccasj.sisauc.framework.dao.GenericEntityHistoricoDAO;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "cadastroPorteService")
public class CadastroPorteServiceImpl implements CadastroPorteService {

	private static final long serialVersionUID = 6119550112025860134L;
	
	@Autowired
	private PorteDAO porteDAO;
	
	@Autowired
	private GenericEntityHistoricoDAO<HistoricoValorPorte> genericEntityHistoricoDAO;
	
	@Override
	public Porte salvar(Porte porte) {
		verificarUnicidade(porte.getId(),porte.getCodigo());
		Porte porteSalvo = porteDAO.merge(porte);
		genericEntityHistoricoDAO.merge(new HistoricoValorPorte(porte.getValor(), porteSalvo));
		return porteSalvo;
	}
	
	

	private void verificarUnicidade(Integer id,String codigo) {
		if (porteDAO.verificarCodigoPorteExistente(id,codigo)) {
			throw new SystemRuntimeException("O código informado já é existente!");
		}
	}

}
