package br.ccasj.sisauc.administracao.intendencia;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.auditoriaretrospectiva.domain.AuditoriaRetrospectiva;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.faces.Mensagem;
import br.ccasj.sisauc.framework.utils.managedbean.ManagedBeanUtils;
import br.ccasj.sisauc.intendencia.dao.DescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.DescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.EstadoRelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.service.DescontoBeneficiariosService;

@Scope(value = "view")
@Service(value = "descontoRealizadoFormBean")
public class DescontoRealizadoFormBean implements Serializable {

	private static final long serialVersionUID = 2768364952350699166L;
	
	@Autowired
	private DescontoBeneficiarioDAO descontoBeneficiarioDAO;

	@Autowired
	private DescontoBeneficiariosService descontoBeneficiariosService;
	
	private DescontoBeneficiario descontoBeneficiario;
	
	
	
	@PostConstruct
	private void init() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		descontoBeneficiario = descontoBeneficiarioDAO.obterCompletoPorId(Integer.valueOf(id));
	}

	public DescontoBeneficiario getDescontoBeneficiario() {
		return descontoBeneficiario;
	}

	public void setDescontoBeneficiario(DescontoBeneficiario descontoBeneficiario) {
		this.descontoBeneficiario = descontoBeneficiario;
	}
	
	public void confirmaReativacaoDesconto() {
		ManagedBeanUtils.showDialog("confirmaReativacaoDescontoDialog");	
	}
	
	public void reativarDescontoInterrompido() {
		this.descontoBeneficiario.setEstadoDescontoBeneficiario(EstadoRelatorioFolhaBeneficiario.INTERROMPIDO_ERRO);
		this.descontoBeneficiarioDAO.merge(this.descontoBeneficiario);
		
		Mensagem.informacao("Desconto Interrompido foi Reativado com Sucesso! Pronto para o reenvio ao Aerconsig");
		ManagedBeanUtils.hideDialog("confirmaReativacaoDescontoDialog");
	}

	public double obterValorDescontoItem(ItemGAB item) {
		if (item.getGab().isIsento()) {
			return 0.0;
		} else {
			return this.descontoBeneficiariosService.calcularValorDesconto(obterValorFinalItem(item));	
		}
	}
	
	public double obterValorFinalItem(ItemGAB item) {
		AuditoriaRetrospectiva auditoria = item.getAuditoriaRetrospectiva();
		return auditoria.getValorFinal();
	}
	
}
