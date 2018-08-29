package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.mil.fab.consigext.entity.Perfil;
import br.mil.fab.consigext.entity.Permissao;

public interface PermissaoRepository extends GenericRepository<Permissao,Long> {
	static String GRUPO_PERMISSAO = "SELECT T_PERMISSAO.* FROM T_PERMISSAO "
		    + "LEFT JOIN T_GRUPO_PERMISSAO ON T_PERMISSAO.ID_GRUPO_PERMISSAO = T_GRUPO_PERMISSAO.ID_GRUPO_PERMISSAO "
		    + "WHERE T_GRUPO_PERMISSAO.NM_GRUPO = ?";
	
	@Query(value = GRUPO_PERMISSAO, nativeQuery = true)
	public List<Permissao> findPermissaoPorGrup(String nmGrupo);
	
	@Query(value="SELECT P.* FROM CONSIG.T_PERMISSAO P, CONSIG.T_PERFIL_PERMISSAO PP WHERE P.ID_PERMISSAO = PP.ID_PERMISSAO AND PP.ID_PERFIL = ? order by P.NM_PERMISSAO ASC", nativeQuery = true)
	public List<Permissao> findByIdPerfil(Long idPerfil);
}
