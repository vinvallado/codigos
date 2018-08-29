package br.mil.fab.consigext.service.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.mil.fab.consigext.entity.EntidadeConsig;


public interface ComboUtilService {

	public HashMap<Long,String> findEntidadesConsig();
	public HashMap<Long,String> findServicoEntidade(EntidadeConsig entidadeConsig); 
	public HashMap<String,String> findOrganizacoes();
	public HashMap<Long,String> findMotivos();
	public HashMap<Long,String> findNaturezas();
	public Map<Long, String> getServiceComboByEntidadeConsig(EntidadeConsig ent);
	List<String> getMotivosSuspenderReativarConsignacao();
}
