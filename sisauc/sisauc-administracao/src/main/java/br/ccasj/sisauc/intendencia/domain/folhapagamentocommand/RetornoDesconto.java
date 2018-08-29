package br.ccasj.sisauc.intendencia.domain.folhapagamentocommand;

import java.util.List;

import br.ccasj.sisauc.administracao.beneficiario.service.dto.BeneficiarioDTO;
import br.ccasj.sisauc.administracao.beneficiario.service.dto.RetornoBeneficiarios.Beneficiario;

public class RetornoDesconto {

	private Desconto descontoList;

	public Desconto getDescontoList() {
		return descontoList;
	}
	
	public void setDescontoList(Desconto descontoList) {
		this.descontoList = descontoList;
	}
	
	public class Desconto {

		private List<DescontoDTO> desconto;

		public Desconto() {
		}

		public List<DescontoDTO> getDesconto() {
			return this.desconto;
		}

		public void setDesconto(List<DescontoDTO> desconto) {
			this.desconto = desconto;
		}

	}


}
