package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import br.ccasj.sisauc.administracao.cadastro.dao.ProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.*;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Transactional
@Repository(value = "procedimentoDAO")
@NamedNativeQueries({
	@NamedNativeQuery(name = ProcedimentoDAOImpl.LISTAR_TODOS, query = ProcedimentoDAOImpl.LISTAR_TODOS)
})
public class ProcedimentoDAOImpl extends GenericEntityDAOImpl<ProcedimentoBase> implements ProcedimentoDAO {
	
	private static final long serialVersionUID = -7829336877752896116L;

	//TODO estudar como fazer isso com @SqlResultSetMapping
	public static final String LISTAR_TODOS = "select "
			+ "pb.id as id_procedimento, " //0
			+ "pb.codigo as codigo_procedimento, "//1
			+ "pb.descricao as descricao_procedimento, "//2
			+ "pb.tabela as tabela, "//3
			//DADOS CBHPM2012
			+ "p2012.id_subgrupo as id_subgrupo, "//4
			+ "subgrupo.codigo as codigo_subgrupo, "//5
			+ "subgrupo.descricao as descricao_subgrupo, "//6
			+ "subgrupo.id_grupo as id_grupo, "//7
			+ "grupo.codigo as codigo_grupo, "//8
			+ "grupo.descricao as descricao_grupo, "//9
			//DADOS TRS
			+ "ptrs.valor_enfermaria as valor_enfermaria, "//10
			+ "ptrs.valor_apartamento as valor_apartamento, "//11
			+ "ptrs.internacao as internacao, " //12
			+ "ptrs.odontologico as odontologico, "//13
			//DADOS CISSFA
			+ "pcissfa.id_subgrupo as id_subgrupoCissfa, "//14
			+ "subgrupoCissfa.codigo as codigo_subgrupoCissfa, "//15
			+ "subgrupoCissfa.descricao as descricao_subgrupoCissfa, "//16
			+ "subgrupoCissfa.id_grupo as id_grupoCissfa, "//17
			+ "grupoCissfa.codigo as codigo_grupoCissfa, "//18
			+ "grupoCissfa.descricao as descricao_grupoCissfa "//19

			
			+ "from sch_sisauc.procedimento_base pb "
			// JOINS CPHPM2012
			+ "left outer join sch_sisauc.procedimento_cbhpm_2012 p2012 on pb.id=p2012.id "
			+ "left outer join sch_sisauc.subgrupo subgrupo on p2012.id_subgrupo=subgrupo.id "
			+ "left outer join sch_sisauc.grupo grupo on subgrupo.id_grupo=grupo.id "
			// TRS CPHPM2012
			+ "left outer join sch_sisauc.procedimento_trs ptrs on pb.id=ptrs.id "
			// JOINS CISSFA
			+ "left outer join sch_sisauc.procedimento_cissfa pcissfa on pb.id=pcissfa.id "
			+ "left outer join sch_sisauc.subgrupo subgrupoCissfa on pcissfa.id_subgrupo=subgrupoCissfa.id "
			+ "left outer join sch_sisauc.grupo grupoCissfa on subgrupoCissfa.id_grupo=grupoCissfa.id "
			+ "";
		
	public static final String LISTAR_POR_SUBGRUPO = "select new ProcedimentoCBHPM2012(p.id, p.codigo, p.descricao, subGrupo.id, subGrupo.codigo, subGrupo.descricao, grupo.id, grupo.codigo, grupo.descricao) "
			+ "from ProcedimentoBase p left join p.subGrupo as subGrupo left join subGrupo.grupoProcedimento as grupo where subGrupo.id = :idSubgrupo order by p.codigo";

	public static final String LISTAR_POR_GRUPO = "select new ProcedimentoCBHPM2012(p.id, p.codigo, p.descricao, subGrupo.id, subGrupo.codigo, subGrupo.descricao, grupo.id, grupo.codigo, grupo.descricao) "
			+ "from ProcedimentoBase p left join p.subGrupo as subGrupo left join subGrupo.grupoProcedimento as grupo where grupo.id = :idGrupo order by p.codigo";

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcedimentoBase> findAll() {
		//TODO correr atrás de uma solução melhor em tempo oportuno!
		List<ProcedimentoBase> procedimentos = new ArrayList<>();
		List<Object[]> rows = entityManager.createNativeQuery(LISTAR_TODOS).getResultList();
		for (Object[] row : rows) {
			if(row[3].equals(Tabela.CBHPM2012.name())){
				procedimentos.add(criarProcedimentoCBHPM2012(row));
			}
			if(row[3].equals(Tabela.TRS.name())){
				procedimentos.add(criarProcedimentoTRS(row));
			}
			if(row[3].equals(Tabela.CISSFA.name())){
				procedimentos.add(criarProcedimentoCISSFA(row));
			}
		}
		return procedimentos;
	}

	private ProcedimentoCBHPM2012 criarProcedimentoCBHPM2012(Object[] row) {
		return new ProcedimentoCBHPM2012((Integer)row[0], (String)row[1], (String)row[2],  (Integer)row[4],  (String)row[5],  (String)row[6],  (Integer)row[7],  (String)row[8],  (String)row[9]);
	}

	private ProcedimentoTRS criarProcedimentoTRS(Object[] row) {
		return new ProcedimentoTRS((Integer)row[0], (String)row[1], (String)row[2], (Double)row[10], (Double)row[11], (boolean)row[12], (boolean)row[13]);
	}
	
	private ProcedimentoCISSFA criarProcedimentoCISSFA(Object[] row){
		return new ProcedimentoCISSFA((Integer)row[0], (String)row[1], (String)row[2],  (Integer)row[14],  (String)row[15],  (String)row[16],  (Integer)row[17],  (String)row[18],  (String)row[19]);
		
	}

	
	//ATENÇÃO - FOI NECESSÁRIO USAR CRITERIA NESTE TRECHO DE CÓDIGO POIS AMBAS CISSFA E CBHPM POSSUEM GRUPO E SUBGRUPO
	@Override
	public List<? extends ProcedimentoBase> listarProcedimentosPorGrupo(Integer id, Class<? extends ProcedimentoBase> clazz) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<? extends ProcedimentoBase> criteria = builder.createQuery(clazz);
		Root<? extends ProcedimentoBase> root = criteria.from(clazz);
		Join<? extends ProcedimentoBase, SubGrupoProcedimento> subgrupo = root.join("subGrupo");
		criteria.where(builder.equal(subgrupo.get("grupoProcedimento"), new GrupoProcedimento(id)));

		System.out.println(ToStringBuilder.reflectionToString(criteria.getParameters()));

		return entityManager.createQuery(criteria).getResultList();		
	}

	@Override
	public List<? extends ProcedimentoBase> listarProcedimentosPorSubgrupo(Integer id, Class<? extends ProcedimentoBase> clazz) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<? extends ProcedimentoBase> criteria = builder.createQuery(clazz);
		Root<? extends ProcedimentoBase> root = criteria.from(clazz);
		criteria.where(builder.equal(root.get("subGrupo"), new SubGrupoProcedimento(id)));

		System.out.println(ToStringBuilder.reflectionToString(criteria.getParameters()));

		return entityManager.createQuery(criteria).getResultList();
	}

	

}
