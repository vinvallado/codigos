package br.mil.fab.consigext.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.mil.fab.consigext.entity.CfgBasicaPgm;

@Repository
public interface CfgBasicaPgmRepository extends GenericRepository<CfgBasicaPgm,Long> {
	
	public List<CfgBasicaPgm> findByKeyOfCfgBasicaPgm_cdAnomes(String cdAnomes);	
}
