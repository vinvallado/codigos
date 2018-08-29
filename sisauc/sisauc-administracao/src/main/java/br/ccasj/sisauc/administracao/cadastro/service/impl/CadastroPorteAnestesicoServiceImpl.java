package br.ccasj.sisauc.administracao.cadastro.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.PorteAnestesicoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.PorteAnestesico;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroPorteAnestesicoService;
import br.ccasj.sisauc.administracao.historico.domain.HistoricoValorPorteAnestesico;
import br.ccasj.sisauc.framework.dao.GenericEntityHistoricoDAO;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "cadastroPorteAnestesicoService")
public class CadastroPorteAnestesicoServiceImpl implements CadastroPorteAnestesicoService{
	
	private static final long serialVersionUID = 8850692819969872354L;
	
	@Autowired
	private PorteAnestesicoDAO porteAnestesicoDAO;
	
	@Autowired
	private GenericEntityHistoricoDAO<HistoricoValorPorteAnestesico> genericEntityHistoricoDAO;
	
	@Override
	public PorteAnestesico salvar(PorteAnestesico porteAnestesico) {
		verificarUnicidade(porteAnestesico.getId(), porteAnestesico.getCodigo());
		PorteAnestesico porteAnestesicoSalvo = porteAnestesicoDAO.merge(porteAnestesico); 
		genericEntityHistoricoDAO.merge(new HistoricoValorPorteAnestesico(porteAnestesico.getValor(), porteAnestesicoSalvo));
		return porteAnestesicoSalvo;
	}

	private void verificarUnicidade(Integer id,String codigo) {
		if (porteAnestesicoDAO.verificarCodigoPorteAnestesicoExistente(id,codigo)) {
			throw new SystemRuntimeException("O código informado já é existente!");
		}
	}
}
