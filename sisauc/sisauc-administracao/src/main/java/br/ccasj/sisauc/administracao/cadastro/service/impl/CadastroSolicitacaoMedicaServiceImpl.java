package br.ccasj.sisauc.administracao.cadastro.service.impl;

import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.BeneficiarioService;
import br.ccasj.sisauc.administracao.cadastro.dao.OrganizacaoMilitarDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.SolicitacaoRessarcimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.*;
import br.ccasj.sisauc.administracao.cadastro.service.ArquivoService;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroSolicitacaoMedicaService;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoSistemaDao;
import br.ccasj.sisauc.administracao.sistema.domain.ConfiguracaoSistema;
import br.ccasj.sisauc.emissaoar.domain.ApresentacaoAutorizacaoRessarcimento;
import br.ccasj.sisauc.framework.context.AuthenticationContext;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Transactional
@Service(value = "cadastroSolicitacaoMedicaService")
public class CadastroSolicitacaoMedicaServiceImpl implements CadastroSolicitacaoMedicaService {

    private static final long serialVersionUID = -2467295235321516268L;

    @Autowired
    private AuthenticationContext authenticationContext;
    @Autowired
    private SolicitacaoRessarcimentoDAO solicitacaoRessarcimentoDAO;
    @Autowired
    private ConfiguracaoSistemaDao configuracaoSistemaDao;
    @Autowired
    private ArquivoService arquivoService;
    @Autowired
    private SolicitacaoProcedimentoDAO solicitacaoProcedimentoDAO;

    @Autowired
    private BeneficiarioDAO beneficiarioDAO;

    @Autowired
    private OrganizacaoMilitarDAO organizacaoMilitarDAO;

    @Autowired
    private BeneficiarioService beneficiarioService;

