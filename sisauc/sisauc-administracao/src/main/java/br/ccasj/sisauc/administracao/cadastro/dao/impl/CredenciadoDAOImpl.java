package br.ccasj.sisauc.administracao.cadastro.dao.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.ccasj.sisauc.administracao.cadastro.dao.CredenciadoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Credenciado;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;

@MappedSuperclass
@Transactional
@Repository(value = "credenciadoDAO")
@NamedQueries({ 
	@NamedQuery(name = CredenciadoDAONamedQueries.OBTER_POR_NOME_CREDENCIADO, query = CredenciadoDAONamedQueries.OBTER_POR_NOME_CREDENCIADO),
	@NamedQuery(name = CredenciadoDAONamedQueries.MUDAR_STATUS_ATIVO_CREDENCIADO, query = CredenciadoDAONamedQueries.MUDAR_STATUS_ATIVO_CREDENCIADO),
	@NamedQuery(name = CredenciadoDAONamedQueries.VERIFICAR_CPF_EXISTENTE, query = CredenciadoDAONamedQueries.VERIFICAR_CPF_EXISTENTE),
	@NamedQuery(name = CredenciadoDAONamedQueries.VERIFICAR_CNPJ_EXISTENTE, query = CredenciadoDAONamedQueries.VERIFICAR_CNPJ_EXISTENTE),
	@NamedQuery(name = CredenciadoDAOImpl.LISTAR_ATIVOS_PARA_COMBO, query = CredenciadoDAOImpl.LISTAR_ATIVOS_PARA_COMBO),
	@NamedQuery(name = CredenciadoDAOImpl.LISTAR_TODOS, query = CredenciadoDAOImpl.LISTAR_TODOS)
})
public class CredenciadoDAOImpl  extends GenericEntityDAOImpl<Credenciado> implements CredenciadoDAO{
	
	private static final long serialVersionUID = -2721697212796419079L;

	public static final String LISTAR_ATIVOS_PARA_COMBO = "select new Credenciado(id, cpf, cnpj, tipoPessoa, nomeFantasia, razaoSocial) from Credenciado where ativo = true order by nomeFantasia";
	public static final String LISTAR_TODOS = "select new Credenciado(id, cpf, cnpj, tipoPessoa, nomeFantasia, razaoSocial, ativo, telefonePrincipal) from Credenciado order by nomeFantasia";
	
	@Override
	public Credenciado obterPorNomeCredenciado(String nomeCredenciado) {
		List<Credenciado> result = entityManager.createNamedQuery(CredenciadoDAONamedQueries.OBTER_POR_NOME_CREDENCIADO, Credenciado.class).setParameter("nomeFantasia", nomeCredenciado).getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public void definirStatusAtivoCredenciado(Integer id, boolean status) {
		entityManager.createNamedQuery(CredenciadoDAONamedQueries.MUDAR_STATUS_ATIVO_CREDENCIADO).setParameter("id", id).setParameter("status", status).executeUpdate();
	}
	
	@Override
	public boolean verificarUnicidadeCPF(Integer id, String cpf) {
		id = id == null ? -1 : id;
		return (boolean) entityManager.createNamedQuery(CredenciadoDAONamedQueries.VERIFICAR_CPF_EXISTENTE).setParameter("cpf",cpf).setParameter("id", id).getSingleResult();
	}
	
	@Override
	public boolean verificarUnicidadeCNPJ(Integer id, String cnpj) {
		id = id == null ? -1 : id;
		return (boolean) entityManager.createNamedQuery(CredenciadoDAONamedQueries.VERIFICAR_CNPJ_EXISTENTE).setParameter("cnpj",cnpj).setParameter("id", id).getSingleResult();
	}
	
	@Override
	public List<Credenciado> listarAtivosParaCombo() {
		return entityManager.createNamedQuery(LISTAR_ATIVOS_PARA_COMBO, Credenciado.class).getResultList();
	}
	
	@Override
	public List<Credenciado> findAll() {
		return entityManager.createNamedQuery(LISTAR_TODOS, Credenciado.class).getResultList();
	}

}