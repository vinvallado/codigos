package br.ccasj.sisauc.administracao.cadastro.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.BeneficiarioService;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.EstadoSolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoMedica;
import br.ccasj.sisauc.administracao.cadastro.domain.SolicitacaoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.service.ArquivoService;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroSolicitacaoService;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoSistemaDao;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "cadastroSolicitacaoService")
public class CadastroSolicitacaoServiceImpl implements CadastroSolicitacaoService {

	private static final long serialVersionUID = 406883579738832312L;

	@Autowired
	private AuthenticationContext authenticationContext;
	@Autowired
	private SolicitacaoProcedimentoDAO solicitacaoDAO;
	@Autowired
	private ArquivoService arquivoService;
	@Autowired
	private ConfiguracaoSistemaDao configuracaoSistemaDao;
	@Autowired
	private OrganizacaoMilitarDAO organizacaoMilitarDAO;
	@Autowired
	private BeneficiarioDAO beneficiarioDao;
	@Autowired
	private BeneficiarioService beneficiarioService;

	@Override
	public void salvar(SolicitacaoProcedimento solicitacao) {
		// TODO precisamos arrumar um modo de criptografar campos pre
		// determinados (dados clínicos)
		persistirSolicitacao(solicitacao, verificarNovaSolicitacao(solicitacao));
	}

	public synchronized SolicitacaoProcedimento persistirSolicitacao(SolicitacaoProcedimento solicitacao,
			EstadoSolicitacaoProcedimento estado) {
		verificaBeneficiario(solicitacao);
		
		solicitacao.setEstado(estado);
		Date dataAtual = new Date();
		if (solicitacao.getId() == null) {
			solicitacao.setDataInsercaoSistema(dataAtual);
			solicitacao.setDataUltimaAlteracaoEstado(dataAtual);
			// TODO verificar se isso não poderá ser inserido pelo usuário
			solicitacao
					.setOrganizacaoMilitarSolicitante(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar());

		}
		solicitacao.setAutorSolicitacaoSistema(authenticationContext.getUsuarioLogado());
		verificarNovoArquivo(solicitacao);
		verificarNumeracao(solicitacao);

		return solicitacaoDAO.merge(solicitacao);
	}

	private synchronized void verificarNovoArquivo(SolicitacaoProcedimento solicitacao) {
		if (solicitacao.getArquivo() != null && solicitacao.getArquivo().getId() == null) {
			solicitacao.getArquivo().setDataInsercao(new Date());
			solicitacao.setArquivo(arquivoService.inserirArquivoNoSistema(solicitacao.getArquivo(),
					solicitacao.getOrganizacaoMilitarSolicitante().getId(), solicitacao.getDataInsercaoSistema()));
		}
	}

	private synchronized EstadoSolicitacaoProcedimento verificarNovaSolicitacao(SolicitacaoProcedimento solicitacao) {
		if (solicitacao.getId() == null) {
			return EstadoSolicitacaoProcedimento.CRIADA;
		}
		return solicitacao.getEstado();
	}

	@Override
	public SolicitacaoProcedimento enviarAuditoria(SolicitacaoProcedimento solicitacao) throws Exception {
		verificarEnvioAuditoria(solicitacao);
		// if (solicitacao.getId() == null) {
		// solicitacao = salvar(solicitacao);
		// }
		Date dataAtual = new Date();
		solicitacao.setDataEnvioAuditoria(dataAtual);
		solicitacao.setDataUltimaAlteracaoEstado(dataAtual);
		return persistirSolicitacao(solicitacao, EstadoSolicitacaoProcedimento.ENVIADA_PARA_AUDITORIA);
	}

	private void verificarEnvioAuditoria(SolicitacaoProcedimento solicitacao) throws Exception {
		if (solicitacao.getProcedimentos() == null || solicitacao.getProcedimentos().isEmpty()) {
			throw new Exception(
					"É necessário adicionar ao menos um procedimento para enviar esta solicitação para auditoria");
		}
	}

