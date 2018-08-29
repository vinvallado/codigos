package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.administracao.cadastro.domain.GrupoProcedimento;

public class GrupoFormatter implements SisaucFormatter<GrupoProcedimento> {

	public String getCodigoDescricao(GrupoProcedimento grupo) {
		if(grupo.getDescricao() == null){
			return "";
		}
		return new StringBuilder().append(grupo.getCodigo()).append(" - ").append(grupo.getDescricao()).toString();
	}
	
	@Override
	public String getAutocompleteLabel(GrupoProcedimento object) {
		return getCodigoDescricao(object);
	}

}
