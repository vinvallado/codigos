package br.mil.fab.consigext.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.mil.fab.consigext.dto.CetConsigRanking;
import br.mil.fab.consigext.dto.ConfigAvancada;
import br.mil.fab.consigext.dto.Reengajamento;
import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EstabilidadeQuadroTempoServico;
import br.mil.fab.consigext.entity.ParcelaConsignacao;
import br.mil.fab.consigext.entity.ServicoConsig;
import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.entity.StatusParcela;
import br.mil.fab.consigext.service.margem.EstabilidadeQuadroTempoServicoService;
import br.mil.fab.consigext.service.margem.ReengajamentoService;
import br.mil.fab.consigext.service.margem.ServicoService;

@Component
public class ParcelaUtil {
	
	@Autowired
	EstabilidadeQuadroTempoServicoService estQdrTmpService;
	
	@Autowired
	ReengajamentoService reengService;
	
	@Autowired
	UsuarioLogado usuarioLogado;
	
	@Autowired
	ServicoService servicoService;
	
	
	/**
	 * Retorna o número máximo de parcelas para a consignação.
	 * 
	 * Caso retorne 0: Servidor não tem direto de realizar a reserva de 
	 * margem para o serviço solicitado.
	 * Case retorne 999: Serviço não possui número de parcelas, término 
	 * indefinido.
	 * 
	 * @param ServidorConsig, ServicoConsig
	 * @return int
	 *
	 */
	public int getParcelaMaxima(ServidorConsig servidor, ServicoConsig servicoConsig) {
	
		try {
			String paramParcMax = servicoService.findNumParcelasMaximas(servicoConsig.getServico().getId(),"INFQTDMAXPARINC");
			
			if (paramParcMax == null) return 0;
			
			String arrParamParcMax[] = paramParcMax.split("[|]");
			
			Integer parcelaMaximaParametro = Integer.parseInt(arrParamParcMax[0]);
			String nrOrdem = servidor.getPesfis().getNrOrdem();

			EstabilidadeQuadroTempoServico estQdrTmp = estQdrTmpService.retornaEstabilidadeQuadroTempoServico(nrOrdem);
			int tmpServicoMeses = this.converteTempoServico(estQdrTmp.getTxTempoServico());
			boolean isEstabilizado = this.isEstabilizado(tmpServicoMeses);
			
			if (isEstabilizado){
				return parcelaMaximaParametro;
			}else {
				//QSS, QTA, QTA(*)
				if (estQdrTmp.getStTemporario().equals("N") && !(estQdrTmp.getCdQdr().equals("13") || estQdrTmp.getCdQdr().equals("17") || estQdrTmp.getCdQdr().equals("31"))){
					return parcelaMaximaParametro;
				}else {
					return this.calculaParcelaMaxima(nrOrdem);
				}
			}		
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private int calculaParcelaMaxima(String nrOrdem){
		
		String perfil = usuarioLogado.getRole();
		
		//perfil = "CONSIGEXT_GESTOR";
		//perfil = "CONSIGEXT_SERVIDOR";
		//perfil = "CONSIGEXT_CONSIGNATARIA";
		
		Reengajamento dtReengaj = reengService.retornaProximaDataReengajamento(nrOrdem);
		
		if (dtReengaj == null) {
				return 0;
		}
		
		String strDataCorrente = new SimpleDateFormat("dd/MM/yyyy").format( new Date() );		
		int subtract = 1; //desconsidera o mês que irá reengajar
		
		LocalDate dataCorrente = LocalDate.parse(strDataCorrente, DateTimeFormatter.ofPattern("d/MM/uuuu"));
		LocalDate dataReenganjamento = LocalDate.parse(dtReengaj.getDtReengajamento(), DateTimeFormatter.ofPattern("d/MM/uuuu"));

		
		//maior que data do fechamento do ciclo: dia 4 de todo mês
		if (perfil.equals("CONSIGEXT_GESTOR") || perfil.equals("CONSIGEXT_CONSIGNATARIA")) {
			
			if (dataCorrente.getDayOfMonth() > 4) {
				subtract++;
			}
		}else {
			if (dataCorrente.plusDays(6).getDayOfMonth() > 4) { //6 dias a mais para deferir a solicitação
				dataCorrente = dataCorrente.plusDays(6);
				subtract++;
			}
		}

		dataCorrente = dataCorrente.withDayOfMonth(1); //seta mes como dia 01 para comparação mais precisa
		dataReenganjamento = dataReenganjamento.withDayOfMonth(1); // seta mes como dia 01 para comparação mais precisa
		
		int monthsBetween = (int) ChronoUnit.MONTHS.between(dataCorrente, dataReenganjamento) - subtract; 

		return monthsBetween < 0 ? 0 : monthsBetween;
	}
	
	
	/**
	 * Retorna o tempo de serviço em meses
	 */
	private int converteTempoServico(String txTempoServico) {
		int anos = Integer.parseInt(txTempoServico.substring(0, 2));
		int meses = Integer.parseInt(txTempoServico.substring(5, 7));
		
		return anos * 12 + meses;
	}
	
	private boolean isEstabilizado(int tmpServicoMeses) {
		return tmpServicoMeses >= 120;
	}
	
	public static List<ParcelaConsignacao> gerarParcelas(Consignacao consignacao){
		List<ParcelaConsignacao> parcelas = new ArrayList<ParcelaConsignacao>();
		Calendar dtVencParc = Calendar.getInstance();
		dtVencParc.setTime(consignacao.getDtReserva());
		for (int nrParc = 0; nrParc <= consignacao.getNrPrestacoes() - 1; nrParc ++) {
			dtVencParc.add(Calendar.MONTH, 1);
			//Date dtParcGeradas = dtVencParc.getTime();
			ParcelaConsignacao parcela = new ParcelaConsignacao(Long.parseLong(GenericUtil.getAAAAMMtual(dtVencParc)), Long.valueOf(nrParc + 1), "", 
																consignacao.getVlPrestacao(), consignacao, new StatusParcela(1));
			parcelas.add(parcela);
		}
		
		return parcelas;
	}
	
	public static boolean autorizaValorParcela(BigDecimal margem, double taxa, Consignacao consignacao) {
		
		BigDecimal valorLiquido =  BigDecimal.ZERO;
		if (consignacao.getVlLiquidoLiberado() != BigDecimal.ZERO && consignacao.getVlLiquidoLiberado() != null) {
			valorLiquido = consignacao.getVlLiquidoLiberado();
		} else {
			valorLiquido = consignacao.getVlPrestacao().multiply(BigDecimal.valueOf(consignacao.getNrPrestacoes()).setScale(2,RoundingMode.HALF_UP));
		}
		
		BigDecimal valorParcela =  CalculoUtil.valorParcela(new BigDecimal(taxa, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP),
				consignacao.getNrPrestacoes(), valorLiquido);
		if ((valorParcela).compareTo(margem) == 1) {
			return false;
		}
		
		consignacao.setVlCet(BigDecimal.valueOf(taxa).setScale(2,RoundingMode.HALF_UP));
		consignacao.setVlLiquidoLiberado(valorLiquido);
		consignacao.setVlPrestacao(valorParcela);
		
		return true;
	}
	
	public static boolean autorizaValorParcela(BigDecimal margem, CetConsigRanking cet, Long nrPrestacao) {
		
		BigDecimal valorLiquido =  BigDecimal.ZERO;
		if (cet.getVlLiquidoLib() != BigDecimal.ZERO && cet.getVlLiquidoLib() != null) {
			valorLiquido = cet.getVlLiquidoLib();
		} else {
			valorLiquido = cet.getVlPrestacao().multiply(BigDecimal.valueOf(nrPrestacao).setScale(2,RoundingMode.HALF_UP));
		}

		BigDecimal valorParcela =  CalculoUtil.valorParcela(new BigDecimal(cet.getVlCet(), MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP),
					nrPrestacao, valorLiquido);
		
		cet.setVlLiquidoLib(valorLiquido);
		cet.setVlPrestacao(valorParcela);
		
		if ((valorParcela).compareTo(margem) == 1) {
			return false;
		}
		return true;
	}
	
	public static void definirPeriodoParcela (Long carencia, ParcelaConsignacao parcela) {
		
		Integer ordem = parcela.getNrParcela() != null ? parcela.getNrParcela().intValue() : 0;
		
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(new Date());
		calNow.add(Calendar.MONTH, ordem - 1);
		
		Calendar calPeriodo = Calendar.getInstance();
		calPeriodo.set(Calendar.YEAR, calNow.get(Calendar.YEAR));
		calPeriodo.set(Calendar.MONTH, calNow.get(Calendar.MONTH));
		calPeriodo.set(Calendar.DAY_OF_MONTH, 4);
		
		
		Calendar calReserva = Calendar.getInstance();
		calReserva.setTime(calReserva.getTime());
		calReserva.set(Calendar.YEAR, calNow.get(Calendar.YEAR)); 
		calReserva.set(Calendar.MONTH, calNow.get(Calendar.MONTH));
		
		if (calReserva.after(calPeriodo)){
			calReserva.add(Calendar.MONTH, 1);
		}
		
		Integer i = carencia != null ? carencia.intValue() : 0;

		if (carencia > 0) {
			calReserva.add(Calendar.MONTH, i);
		}
		
		parcela.setCdAnomes(Long.parseLong(GenericUtil.getAAAAMMtual(calReserva)));
	
	}
	
	public static void qtdParcelasConsig(Consignacao consignacao, ConfigAvancada configuracao) {
		consignacao.getParcelaConsignacaos().sort((ParcelaConsignacao pc1, ParcelaConsignacao pc2) -> pc1.getCdAnomes().compareTo(pc2.getCdAnomes()));
		Calendar dtVencParc = Calendar.getInstance();
		//Regra para acrescentar parcelas restantes.
		if (consignacao.getParcelasNaoPagas().size() < configuracao.getNrPrestResestantes()) {
			int numNovasParcelas = (int) (configuracao.getNrPrestResestantes() - consignacao.getParcelasNaoPagas().size());
			
			ParcelaConsignacao pc = consignacao.getParcelaConsignacaos().get(consignacao.getParcelaConsignacaos().size() -1);
			
			dtVencParc.set(Calendar.YEAR, Integer.parseInt(pc.getCdAnomes().toString().substring(0, 4)));
			dtVencParc.set(Calendar.MONTH, Integer.parseInt(pc.getCdAnomes().toString().substring(4, 2)));
			for (int nrParc=(int)(long)pc.getNrParcela(); nrParc < ((int)(long)pc.getNrParcela() + numNovasParcelas); nrParc++) {
				dtVencParc.add(Calendar.MONTH, 1);
				ParcelaConsignacao parcela = new ParcelaConsignacao(Long.parseLong(GenericUtil.getAAAAMMtual(dtVencParc)), Long.valueOf(nrParc + 1), "", 
						consignacao.getVlPrestacao(), consignacao, new StatusParcela(1));
				consignacao.getParcelaConsignacaos().add(parcela);
			}
		} else {
		//Regra para retirar as parcelas restantes.
			int numParcelasRestantes = (int) (consignacao.getParcelasNaoPagas().size() - configuracao.getNrPrestResestantes());
			ParcelaConsignacao pc = consignacao.getParcelaConsignacaos().get(consignacao.getParcelaConsignacaos().size() -1);
			for (int nrParc=(int)(long)pc.getNrParcela(); nrParc >= ((int)(long)pc.getNrParcela() - numParcelasRestantes); nrParc--) {
				consignacao.getParcelaConsignacaos().remove(consignacao.getParcelaConsignacaos().size() -1);
				//consignacao.getParcelaConsignacaos().get(consignacao.getParcelaConsignacaos().size() -1).setStatusParcela(new StatusParcela(3));							
			}
		}
	}
	
}
