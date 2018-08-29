package br.ccasj.sisauc.administracao.beneficiario.dao.impl;

import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.cadastro.domain.Convenio;
import br.ccasj.sisauc.administracao.cadastro.domain.PostoGraduacao;
import br.ccasj.sisauc.framework.dao.impl.GenericEntityDAOImpl;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@MappedSuperclass
@Transactional
@Repository(value = "beneficiarioDAO")
@NamedQueries({
        @NamedQuery(name = BeneficiarioDAOImpl.LISTAR_TODOS, query = BeneficiarioDAOImpl.LISTAR_TODOS),
        @NamedQuery(name = BeneficiarioDAOImpl.LISTAR_TODOS_POR_NOME, query = BeneficiarioDAOImpl.LISTAR_TODOS_POR_NOME),
        @NamedQuery(name = BeneficiarioDAOImpl.LISTAR_DEPENDENTES_BENEFICIARIO, query = BeneficiarioDAOImpl.LISTAR_DEPENDENTES_BENEFICIARIO),
        @NamedQuery(name = BeneficiarioDAOImpl.LISTAR_TODOS_POSTOS, query = BeneficiarioDAOImpl.LISTAR_TODOS_POSTOS),
        @NamedQuery(name = BeneficiarioDAOImpl.BUSCAR_POSTO_POR_SIGLA, query = BeneficiarioDAOImpl.BUSCAR_POSTO_POR_SIGLA),
        @NamedQuery(name = BeneficiarioDAOImpl.OBTER_POR_CPF, query = BeneficiarioDAOImpl.OBTER_POR_CPF),
        @NamedQuery(name = BeneficiarioDAOImpl.OBTER_POR_SARAM, query = BeneficiarioDAOImpl.OBTER_POR_SARAM),
        @NamedQuery(name = BeneficiarioDAOImpl.OBTER_POR_NOME, query = BeneficiarioDAOImpl.OBTER_POR_NOME),
        @NamedQuery(name = BeneficiarioDAOImpl.LISTAR_TODOS_CONVENIOS, query = BeneficiarioDAOImpl.LISTAR_TODOS_CONVENIOS),
        @NamedQuery(name = BeneficiarioDAOImpl.BUSCAR_CONVENIO_POR_SIGLA, query = BeneficiarioDAOImpl.BUSCAR_CONVENIO_POR_SIGLA),
        @NamedQuery(name = BeneficiarioDAOImpl.LISTAR_TODOS_AMHC_ATIVOS_POR_NOME, query = BeneficiarioDAOImpl.LISTAR_TODOS_AMHC_ATIVOS_POR_NOME)
})
public class BeneficiarioDAOImpl extends GenericEntityDAOImpl<Beneficiario> implements BeneficiarioDAO {

    private static final long serialVersionUID = 3575457855433491223L;

    public static final String LISTAR_TODOS = "from Beneficiario";
    public static final String LISTAR_TODOS_POR_NOME = "from Beneficiario as b order by b.nome";
    public static final String LISTAR_DEPENDENTES_BENEFICIARIO = "from Beneficiario where beneficiarioTitular.id=:idBeneficiarioTitular";
    public static final String LISTAR_TODOS_POSTOS = "from PostoGraduacao order by sigla";
    public static final String BUSCAR_POSTO_POR_SIGLA = "from PostoGraduacao where sigla=:sigla";
    public static final String BUSCAR_CONVENIO_POR_SIGLA = "from Convenio where sigla=:sigla";
    public static final String LISTAR_TODOS_CONVENIOS = "from Convenio order by sigla";
    public static final String OBTER_POR_CPF = "from Beneficiario where cpf=:numerocpf and ativo=:ativo";
    public static final String OBTER_POR_SARAM = "from Beneficiario where saram=:saram and titular=:titular";
    public static final String OBTER_POR_NOME = "from Beneficiario where nome=:nome";
    public static final String LISTAR_TODOS_AMHC_ATIVOS_POR_NOME = "select new Beneficiario(beneficiario.id, beneficiario.saram, beneficiario.nome, beneficiario.tipoContribuicao, "
            + "postoGraduacao.sigla, postoGraduacao.descricao, titular.nome, titular.saram, titular.tipoContribuicao, titular.cpf, "
            + "postoGraduacaoTitular.descricao, beneficiario.sexo, beneficiario.cpf, "
            + "beneficiario.dataNascimento, om.sigla, omTitular.sigla) from Beneficiario beneficiario "
            + "LEFT JOIN beneficiario.postoGraduacao as postoGraduacao "
            + "LEFT JOIN beneficiario.beneficiarioTitular as titular "
            + "LEFT JOIN titular.postoGraduacao as postoGraduacaoTitular "
            + "LEFT JOIN titular.organizacaoMilitar as omTitular "
            + "LEFT JOIN beneficiario.organizacaoMilitar as om "
            + "LEFT JOIN beneficiario.convenio as convenio "
            + "WHERE beneficiario.ativo = true AND UPPER(convenio.sigla) = 'AMHC' order by beneficiario.nome";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Beneficiario merge(Beneficiario beneficiario) {
        return entityManager.merge(beneficiario);
    }

