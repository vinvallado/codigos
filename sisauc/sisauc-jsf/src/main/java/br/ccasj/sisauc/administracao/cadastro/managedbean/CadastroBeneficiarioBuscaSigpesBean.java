package br.ccasj.sisauc.administracao.cadastro.managedbean;

import br.ccasj.sisauc.administracao.beneficiario.adapter.BeneficiarioAdapter;
import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.BeneficiarioService;
import br.ccasj.sisauc.administracao.beneficiario.service.GerenciadorBeneficiarioService;
import br.ccasj.sisauc.administracao.beneficiario.service.SincronizadorBeneficiarioService;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ApplicationBean;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Scope(value = "view")
@Service(value = "cadastroBeneficiarioBuscaSigpesBean")
public class CadastroBeneficiarioBuscaSigpesBean implements Serializable {

    private static final long serialVersionUID = -7322803090228228123L;

    List<Beneficiario> beneficiariosLista;

    @Autowired
    private GerenciadorBeneficiarioService gerenciadorBeneficiarioService;

    @Autowired
    private SincronizadorBeneficiarioService sincronizadorBeneficiarioService;

    @Autowired
    private BeneficiarioDAO beneficiarioDAO;

    @Autowired
    private BeneficiarioService beneficiarioService;

    @Autowired
    private OrganizacaoMilitarDAO organizacaoMilitarDAO;
    
    @PostConstruct
    private void init() {
        //vazio
    }


    public void buscar() throws SisaucException {
        final String cpf = ManagedBeanUtils.obterParametroRequest("cpf");
        final String saram = ManagedBeanUtils.obterParametroRequest("saram");
        if (StringUtils.isNotBlank(cpf) || StringUtils.isNotBlank(saram)) {
            if (!cpf.isEmpty() && !saram.isEmpty()) {
                //Caso preencha os 2 campos
                Mensagem.erro("Só é possível escolher uma opção de pesquisa: CPF do Titular ou SARAM do Titular");
                return; 
            }
            beneficiariosLista = beneficiarioService.findByCpfOrSaramOrName(cpf, saram, null, 1, 30);
            if (!cpf.isEmpty()){
            	// Simula o mesmo comportamento do sistema antes da alteração para o novo serviço do SIGPES
            	// Busca todos os beneficiários ligados ao CPF
            	if (beneficiariosLista.size() > 0){
            		Beneficiario beneficiarioTemp = beneficiariosLista.get(0);
            		String saramTemp = BeneficiarioAdapter.converteIdLongParaString(beneficiarioTemp.getId()/100L, Beneficiario.TAMANHO_SARAM);
            		beneficiariosLista = beneficiarioService.findByCpfOrSaramOrName(false, null, saramTemp, null, 1, 30);
            	}
            }
            if (beneficiariosLista != null && beneficiariosLista.size() > 0) {
            	for (Beneficiario beneficiario : beneficiariosLista) {
            		checkBeneficiario(beneficiario);
				}
                ManagedBeanUtils.obter("applicationBean", ApplicationBean.class).initBeneficiarios();
                Mensagem.informacao("Beneficiários Importados com Sucesso.");
            } else {
                Mensagem.erro("Nenhum Beneficiário encontrado!");
            }

        } else {
            Mensagem.erro("Necessário informar CPF ou SARAM!");
        }
    }

	//TODO colocar essa função no Service de Beneficiário pois está sendo reutilizada em 3 funcionalidades.
	/**
	 * 
	 * @param beneficiarioSolicitacao
	 * @return
	 */
    private Beneficiario checkBeneficiario(Beneficiario beneficiarioSolicitacao) {
        if (beneficiarioSolicitacao == null) 
        	return null;


        Beneficiario beneficiario = null;
        if (beneficiarioSolicitacao.getId() != null){
        	beneficiario = beneficiarioDAO.findById(beneficiarioSolicitacao.getId());
        } else if (StringUtils.isNotBlank(beneficiarioSolicitacao.getSaram())) {
            beneficiario = beneficiarioDAO.obterPorSARAM(beneficiarioSolicitacao.getSaram(), true);
        } else {
            beneficiario = beneficiarioDAO.obterPorCPFAtivo(beneficiarioSolicitacao.getCpf());
        }

        if (beneficiario == null) {
            beneficiario = new Beneficiario();
            beneficiario.setId(beneficiarioSolicitacao.getId());
        }
        beneficiario.setSaramTitular(beneficiarioSolicitacao.getSaramTitular());
        beneficiario.setSaram(beneficiarioSolicitacao.getSaram());
        beneficiario.setAtivo(beneficiarioSolicitacao.isAtivo());
        beneficiario.setDataAtualizacao(beneficiarioSolicitacao.getDataAtualizacao());
        beneficiario.setCpf(beneficiarioSolicitacao.getCpf());
        beneficiario.setNome(beneficiarioSolicitacao.getNome());
        beneficiario.setSexo(beneficiarioSolicitacao.getSexo());
        beneficiario.setTitular(beneficiarioSolicitacao.isTitular());
        beneficiario.setDataNascimento(beneficiarioSolicitacao.getDataNascimento());
        beneficiario.setTipoContribuicao(beneficiarioSolicitacao.getTipoContribuicao());

        if (beneficiarioSolicitacao.getConvenio() != null && StringUtils.isNotBlank(beneficiarioSolicitacao.getConvenio().getSigla())) {
            beneficiario.setConvenio(beneficiarioDAO.obterConvenio(beneficiarioSolicitacao.getConvenio().getSigla()));
        }
        if (beneficiarioSolicitacao.getPostoGraduacao() != null && StringUtils.isNotBlank(beneficiarioSolicitacao.getPostoGraduacao().getSigla())) {
            beneficiario.setPostoGraduacao(beneficiarioDAO.obterPosto(beneficiarioSolicitacao.getPostoGraduacao().getSigla()));
        }
        if (beneficiarioSolicitacao.getOrganizacaoMilitar() != null && StringUtils.isNotBlank(beneficiarioSolicitacao.getOrganizacaoMilitar().getSigla())) {
            beneficiario.setOrganizacaoMilitar(organizacaoMilitarDAO.obterOrganizacaoMilitarPorSigla(beneficiarioSolicitacao.getOrganizacaoMilitar().getSigla()));
        }

        if (!beneficiario.isTitular() && StringUtils.isBlank(beneficiario.getSaram()) && StringUtils.isNotBlank(beneficiario.getSaramTitular())) {
        	
            final Beneficiario tmp = beneficiarioService.findBySaram(beneficiario.getSaramTitular());

            final Beneficiario titular = checkBeneficiario(tmp);

            beneficiario.setBeneficiarioTitular(titular);
        }

        beneficiarioDAO.salvarBeneficiariosImportados(Arrays.asList(beneficiario));
        return beneficiario;
    }
 
    
    


    public List<Beneficiario> getBeneficiariosLista() {
        return beneficiariosLista;
    }

    public void atualizar() {
        sincronizadorBeneficiarioService.sincronizar();
    }

}
