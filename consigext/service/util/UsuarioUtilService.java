package br.mil.fab.consigext.service.util;


import java.util.List;

import br.mil.fab.consigext.dto.MargemApiDTO;
import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.Usuario;
import br.mil.fab.consigext.entity.UsuarioExterno;
import br.mil.fab.consigext.enums.Restriction;
import br.mil.fab.consigext.enums.StatusAcesso;
import br.mil.fab.util.sigpes.entity.PesfisComgepResponse;
import br.mil.fab.util.sigpes.entity.UsuarioExternoResponse;

public interface UsuarioUtilService {

		UsuarioExternoResponse getFisicaByCpf(String cpf);

		PesfisComgepResponse getMilitarByMatriculaOrCpf(String ordemOuCpf);
		
		PesfisComgep getPessoaConverter(String nrCpf);
		
		boolean isPessoaFab(String cpf);
		
		UsuarioExterno findUsuarioExternoByNrCpf(String cpf);
		
		Usuario findUsuarioByNrCpf(String cpf);

		StatusAcesso hasAccess(Restriction restriction, Long idConsignatariaChamada);

		StatusAcesso hasAccess(String permission, Restriction restriction, Long idConsignatariaChamada);

		StatusAcesso hasAccess(List<String> permissions, Restriction restriction, Long idConsignatariaChamada);

		StatusAcesso isBlockedOrExcluded();

		MargemApiDTO getMargemApiDTO(String nrOrdem, String cdAnoMes);

		String getMargem40(String nrOrdem, String cdAnoMes);

		String getMargem30MesAtual(String nrOrdem);

		String getMargem70(String nrOrdem, String cdAnoMes);

		String getMargem40MesAtual(String nrOrdem);

		String getMargem70MesAtual(String nrOrdem);

		String getMargem30(String nrOrdem, String cdAnoMes);

}