    @Override
    public Beneficiario obterPorCPFAtivo(String cpf) {
        final List<Beneficiario> resultList = entityManager.createNamedQuery(OBTER_POR_CPF, Beneficiario.class).setParameter("numerocpf", cpf).setParameter("ativo", true).getResultList();
        if (resultList.size() > 0)
        	return resultList.get(0);
        else 
        	return null;
    }


    @Override
    public List<Beneficiario> obterPorNome(String nome) {
        return entityManager.createNamedQuery(OBTER_POR_NOME, Beneficiario.class).setParameter("nome", nome).getResultList();
    }

    @Override
    public Beneficiario obterPorSARAM(String saram, boolean titular) {
        List<Beneficiario> resultado = entityManager.createNamedQuery(OBTER_POR_SARAM, Beneficiario.class).setParameter("saram", saram).setParameter("titular", titular).getResultList();
        return DataAccessUtils.singleResult(resultado);
    }

    @Override
    public List<Beneficiario> findAll() {
        return entityManager.createNamedQuery(LISTAR_TODOS, Beneficiario.class).getResultList();
    }

    @Override
    public List<Beneficiario> obterTodosOrdenadosPorNome() {
        return entityManager.createNamedQuery(LISTAR_TODOS_POR_NOME, Beneficiario.class).getResultList();
    }

    @Override
    public List<Beneficiario> obterTodosAMHCAtivosPorNome() {
        entityManager.createQuery("UPDATE Beneficiario b "
                + "SET b.ativo = true WHERE b.id in (select bf.id from Beneficiario bf "
                + "LEFT JOIN bf.organizacaoMilitar as om "
                + "LEFT JOIN bf.postoGraduacao as posto "
                + "WHERE bf.titular = true and om.id = 432 and posto.id in (1,2,3,4))").executeUpdate();

        return entityManager.createNamedQuery(LISTAR_TODOS_AMHC_ATIVOS_POR_NOME, Beneficiario.class).getResultList();
    }

    @Override
    public List<Beneficiario> obterDependentes(Beneficiario beneficiario) {
        return entityManager.createNamedQuery(LISTAR_DEPENDENTES_BENEFICIARIO, Beneficiario.class).setParameter("idBeneficiarioTitular", beneficiario.getId()).getResultList();
    }

    @Override
    public PostoGraduacao obterPosto(String siglaPosto) {
        return entityManager.createNamedQuery(BUSCAR_POSTO_POR_SIGLA, PostoGraduacao.class).setParameter("sigla", siglaPosto).getSingleResult();
    }

    @Override
    public int salvarBeneficiariosImportados(List<Beneficiario> beneficiarios) {
        Beneficiario beneficiarioTitular = null;

        //Adiciona inicialmente somente o Titular
        for (Beneficiario beneficiario : beneficiarios) {
            if (beneficiario.isTitular()) {
                beneficiarioTitular = persistirBeneficiario(beneficiario, null);
            }
        }

        //Depois adiciona os dependentes
        for (Beneficiario beneficiario : beneficiarios) {
            if (beneficiario.isTitular()) {
                continue;
            }
            persistirBeneficiario(beneficiario, beneficiarioTitular);
        }
        entityManager.flush();
        return 0;
    }

    @Override
    public List<String> salvarBeneficiariosSincronizados(List<Beneficiario> beneficiarios) {
        List<String> errosSalvar = new LinkedList<String>();
        for (Beneficiario beneficiario : beneficiarios) {
            if (beneficiario.isTitular()) {
                persistirBeneficiario(beneficiario, null);
            } else {
                if (beneficiario.getSaramTitular() != null) {
                    Beneficiario beneficiarioTitular = obterPorSARAM(beneficiario.getSaramTitular(), true);
                    if (beneficiarioTitular != null) {
                        persistirBeneficiario(beneficiario, beneficiarioTitular);
                    } else {
                        String mensagemErro = "Não foi possível cadastrar o beneficiario: " + beneficiario.getNome();
                        mensagemErro += ". " + "Não foi encontrado Titular com o SARAM :" + beneficiario.getSaramTitular();
                        errosSalvar.add(mensagemErro);
                        //Erro do titular null
                    }
                } else {
                    errosSalvar.add("O Dependente não possui o saram do titular. " + beneficiario.getNome());
                }


            }
        }
        return errosSalvar;
    }

