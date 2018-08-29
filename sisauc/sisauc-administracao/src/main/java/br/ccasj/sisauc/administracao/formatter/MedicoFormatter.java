package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.administracao.cadastro.domain.Medico;

public class MedicoFormatter implements SisaucFormatter<Medico> {

	
	public String getDescricaoMedico(Medico medico){
		if(medico == null){
			return "";
		}
		StringBuilder builder = new StringBuilder(medico.getNome()).append(" - ")
				.append(medico.isMilitar() ? "Militar":"Civil").append(" - ")
				.append(getLabelTipoProfissionalSaude(medico));
		if(medico.isProfissionalSaude()){
			builder.append(" - ").append(medico.getNumeroConselhoRegional());
		}
		return builder.toString();
	}
	
	public String getLabelTipoProfissionalSaude(Medico medico) {
		return medico.getTipoProfissionalSaude() == null ? "Não Informado" : medico.getTipoProfissionalSaude()
				.getLabel();
	}

	@Override
	public String getAutocompleteLabel(Medico medico) {
		return getDescricaoMedico(medico);
	}

}
