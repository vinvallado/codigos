package br.ccasj.sisauc.intendencia.domain.folhapagamentocommand;

import br.ccasj.sisauc.intendencia.domain.RelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.service.EnvioDescontosSisconsigService;

public class RessarcimentoFolhaCommand extends LancamentoFolhaCommand {

	private RelatorioFolhaBeneficiario folhaBeneficiario;
	
	private EnvioDescontosSisconsigService service;
	
	public RessarcimentoFolhaCommand(RelatorioFolhaBeneficiario folhaBeneficiario, EnvioDescontosSisconsigService service) {
		this.folhaBeneficiario = folhaBeneficiario;
		this.service = service;
	}

	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
