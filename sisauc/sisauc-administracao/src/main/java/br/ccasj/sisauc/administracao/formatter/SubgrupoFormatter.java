package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.administracao.cadastro.domain.SubGrupoProcedimento;

public class SubgrupoFormatter implements SisaucFormatter<SubGrupoProcedimento> {

	public String getCodigoDescricao(SubGrupoProcedimento grupo) {
		if(grupo.getDescricao() == null){
			return "";
		}
		return new StringBuilder().append(grupo.getCodigo()).append(" - ").append(grupo.getDescricao()).toString();
	}

	@Override
	public String getAutocompleteLabel(SubGrupoProcedimento object) {
		return getCodigoDescricao(object);
	}
	
}
