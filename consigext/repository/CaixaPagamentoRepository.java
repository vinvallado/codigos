package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.CaixaPagamento;

@Repository
public interface CaixaPagamentoRepository extends GenericRepository<CaixaPagamento,Long>{
	public List<CaixaPagamento> findByStExtintoAndCdCaixaAcantusNotNullOrderByCdCaixaAcantus(String stExtinto);
	public CaixaPagamento findByCdCaixa(String cdCaixa);

}
