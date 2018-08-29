package br.ccasj.sisauc.administracao.beneficiario.service;


/**
 * A Sincronização com o SIGPES foi substituída pela consulta online
 * ao SIGPES via serviço no DSS ServiceSisauc.
 * 
 * @author amersonpcb
 *
 */
@Deprecated
public interface SincronizadorBeneficiarioService {
	public void sincronizar();
	public void verificaSincronizacaoAgendada();
}
