package br.ccasj.sisauc.administracao.clientews.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.clientews.BeneficiarioWSClient;
import br.ccasj.sisauc.administracao.clientews.WSClient;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.integracao.entity.MensagemRetornoDTO;
import br.ccasj.sisauc.integracao.entity.MensagemRetornoDTO.Status;
import br.ccasj.sisauc.integracao.entity.MensagemRetornoListDTO;
import br.ccasj.sisauc.integracao.entity.PacienteDTO;
import br.ccasj.sisauc.integracao.entity.TitularDTO;
import br.ccasj.sisauc.webservices.service.SigpesSincWS;

@Transactional
@Service(value = "beneficiarioWSClient")
public class BeneficiarioWSClientImpl implements BeneficiarioWSClient {

	@Autowired
	private WSClient wsClient;

	@Override
	public PacienteDTO obterPacienteSaram(String saram) throws SisaucException {

		SigpesSincWS sigpesSincWS = wsClient.criarSigpeshWS();
		MensagemRetornoDTO mensgemRetorno = null;
		try{
			mensgemRetorno = sigpesSincWS.buscarPacienteTitularSARAM(saram);
		}
	 	catch (WebServiceException e) {
			throw new SisaucException("Não foi possível acessar o WebService Sisauc-Sigpes!!");
		}
		PacienteDTO pacienteDTO = processarMensagem(mensgemRetorno);

		return pacienteDTO;
	}


	@Override
	public PacienteDTO obterPacienteCPF(String cpf)  throws SisaucException {
		SigpesSincWS sigpesSincWS = wsClient.criarSigpeshWS();
		MensagemRetornoDTO mensgemRetorno = null;
		try{
			mensgemRetorno = sigpesSincWS.buscarPacienteTitularCPF(cpf);
		}
		catch (WebServiceException e) {
			throw new SisaucException("Não foi possível acessar o WebService Sisauc-Sigpes!!");
		}
		PacienteDTO pacienteDTO = processarMensagem(mensgemRetorno);

		return pacienteDTO;
	}

	private PacienteDTO processarMensagem(MensagemRetornoDTO mensgemRetorno) throws SisaucException {
		PacienteDTO paciente = null;
		if (mensgemRetorno.getStatusRetorno() == Status.SUCESSO) {
			paciente = mensgemRetorno.getPacienteDTO();	
		} else if (mensgemRetorno.getStatusRetorno() == Status.VAZIO) {
			paciente = null;	
		} else if (mensgemRetorno.getStatusRetorno() == Status.ERRO) {
			throw new SisaucException("Acesso negado com o usuário e senha cadastrado no Web Service! "+mensgemRetorno.getStatusRetorno());
		} else {
			System.out.println("Erro:= " + mensgemRetorno.getStatusRetorno());
			throw new SisaucException("Não foi possível acessar o WebService Sisauc-Sigpes!! "+mensgemRetorno.getStatusRetorno());
		}
		return paciente;
	}

	@Override
	public MensagemRetornoListDTO sincronizar(Date data) {
		MensagemRetornoListDTO mensgemRetornoList = null;
		// **** REVERTE ADD > criacao_ws_beneficiarios : 
		List<TitularDTO> resultado = null;
		try{

			SigpesSincWS sigpesWS = wsClient.criarSigpeshWS();
			try{
				mensgemRetornoList = sigpesWS.buscarPacientesModificados(data);
				// **** REVERTE ADD > criacao_ws_beneficiarios : 
				resultado = mensgemRetornoList.getTitularesDTO();
			}
			catch (WebServiceException e) {
				throw new SisaucException("Não foi possível acessar o WebService Sisauc-Sigpes!!");
			}
			PacienteDTO pacienteDTO = processarMensagem(mensgemRetornoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mensgemRetornoList;

	}

}
