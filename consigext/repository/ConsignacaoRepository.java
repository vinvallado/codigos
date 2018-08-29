package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.ServicoConsig;

public interface ConsignacaoRepository extends GenericRepository<Consignacao,Long> {


	static String FIND_SERVIDOR = "SELECT * FROM T_CONSIGNACAO C WHERE C.ID_SERVIDOR_CONSIG = ?";

	static String FIND_CONSIG_INDICE = " SELECT * " + 
										" FROM T_CONSIGNACAO C " + 
										" INNER JOIN T_SERVIDOR_CONSIG SC " + 
										" ON C.ID_SERVIDOR_CONSIG = SC.ID_SERVIDOR_CONSIG " + 
										" INNER JOIN T_SERVICO_CONSIG S " + 
										" ON C.ID_SERVICO_CONSIG = S.ID_SERVICO_CONSIG " + 
										" WHERE SC.ID_SERVIDOR_CONSIG = ?" + 
										" AND S.ID_ENTIDADE_CONSIG = ? " + 
										" AND C.NR_INDICE = ? ";

	@Query(value = FIND_SERVIDOR, nativeQuery = true)
	public List<Consignacao> findByServidor(Long idServidor);

	@Query("Select c from Consignacao c where c.servicoConsig.entidadeConsig.cdEntidade = :cdEntidade and c.servidorConsig.pesfis.nrCpf = :nrCpf")
	public List<Consignacao> findByConsignatariaAndServidor(@Param("cdEntidade") String cdEntidade, @Param("nrCpf") String nrCpf);
	
	@Query("Select c from Consignacao c where c.servicoConsig.entidadeConsig.cdEntidade = :cdEntidade and c.nrAde = :nrAde")
	public List<Consignacao> findByConsignatariaAndNrAde(@Param("cdEntidade") String cdEntidade, @Param("nrAde") Long nrAde);

	@Query("Select c from Consignacao c where c.servicoConsig.entidadeConsig.cdEntidade = :cdEntidade and c.servidorConsig.pesfis.nrOrdem = :nrOrdem")
	public List<Consignacao> findByConsignatariaAndServidorMatricula(@Param("cdEntidade") String cdEntidade, @Param("nrOrdem") String nrOrdem);
	
	public List<Consignacao> findByServicoConsig_EntidadeConsig_id(Long idServicoConsig);

	@Query(value = FIND_CONSIG_INDICE, nativeQuery = true)
	public Consignacao findByIndiceConsig(Long idServidorConsig, Long idEntidade, String nrIndice);
	
	public Consignacao findById(long id);

	public Consignacao findByNrAde(Long nrAde);
	public Consignacao findByNrAdeAndServicoConsig(long nrAde, ServicoConsig srvConsig);

	public List<Consignacao> findByServicoConsig_id(Long auxIdServicoConsig);

	public List<Consignacao> findByNrIndiceAndServidorConsig_idAndServicoConsig_id(String nrIndice, Long idServidorConsig,
			Long idServicoConsig);
	
}

