package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.ArquivoEntidadeConsig;
import br.mil.fab.consigext.entity.CargaArquivo;
import br.mil.fab.consigext.entity.EntidadeConsig;

@Repository
public interface ArquivoEntidadeConsigRepository extends GenericRepository<ArquivoEntidadeConsig,Long>{
	ArquivoEntidadeConsig findById(long idArquivoEntidadeConsig);
	
	List <ArquivoEntidadeConsig> findByCargaArquivo(CargaArquivo cargaArquivo);

	List<ArquivoEntidadeConsig> findByCargaArquivoAndEntidadeConsig(CargaArquivo findByIdCargaArquivo,
			EntidadeConsig findById);

	List<ArquivoEntidadeConsig> findByEntidadeConsig(EntidadeConsig findById);
}
