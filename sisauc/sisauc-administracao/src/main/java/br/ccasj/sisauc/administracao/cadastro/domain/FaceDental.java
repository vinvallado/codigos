package br.ccasj.sisauc.administracao.cadastro.domain;

public enum FaceDental {
	
	DISTAL("Distal"), 
	MESIAL("Mesial"), 
	OCLUSAL("Oclusal"), 
	VESTIBULAR("Vestibular"), 
	LINGUAL("Lingual/Palatal");
	
	private String label;

	private FaceDental(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}	

}