    private Beneficiario persistirBeneficiario(Beneficiario beneficiario, Beneficiario titular) {

        Beneficiario beneficiarioPersistir = findById(beneficiario.getId());

        //Atualiza Beneficiario exitente ou adiciona um novo quando não existir
        if (beneficiarioPersistir == null) {
            beneficiarioPersistir = new Beneficiario();
            beneficiarioPersistir.setId(beneficiario.getId());
            beneficiarioPersistir.setTitular(beneficiario.isTitular());
        }

        beneficiarioPersistir.setCpf(beneficiario.getCpf());
        beneficiarioPersistir.setSaram(beneficiario.getSaram());
        beneficiarioPersistir.setNome(beneficiario.getNome());
        beneficiarioPersistir.setAtivo(beneficiario.isAtivo());
        beneficiarioPersistir.setSexo(beneficiario.getSexo());
        beneficiarioPersistir.setDataNascimento(beneficiario.getDataNascimento());
        beneficiarioPersistir.setPostoGraduacao(beneficiario.getPostoGraduacao());
        beneficiarioPersistir.setOrganizacaoMilitar(beneficiario.getOrganizacaoMilitar());
        beneficiarioPersistir.setBeneficiarioTitular(beneficiario.getBeneficiarioTitular() == null ? titular : beneficiario.getBeneficiarioTitular());
        beneficiarioPersistir.setConvenio(beneficiario.getConvenio());
        beneficiarioPersistir.setDataAtualizacao(beneficiario.getDataAtualizacao());
        beneficiarioPersistir.setTipoContribuicao(beneficiario.getTipoContribuicao());


        return entityManager.merge(beneficiarioPersistir);
    }


    @Override
    public Convenio obterConvenio(String siglaConvenio) {
        TypedQuery<Convenio> query = entityManager.createNamedQuery(BUSCAR_CONVENIO_POR_SIGLA, Convenio.class);
        query.setParameter("sigla", siglaConvenio);
        Convenio resultado = null;
        try {
            resultado = query.getSingleResult();
        } catch (NoResultException noResult) {
            //Nenhum resultado. Retorna null
        }
        return resultado;
    }

    @Override
    public List<Beneficiario> listarParaLazyModel(int first, int pageSize, Map<String, Object> filters) {
        StringBuilder builder = new StringBuilder("from Beneficiario");
        builder.append(montarQueryPorFiltros(filters));
        return entityManager.createQuery(builder.append(" order by nome").toString(), Beneficiario.class).setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    @Override
    public int obterQuantidadeResultadosLazyModel(Map<String, Object> filters) {
        StringBuilder builder = new StringBuilder("select count(*) from Beneficiario");
        builder.append(montarQueryPorFiltros(filters));
        return (int) (long) entityManager.createQuery(builder.toString(), Long.class).getSingleResult();
    }

    private Object montarQueryPorFiltros(Map<String, Object> filters) {
        StringBuilder builder = new StringBuilder(" WHERE 1 = 1 ");
        builder.append(criarLike("nome", filters));
        builder.append(criarLike("cpf", filters));
        builder.append(criarLike("saram", filters));
        if (filters.get("ativo") != null) {
            boolean value = "Sim".equals((String) filters.get("ativo"));
            builder.append(" AND ativo = " + value);
        }
        if (filters.get("titular") != null) {
            boolean value = "Sim".equals((String) filters.get("titular"));
            builder.append(" AND titular = " + value);
        }
        if (filters.get("sigla") != null) {
            if ("AMH".equals((String) filters.get("sigla"))) {
                builder.append(" AND convenio.sigla = 'AMH' ");
            } else if ("AMHC".equals((String) filters.get("sigla"))) {
                builder.append(" AND convenio.sigla = 'AMHC' ");
            }
        }
        return builder.toString();
    }

    public String criarLike(String coluna, Map<String, Object> filters) {
        //XXX Preciso ainda ou posso chamar da classe pai???????????
        //NÃO ME ORGULHO DE TER FEITO ISSO zaccaromvgcamz
        if (filters.get(coluna) != null && coluna.equals("nome")) {
            String value = (String) filters.get(coluna);
            return "AND UPPER(TRANSLATE (" + coluna + ", 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü','ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu')) "
                    + "LIKE UPPER((TRANSLATE('%" + value + "%', 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü', 'ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu'))) ";
        }
        if (filters.get(coluna) != null && coluna.equals("saram")) {
            String value = (String) filters.get(coluna);
            return "AND (UPPER(TRANSLATE (" + coluna + ", 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü','ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu')) "
                    + "LIKE UPPER((TRANSLATE('%" + value + "%', 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü', 'ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu'))) "
                    + "OR id_beneficiario_titular = " + Integer.valueOf(value) + "00)";
        }
        if (filters.get(coluna) != null && coluna.equals("cpf")) {
            String value = (String) filters.get(coluna);
            value = value.replace(".", "");
            value = value.replace("-", "");
            value = value.replace("/", "");
            value = value.replace(";", "");
            value = value.replace(":", "");
            return "AND UPPER(TRANSLATE (" + coluna + ", 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü','ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu')) "
                    + "LIKE UPPER((TRANSLATE('%" + value + "%', 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü', 'ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu'))) ";
        }
        return "";
    }

    @Override
    public List<PostoGraduacao> obterTodosPostos() {
        return entityManager.createNamedQuery(LISTAR_TODOS_POSTOS, PostoGraduacao.class).getResultList();
    }

    @Override
    public List<Convenio> obterTodosConvenios() {
        return entityManager.createNamedQuery(LISTAR_TODOS_CONVENIOS, Convenio.class).getResultList();
    }
}
