package br.mil.fab.consigext.service.impl.margem;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.HistoricoConsignacao;
import br.mil.fab.consigext.entity.StatusConsignacao;
import br.mil.fab.consigext.entity.TipoHistoricoConsig;
import br.mil.fab.consigext.repository.HistoricoConsignacaoRepository;
import br.mil.fab.consigext.service.margem.HistoricoConsignacaoService;
import br.mil.fab.consigext.util.UsuarioLogado;

@Service
public class HistoricoConsignacaoServiceImpl implements HistoricoConsignacaoService{

	@Autowired
	HistoricoConsignacaoRepository historicoRepo;
	
	@Autowired
	UsuarioLogado usrLogado;
	
	@Override
	public void save(HistoricoConsignacao historicoConsig) {
		historicoRepo.save(historicoConsig);
	}
	
	@Override
	public void save(List<HistoricoConsignacao> historicos) {
		historicoRepo.save(historicos);
	}
	
	@Override
	public HistoricoConsignacao salvarHistorico (Consignacao consig, String txColuna, String valAtual, StatusConsignacao status, TipoHistoricoConsig tipoHistorico, Map<String, Object> body){
		
		HistoricoConsignacao historicoConsig = new HistoricoConsignacao();
		try {
		String nomestatus = status.getNmStatus();
		if(nomestatus.equals("SUSPENSA") || nomestatus.equals("SUSPENSA PELA CSE")){
			historicoConsig.setDsHistorico(body.get("txtObservacao").toString() +"Novo status:"+ status.getNmStatus());
			//TODO: VERIFICAR O TIPO QUE VEM DA TELA E ONDE GRAVA
		}else{
			historicoConsig.setDsHistorico("Atualização de status da Consignação. Novo status :" + status.getNmStatus());
		}
		historicoConsig.setConsignacao(consig);
		
		historicoConsig.setDtHistorico(new Date());
		historicoConsig.setNrCpfResponsavel(usrLogado.getCPF());
		historicoConsig.setTxColuna("ID_STATUS_CONSIGNACAO");
		historicoConsig.setVlAnterior(valAtual);
		historicoConsig.setVlNovo(String.valueOf(status.getId()));
		historicoConsig.setTipoHistoricoConsig(tipoHistorico);
		// TODO - CORRIGIR GRAVAÇÃO DO IP
		historicoConsig.setTxIp(usrLogado.obterIP());
		
		save(historicoConsig);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return historicoConsig;
		
	}

	@Override
	public List<HistoricoConsignacao> getHistoricoByConsignacaoAndTipoHistorico(Consignacao consignacao, long i) {
		return historicoRepo.findByConsignacaoAndTipoHistoricoConsig(consignacao, new TipoHistoricoConsig(i));
	}

	@Override
	public HistoricoConsignacao findHistoricoLiquidado(Long idConsig) {
		return historicoRepo.findHistoricoLiquidado(idConsig);
	}

	@Override
	public HistoricoConsignacao findUltimoHistorico(Long idConsig) {
		return historicoRepo.findUltimoHistorico(idConsig);
	}

}
