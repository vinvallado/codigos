package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.administracao.cadastro.domain.TipoPessoa;

public class CredenciadoFormatter implements SisaucFormatter<Credenciado> {

	public String getCpfCnpj(Credenciado credenciado) {
		if (credenciado != null) {
			if (TipoPessoa.PESSOA_FISICA == credenciado.getTipoPessoa()) {
				return credenciado.getCpf();
			} else {
				return credenciado.getCnpj();
			}
		} else {
			return "";
		}
	}
	
	@Override
	public String getAutocompleteLabel(Credenciado object) {
		return getCpfCnpj(object);
	}

}
