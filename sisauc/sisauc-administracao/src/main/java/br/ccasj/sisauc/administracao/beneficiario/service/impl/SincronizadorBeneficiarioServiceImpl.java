package br.ccasj.sisauc.administracao.beneficiario.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.dao.HistoricoSincronizacaoBeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.domain.EstadoSincronizacao;
import br.ccasj.sisauc.administracao.beneficiario.domain.HistoricoSincronizacaoBeneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.SincronizadorBeneficiarioService;
import br.ccasj.sisauc.administracao.clientews.BeneficiarioWSClient;
import br.ccasj.sisauc.administracao.sistema.dao.ConfiguracaoWSSigpesDAO;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.integracao.entity.DependenteDTO;
import br.ccasj.sisauc.integracao.entity.MensagemRetornoListDTO;
import br.ccasj.sisauc.integracao.entity.TitularDTO;

@Deprecated
@Service(value = "sincronizadorBeneficiarioService")
public class SincronizadorBeneficiarioServiceImpl implements SincronizadorBeneficiarioService {

	@Autowired
	BeneficiarioWSClient beneficiarioWSClient;

	@Autowired
	private BeneficiarioDAO beneficiarioDAO;
	
	@Autowired
	private HistoricoSincronizacaoBeneficiarioDAO historicoSincronizacaoBeneficiarioDAO;
	
	@Autowired
	private ConfiguracaoWSSigpesDAO configuracaoWSSigpesDAO; 
	
	HistoricoSincronizacaoBeneficiario historicoSincronizacao;
	
	@Autowired
	private GerenciadorBeneficiarioUtil gerenciadorBeneficiarioUtil;
	
	@Override
	public void verificaSincronizacaoAgendada() {
		Date horaSincronizacao = configuracaoWSSigpesDAO.obterConfiguracoesWSSigpes().getHoraSincronizacao();
			
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(horaSincronizacao);
		
		int horaAgenda = calendarDate.get(Calendar.HOUR_OF_DAY);
		int minutoAgenda = calendarDate.get(Calendar.MINUTE);
		
		calendarDate.setTime(new Date());
		int horaAtual = calendarDate.get(Calendar.HOUR_OF_DAY);
		int minutoAtual = calendarDate.get(Calendar.MINUTE);
		
		if (horaAtual == horaAgenda && minutoAtual == minutoAgenda){
			this.sincronizar();
		}
	}
	
