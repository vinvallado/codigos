package br.ccasj.sisauc.administracao.beneficiario.service;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;

import java.util.List;


public interface BeneficiarioService {
    List<Beneficiario> findByTextParam(String param, int maxResult);

    List<Beneficiario> findByCpfOrSaramOrName(String cpf, String saram, String name, int first, int pageSize);
    
    List<Beneficiario> findByCpfOrSaramOrName(boolean filtrarPermitidosAMHC ,String cpf, String saram, String name, int first, int pageSize);
    
    List<Beneficiario> findByCpfOrSaramOrNameFullLike(boolean filtrarPermitidosAMHC ,String cpf, String saram, String name, int first, int pageSize);
    
    Beneficiario findBySaram(boolean filtrarPermitidosAMHC, String saram);
    
    Beneficiario findBySaram(String saram);
    
    Beneficiario checkBeneficiario(Beneficiario beneficiario); 
}