	private synchronized void verificarNumeracao(SolicitacaoProcedimento solicitacao) {
		if (solicitacao.getId() == null) {
			Calendar calendar = Calendar.getInstance();
			StringBuilder builder = new StringBuilder();
			Integer ano = Integer.valueOf(calendar.get(Calendar.YEAR));
			int quantidade = solicitacaoDAO.obterQuantidadeSolicitacoesProcedimentoPorAnoEOM(
					solicitacao.getOrganizacaoMilitarSolicitante().getId(), ano) + 1;
			builder.append("SP").append(String.valueOf(ano)).append("/")
					.append(solicitacao.getOrganizacaoMilitarSolicitante().getSigla()).append("/")
					.append(StringUtils.leftPad(String.valueOf(quantidade), 5, "0"));
			solicitacao.setNumero(builder.toString());
		}
	}

	@Override
	public void verificarSolicitacaoPossuiArquivo(SolicitacaoProcedimento solicitacao) {
		ConfiguracaoSistema configuracaoSistema = configuracaoSistemaDao.obterConfiguracaoSistema();
		if (configuracaoSistema.isPermitirSolicitacoesSemUpload() == false && solicitacao.getArquivo() == null) {
			throw new SystemRuntimeException("É necessário inserir um arquivo (Solicitação Física) para prosseguir");
		}
	}
	
	 private synchronized void verificaBeneficiario(SolicitacaoMedica solicitacao) {
	        if (solicitacao.getId() != null) {
	            return;
	        }
	        final Beneficiario beneficiario = checkBeneficiario(solicitacao.getBeneficiario());
	        solicitacao.setBeneficiario(beneficiario);

	    }
	
	//TODO colocar essa função no Service de Beneficiário pois está sendo reutilizada em 3 funcionalidades.
	/**
	 * 
	 * @param beneficiarioSolicitacao
	 * @return
	 */
    private Beneficiario checkBeneficiario(Beneficiario beneficiarioSolicitacao) {
        if (beneficiarioSolicitacao == null) return null;


        Beneficiario beneficiario = null;

        if (beneficiarioSolicitacao.getId() != null){
        	beneficiario = beneficiarioDao.findById(beneficiarioSolicitacao.getId());
        } else if (StringUtils.isNotBlank(beneficiarioSolicitacao.getSaram())) {
            beneficiario = beneficiarioDao.obterPorSARAM(beneficiarioSolicitacao.getSaram(), true);
        } else {
            beneficiario = beneficiarioDao.obterPorCPFAtivo(beneficiarioSolicitacao.getCpf());
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
            beneficiario.setConvenio(beneficiarioDao.obterConvenio(beneficiarioSolicitacao.getConvenio().getSigla()));
        }
        if (beneficiarioSolicitacao.getPostoGraduacao() != null && StringUtils.isNotBlank(beneficiarioSolicitacao.getPostoGraduacao().getSigla())) {
            beneficiario.setPostoGraduacao(beneficiarioDao.obterPosto(beneficiarioSolicitacao.getPostoGraduacao().getSigla()));
        }
        if (beneficiarioSolicitacao.getOrganizacaoMilitar() != null && StringUtils.isNotBlank(beneficiarioSolicitacao.getOrganizacaoMilitar().getSigla())) {
            beneficiario.setOrganizacaoMilitar(organizacaoMilitarDAO.obterOrganizacaoMilitarPorSigla(beneficiarioSolicitacao.getOrganizacaoMilitar().getSigla()));
        }

        if (!beneficiario.isTitular() && StringUtils.isBlank(beneficiario.getSaram()) && StringUtils.isNotBlank(beneficiario.getSaramTitular())) {

            final Beneficiario tmp = beneficiarioService.findBySaram(beneficiario.getSaramTitular());

            final Beneficiario titular = checkBeneficiario(tmp);

            beneficiario.setBeneficiarioTitular(titular);
        }

        beneficiarioDao.salvarBeneficiariosImportados(Arrays.asList(beneficiario));
        return beneficiario;
    }
	

}
