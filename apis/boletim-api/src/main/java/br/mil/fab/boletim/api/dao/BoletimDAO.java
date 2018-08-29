package br.mil.fab.boletim.api.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.mil.fab.boletim.api.entity.MotivoUtilizado;
import br.mil.fab.boletim.api.response.ItemBoletimResponse;

@Repository
public class BoletimDAO {

	@PersistenceContext
	private EntityManager entityManager;
	

	@Transactional()
	public List<ItemBoletimResponse> bulkPersist(List<MotivoUtilizado> entities,List<ItemBoletimResponse> resps ) {
		entities.forEach(wrapper ->{
			ItemBoletimResponse resp = new ItemBoletimResponse();
			entityManager.persist(wrapper);
			resp.setCode("200");
			resp.setIdLancamento(wrapper.getIdLancamento()+"");
			resp.setMessage("Boletim inserido com sucesso.");
			resp.setIdModulo(wrapper.getIdModulo().longValue());
			resp.setIdProcesso(wrapper.getIdProcesso().longValue());
			resps.add(resp);
		});
		return resps;
	}
	

}
