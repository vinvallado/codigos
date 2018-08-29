package br.ccasj.sisauc.administracao.clientews;

import java.util.Date;

import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.integracao.entity.MensagemRetornoListDTO;
import br.ccasj.sisauc.integracao.entity.PacienteDTO;

public interface BeneficiarioWSClient {
	public PacienteDTO obterPacienteSaram(String saram)  throws SisaucException; 

	public PacienteDTO obterPacienteCPF(String cpf)  throws SisaucException;

	public MensagemRetornoListDTO sincronizar(Date data); 
	 
	
}
