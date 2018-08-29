package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.framework.domain.Cidade;

public class CidadeFormatter implements SisaucFormatter<Cidade>{

	public String getNomeEstado(Cidade cidade){
		return new StringBuilder().append(cidade.getNome()).append("/").append(cidade.getEstado().getSigla()).toString();
	}
	
	@Override
	public String getAutocompleteLabel(Cidade object) {
		return getNomeEstado(object);
	}
	
}
