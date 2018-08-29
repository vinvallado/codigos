package br.ccasj.sisauc.administracao.beneficiario.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Convenio;
import br.ccasj.sisauc.administracao.cadastro.domain.PostoGraduacao;
import br.ccasj.sisauc.administracao.cadastro.domain.Sexo;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import br.ccasj.sisauc.integracao.entity.DependenteDTO;
import br.ccasj.sisauc.integracao.entity.PacienteDTO;
import br.ccasj.sisauc.integracao.entity.TitularDTO;

@Service(value = "gerenciadorBeneficiarioUtil")
public class GerenciadorBeneficiarioUtil {
	
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;
	
	@Autowired
	private BeneficiarioDAO beneficiarioDAO;
	
	private HashMap<String, OrganizacaoMilitar> organizacoesMilitaresMap;
	private HashMap<String, PostoGraduacao> postosGraduacaoMap;
	private HashMap<String, Convenio> conveniosMap;
	
	@PostConstruct
	public void iniciarGerenciadorBeneficiarioUtil() {
		organizacoesMilitaresMap = new HashMap<String, OrganizacaoMilitar>();
		List<OrganizacaoMilitar> listaOM = organizacaoMilitarDAO.findAll();
		for (OrganizacaoMilitar organizacaoMilitar : listaOM) {
			organizacoesMilitaresMap.put(organizacaoMilitar.getSigla().trim(),organizacaoMilitar);
		}
		
		postosGraduacaoMap = new HashMap<String, PostoGraduacao>();
		List<PostoGraduacao> listaPostos = beneficiarioDAO.obterTodosPostos();
		for (PostoGraduacao postoGraduacao : listaPostos) {
			postosGraduacaoMap.put(postoGraduacao.getSigla().trim(), postoGraduacao);
		}
		
		conveniosMap = new HashMap<String, Convenio>();
		List<Convenio> listaConvenios = beneficiarioDAO.obterTodosConvenios();
		for (Convenio convenio : listaConvenios) {
			conveniosMap.put(convenio.getSigla().trim(), convenio);
		}
		
	}
	
	
	public List<Beneficiario> converterPacienteDTOBeneficiario(PacienteDTO pacienteDTO) {
		List<Beneficiario> retorno = new ArrayList<Beneficiario>();
		Beneficiario beneficiarioTitular = null;
		if (pacienteDTO == null) {
			return null;
		}

		beneficiarioTitular = criarBeneficiario(pacienteDTO, true);
		retorno.add(beneficiarioTitular);
		if (pacienteDTO.getDependentes() != null) {
			for (PacienteDTO dependenteDTO : pacienteDTO.getDependentes()) {
				Beneficiario dependente = criarBeneficiario(dependenteDTO, false);
				dependente.setBeneficiarioTitular(beneficiarioTitular);
				retorno.add(dependente);
			}	
		}

		return retorno;
	}
	
	private Beneficiario criarBeneficiario(PacienteDTO pacienteDTO, boolean titular) {
		Beneficiario beneficiario = new Beneficiario();

		beneficiario.setId(pacienteDTO.getId());
		beneficiario.setTitular(titular);
		beneficiario.setNome(pacienteDTO.getNome());
		beneficiario.setCpf(pacienteDTO.getCpf());
		beneficiario.setSaram(pacienteDTO.getSaram());
		beneficiario.setAtivo(pacienteDTO.isAtivo());
		beneficiario.setSexo(Sexo.getSexo(pacienteDTO.getSexo()));
		beneficiario.setDataNascimento(pacienteDTO.getDataNascimento());
		beneficiario.setTipoContribuicao(pacienteDTO.getTipoContribuicao());
		beneficiario.setDataAtualizacao(new Date());


		String siglaPostoGraduacao = pacienteDTO.getSiglaPostoGraduacao();
		if (siglaPostoGraduacao != null) {
			PostoGraduacao postoGraduacao = beneficiarioDAO.obterPosto(siglaPostoGraduacao);
			beneficiario.setPostoGraduacao(postoGraduacao);
		}

		String siglaOrganizacaoMiligar = pacienteDTO.getSiglaUnidadeLotacao();
		if (siglaOrganizacaoMiligar != null) {
			OrganizacaoMilitar om = organizacaoMilitarDAO.obterOrganizacaoMilitarPorSigla(siglaOrganizacaoMiligar);
			beneficiario.setOrganizacaoMilitar(om);
		}

		String siglaConvenio = pacienteDTO.getSiglaConvenio();
		if (siglaConvenio != null) {
			Convenio convenio = beneficiarioDAO.obterConvenio(siglaConvenio);
			beneficiario.setConvenio(convenio);
		}

		return beneficiario;
	}
	
	public Beneficiario criarBeneficiarioDependenteDTO(DependenteDTO pacienteDTO) {
		Beneficiario beneficiario = new Beneficiario();

		beneficiario.setId(pacienteDTO.getId());
		beneficiario.setTitular(false);

		beneficiario.setNome(pacienteDTO.getNome());
		beneficiario.setCpf(pacienteDTO.getCpf());
		beneficiario.setAtivo(pacienteDTO.isAtivo());
		beneficiario.setSexo(Sexo.getSexo(pacienteDTO.getSexo()));
		beneficiario.setDataNascimento(pacienteDTO.getDataNascimento());
		beneficiario.setTipoContribuicao(pacienteDTO.getTipoContribuicao());
		beneficiario.setDataAtualizacao(new Date());

		String siglaConvenio = pacienteDTO.getSiglaConvenio();
		if (siglaConvenio != null) {
			Convenio convenio = this.conveniosMap.get(siglaConvenio.trim());
			beneficiario.setConvenio(convenio);
		}

		//Para poder obter o titular
		beneficiario.setSaramTitular(pacienteDTO.getSaramTitular());
		
		return beneficiario;
	}

	public Beneficiario criarBeneficiarioTitularDTO(TitularDTO pacienteDTO) {
		Beneficiario beneficiario = new Beneficiario();

		beneficiario.setId(pacienteDTO.getId());
		beneficiario.setTitular(true);

		beneficiario.setNome(pacienteDTO.getNome());
		beneficiario.setCpf(pacienteDTO.getCpf());
		beneficiario.setSaram(pacienteDTO.getSaram());
		beneficiario.setAtivo(pacienteDTO.isAtivo());
		beneficiario.setSexo(Sexo.getSexo(pacienteDTO.getSexo()));
		beneficiario.setDataNascimento(pacienteDTO.getDataNascimento());
		beneficiario.setTipoContribuicao(pacienteDTO.getTipoContribuicao());
		beneficiario.setDataAtualizacao(new Date());


		String siglaPostoGraduacao = pacienteDTO.getSiglaPostoGraduacao();
		if (siglaPostoGraduacao != null) {
			PostoGraduacao postoGraduacao = this.postosGraduacaoMap.get(siglaPostoGraduacao.trim());
			beneficiario.setPostoGraduacao(postoGraduacao);
		}

		String siglaOrganizacaoMiligar = pacienteDTO.getSiglaUnidadeLotacao();
		if (siglaOrganizacaoMiligar != null) {
			OrganizacaoMilitar om = this.organizacoesMilitaresMap.get(siglaOrganizacaoMiligar.trim());
			beneficiario.setOrganizacaoMilitar(om);
		}

		String siglaConvenio = pacienteDTO.getSiglaConvenio();
		if (siglaConvenio != null) {
			Convenio convenio = this.conveniosMap.get(siglaConvenio.trim());
			beneficiario.setConvenio(convenio);
		}

		return beneficiario;
	}
	
}
