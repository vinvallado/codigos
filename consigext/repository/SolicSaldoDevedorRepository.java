package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.SolicitacaoSaldoDevedor;

@Repository
public interface SolicSaldoDevedorRepository extends GenericRepository<SolicitacaoSaldoDevedor, Long>{
	
	public List<SolicitacaoSaldoDevedor> findByConsignacao_ServicoConsig_EntidadeConsig_id(Long idConsignataria);
	public List<SolicitacaoSaldoDevedor> findAll();
	public SolicitacaoSaldoDevedor findById(long id);
	public List<SolicitacaoSaldoDevedor> findByConsignacao(Consignacao consignacao);
	public List<SolicitacaoSaldoDevedor> findByStAtendida(long stAtendida);
	public List<SolicitacaoSaldoDevedor> findByConsignacao_ServicoConsig_EntidadeConsig_Organizacao_cdOrg(String cdOrg);
	
}