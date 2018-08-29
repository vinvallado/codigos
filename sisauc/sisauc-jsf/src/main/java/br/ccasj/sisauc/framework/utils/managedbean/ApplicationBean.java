package br.ccasj.sisauc.framework.utils.managedbean;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static org.hamcrest.Matchers.equalTo;

import java.io.Serializable;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.BeneficiarioService;
import br.ccasj.sisauc.administracao.cadastro.dao.CidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.CodigoInternacionalDoencaDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.ConfiguracaoEditalCredenciadoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.EspecialidadeDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.GrupoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.MedicoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.ProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.dao.SubGrupoProcedimentoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.CodigoInternacionalDoenca;
import br.ccasj.sisauc.administracao.cadastro.domain.ConfiguracaoEditalCredenciadoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Especialidade;
import br.ccasj.sisauc.administracao.cadastro.domain.GrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Medico;
import br.ccasj.sisauc.administracao.cadastro.domain.ProcedimentoBase;
import br.ccasj.sisauc.administracao.cadastro.domain.SubGrupoProcedimento;
import br.ccasj.sisauc.administracao.cadastro.domain.Tabela;
import br.ccasj.sisauc.administracao.formatter.BeneficiarioFormatter;
import br.ccasj.sisauc.administracao.formatter.CidadeFormatter;
import br.ccasj.sisauc.administracao.formatter.CodigoInternacionalDoencasFormatter;
import br.ccasj.sisauc.administracao.formatter.ConfiguracaoEditalCredenciadoProcedimentoFormatter;
import br.ccasj.sisauc.administracao.formatter.GrupoFormatter;
import br.ccasj.sisauc.administracao.formatter.MedicoFormatter;
import br.ccasj.sisauc.administracao.formatter.ProcedimentoFormatter;
import br.ccasj.sisauc.administracao.formatter.SisaucFormatter;
import br.ccasj.sisauc.administracao.formatter.SubgrupoFormatter;
import br.ccasj.sisauc.framework.domain.Cidade;

@ApplicationScoped
@Service(value = "applicationBean")
public class ApplicationBean implements Serializable {

    // TODO criar um bean chamado autocompleteBean com escopo de aplicação com
    // os dados de autocomplete


    private static final long serialVersionUID = 7150946335555493670L;

    private static final int MAX_RESULTS = 30;

    @Autowired
    private CidadeDAO cidadeDAO;
    //@Autowired
    //private BeneficiarioDAO beneficiarioDAO;
    @Autowired
    private CodigoInternacionalDoencaDAO codigoInternacionalDoencaDAO;
    @Autowired
    private MedicoDAO medicoDAO;
    @Autowired
    private ProcedimentoDAO procedimentoDAO;
    @Autowired
    private EspecialidadeDAO especialidadeDAO;
    @Autowired
    private GrupoProcedimentoDAO grupoDAO;
    @Autowired
    private SubGrupoProcedimentoDAO subgruposDAO;
    @Autowired
    private ConfiguracaoEditalCredenciadoProcedimentoDAO configuracaoEditalCredenciadoProcedimentoDAO;
    @Autowired
    private ConfiguracaoEditalCredenciadoProcedimentoFormatter configuracaoFormatter;

    @Autowired
    private BeneficiarioService beneficiarioService;

    private List<CodigoInternacionalDoenca> codigos;
    private List<Cidade> cidades;
    private List<Medico> medicos;
    private List<ProcedimentoBase> procedimentos;
    private List<Especialidade> especialidades;
    private List<GrupoProcedimento> grupos;
    private List<SubGrupoProcedimento> subgrupos;
    private List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes;

    public <T> List<T> procurar(final SisaucFormatter<T> formatter, String value, final List<T> source, final int max) {
        final List<T> result = new ArrayList<T>();
        value = removerAcentos(value);

        for (final T t : source) {
            final String autocomplete = removerAcentos(formatter.getAutocompleteLabel(t));
            if (autocomplete.toLowerCase().contains(value.toLowerCase())) {
                result.add(t);
                if (result.size() >= max) {
                    break;
                }
            }
        }

        return result;
    }

