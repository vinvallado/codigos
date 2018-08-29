package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoCBHPM2012;

public class ProcedimentoFormatter implements SisaucFormatter<ProcedimentoBase> {

	private SubgrupoFormatter subgrupoFormatter = new SubgrupoFormatter();
	private GrupoFormatter grupoFormatter = new GrupoFormatter();
	
	public String getCodigoDescricao(ProcedimentoBase procedimento) {
		if (procedimento == null) {
			return "";
		}
		return procedimento.getCodigo().concat(" - ").concat(procedimento.getDescricao());
	}
	
	public String getDescricaoCompleta(ProcedimentoBase procedimento){
		StringBuilder builder = new StringBuilder(getCodigoDescricao(procedimento));
		if(procedimento instanceof ProcedimentoCBHPM2012){
			builder.append(" ")
				.append(subgrupoFormatter.getCodigoDescricao(((ProcedimentoCBHPM2012)procedimento).getSubGrupo()))
				.append(" ")
				.append(grupoFormatter.getCodigoDescricao(((ProcedimentoCBHPM2012)procedimento).getSubGrupo().getGrupoProcedimento())).toString();
		}
		return builder.toString();
	}
	
	@Override
	public String getAutocompleteLabel(ProcedimentoBase object) {
		return getDescricaoCompleta(object);
	}

}
