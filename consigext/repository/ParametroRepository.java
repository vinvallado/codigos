package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.mil.fab.consigext.entity.Parametro;

public interface ParametroRepository extends GenericRepository<Parametro,Long> {

	
	@Query(value="select * from t_parametro where id_parametro_visualizacao in ('1', '3') and id_parametro_secao = ?", nativeQuery = true)
	public List<Parametro> findByParametroSecao_id(long idSecao);
	
	@Query(value="select * from t_parametro where id_parametro_visualizacao in ('1', '3') and st_excluido = ? and id_parametro_secao = ?", nativeQuery = true)
	public List<Parametro> findByStExcluidoAndSecao_id(long stExcluido, long idSecao);
	
	public Parametro findById(long id);
	
	public Parametro findBySgParametro(String sgParam);
	
}
