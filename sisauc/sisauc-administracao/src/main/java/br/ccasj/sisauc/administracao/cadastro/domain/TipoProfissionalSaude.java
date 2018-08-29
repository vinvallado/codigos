package br.ccasj.sisauc.administracao.cadastro.domain;

public enum TipoProfissionalSaude {
	
	MEDICO("Médico"),
	DENTISTA("Dentista"),
	NUTRICIONISTA("Nutricionista"),
	FISIOTERAPEUTA("Fisioterapeuta"),
	TERAPEUTA_OCUPACIONAL("Terapeuta Ocupacional"),
	PSICOLOGO("Psicólogo"),
	FONOAUDIOLOGO("Fonoaudiólogo");
	
	private String label;
	
	private TipoProfissionalSaude(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
