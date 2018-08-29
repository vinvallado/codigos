package br.mil.fab.consigext.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.SolicitacaoSaldoDevedor;
import br.mil.fab.consigext.enums.TipoHistoricoEc;
import br.mil.fab.consigext.repository.SchedulerRepository;
import br.mil.fab.consigext.repository.SolicSaldoDevedorRepository;
import br.mil.fab.consigext.service.ParametroSistema.ParametroSistemaService;
import br.mil.fab.consigext.service.parametro.ParametroService;
import br.mil.fab.consigext.util.GenericUtil;

@Component
public class Scheduler {
	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	ParametroSistemaService parametroSistemaService;
    
    @Autowired
    SolicSaldoDevedorRepository solicSaldoDevedorRepo;
    
	@Autowired
	ParametroService parametroService;
    
	@Autowired
	SchedulerRepository schedulerRepository;
	
    //Todo dia 01:00 e 13:00 verifica se bloqueia alguma entidade 
    @Scheduled(cron = "0 0 1 * * *")
    @Scheduled(cron = "0 0 13 * * *")       
    public void blockConsignatarias() {
        log.info("Verificando se bloqueia alguma entidade consignatária (Scheduled Method)",dateFormat.format(new Date()));
        boolean nenhumaConsignatariaBloqueada = true;
        List<SolicitacaoSaldoDevedor> solicsNaoAtendidas = solicSaldoDevedorRepo.findByStAtendida(0);
        for(SolicitacaoSaldoDevedor solic: solicsNaoAtendidas) {
        	Consignacao consignacao = solic.getConsignacao();
        	Date dtSolicitacao = solic.getDtSolicitacao();
    		String flagUteisStr = parametroSistemaService.getVlParamSistemaAndChangeParamIfNull(19, "1");
    		boolean flagUteis = flagUteisStr.equals("1") ? true : false;
    		String prazoStr = parametroService.findByServicoConsigSiglaParametro(consignacao.getServicoConsig(), "PZATENDSDDEV");
    		if(prazoStr==null || prazoStr.isEmpty())
    			prazoStr="15";
    		int prazo = Integer.parseInt(prazoStr);
    		if(GenericUtil.addDaysToDate(flagUteis, dtSolicitacao, prazo).before(new Date())) {
    			EntidadeConsig consignataria = consignacao.getEntidadeConsig();
    			if(consignataria.getStEntidade().equals(0L))
    				continue;
    			String query = "update EntidadeConsig set stEntidade = ? where id = ?";
    			Map<Integer, Object> params = new HashMap<Integer, Object>();
    			params.put(1, 0L);
    			params.put(2, consignataria.getId());
    			schedulerRepository.insertOrUpdate(query, params, false);
    			nenhumaConsignatariaBloqueada =false;
    	        log.info("Consignataria "+consignataria.getOrganizacao().getNmOrg()+ " bloqueada.",dateFormat.format(new Date()));
    	        TipoHistoricoEc tipoHistoricoEc = TipoHistoricoEc.BLOQUEIO_AUTOMATICO;
    	        String tipoHistorico = tipoHistoricoEc.toString();
    	    	query = "insert INTO t_historico_ec(DS_HISTORICO, DT_HISTORICO, ID_ENTIDADE_CONSIG, TP_HISTORICO) VALUES (?, ?, ?, ?)";
    	    	params = new HashMap<Integer, Object>();
    	    	params.put(1, tipoHistorico);
    	    	params.put(2, new Date());
    	    	params.put(3, consignataria.getId());
    	    	params.put(4, tipoHistorico);
    			schedulerRepository.insertOrUpdate(query, params, true);
    		}
        }
        if(nenhumaConsignatariaBloqueada)
        	log.info("Nenhuma consignatária foi bloqueada.",dateFormat.format(new Date()));
    }
    
    
    
}