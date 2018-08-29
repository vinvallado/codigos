package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.ParametroSistemaOpcao;

@Repository
public interface ParametroSistemaOpcaoRepository extends GenericRepository<ParametroSistemaOpcao,Long> {
	
	public ParametroSistemaOpcao findByParametroSistema_IdAndVlOpcao(Long idParametroSistema, String vlOpcao);
	public List<ParametroSistemaOpcao> findByParametroSistema_Id(Long idParametroSistema);
}
