package br.mil.fab.consigext.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.util.GenericUtil;

@Repository
public class ArquivoPlenoRepositoryImpl  {

@PersistenceContext
private EntityManager em;

@Async
	public void updateProcesso(List<ServidorConsig> server) {
	
		try {
		Query query = em.createQuery("Update ServidorConsig sc SET sc.stCapacidade = :stCapacidade, "
				+ "sc.stEstabilizado = :stEstabilizado, sc.dsCategoria = :dsCategoria,sc.dtReenganjamento = :dtReengajamento "
				+ " where sc.pesfis.nrOrdem = :nrOrdem");
	
		server.forEach(serv ->{
//		query.setParameter("vlMargem70", serv.getVlMargem70());
		query.setParameter("stCapacidade", serv.getStCapacidade());
		query.setParameter("stEstabilizado", serv.getStEstabilizado());
		query.setParameter("dsCategoria", serv.getDsCategoria());
		query.setParameter("dtReengajamento", serv.getDtReenganjamento());
//		query.setParameter("vlMargem30", serv.getVlMargem30());
		query.setParameter("nrOrdem", serv.getPesfis().getNrOrdem());
		query.executeUpdate();
     	GenericUtil.logInfo("ArquivoPlenoServiceImpl","==="+serv.getPesfis().getNrOrdem());
     
   	});
		

	
	
	}catch(Exception ex) {
		GenericUtil.logErro("ArquivoPlenoServiceImpl",ex.getMessage());
		ex.printStackTrace();
	}
	
	}
}
