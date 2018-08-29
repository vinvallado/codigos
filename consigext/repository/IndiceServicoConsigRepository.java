package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.IndiceServicoConsig;

@Repository
public interface IndiceServicoConsigRepository extends GenericRepository<IndiceServicoConsig, Long>{	
	public List<IndiceServicoConsig> findByServicoConsig_id(long idServicoConsig);
	
	public IndiceServicoConsig findByCdIndiceAndServicoConsig_id(String cdIndice, long idServicoConsig);
	
	public IndiceServicoConsig findByNmIndiceAndServicoConsig_id(String nmIndice, long idServicoConsig);
	
	public List<IndiceServicoConsig> findByServicoConsig_Servico_idAndServicoConsig_EntidadeConsig_idOrderByCdIndice(long idServico, long idEntidade);
}
