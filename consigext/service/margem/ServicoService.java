package br.mil.fab.consigext.service.margem;

import java.util.List;

import br.mil.fab.consigext.dto.ServicoDTO;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.ParametroServico;
import br.mil.fab.consigext.entity.Servico;

public interface ServicoService {

	public List<Servico> getServicos();
	public Servico findById(long id);
	public Servico findByCdServico(String cdServico);
	public String findNumParcelasMaximas(long idServico, String parametro);
	public List<Servico> findServicoByEntidade(String cdOrg);
	public void setStServiceOfService(long newSt, Servico servico);
	public ParametroServico getParametroServicoBySiglaParametro(Servico servico, String siglaParametro);
	public void addRenegociarInOtherServico(long id, String vlParametro, String vlParametro2);
	public Servico alterarServico(ServicoDTO servicoDTO);
	List<Servico> getServicosAtivos();
}
