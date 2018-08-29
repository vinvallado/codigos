package br.ccasj.sisauc.administracao.beneficiario.adapter;


import java.util.ArrayList;
import java.util.List;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.dto.BeneficiarioDTO;
import br.ccasj.sisauc.administracao.cadastro.domain.Convenio;
import br.ccasj.sisauc.administracao.cadastro.domain.PostoGraduacao;
import br.ccasj.sisauc.administracao.cadastro.domain.Sexo;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;

public class BeneficiarioAdapter {

    public static Beneficiario convert(final BeneficiarioDTO in) {
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setAtivo(in.getAtivo() == 1);
        beneficiario.setSaram(in.getSaram());
        beneficiario.setNome(in.getNome());
        beneficiario.setCpf(in.getCpf());
        beneficiario.setDataNascimento(in.getDataNascimento());
        beneficiario.setDataAtualizacao(in.getDataAtualizacao());
        beneficiario.setSexo(Sexo.valueOf(in.getSexo()));
        beneficiario.setTipoContribuicao(in.getTipoContribuicao());
        beneficiario.setTitular(in.getTitular() == 1);

        beneficiario.setPostoGraduacao(new PostoGraduacao(in.getSiglaPostoGraduacao()));
        beneficiario.setOrganizacaoMilitar(new OrganizacaoMilitar(in.getSiglaOrganizacaoMilitar()));
        beneficiario.setConvenio(new Convenio(in.getSiglaConvenio()));
        
        beneficiario.setCdParentesco(in.getCdParentesco());
        
        if (in.getIdBeneficiarioTitular() != null)
            beneficiario.setSaramTitular(converteIdLongParaString(Long.valueOf(String.valueOf(in.getIdBeneficiarioTitular() / 100)), Beneficiario.TAMANHO_SARAM));
        
        if (in.getId() != null)
            beneficiario.setId(in.getId().intValue());
        
        return beneficiario;
    }


    public static Beneficiario convertCpfPorCpfTitular(final BeneficiarioDTO in) {
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setAtivo(in.getAtivo() == 1);
        beneficiario.setSaram(in.getSaram());
        beneficiario.setNome(in.getNome());
        beneficiario.setCpf(in.getCpf());
        beneficiario.setDataNascimento(in.getDataNascimento());
        beneficiario.setDataAtualizacao(in.getDataAtualizacao());
        beneficiario.setSexo(Sexo.valueOf(in.getSexo()));
        beneficiario.setTipoContribuicao(in.getTipoContribuicao());
        beneficiario.setTitular(in.getTitular() == 1);
        
        beneficiario.setPostoGraduacao(new PostoGraduacao(in.getSiglaPostoGraduacao()));
        beneficiario.setOrganizacaoMilitar(new OrganizacaoMilitar(in.getSiglaOrganizacaoMilitar()));
        beneficiario.setConvenio(new Convenio(in.getSiglaConvenio()));
        
        beneficiario.setCdParentesco(in.getCdParentesco());
        
        if (in.getIdBeneficiarioTitular() != null)
            beneficiario.setSaramTitular(converteIdLongParaString(Long.valueOf(String.valueOf(in.getIdBeneficiarioTitular() / 100)), Beneficiario.TAMANHO_SARAM));
        
        if (in.getId() != null)
            beneficiario.setId(in.getId().intValue());

        return beneficiario;
    }

    
    public static String converteIdLongParaString(Long idLong, int tamanho){
    	String result = String.valueOf(idLong);
    	int numeroZerosEsquerda = (tamanho) - result.length();  
    	if (numeroZerosEsquerda > 0 && numeroZerosEsquerda < 7){
    		result = "0000000".substring(0, numeroZerosEsquerda) + result;
    	}
    	return result;
    }
    
    
    public static List<Beneficiario> convert(final List<BeneficiarioDTO> in) {
        final List<Beneficiario> aux = new ArrayList<Beneficiario>();

        if (in == null)
            return aux;

        for (final BeneficiarioDTO toConverter : in) {
            aux.add(convert(toConverter));
        }
        return aux;
    }
}
