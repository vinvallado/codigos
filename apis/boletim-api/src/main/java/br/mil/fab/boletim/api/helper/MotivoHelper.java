package br.mil.fab.boletim.api.helper;

import java.util.List;

import br.mil.fab.boletim.api.entity.MotivoUtilizado;

public class MotivoHelper {
	
	
	public static void formatarTextos (List<MotivoUtilizado> comps) {
	
		comps.forEach(motivoUtilizado -> {
			motivoUtilizado.getMotivo().setTxRodapeGeralFormatado(motivoUtilizado.getMotivo().getTxRodapeGeral());
			motivoUtilizado.getMotivo().setTxIndividualFormatado(motivoUtilizado.getMotivo().getTxIndividual());
			motivoUtilizado.getMotivo().setTxPadraoFormatado(motivoUtilizado.getMotivo().getTxPadrao());
			motivoUtilizado.getComponentes().forEach(componente->{
				if(motivoUtilizado.getMotivo().getTxRodapeGeralFormatado() != null) {
				motivoUtilizado.getMotivo().setTxRodapeGeralFormatado(
						txFormatado(motivoUtilizado.getMotivo().getTxRodapeGeralFormatado(),
								componente.getCompMotivo().getNmCampo(),
								componente.getVlLancado()));
				}
				if(motivoUtilizado.getMotivo().getTxIndividualFormatado() != null) {
				motivoUtilizado.getMotivo().setTxIndividualFormatado(
						txFormatado(motivoUtilizado.getMotivo().getTxIndividualFormatado(),
								componente.getCompMotivo().getNmCampo(),
								componente.getVlLancado()));
				}
				if(motivoUtilizado.getMotivo().getTxPadraoFormatado() != null) {
				motivoUtilizado.getMotivo().setTxPadraoFormatado(
					txFormatado(motivoUtilizado.getMotivo().getTxPadraoFormatado(),
							componente.getCompMotivo().getNmCampo(),
							componente.getVlLancado()));
					
				}
				
			});
		});
	}
	
	
	private static String txFormatado(String txt,String campo,String valor) {
		return txt = txt.replaceAll("@"+campo+"@", valor);
	}
	
	
	

}
