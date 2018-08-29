package br.ccasj.sisauc.administracao.cadastro.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.ProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.GrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCBHPM2012;
import br.ccasj.sisauc.administracao.cadastro.domain.SubGrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoCobrancaCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.service.CadastroConfiguracaoEditalService;
import br.ccasj.sisauc.administracao.exceptions.ConfiguracaoExistenteException;
import br.ccasj.sisauc.administracao.formatter.ProcedimentoFormatter;
import br.ccasj.sisauc.administracao.historico.domain.HistoricoValorCadastroEditalConfiguracao;
import br.ccasj.sisauc.framework.dao.GenericEntityHistoricoDAO;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.faces.Mensagem;

@Transactional
@Service(value = "cadastroConfiguracaoEditalService")
public class CadastroConfiguracaoEditalServiceImpl implements CadastroConfiguracaoEditalService {

	@Autowired
	private ConfiguracaoEditalCredenciadoProcedimentoDAO configuracaoEditalCredenciadoProcedimentoDAO;
	@Autowired
	private ProcedimentoDAO procedimentoDAO;
	@Autowired
	private GenericEntityHistoricoDAO<HistoricoValorCadastroEditalConfiguracao> genericEntityHistoricoDAO;
	
	private ProcedimentoFormatter procedimentoFormatter = new ProcedimentoFormatter();

	@Override
	public void salvar(ConfiguracaoEditalCredenciadoProcedimento configuracao) throws ConfiguracaoExistenteException{
		verificarConfiguracao(configuracao);
		ConfiguracaoEditalCredenciadoProcedimento configuracaoSalva = configuracaoEditalCredenciadoProcedimentoDAO.merge(configuracao);
		genericEntityHistoricoDAO.merge(new HistoricoValorCadastroEditalConfiguracao(configuracao.getValor(), configuracao.getTipo(), configuracao.getProcedimento(), configuracao.getEspecialidade(), configuracaoSalva));
	}
	
	public void criarConfiguracoesPorProcedimento(ProcedimentoBase procedimento, TipoCobrancaCredenciadoProcedimento tipo, Double valor, ConfiguracaoEditalCredenciado credenciado, List<Especialidade> especialidades) {
		List<ProcedimentoBase> procedimentos = new ArrayList<ProcedimentoBase>();
		procedimentos.add(procedimento);
		criarConfiguracoesPorProcedimentos(procedimentos, tipo, valor, credenciado, especialidades);
	}
	
	@Override
	public void criarConfiguracoesPorGrupo(GrupoProcedimento grupo, Class<? extends ProcedimentoBase> clazz, TipoCobrancaCredenciadoProcedimento tipo, Double valor, ConfiguracaoEditalCredenciado credenciado, List<Especialidade> especialidades){	
		List<? extends ProcedimentoBase> procedimentos = procedimentoDAO.listarProcedimentosPorGrupo(grupo.getId(), clazz);
		//Regra de Negócio: Os únicos procedimentos que deverão ter especialidades no Grupo CONSULTAS são os procedimentos do Subgrupo CONSULTAS
		if (grupo.getCodigo().equals("10100008")) {
			List<ProcedimentoBase> procedimentosComEspecialidade = new ArrayList<ProcedimentoBase>();
			List<ProcedimentoBase> procedimentosSemEspecialidade = new ArrayList<ProcedimentoBase>();
			for (ProcedimentoBase procedimento : procedimentos) {
				if(procedimento.getCodigo().equals("10101012") || procedimento.getCodigo().equals("10101020") || procedimento.getCodigo().equals("10101039")){
					procedimentosComEspecialidade.add(procedimento);
				} else {
					procedimentosSemEspecialidade.add(procedimento);
				}
			}
			criarConfiguracoesPorProcedimentos(procedimentosComEspecialidade, tipo, valor, credenciado, especialidades);
			criarConfiguracoesPorProcedimentos(procedimentosSemEspecialidade, tipo, valor, credenciado, null);
		} else {
			criarConfiguracoesPorProcedimentos(new ArrayList<ProcedimentoBase>(procedimentos), tipo, valor, credenciado, especialidades);
		}
		
	}
	
	@Override
	public  void criarConfiguracoesPorSubGrupo(SubGrupoProcedimento subGrupoProcedimento, Class<? extends ProcedimentoBase> clazz, TipoCobrancaCredenciadoProcedimento tipo, Double valor, ConfiguracaoEditalCredenciado credenciado, List<Especialidade> especialidades){	
		List<? extends ProcedimentoBase> procedimentos = procedimentoDAO.listarProcedimentosPorSubgrupo(subGrupoProcedimento.getId(), clazz);
		criarConfiguracoesPorProcedimentos(new ArrayList<ProcedimentoBase>(procedimentos), tipo, valor, credenciado, especialidades);
	}
	
