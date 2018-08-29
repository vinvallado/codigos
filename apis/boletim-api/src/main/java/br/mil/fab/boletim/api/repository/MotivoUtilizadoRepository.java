package br.mil.fab.boletim.api.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.mil.fab.boletim.api.entity.MotivoUtilizado;

@Repository
public interface MotivoUtilizadoRepository extends JpaRepository<MotivoUtilizado, Long> {
	
	@Query("Select c from MotivoUtilizado c where c.idProcesso = :idProcesso and c.idModulo = :idModulo")
	List<MotivoUtilizado> buscarMotivoUtilizadoByIdProcesso
	(@Param("idModulo")BigDecimal idMotivo,@Param("idProcesso")BigDecimal idProcesso);
}
