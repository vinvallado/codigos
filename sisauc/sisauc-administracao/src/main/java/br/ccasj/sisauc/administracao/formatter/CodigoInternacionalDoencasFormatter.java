package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.administracao.cadastro.domain.CodigoInternacionalDoenca;


public class CodigoInternacionalDoencasFormatter implements SisaucFormatter<CodigoInternacionalDoenca> {

	public static String getCodigoDescricao(CodigoInternacionalDoenca codigo){
		if(codigo == null){
			return null;
		}
		return new StringBuilder(codigo.getCodigo()).append(" - ").append(codigo.getDescricao()).toString();
	}
	
	@Override
	public String getAutocompleteLabel(CodigoInternacionalDoenca object) {
		return getCodigoDescricao(object);
	}
	
}