    public String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }


    public <T> List<T> procurar(SisaucFormatter<T> formatter, String value, List<T> source) {
        return procurar(formatter, value, source, MAX_RESULTS);
    }

    public List<ProcedimentoBase> procurarProcedimentos(String value, Tabela... tabelas) {
        List<ProcedimentoBase> listaPorTabela = new ArrayList<ProcedimentoBase>();
        for (Tabela tabela : tabelas) {
            listaPorTabela.addAll(select(getProcedimentos(), having(on(ProcedimentoBase.class).getTabela(), equalTo(tabela))));
        }
        return procurar(new ProcedimentoFormatter(), value, listaPorTabela);
    }

    public List<ProcedimentoBase> procurarProcedimentosParaAtendente(String value, Tabela... tabelas) {
        List<ProcedimentoBase> listaPorTabela = new ArrayList<ProcedimentoBase>();
        listaPorTabela = procurarProcedimentos(value, tabelas);
        for (ProcedimentoBase p : listaPorTabela) {
            if (p.getId() == 9871) {
                listaPorTabela.remove(p);
                break;
            }
        }
        return procurar(new ProcedimentoFormatter(), value, listaPorTabela);
    }


    public List<ProcedimentoBase> procurarProcedimentos(String value) {
        return procurar(new ProcedimentoFormatter(), value, getProcedimentos());
    }

    public List<Cidade> procurarCidade(String nome) {
        return procurar(new CidadeFormatter(), nome, getCidades(), 15);
    }

    public List<CodigoInternacionalDoenca> procurarCodigo(String nome) {
        return procurar(new CodigoInternacionalDoencasFormatter(), nome, getCodigos());
    }

    public List<Beneficiario> procurarBeneficiario(String param) {
        return procurar(new BeneficiarioFormatter(), param, getBeneficiarios(param, 100), 100);
    }

    private List<Beneficiario> getBeneficiarios(final String param, int maxResult) {
        return beneficiarioService.findByTextParam(param, maxResult);
    }

    public List<Medico> procurarMedicos(String nome) {
        return procurar(new MedicoFormatter(), nome, getMedicos());
    }

    public List<GrupoProcedimento> procurarGrupos(String value) {
        return procurar(new GrupoFormatter(), value, getGrupos());
    }

    public List<GrupoProcedimento> procurarGrupos(String value, Tabela tabela) {
        List<GrupoProcedimento> listaPorTabela = select(getGrupos(), having(on(GrupoProcedimento.class).getTabela(), equalTo(tabela)));
        return procurar(new GrupoFormatter(), value, listaPorTabela);
    }

    public List<SubGrupoProcedimento> procurarSubgrupos(String value) {
        return procurar(new SubgrupoFormatter(), value, getSubgrupos());
    }

    public List<SubGrupoProcedimento> procurarSubgrupos(String value, Tabela tabela) {
        List<SubGrupoProcedimento> listaPorTabela = select(getSubgrupos(), having(on(SubGrupoProcedimento.class).getGrupoProcedimento().getTabela(), equalTo(tabela)));
        return procurar(new SubgrupoFormatter(), value, listaPorTabela);
    }

    public List<ConfiguracaoEditalCredenciadoProcedimento> procurarConfiguracoes(String value) {
        return procurar(configuracaoFormatter, value, getConfiguracoes(), 50);
    }

    public void initCidades() {
        cidades = cidadeDAO.findAll();
    }

    public void initMedico() {
        medicos = medicoDAO.listarAtivosParaCombo();
    }

    public void initCID() {
        codigos = codigoInternacionalDoencaDAO.findAll();
    }

    public void initProcedimentos() {
        procedimentos = procedimentoDAO.findAll();
    }

    private void initGrupos() {
        grupos = grupoDAO.findAll();
    }

    private void initSubGrupos() {
        subgrupos = subgruposDAO.findAll();
    }

    public void initConfiguracoes() {
        configuracoes = configuracaoEditalCredenciadoProcedimentoDAO.listarConfiguracoesAtivasEmEditaisVigentes();
    }

    public List<Especialidade> getEspecialidades() {
        if (especialidades == null || especialidades.isEmpty()) {
            especialidades = especialidadeDAO.listarEspecialidadesAtivas();
        }
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    public List<CodigoInternacionalDoenca> getCodigos() {
        if (codigos == null || codigos.isEmpty()) {
            initCID();
        }
        return codigos;
    }

    public void setCodigos(List<CodigoInternacionalDoenca> codigos) {
        this.codigos = codigos;
    }

    public List<Cidade> getCidades() {
        if (cidades == null || cidades.isEmpty()) {
            initCidades();
        }
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public List<Medico> getMedicos() {
        if (medicos == null || medicos.isEmpty()) {
            initMedico();
        }
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    public List<ProcedimentoBase> getProcedimentos() {
        if (procedimentos == null || procedimentos.isEmpty()) {
            initProcedimentos();
        }
        return procedimentos;
    }

    public void setProcedimentos(List<ProcedimentoBase> procedimentos) {
        this.procedimentos = procedimentos;
    }

    public List<GrupoProcedimento> getGrupos() {
        if (grupos == null || grupos.isEmpty()) {
            initGrupos();
        }
        return grupos;
    }

    public void setGrupos(List<GrupoProcedimento> grupos) {
        this.grupos = grupos;
    }

    public List<SubGrupoProcedimento> getSubgrupos() {
        if (subgrupos == null || subgrupos.isEmpty()) {
            initSubGrupos();
        }
        return subgrupos;
    }

    public void setSubgrupos(List<SubGrupoProcedimento> subgrupos) {
        this.subgrupos = subgrupos;
    }

    public List<ConfiguracaoEditalCredenciadoProcedimento> getConfiguracoes() {
        if (configuracoes == null || configuracoes.isEmpty()) {
            initConfiguracoes();
        }
        return configuracoes;
    }

    public void setConfiguracoes(List<ConfiguracaoEditalCredenciadoProcedimento> configuracoes) {
        this.configuracoes = configuracoes;
    }


    public void initBeneficiarios() {

    }
}