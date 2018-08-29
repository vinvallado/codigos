package br.ccasj.sisauc.administracao.beneficiario.service.dto;

import java.util.List;

public class RetornoBeneficiarios {
	
  private Beneficiario beneficiarios;
  
  public Beneficiario getBeneficiarios(){
    return this.beneficiarios;
  }
  
  public void setBeneficiarios(Beneficiario beneficiarios){
    this.beneficiarios = beneficiarios;
  }
  
  
  public class Beneficiario  {
	  
    private List<BeneficiarioDTO> beneficiario;
    
    public Beneficiario() {
    }
    
    public List<BeneficiarioDTO> getBeneficiario(){
      return this.beneficiario;
    }
    
    public void setBeneficiario(List<BeneficiarioDTO> beneficiario){
      this.beneficiario = beneficiario;
    }
    
  }
  
}
