package br.mil.fab.consigext.repository;

import org.springframework.data.jpa.repository.Query;

import br.mil.fab.consigext.entity.EstabilidadeQuadroTempoServico;

public interface EstabilidadeQuadroTempoServicoRepository extends GenericRepository<EstabilidadeQuadroTempoServico,Long> {

	
	static String RETORNA_ESTAB_QDR_TMPSVC = "select pf.TX_TEMPO_SERVICO as TX_TEMPO_SERVICO, q.ST_TEMPORARIO as ST_TEMPORARIO, q.SG_QDR as SG_QDR, q.CD_QDR as CD_QDR from sig.t_pesfis_comgep_dw pf, sig.t_quadro_esp_militar qem, sig.t_quadro q where pf.nr_ordem = qem.nr_ordem and qem.cd_qdr = q.cd_qdr and qem.st_atual = 'S' and pf.nr_ordem = ?";
	
	
	@Query(value = RETORNA_ESTAB_QDR_TMPSVC, nativeQuery = true)
	public EstabilidadeQuadroTempoServico retornaEstabilidadeQuadroTempoServico(String nrOrdem);
	

}
