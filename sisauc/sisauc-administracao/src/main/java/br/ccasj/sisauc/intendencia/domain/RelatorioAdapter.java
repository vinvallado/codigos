package br.ccasj.sisauc.intendencia.domain;

import java.util.ArrayList;
import java.util.List;

public class RelatorioAdapter {

	private RelatorioFolhaBeneficiario relatorioFolhaBeneficiario;
	
	private List<RelatorioFolhaBeneficiarioItem> itensEnviados = new ArrayList<RelatorioFolhaBeneficiarioItem>();
	
	public RelatorioAdapter(RelatorioFolhaBeneficiario relatorioFolhaBeneficiario) {
		this.relatorioFolhaBeneficiario = relatorioFolhaBeneficiario;
	}
	
	public void addItemEnviado(RelatorioDescontoBeneficiarioItem item){
		this.itensEnviados.add(item);
	}
	
	public List<RelatorioFolhaBeneficiarioItem> getItensEnviados() {
		return itensEnviados;
	}
	
	public void setItensEnviados(List<RelatorioFolhaBeneficiarioItem> itensEnviados) {
		this.itensEnviados = itensEnviados;
	}

	public RelatorioFolhaBeneficiario getRelatorioFolhaBeneficiario() {
		return this.relatorioFolhaBeneficiario;
	}

	public void setRelatorioFolhaBeneficiario(RelatorioFolhaBeneficiario relatorioFolhaBeneficiario) {
		this.relatorioFolhaBeneficiario = relatorioFolhaBeneficiario;
	}
	
	public boolean isDebito(){
		return relatorioFolhaBeneficiario.isDebito();
	}

	
}
