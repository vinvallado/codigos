package br.ccasj.sisauc.emissaoar.domain;

public enum TipoEspecificacaoItemARE {
	
	PROCEDIMENTO("Procedimento"),
	OPME("OPME"),
	DIARIAS_E_TAXAS_HOSPITALARES("Diárias e Taxas"),
	MATERIAS_DESCARTAVEIS("Mat. Descartáveis"),
	EXAMES_COMPLEMENTARES("Exames Complem."),
	MEDICAMENTOS("Medicamentos"),
	GASES_MEDICINAIS("Gases Medicinais"),
	ORTESES("Órteses"),
	PROTESES("Próteses"),
	MATERIAIS_ESPECIAIS("Mat. Especiais"),
	MEDICAMENTOS_ESPECIAIS("Medic. Especiais"),
	HEMOTERAPIA("Hemoterapia"),
	TERAPIAS_AUXILIARES("Terapias Aux.");
	
	private String label;
	
	private TipoEspecificacaoItemARE(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