	private void criarConfiguracoesPorProcedimentos(List<ProcedimentoBase> procedimentos, TipoCobrancaCredenciadoProcedimento tipo, Double valor, ConfiguracaoEditalCredenciado credenciado, List<Especialidade> especialidades){	
		List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes = new ArrayList<ConfiguracaoEditalCredenciadoProcedimento>();
		for (ProcedimentoBase procedimento : procedimentos) {
			if(especialidades ==  null || especialidades.isEmpty()){
				configuracoes.add(criarConfiguracao(procedimento, tipo, valor, credenciado));
			} else {
				configuracoes.addAll(criarConfiguracaoPorEspecialidades(procedimento, tipo, valor, credenciado, especialidades));
			}
		}

		StringBuilder b = new StringBuilder();
		for (ConfiguracaoEditalCredenciadoProcedimento configuracaoEditalCredenciadoProcedimento : configuracoes) {
			try {
				salvar(configuracaoEditalCredenciadoProcedimento);
			} catch (ConfiguracaoExistenteException e) {
				b.append(" " + configuracaoEditalCredenciadoProcedimento.getProcedimento().getCodigo()+" ");
			}
		}
		if(!b.toString().isEmpty()){
			Mensagem.erro("Os seguintes procedimentos não foram adicionados:" + b.toString() + ". Os mesmos já foram previamente cadastrados.");
		}
		
	}
	
	private List<ConfiguracaoEditalCredenciadoProcedimento> criarConfiguracaoPorEspecialidades(ProcedimentoBase proc, TipoCobrancaCredenciadoProcedimento tipo, Double valor, ConfiguracaoEditalCredenciado credenciado, List<Especialidade> especialidades) {
		List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes = new ArrayList<ConfiguracaoEditalCredenciadoProcedimento>();
		for (Especialidade especialidade : especialidades) {
			ConfiguracaoEditalCredenciadoProcedimento conf = criarConfiguracao(proc, tipo, valor, credenciado);
			conf.setEspecialidade(especialidade);
			configuracoes.add(conf);
		}
		return configuracoes;
	}

	private ConfiguracaoEditalCredenciadoProcedimento criarConfiguracao(ProcedimentoBase proc, TipoCobrancaCredenciadoProcedimento tipo, Double valor, ConfiguracaoEditalCredenciado credenciado){
		ConfiguracaoEditalCredenciadoProcedimento conf = new ConfiguracaoEditalCredenciadoProcedimento();
		conf.setProcedimento(proc);
		conf.setTipo(tipo);
		conf.setConfiguracaoEditalCredenciado(credenciado);
		conf.setAtivo(true);
		conf.setValor(valor);
		return conf;
	}
	

	private void verificarConfiguracao(ConfiguracaoEditalCredenciadoProcedimento configuracao) throws ConfiguracaoExistenteException {
		verificarProcedimentoConsulta(configuracao);
		verificarUnicidadeConfiguracao(configuracao);
		verificarNovaConfiguracao(configuracao);
	}

	private void verificarNovaConfiguracao(ConfiguracaoEditalCredenciadoProcedimento configuracao) {
		if (configuracao.getId() == null) {
			configuracao.setDataInsercao(new Date());
		}
	}

	private void verificarProcedimentoConsulta(ConfiguracaoEditalCredenciadoProcedimento configuracao) {
		if(configuracao.getProcedimento() instanceof ProcedimentoCBHPM2012){
			ProcedimentoCBHPM2012 procedimento = (ProcedimentoCBHPM2012) configuracao.getProcedimento();
				if (procedimento.getSubGrupo() == null) {
					throw new SystemRuntimeException("Não foi possível verificar o subgrupo deste procedimento.");
				}
				if (ProcedimentoCBHPM2012.CODIGO_CONSULTA.equalsIgnoreCase(procedimento.getSubGrupo().getCodigo()) && configuracao.getEspecialidade() == null) {
					throw new SystemRuntimeException("Ao selecionar um procedimento do subgrupo 'Consulta', escolher uma especialidade torna-se obrigatório");
				}
		}
	}

	private void verificarUnicidadeConfiguracao(ConfiguracaoEditalCredenciadoProcedimento configuracao) throws ConfiguracaoExistenteException {
		if (configuracaoEditalCredenciadoProcedimentoDAO.verificarPresencaCredenciadoProcedimentoEspecialidadePorEdital(configuracao.getId(), configuracao.getConfiguracaoEditalCredenciado().getId(), configuracao.getProcedimento(), configuracao.getEspecialidade())) {
			throw new ConfiguracaoExistenteException("O procedimento" + procedimentoFormatter.getCodigoDescricao(configuracao.getProcedimento()) + " já foi adicionado neste Edital.");
		}
	}
	
	@Override
	public void desativar(Integer id) {
		List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes = new ArrayList<ConfiguracaoEditalCredenciadoProcedimento>();
		configuracoes.add(new ConfiguracaoEditalCredenciadoProcedimento(id));
		desativar(configuracoes);
	}
	
	@Override
	public void desativar(List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes) {
		if(configuracoes != null && !configuracoes.isEmpty()){
			configuracaoEditalCredenciadoProcedimentoDAO.desativar(configuracoes);
		} else {
			throw new SystemRuntimeException("Escolha um procedimento para excluir");
		}
	}

}