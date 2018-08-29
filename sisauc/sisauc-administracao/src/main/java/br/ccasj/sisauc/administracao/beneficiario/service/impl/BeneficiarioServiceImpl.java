package br.ccasj.sisauc.administracao.beneficiario.service.impl;

import br.ccasj.sisauc.administracao.beneficiario.adapter.BeneficiarioAdapter;
import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.BeneficiarioService;
import br.ccasj.sisauc.administracao.beneficiario.service.dto.RetornoBeneficiarios;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Convenio;
import br.ccasj.sisauc.framework.utils.Wso2RestfulHelper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "beneficiarioService")
public class BeneficiarioServiceImpl implements BeneficiarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiarioServiceImpl.class);
    
    @Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;
	@Autowired
	private BeneficiarioDAO beneficiarioDao;


    @Override
    public List<Beneficiario> findByTextParam(final String param, final int maxResult) {
        LOGGER.info("Find Beneficiario by param [{}]", param);
        return findByCpfOrSaramOrName(true, param, param, param, 1, maxResult);
    }

    @Override
    public List<Beneficiario> findByCpfOrSaramOrName(final String cpf, final String saram, final String name, final int first, final int pageSize) {
    	return  findByCpfOrSaramOrName(false, cpf, saram, name, first, pageSize);
    }
    
    
    
    public List<Beneficiario> findByCpfOrSaramOrName(final boolean filtrarPermitidosAMHC ,final String cpf, final String saram, final String name, final int first, final int pageSize) {

        LOGGER.info("Find Beneficiario findByCpfOrSaramOrName [{}], [{}], [{}], [{}], [{}]", cpf, saram, name, first, pageSize);

        final Map<String, Object> parametros = new HashMap<String, Object>();

        parametros.put("nrOrdem", StringUtils.isEmpty(saram) ? null : saram.trim());
        parametros.put("nome", StringUtils.isEmpty(name) ? null : name.trim());
        parametros.put("cpf", StringUtils.isEmpty(cpf) ? null : cpf.trim());

        parametros.put("pageNumber", first);
        parametros.put("pageSize", pageSize);

        final RetornoBeneficiarios retorno = (RetornoBeneficiarios) Wso2RestfulHelper.doGet(Wso2RestfulHelper.baseURISisauc, "buscarBeneficiariosLike/", parametros, RetornoBeneficiarios.class);

        if (retorno == null) {
            LOGGER.info("Response is empty.");
            return new ArrayList<>();
        }

        final List<Beneficiario> beneficiarios = BeneficiarioAdapter.convert(retorno.getBeneficiarios().getBeneficiario());

        List<Beneficiario> beneficiariosAtivos = beneficiarios;
        if (filtrarPermitidosAMHC){
        	beneficiariosAtivos = filtrarBeneficiariosNaoPermitidosNaSolicitacao(beneficiarios);
        }
        
        return beneficiariosAtivos;
    }


    public List<Beneficiario> findByCpfOrSaramOrNameFullLike(final boolean filtrarPermitidosAMHC ,final String cpf, final String saram, final String name, final int first, final int pageSize) {

        LOGGER.info("Find Beneficiario findByCpfOrSaramOrName [{}], [{}], [{}], [{}], [{}]", cpf, saram, name, first, pageSize);

        final Map<String, Object> parametros = new HashMap<String, Object>();

        parametros.put("nrOrdem", StringUtils.isEmpty(saram) ? null : saram.trim() );
        parametros.put("nome", StringUtils.isEmpty(name) ? null : name.trim());
        parametros.put("cpf", StringUtils.isEmpty(cpf) ? null : cpf.trim());

        parametros.put("pageNumber", first);
        parametros.put("pageSize", pageSize);

        final RetornoBeneficiarios retorno = (RetornoBeneficiarios) Wso2RestfulHelper.doGet(Wso2RestfulHelper.baseURISisauc, "buscarTabelaBeneficiarios/", parametros, RetornoBeneficiarios.class);
        
        if (retorno == null) {
            LOGGER.info("Response is empty.");
            return new ArrayList<>();
        }

        final List<Beneficiario> beneficiarios = BeneficiarioAdapter.convert(retorno.getBeneficiarios().getBeneficiario());
        
        for(Beneficiario b : beneficiarios){
        	checkBeneficiario(b);
        }

        List<Beneficiario> beneficiariosAtivos = beneficiarios;
        if (filtrarPermitidosAMHC){
        	beneficiariosAtivos = filtrarBeneficiariosNaoPermitidosNaSolicitacao(beneficiarios);
        }
        
        return beneficiariosAtivos;
    }

    
    private List<Beneficiario> filtrarBeneficiariosNaoPermitidosNaSolicitacao(List<Beneficiario> beneficiarios){
    	List<Beneficiario> result = new ArrayList<Beneficiario>();
    	for (Beneficiario beneficiario : beneficiarios) {
			if (beneficiario.isTitular() && beneficiario.isAtivo()){
				result.add(beneficiario);
			} else if (!beneficiario.isTitular() && beneficiario.isAtivo() && beneficiario.getConvenio().getSigla().equals(Convenio.SIGLA_AMHC)){
				result.add(beneficiario);
			}
		}
    	return result;
    }
    
    @Override
    public Beneficiario findBySaram(final String saram) {
        return findBySaram(false, saram);
    }
    
    
    @Override
    public Beneficiario findBySaram(final boolean filtrarPermitidosAMHC, final String saram) {
        LOGGER.info("Find Beneficiario findBySaram[{}]", saram);
        final List<Beneficiario> byCpfOrSaramOrName = findByCpfOrSaramOrName(filtrarPermitidosAMHC, null, saram, null, 1, 100);
        for (final Beneficiario beneficiario : byCpfOrSaramOrName) {
            if (beneficiario.isTitular() && StringUtils.equals(saram, beneficiario.getSaram())) {
                return beneficiario;
            }
        }

        return null;
    }
    
  
    public Beneficiario checkBeneficiario(Beneficiario pBeneficiario) {
          if (pBeneficiario == null) return null;


          Beneficiario beneficiario = null;

          if (pBeneficiario.getId() != null){
          	beneficiario = beneficiarioDao.findById(pBeneficiario.getId());
          } else if (StringUtils.isNotBlank(pBeneficiario.getSaram())) {
              beneficiario = beneficiarioDao.obterPorSARAM(pBeneficiario.getSaram(), true);
          } else {
              beneficiario = beneficiarioDao.obterPorCPFAtivo(pBeneficiario.getCpf());
          }

          if (beneficiario == null) {
              beneficiario = new Beneficiario();
              beneficiario.setId(pBeneficiario.getId());
          }
          beneficiario.setSaramTitular(pBeneficiario.getSaramTitular());
          beneficiario.setSaram(pBeneficiario.getSaram());
          beneficiario.setAtivo(pBeneficiario.isAtivo());
          beneficiario.setDataAtualizacao(pBeneficiario.getDataAtualizacao());
          beneficiario.setCpf(pBeneficiario.getCpf());
          beneficiario.setNome(pBeneficiario.getNome());
          beneficiario.setSexo(pBeneficiario.getSexo());
          beneficiario.setTitular(pBeneficiario.isTitular());
          beneficiario.setDataNascimento(pBeneficiario.getDataNascimento());
          beneficiario.setTipoContribuicao(pBeneficiario.getTipoContribuicao());

          if (pBeneficiario.getConvenio() != null && StringUtils.isNotBlank(pBeneficiario.getConvenio().getSigla())) {
              beneficiario.setConvenio(beneficiarioDao.obterConvenio(pBeneficiario.getConvenio().getSigla()));
          }
          if (pBeneficiario.getPostoGraduacao() != null && StringUtils.isNotBlank(pBeneficiario.getPostoGraduacao().getSigla())) {
              beneficiario.setPostoGraduacao(beneficiarioDao.obterPosto(pBeneficiario.getPostoGraduacao().getSigla()));
          }
          if (pBeneficiario.getOrganizacaoMilitar() != null && StringUtils.isNotBlank(pBeneficiario.getOrganizacaoMilitar().getSigla())) {
              beneficiario.setOrganizacaoMilitar(organizacaoMilitarDAO.obterOrganizacaoMilitarPorSigla(pBeneficiario.getOrganizacaoMilitar().getSigla()));
          }

          if (!beneficiario.isTitular() && StringUtils.isBlank(beneficiario.getSaram()) && StringUtils.isNotBlank(beneficiario.getSaramTitular())) {

              final Beneficiario tmp = findBySaram(beneficiario.getSaramTitular());

              final Beneficiario titular = checkBeneficiario(tmp);

              beneficiario.setBeneficiarioTitular(titular);
          }

          beneficiarioDao.salvarBeneficiariosImportados(Arrays.asList(beneficiario));
          return beneficiario;
      }
  	
}