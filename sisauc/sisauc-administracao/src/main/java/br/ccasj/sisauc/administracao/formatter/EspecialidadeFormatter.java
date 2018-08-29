package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;

public class EspecialidadeFormatter implements SisaucFormatter<Especialidade> {

	public String getSiglaNome(Especialidade especialidade){
		if(especialidade.getNome() == null) {
			return "";
		} else {
			return new StringBuilder().append(especialidade.getSigla())
					.append(" - ").append(especialidade.getNome()).toString();
		}
	}
	
	@Override
	public String getAutocompleteLabel(Especialidade object) {
		return getSiglaNome(object);
	}
	
}