	@Override
	public void sincronizar() {
		int quantidadeRegistrosObtidos = 0;
		int quantidadeAtualizados = 0;
		String mensagemAtualizacao = "";
		
		System.out.printf("\n[%tF %tT]XXXXX Beneficiario Sincronização Início XXXX ",new Date(),new Date());
		long start = System.currentTimeMillis();
		this.iniciarHistoricoSincronizacao();
		
		try{
			
			//Busca a lista das alterações no SIGPES
			MensagemRetornoListDTO mensagemRetorno = beneficiarioWSClient.sincronizar(this.obterDataNovaSincronizacao());
			System.out.println((System.currentTimeMillis() - start) / 60000.0 + " minutos de http" );
			System.out.printf("\n\t[%tT]XXXXX Dados obtidos",new Date());
			if (mensagemRetorno == null) {
				this.finalizarHistoricoSincronizacao(0, 0, EstadoSincronizacao.INTERROMPIDO, "Não foi possível buscar conteúdo no WebService");
				return;
			}
			
			List<Beneficiario> titulares = new LinkedList<Beneficiario>();
			List<Beneficiario> dependentes = new LinkedList<Beneficiario>();
			
			//Transforma os TitularDTO em Beleficiários
			if (mensagemRetorno.getTitularesDTO() != null) {
				quantidadeRegistrosObtidos = mensagemRetorno.getTitularesDTO().size();
				for (TitularDTO pacienteDTO : mensagemRetorno.getTitularesDTO()) {
					titulares.add(gerenciadorBeneficiarioUtil.criarBeneficiarioTitularDTO(pacienteDTO));
				}
			}
			System.out.printf("\n\t[%tT]XXXXX Titulares Convertidos", new Date());
					
			//Transforma os DependenteDTO em Beleficiários
			
			if (mensagemRetorno.getDependentesDTO() != null) {
				quantidadeRegistrosObtidos += mensagemRetorno.getDependentesDTO().size();
				for (DependenteDTO pacienteDTO : mensagemRetorno.getDependentesDTO()) {
					dependentes.add(gerenciadorBeneficiarioUtil.criarBeneficiarioDependenteDTO(pacienteDTO));
				}
			}
			
			System.out.printf("\n\t[%tT]XXXXX Dependentes Convertidos", new Date());

			if (quantidadeRegistrosObtidos == 0) {
				mensagemAtualizacao = "Nenhum atualização foi devolvida";
			} else {
				List<String> mensagensErros = salvarBeneficiarios(titulares, dependentes);
				
				quantidadeAtualizados = dependentes.size() + titulares.size();
				//Atualiza a quantidade de registros atualizados
				quantidadeAtualizados -= mensagensErros.size();//Subtrai a quantidade de atualizados a quantidade de erros

				mensagemAtualizacao = mensagensErros.toString();
			}
			
			this.finalizarHistoricoSincronizacao(quantidadeAtualizados, (quantidadeRegistrosObtidos-quantidadeAtualizados),
					EstadoSincronizacao.FINALIZADO, mensagemAtualizacao);
			
			System.out.printf("\n[%tF %tT]XXXXX Beneficiario Sincronização Finalizado XXXX ", new Date(), new Date());
		} catch (SisaucException e) {
			try {
				this.finalizarHistoricoSincronizacao(quantidadeAtualizados, (quantidadeRegistrosObtidos-quantidadeAtualizados),
						EstadoSincronizacao.INTERROMPIDO, "Erro: "+e.getMessage());
				e.printStackTrace();
			} catch (SisaucException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println((System.currentTimeMillis() - start) / 60000.0 + " minutos pro sisauc pegar " + quantidadeAtualizados +" dados" );
		
	}

	private List<String> salvarBeneficiarios(List<Beneficiario> titulares, 	List<Beneficiario> dependentes) {
		//Salva os titulares
		List<String> mensagensErros;
		this.beneficiarioDAO.salvarBeneficiariosSincronizados(titulares);
		
		System.out.printf("\n\t[%tT]XXXXX Titulares Salvos", new Date());
		
		//Salva os Dependentes
		mensagensErros = this.beneficiarioDAO.salvarBeneficiariosSincronizados(dependentes);
		System.out.printf("\n\t[%tT]XXXXX Dependentes Salvos", new Date());
		
		return mensagensErros;
	}
	
	private Date obterDataNovaSincronizacao() {
		Date data = historicoSincronizacaoBeneficiarioDAO.obterDataUltimaSincronizacao();
		if (data == null) {
			String sData = "01/01/1500";  
			SimpleDateFormat dataInicial = new SimpleDateFormat("dd/MM/yyyy");   
			try {
				data = dataInicial.parse(sData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		} 
//		else {
//			Calendar calendarDate = Calendar.getInstance();
//			calendarDate.setTime(data);
//			calendarDate.add(Calendar.DAY_OF_YEAR, -1); // Subtrai um dia da data de ultima sincronização
//			data = calendarDate.getTime();
//		}
		return data;
	}

	
	
	public void iniciarHistoricoSincronizacao() {
		this.historicoSincronizacao = new HistoricoSincronizacaoBeneficiario();
		this.historicoSincronizacao.setDataHoraInicio(new Date());
		this.historicoSincronizacao.setEstadoSincronizacao(EstadoSincronizacao.INCIADO);
		this.historicoSincronizacao = this.historicoSincronizacaoBeneficiarioDAO.merge(this.historicoSincronizacao);
		
	}
	
	public void finalizarHistoricoSincronizacao(int quantidadeAtualizacoes, int quantidadeErro, 
			EstadoSincronizacao estadoSincronizacao, String resumo) throws SisaucException {
		if (this.historicoSincronizacao != null) {
			this.historicoSincronizacao.setEstadoSincronizacao(estadoSincronizacao);
			this.historicoSincronizacao.setDataHoraFim(new Date());
			this.historicoSincronizacao.setNumeroRegistrosAtualizados(quantidadeAtualizacoes);
			this.historicoSincronizacao.setNumeroRegistrosErro(quantidadeErro);
			this.historicoSincronizacao.setResumo(resumo);
			this.historicoSincronizacao = this.historicoSincronizacaoBeneficiarioDAO.merge(this.historicoSincronizacao);
		} else {
			throw new SisaucException("Não foi possível finalizar um histórico de sincronização, pois o mesmo não foi inciado.");
		}
	}
	
	


}