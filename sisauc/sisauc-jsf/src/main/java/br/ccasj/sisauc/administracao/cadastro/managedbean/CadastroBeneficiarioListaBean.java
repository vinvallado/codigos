package br.ccasj.sisauc.administracao.cadastro.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.adapter.BeneficiarioAdapter;
import br.ccasj.sisauc.administracao.beneficiario.dao.BeneficiarioDAO;
import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.administracao.beneficiario.service.BeneficiarioService;

@Scope(value = "view")
@Service(value = "cadastroBeneficiarioListaBean")
public class CadastroBeneficiarioListaBean implements Serializable {

	private static final long serialVersionUID = -6798863675958018006L;

	// **** REVERTE ADD > criacao_ws_beneficiarios :
	@Autowired
	private BeneficiarioDAO beneficiarioDAO;
	@Autowired
	private BeneficiarioService beneficiarioService;

	private Beneficiario beneficiario;
	private LazyDataModel<Beneficiario> lazyDataModel;
	private List<Beneficiario> beneficiarios = new ArrayList<Beneficiario>();


	@PostConstruct
	private void init() {
		// **** REVERTE REMOVED > criacao_ws_beneficiarios
		lazyDataModel = new LazyDataModel<Beneficiario>() {
			private static final long serialVersionUID = 3099397230239855086L;

			private String saram = "";
			private String cpf = "";
			private String name = "";

			private List<Beneficiario> _filtrarRepeticoesETratarParametrosComAnd(String cpf, String saram, String name,
					List<Beneficiario> beneficiarios, int pageSize) {
				List<Beneficiario> beneficiariosResult = new ArrayList<Beneficiario>();
				if (beneficiarios.size() == 1 && (cpf != null || saram != null)) {
					// Para o caso da possibilidade de trazer apenas um
					// beneficiario sem os seus afins
					String saramTemp = beneficiarios.get(0).getSaramTitular() != null
							? beneficiarios.get(0).getSaramTitular() : beneficiarios.get(0).getSaram();
					beneficiarios = beneficiarioService.findByCpfOrSaramOrName(false, null, saramTemp, null, 1,
							pageSize);
				}
				if (beneficiarios.size() > 0) {
					Comparator<Beneficiario> beneficiarioNomeIdComparator = new Comparator<Beneficiario>() {
						@Override
						public int compare(Beneficiario o1, Beneficiario o2) {
							int i = o1.getNome().compareTo(o2.getNome());
							if (i != 0)
								return i;
							return o1.getId().compareTo(o2.getId());
						}
					};
					Map<String, Beneficiario> beneficiariosSemRepeticaoMap = new HashMap<String, Beneficiario>();
					Collections.sort(beneficiarios, beneficiarioNomeIdComparator);
					for (Beneficiario beneficiario : beneficiarios) {
						beneficiariosSemRepeticaoMap.put(beneficiario.getNome(), beneficiario);
					}
					// Simula um AND
					boolean bateSaram = false;
					boolean bateCpf = false;
					boolean bateNome = false;
					for (Beneficiario beneficiario : beneficiariosSemRepeticaoMap.values()) {
						bateSaram = saram == null;
						bateCpf = cpf == null;
						bateNome = name == null;
						if (saram != null) {
							if ((beneficiario.getSaramTitular() != null && beneficiario.getSaramTitular().equals(saram))
									|| (beneficiario.getSaram() != null && beneficiario.getSaram().equals(saram))) {
								bateSaram = true;
							}
						}
						if (cpf != null) {
							if (beneficiario.getCpf() == null || cpf.equals(beneficiario.getCpf()
									.replaceAll(Pattern.quote("."), "").replaceAll(Pattern.quote("-"), ""))) {
								bateCpf = true;
							}
						}
						if (name != null) {
							if (beneficiario.getNome().indexOf(name) != -1) {
								bateNome = true;
							}
						}
						if (bateSaram && bateCpf && bateNome) {
							beneficiariosResult.add(beneficiario);
						}
					}

					Collections.sort(beneficiariosResult, beneficiarioNomeIdComparator);
				}
				return beneficiariosResult;
			}

			private List<Beneficiario> _filtrarOpcoes(String convenio, String titular, String ativo) {
				boolean filtrarTitular = false;
				boolean filtrarAtivo = false;
				boolean filtrarConvenio = false;
				List<Beneficiario> beneficiariosFiltrados = new ArrayList<Beneficiario>();
				if ((convenio != null) ) {
					filtrarConvenio = true;
				}
				if ((titular != null ) ) {
					filtrarTitular = true;
				}
				if ((ativo != null ) ) {
					filtrarAtivo = true;
				}
				if (filtrarConvenio || filtrarTitular || filtrarAtivo) {
					for (Beneficiario beneficiario : CadastroBeneficiarioListaBean.this.beneficiarios) {
						boolean mostrarConvenio = false;
						boolean mostrarAtivo = false;
						boolean mostrarTitular = false;
						if (convenio != null) {
							if (beneficiario.getConvenio().getSigla().equals(convenio)) {
								mostrarConvenio = true;
							}
						} else {
							mostrarConvenio = true;
						}
						if (titular != null) {
							if ((titular.equals("Sim") && beneficiario.isTitular())
									|| (titular.equals("Não") && !beneficiario.isTitular())) {
								mostrarTitular = true;
							}
						} else {
							mostrarTitular = true;
						}
						if (ativo != null) {
							if ((ativo.equals("Sim") && beneficiario.isAtivo())
									|| (ativo.equals("Não") && !beneficiario.isAtivo())) {
								mostrarAtivo = true;
							}
						} else {
							mostrarAtivo = true;
						}
						if (mostrarConvenio && mostrarAtivo && mostrarTitular) {
							beneficiariosFiltrados.add(beneficiario);
						}
					}
					return beneficiariosFiltrados;
				} else {
					return CadastroBeneficiarioListaBean.this.beneficiarios;
				}
			}

			@Override
			public List<Beneficiario> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				boolean buscarNovosRegistros = false;
				String cpf = (filters.get("cpf") == null || StringUtils.isEmpty(String.valueOf(filters.get("cpf"))))
						? null : String.valueOf(filters.get("cpf"));
				String saram = (filters.get("saram") == null
						|| StringUtils.isEmpty(String.valueOf(filters.get("saram")))) ? null
								: String.valueOf(filters.get("saram"));
				String name = (filters.get("nome") == null || StringUtils.isEmpty(String.valueOf(filters.get("nome"))))
						? null : String.valueOf(filters.get("nome")).toUpperCase();
				String convenio = (filters.get("sigla") == null
						|| StringUtils.isEmpty(String.valueOf(filters.get("sigla")))) ? null
								: String.valueOf(filters.get("sigla"));
				String titular = (filters.get("titular") == null
						|| StringUtils.isEmpty(String.valueOf(filters.get("titular")))) ? null
								: String.valueOf(filters.get("titular"));
				String ativo = (filters.get("ativo") == null
						|| StringUtils.isEmpty(String.valueOf(filters.get("ativo")))) ? null
								: String.valueOf(filters.get("ativo"));

				if ((name != null && !name.equals(this.name) && name.trim().length() > 7)
						|| (name == null && this.name != null)
						|| (name != null && this.name == null && name.trim().length() > 7)) {
					buscarNovosRegistros = true;
					this.name = name;
				}
				if ((saram == null && this.saram != null) || (saram != null && this.saram == null)
						|| (saram != null && !saram.equals(this.saram))) {
					buscarNovosRegistros = true;
					this.saram = saram;
				}
				if (cpf != null) {
					cpf = cpf.replaceAll(Pattern.quote("."), "").replaceAll(Pattern.quote("-"), "");
					if (!cpf.equals(this.cpf)) {
						buscarNovosRegistros = true;
						this.cpf = cpf;
					}
				} else if ((cpf == null && this.cpf != null) || (cpf != null && this.cpf == null)) {
					buscarNovosRegistros = true;
					this.cpf = cpf;
				}
				if (buscarNovosRegistros) {
					List<Beneficiario> beneficiarios = new ArrayList<Beneficiario>();
					if (cpf != null || saram != null || name != null) {
						beneficiarios = beneficiarioService.findByCpfOrSaramOrNameFullLike(false, cpf, saram, name,
								first + 1, pageSize);
					}
					CadastroBeneficiarioListaBean.this.beneficiarios = _filtrarRepeticoesETratarParametrosComAnd(cpf,
							saram, name, beneficiarios, pageSize);
					// DAR UPDATE NA VIEW
					setRowCount(beneficiarios.size());
				}
				return _filtrarOpcoes(convenio, titular, ativo);
			}

		};

	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public LazyDataModel<Beneficiario> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<Beneficiario> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

}