    @Override
    public SolicitacaoMedica salvar(SolicitacaoMedica solicitacao) {
        validarSolicitacao(solicitacao);
        verificarNovaSolicitacao(solicitacao);
        verificacaoNovoArquivo(solicitacao);
        solicitacao.setAutorSolicitacaoSistema(authenticationContext.getUsuarioLogado());
        if (solicitacao instanceof SolicitacaoRessarcimento) {
            verificaBeneficiario(solicitacao);
            solicitacao = solicitacaoRessarcimentoDAO.merge((SolicitacaoRessarcimento) solicitacao);
        }
        return solicitacao;
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

    private synchronized void verificacaoNovoArquivo(SolicitacaoMedica solicitacao) {
        if (solicitacao.getArquivo() != null && solicitacao.getArquivo().getId() == null) {
            solicitacao.getArquivo().setDataInsercao(new Date());
            solicitacao.setArquivo(arquivoService.inserirArquivoNoSistema(solicitacao.getArquivo(),
                    solicitacao.getOrganizacaoMilitarSolicitante().getId(),
                    solicitacao.getDataInsercaoSistema()));
        }

        if (solicitacao instanceof SolicitacaoRessarcimento) {
            if (((SolicitacaoRessarcimento) solicitacao).isNaoEletiva()) {
                configurarArquivosApresentacao((SolicitacaoRessarcimento) solicitacao);
            }
        }
    }

    private synchronized void validarSolicitacao(SolicitacaoMedica solicitacao) {
        if (solicitacao instanceof SolicitacaoRessarcimento) {
            SolicitacaoRessarcimento solicitacaoRessarcimento = (SolicitacaoRessarcimento) solicitacao;
            if (solicitacaoRessarcimento.getArquivo() == null && !solicitacaoRessarcimento.isNaoEletiva()) {
                throw new SystemRuntimeException("Pedido Médico: É necessário inserir um arquivo (Solicitação Física) para prosseguir.");
            }
        } else if (solicitacao instanceof SolicitacaoProcedimento) {
            ConfiguracaoSistema configuracaoSistema = configuracaoSistemaDao.obterConfiguracaoSistema();
            if (configuracaoSistema.isPermitirSolicitacoesSemUpload() == false && solicitacao.getArquivo() == null) {
                throw new SystemRuntimeException("É necessário inserir um arquivo (Solicitação Física) para prosseguir");
            }
        }
    }

    public boolean isApresentacaoPreenchida(ApresentacaoAutorizacaoRessarcimento apresentacao) {
        return !StringUtils.isEmpty(apresentacao.getNumeroNotaFiscal()) &&
                apresentacao.getDataNotaFiscal() != null &&
                !StringUtils.isEmpty(apresentacao.getCpfCnpjPrestador()) &&
                !StringUtils.isEmpty(apresentacao.getNomePrestador()) &&
                apresentacao.getArquivoRequerimento() != null &&
                apresentacao.getArquivoNotaFiscal() != null;
    }

    private synchronized void verificarNovaSolicitacao(SolicitacaoMedica solicitacao) {
        if (solicitacao.getId() == null) {
            Date dataAtual = new Date();
            solicitacao.setDataInsercaoSistema(dataAtual);
            solicitacao.setDataUltimaAlteracaoEstado(dataAtual);
            solicitacao.setOrganizacaoMilitarSolicitante(authenticationContext.getUsuarioLogado().getOrganizacaoMilitar());
            inserirNumeracao(solicitacao);
        }
    }

    private void inserirNumeracao(SolicitacaoMedica solicitacao) {
        Calendar calendar = Calendar.getInstance();
        StringBuilder builder = new StringBuilder();
        Integer ano = Integer.valueOf(calendar.get(Calendar.YEAR));
        int quantidade = 1;
        if (solicitacao instanceof SolicitacaoProcedimento) {
            quantidade += solicitacaoProcedimentoDAO.obterQuantidadeSolicitacoesProcedimentoPorAnoEOM(solicitacao.getOrganizacaoMilitarSolicitante().getId(), ano);
        } else if (solicitacao instanceof SolicitacaoRessarcimento) {
            quantidade += solicitacaoRessarcimentoDAO.obterQuantidadeSolicitacoesRessarcimentoPorAnoEOM(solicitacao.getOrganizacaoMilitarSolicitante().getId(), ano);
        }
        builder.append(solicitacao instanceof SolicitacaoRessarcimento ? "SR" : "SP")
                .append(String.valueOf(ano)).append("/")
                .append(solicitacao.getOrganizacaoMilitarSolicitante().getSigla()).append("/")
                .append(StringUtils.leftPad(String.valueOf(quantidade), 5, "0"));
        solicitacao.setNumero(builder.toString());
    }

    @Override
    public SolicitacaoMedica enviarParaAuditoria(SolicitacaoMedica solicitacao) {
        validarSolicitacao(solicitacao);
        validarSolicitacaoNaoEletiva(solicitacao);
        verificarSePossuiProcedimentos(solicitacao);
        validarProcedimentosComInternacao(solicitacao);
        Date dataAtual = new Date();
        solicitacao.setDataEnvioAuditoria(dataAtual);
        solicitacao.setDataUltimaAlteracaoEstado(dataAtual);
        solicitacao.setEstado(EstadoSolicitacaoProcedimento.ENVIADA_PARA_AUDITORIA);
        return salvar(solicitacao);
    }

    private void validarSolicitacaoNaoEletiva(SolicitacaoMedica solicitacao) {
        if (solicitacao instanceof SolicitacaoRessarcimento) {
            SolicitacaoRessarcimento solicitacaoRessarcimento = (SolicitacaoRessarcimento) solicitacao;
            if (solicitacaoRessarcimento.isNaoEletiva()) {

                if (!isApresentacaoPreenchida(solicitacaoRessarcimento.getApresentacao())) {
                    throw new SystemRuntimeException("Para enviar para auditoria, é obrigatório preencher os Dados da Apresentação.");
                }

                if (!isCpfCnpjPrestadorValido(solicitacaoRessarcimento.getApresentacao())) {
                    throw new SystemRuntimeException("CNPJ/CPF inválidos.");
                }
            }

        }
    }

    public boolean isCpfCnpjPrestadorValido(ApresentacaoAutorizacaoRessarcimento apresentacao) {
        String cpfCnpj = apresentacao.getCpfCnpjPrestador();
        if (cpfCnpj.length() == 11) {
            return new CPFValidator().invalidMessagesFor(cpfCnpj).isEmpty();
        } else if (cpfCnpj.length() == 14) {
            return new CNPJValidator().invalidMessagesFor(cpfCnpj).isEmpty();
        } else {
            return false;
        }
    }

    private void verificarSePossuiProcedimentos(SolicitacaoMedica solicitacao) {
        if (solicitacao instanceof SolicitacaoRessarcimento) {
            if (((SolicitacaoRessarcimento) solicitacao).getProcedimentos().isEmpty()) {
                throw new SystemRuntimeException("Não é possível enviar para auditoria uma solicitação sem procedimentos");
            }
        }
    }

    private void validarProcedimentosComInternacao(SolicitacaoMedica solicitacao) {
        if (solicitacao instanceof SolicitacaoRessarcimento) {
            SolicitacaoRessarcimento sol = (SolicitacaoRessarcimento) solicitacao;
            for (ProcedimentoPedidoSolicitacaoRessarcimento procedimento : sol.getProcedimentos()) {
                if (procedimento.getProcedimento() != null &&
                        procedimento.getProcedimento().getTabela().equals(Tabela.TRS) &&
                        ((ProcedimentoTRS) procedimento.getProcedimento()).isInternacao() &&
                        !sol.isInternacao()) {
                    throw new SystemRuntimeException("Existem procedimentos TRS adicionados que exigem internação. " +
                            "Marque a opção 'Internação' e selecione a 'Acomodação'.");
                }
            }
        }
    }

    private void configurarArquivosApresentacao(SolicitacaoRessarcimento solicitacao) {
        ApresentacaoAutorizacaoRessarcimento apresentacao = solicitacao.getApresentacao();
        if (apresentacao.getArquivoNotaFiscal() != null && apresentacao.getArquivoNotaFiscal().getId() == null) {
            apresentacao.getArquivoNotaFiscal().setDataInsercao(new Date());
            Arquivo arquivoNotaFiscal = arquivoService.inserirArquivoNoSistema(apresentacao.getArquivoNotaFiscal(),
                    solicitacao.getOrganizacaoMilitarSolicitante().getId(),
                    apresentacao.getArquivoNotaFiscal().getDataInsercao());
            apresentacao.setArquivoNotaFiscal(arquivoNotaFiscal);
        }

        if (apresentacao.getArquivoRequerimento() != null && apresentacao.getArquivoRequerimento().getId() == null) {
            apresentacao.getArquivoRequerimento().setDataInsercao(new Date());
            Arquivo arquivoRequerimento = arquivoService.inserirArquivoNoSistema(apresentacao.getArquivoRequerimento(),
                    solicitacao.getOrganizacaoMilitarSolicitante().getId(),
                    apresentacao.getArquivoRequerimento().getDataInsercao());
            apresentacao.setArquivoRequerimento(arquivoRequerimento);
        }
    }

}
