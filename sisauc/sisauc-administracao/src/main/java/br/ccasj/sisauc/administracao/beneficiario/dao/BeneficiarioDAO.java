package br.ccasj.sisauc.administracao.beneficiario.dao;

import java.util.List;
import java.util.Map;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.domain.Convenio;
import br.ccasj.sisauc.administracao.cadastro.domain.PostoGraduacao;
import br.ccasj.sisauc.framework.dao.GenericEntityDAO;

public interface BeneficiarioDAO  extends GenericEntityDAO<Beneficiario> {

	public Beneficiario merge(Beneficiario beneficiario);

	public PostoGraduacao obterPosto(String siglaPosto);
	public List<PostoGraduacao> obterTodosPostos();
	
	public Convenio obterConvenio(String siglaConvenio);
	public List<Convenio> obterTodosConvenios();
	
	
	public Beneficiario obterPorCPFAtivo(String cpf);
	public Beneficiario obterPorSARAM(String saram, boolean titular);
	public List<Beneficiario> obterDependentes(Beneficiario beneficiario);
	public List<Beneficiario> obterPorNome(String nome);
	public List<Beneficiario> listarParaLazyModel(int first, int pageSize, Map<String, Object> filters);
	public int obterQuantidadeResultadosLazyModel(Map<String, Object> filters);
	public int salvarBeneficiariosImportados(List<Beneficiario> beneficiarios);
	public List<Beneficiario> obterTodosOrdenadosPorNome();
	public List<Beneficiario> obterTodosAMHCAtivosPorNome();
	public List<String> salvarBeneficiariosSincronizados(List<Beneficiario> beneficiarios);

}
