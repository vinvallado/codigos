package br.mil.fab.consigext.dto;

public class IndiceServicoConsigDTO {

	private long idServicoConsig;
	private String cdIndice;
	private String nmIndice;
	private long id;
	private boolean disponivel;
	
	
	public IndiceServicoConsigDTO() {
		super();
	}
	
	public IndiceServicoConsigDTO(long idServicoConsig, String cdIndice, String nmIndice, long id) {
		super();
		this.idServicoConsig = idServicoConsig;
		this.cdIndice = cdIndice;
		this.nmIndice = nmIndice;
		this.id = id;
	}
	
	public long getIdServicoConsig() {
		return idServicoConsig;
	}
	public void setIdServicoConsig(long idServicoConsig) {
		this.idServicoConsig = idServicoConsig;
	}
	public String getCdIndice() {
		return cdIndice;
	}
	public void setCdIndice(String cdIndice) {
		this.cdIndice = cdIndice;
	}
	public String getNmIndice() {
		return nmIndice;
	}
	public void setNmIndice(String nmIndice) {
		this.nmIndice = nmIndice;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
	
	
	
}
