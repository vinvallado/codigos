package br.ccasj.sisauc.auditoriaprospectiva.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaBaseDAO;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaDAO;
import br.ccasj.sisauc.auditoriaprospectiva.dao.AuditoriaProspectivaRessarcimentoDAO;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectiva;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaBase;
import br.ccasj.sisauc.auditoriaprospectiva.domain.AuditoriaProspectivaRessarcimento;

@MappedSuperclass
@Transactional
@Repository(value = "auditoriaProspectivaBaseDAO")
public class AuditoriaProspectivaBaseDAOImpl implements AuditoriaProspectivaBaseDAO {

	private static final long serialVersionUID = 2519093441287126461L;

	@Autowired
	private AuditoriaProspectivaDAO auditoriaProspectivaDAO;
	@Autowired
	private AuditoriaProspectivaRessarcimentoDAO auditoriaProspectivaRessarcimentoDAO;
	
	
	@Override
	public List<AuditoriaProspectivaBase> listarAuditoriasPorBeneficiario(Integer idBeneficiario, String textoPesquisa, Class<?>... types) {
		List<AuditoriaProspectivaBase> result = new ArrayList<AuditoriaProspectivaBase>();
		if(types == null || types.length == 0){
			result.addAll(auditoriaProspectivaDAO.listarAuditoriasPorBeneficiario(idBeneficiario, textoPesquisa));
			result.addAll(auditoriaProspectivaRessarcimentoDAO.listarAuditoriasPorBeneficiario(idBeneficiario, textoPesquisa));
		} else {
			List<Class<?>> typesList = Arrays.asList(types);
			if(typesList.contains(AuditoriaProspectivaRessarcimento.class)){
				result.addAll(auditoriaProspectivaRessarcimentoDAO.listarAuditoriasPorBeneficiario(idBeneficiario, textoPesquisa));
			}
			if(typesList.contains(AuditoriaProspectiva.class)){
				result.addAll(auditoriaProspectivaDAO.listarAuditoriasPorBeneficiario(idBeneficiario, textoPesquisa));
			}
		}
		Collections.sort(result, obterComparator());
		return result;
	}

	public Comparator<AuditoriaProspectivaBase> obterComparator(){
		return new Comparator<AuditoriaProspectivaBase>() {
			
			@Override
			public int compare(AuditoriaProspectivaBase o1, AuditoriaProspectivaBase o2) {
				return -1 * o1.getDataFinalAuditoria().compareTo(o2.getDataFinalAuditoria());
			}
		};
	}
	
}
