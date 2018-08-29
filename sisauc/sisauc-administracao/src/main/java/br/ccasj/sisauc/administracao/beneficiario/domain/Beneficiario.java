package br.ccasj.sisauc.administracao.beneficiario.domain;

import br.ccasj.sisauc.administracao.cadastro.domain.Convenio;
import br.ccasj.sisauc.administracao.cadastro.domain.PostoGraduacao;
import br.ccasj.sisauc.administracao.cadastro.domain.Sexo;
import br.ccasj.sisauc.framework.domain.EntidadeGenerica;
import br.ccasj.sisauc.framework.domain.OrganizacaoMilitar;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "beneficiario", schema = EntidadeGenerica.SCHEMA_SISAUC)
@AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false))
public class Beneficiario extends EntidadeGenerica implements Serializable {

    private static final long serialVersionUID = 1508151674742014665L;

    public static int TAMANHO_SARAM = 7;
    
    private String saram;
    private String nome;
    private String cpf;
    private PostoGraduacao postoGraduacao;
    private Sexo sexo;
    private Date dataNascimento;
    private Beneficiario beneficiarioTitular;
    private boolean ativo;
    private OrganizacaoMilitar organizacaoMilitar;
    private String tipoContribuicao;
    private Convenio convenio;
    private Date dataAtualizacao;
    private String saramTitular;
    private String cpfTitular;
    private boolean titular;
    private String cdParentesco;

    public Beneficiario() {
        super();
    }

    public Beneficiario(Integer id, String saram, String nome) {
        super(id);
        this.saram = saram;
        this.nome = nome;
    }

    public Beneficiario(Integer id, String saram, String nome, String tipoContribuicao, String siglaPostoGraduacao,
                        String descricaoPostoGraduacao, String nomeTitular, String saramTitular, String tipoContribuicaoTitular,
                        String cpfTitular, String descricaoPostoGraduacaoTitular, Sexo sexo, String cpf, Date dataNascimento,
                        String siglaOm, String siglaOmTitular) {
        this();
        this.id = id;
        this.saram = saram;
        this.nome = nome;
        this.tipoContribuicao = tipoContribuicao;
        this.sexo = sexo;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        if (nomeTitular != null) {
            this.beneficiarioTitular = new Beneficiario();
            this.beneficiarioTitular.setNome(nomeTitular);
            this.beneficiarioTitular.setSaram(saramTitular);
            this.beneficiarioTitular.setTipoContribuicao(tipoContribuicaoTitular);
            this.beneficiarioTitular.setCpf(cpfTitular);
            this.beneficiarioTitular.postoGraduacao = new PostoGraduacao();
            this.beneficiarioTitular.postoGraduacao.setDescricao(descricaoPostoGraduacaoTitular);

        }
        if (siglaPostoGraduacao != null) {
            this.postoGraduacao = new PostoGraduacao();
            this.postoGraduacao.setSigla(siglaPostoGraduacao);
        }
        if (descricaoPostoGraduacao != null) {
            this.postoGraduacao = new PostoGraduacao();
            this.postoGraduacao.setDescricao(descricaoPostoGraduacao);
        }
        if (siglaOm != null) {
            this.organizacaoMilitar = new OrganizacaoMilitar();
            this.organizacaoMilitar.setSigla(siglaOm);
        }
        if (siglaOmTitular != null) {
            this.beneficiarioTitular.setOrganizacaoMilitar(new OrganizacaoMilitar());
            this.beneficiarioTitular.getOrganizacaoMilitar().setSigla(siglaOmTitular);
        }
    }

    @Id
/*    @SequenceGenerator(name = "beneficiario_seq",
            sequenceName = "beneficiario_seq", schema = "sch_sisauc",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beneficiario_seq")
    */
    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return super.getId();
    }

    @Column(name = "nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "saram")
    public String getSaram() {
        return saram;
    }

    public void setSaram(String saram) {
        this.saram = saram;
    }

    @Column(name = "cpf")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @ManyToOne
    @JoinColumn(name = "id_posto_graduacao")
    public PostoGraduacao getPostoGraduacao() {
        return postoGraduacao;
    }

    public void setPostoGraduacao(PostoGraduacao postoGraduacao) {
        this.postoGraduacao = postoGraduacao;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    @Column(name = "data_nascimento")
    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Column(name = "ativo")
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @ManyToOne
    @JoinColumn(name = "id_organizacao_militar")
    public OrganizacaoMilitar getOrganizacaoMilitar() {
        return organizacaoMilitar;
    }

    public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
        this.organizacaoMilitar = organizacaoMilitar;
    }

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_beneficiario_titular", referencedColumnName = "id")
    })
    public Beneficiario getBeneficiarioTitular() {
        return beneficiarioTitular;
    }

    public void setBeneficiarioTitular(Beneficiario titular) {
        this.beneficiarioTitular = titular;
    }

    @Column(name = "tipo_contribuicao")
    public String getTipoContribuicao() {
        return tipoContribuicao;
    }

    public void setTipoContribuicao(String tipoContribuicao) {
        this.tipoContribuicao = tipoContribuicao;
    }

    @ManyToOne
    @JoinColumn(name = "id_convenio")
    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atualizacao_registro")
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Column(name = "titular")
    public boolean isTitular() {
        return titular;
    }

    @Column(name = "cd_parentesco")
    public String getCdParentesco(){
    	return this.cdParentesco;
    }
    
    public void setCdParentesco(String cdParentesco){
    	this.cdParentesco = cdParentesco;
    }
    
    public void setTitular(boolean titular) {
        this.titular = titular;
    }

    @Transient
    public String getSaramTitular() {
        return saramTitular;
    }

    @Transient
    public String getCpfTitular() {
        return cpfTitular;
    }

    public void setSaramTitular(String saramTitular) {
        this.saramTitular = saramTitular;
    }

    @Transient
    public Integer getIdade() {
        LocalDate dataNascimento = new LocalDate(this.dataNascimento);
        LocalDate agora = new LocalDate();
        Years age = Years.yearsBetween(dataNascimento, agora);
        return age.getYears();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Beneficiario other = (Beneficiario) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

	public void setCpfTitular(String cpfTitular) {
		this.cpfTitular = cpfTitular;
	}


}
