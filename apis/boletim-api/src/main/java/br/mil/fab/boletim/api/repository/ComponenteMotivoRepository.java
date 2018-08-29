package br.mil.fab.boletim.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mil.fab.boletim.api.entity.ComponenteMotivo;

public interface ComponenteMotivoRepository extends JpaRepository<ComponenteMotivo, Long> {
	
	@Query("Select c from ComponenteMotivo c where c.cdMotivo = :cdMotivo")
	List<ComponenteMotivo> buscarPorMotivoAndCampo
	(@Param("cdMotivo")String cdMotivo);

}
