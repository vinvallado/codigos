package br.mil.fab.consigext.webservice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.mil.fab.consigext.util.GenericUtil;
import br.mil.fab.wso2.exception.Wso2Exception;
import br.mil.fab.wso2.helper.Wso2Restful;

@Component
public class DescontoWebClient implements Serializable {

	private static final long serialVersionUID = 6598440625356857161L;
	private Map<String, String> parametros;
	public static final String baseURIPortal = "/services/ServicePortal";
	public static final String baseURIBoletim = "/services/ServiceBoletim";
	public static final String baseURIAcesso = "/services/ServiceAcesso";
    public static final String baseURIConsig = "/services/ServiceConsig";


	public ArrayList<HashMap<String, String>> buscarDependentesPorNrOrdem(String nrOrdem) {
		parametros = new HashMap<String, String>();
		parametros.put("nrOrdem", nrOrdem);
		try {
			return Wso2Restful.JsonArrayToArrayListOfHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"dependenteByNrOrdem/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
		return null;
}

	public HashMap<String, String> buscarStatusAutorizacaoInatPorNrOrdem(String nrOrdem) {
		parametros = new HashMap<String, String>();
		parametros.put("nrOrdem", nrOrdem);

		try {
			return Wso2Restful.JsonToHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"statusAutorizacaoInat/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
		return new HashMap<String, String>();
	}
	
	

	public ArrayList<HashMap<String, String>> buscarPostosPorHierarquia() {
		parametros = new HashMap<String, String>();
	
		try {
			ArrayList<HashMap<String,String>> lista = Wso2Restful.JsonArrayToArrayListOfHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"buscarPostoByHierarquia/", parametros));
			lista.removeIf(x -> x.containsValue("MA") ||  
					x.containsValue("M1") || 
					x.containsValue("1E") ||
					x.containsValue("2E"));
			return lista;
		} catch (Wso2Exception e) {

			GenericUtil.logErroComException("DescontoWebClient", e);
		} 
		return new ArrayList<HashMap<String, String>>();
	}




	public ArrayList<HashMap<String, String>> buscarOmPorNrOrdem(String nrOrdem) {

		parametros = new HashMap<String, String>();
		parametros.put("nrOrdem", nrOrdem);
		try {
			return Wso2Restful.JsonArrayToArrayListOfHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"omPorNrOrdem/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		} 
		return new ArrayList<HashMap<String, String>>();
	}


	public ArrayList<HashMap<String, String>> naturezaDescontoByCdOrg(String cdOrg) {

		parametros = new HashMap<String, String>();
		parametros.put("cdOrg", cdOrg);
		try {
			return Wso2Restful.JsonArrayToArrayListOfHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"naturezaDescontoByCdOrg/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
		
		
		return new ArrayList<HashMap<String, String>>();

	}

	public void criarMotivoUtilizado(String cdMotivo, String stAutorizacao, String cdOrg, String idProcesso,
			String nrOrdemUtilizado) throws Wso2Exception {
		parametros = new HashMap<String, String>();
		parametros.put("cdMotivo", cdMotivo);
		parametros.put("stAutorizacao", stAutorizacao);
		parametros.put("nrIpUsuario", "0");
		parametros.put("nrMacUsuario", "0");
		parametros.put("cdOrg", cdOrg);
		parametros.put("idProcesso", idProcesso);
		parametros.put("idModulo", "4");
		parametros.put("nrOrdemUtilizado", nrOrdemUtilizado);


			Wso2Restful.doPost(GenericUtil.getHost() + baseURIBoletim, "InserirMotivoUtilizado/",
					"_postinserirmotivoutilizado", parametros);
	
	}

	public void inserirComponenteMotivo(String idLancamento, String nmCampo, String valorComponente, String cdMotivo) {

		Map<String, String> parametros = new HashMap<String, String>();

		parametros.put("idMotivoUtilizado", idLancamento);
		parametros.put("nmCampo", nmCampo);
		parametros.put("valorComponente", valorComponente);
		parametros.put("cdMotivo", cdMotivo);

		try {
			Wso2Restful.doPost(GenericUtil.getHost() + baseURIBoletim, "inserirComponenteNoMotivoByNmCampo/",
					"_postinserircomponentenomotivobynmcampo", parametros);
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
	

	}


	public ArrayList<HashMap<String, String>> obtemMotivoUtilizadoByIdProcessoIdModulo(String idProcesso) {

		parametros = new HashMap<String, String>();
		parametros.put("idProcesso", idProcesso);
		parametros.put("idModulo", "4");
		try {
			return Wso2Restful.JsonArrayToArrayListOfHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIBoletim,
					"obtemMotivoUtilizadoByIdProcessoIdModulo/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		} 
		return null;
	}


	public ArrayList<HashMap<String, String>> obtemComponentes(String cdMotivo) {
		parametros = new HashMap<String, String>();
		parametros.put("cdMotivo", cdMotivo);
		try {
			return Wso2Restful.JsonArrayToArrayListOfHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIBoletim,
					"componenteMotivo/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
		
		return null;
	}


	public String obtemSgOrg(String cdOrg) {

		parametros = new HashMap<String, String>();
		parametros.put("cdOrg", cdOrg);

		HashMap<String, String> sgOrgList;
		try {
			sgOrgList = Wso2Restful.JsonToHashMap(Wso2Restful
					.doGet(GenericUtil.getHost() + baseURIConsig, "sgOrgPorCdOrg/", parametros));
			return sgOrgList.get("sgOrg");
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		} 
		
		return "";

		
	}

	
	public String obtemPostoPgm(String nrOrdem) {

		parametros = new HashMap<String, String>();
		parametros.put("nrOrdem", nrOrdem);

		HashMap<String, String> postoPagList;
		try {
			postoPagList = Wso2Restful
					.JsonToHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
							"postoPagamento/", parametros));
			return postoPagList.get("cdPostoPgm");
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		} 

		return null;
	}
	
	
	public String obtemCdHierarquia(String sgPosto) {

		parametros = new HashMap<String, String>();
		parametros.put("sgPosto", sgPosto);

		HashMap<String, String> cdHierarquiaMap;
		try {
			cdHierarquiaMap = Wso2Restful
					.JsonToHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
							"consultarHierarquiaPorPosto/", parametros));
			return cdHierarquiaMap.get("cdHrq");
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		} 

		return null;
	}

	public double consultarMargem(String nrOrdem) {
	/*	ConsultarMargemResponse response = new ConsultarMargemResponse();
		try {
			String nrOrdemTemp = nrOrdem.substring(0,6);
			ConsultarMargem margem = new ConsultarMargem();
			GenericUtil.configurarPermissaoServico(margem);
			margem.setMatricula(nrOrdemTemp);
			margem.setValorParcela(0.01);
			HostaHostServiceStub stub = new HostaHostServiceStub();
			GenericUtil.configurarProxy(stub);
			response = stub.consultarMargem(margem);
		if(response.getSucesso()){
			return response.getInfoMargem()[0].getValorMargem();
		}else{
			GenericUtil.mensagemErro(response.getMensagem());
		}
		} catch (AxisFault e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
			GenericUtil.mensagemErro("Erro 3255 no servi�o de consulta de margem. Por favor, tente mais tarde: " + e.getMessage());
		
		} catch (RemoteException e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
			GenericUtil.mensagemErro("Erro 3254 no servi�o de consulta de margem. Por favor, tente mais tarde: " + e.getMessage());
			
	
		}*/
		return -1;
	
	}
	
	
	public BigDecimal obtemSoldo(String nrOrdem) {
		parametros = new HashMap<String, String>();
		parametros.put("nrOrdem", nrOrdem);
		HashMap<String, String> soldos;
		try {
			soldos = Wso2Restful.JsonToHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"consultarSoldo/", parametros));
			return new BigDecimal(soldos.get("soldo"));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		} 
	
		return BigDecimal.ZERO;
	}
	
	
	public BigDecimal obtemSaldoParcelaRegular(String nrOrdem) {
		parametros = new HashMap<String, String>();
		parametros.put("nrOrdem", nrOrdem);
		HashMap<String, String> saldos;
		try {
			saldos = Wso2Restful.JsonToHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"consultarSaldoRegular/", parametros));
	
				return new BigDecimal(saldos.get("saldo"));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		} 
	
		return BigDecimal.ZERO;
	}
	
	
	public BigDecimal obtemSaldoParcelaVariavel(String nrOrdem) {
		parametros = new HashMap<String, String>();
		parametros.put("nrOrdem", nrOrdem);
		HashMap<String, String> saldos;
		try {
			saldos = Wso2Restful.JsonToHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"consultarSaldoVariavel/", parametros));
			return new BigDecimal(saldos.get("saldo"));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
		return BigDecimal.ZERO;
	}
	
	
	public HashMap<String,String> buscarPessoaFisicaPorNrOrdemOuCpf(String registro){
		parametros = new HashMap<String, String>();
		parametros.put("registro", registro);
		/*
		 * return new Wso2RestfulObject().doGetAsObject(GenericUtil.getHost()+
		 * baseURIConsig, "pessoaFisicaByNrOrdemOuCpf/", parametros);
		 */
		try {
			return Wso2Restful.JsonToHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"pessoaFisicaByNrOrdemOuCpf/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
		return new HashMap<String, String>();
	}
	
	public HashMap<String,String> buscarCaixaPagamento(String parcela, String cdOrg){
		parametros = new HashMap<String, String>();
		parametros.put("parcelaRe", parcela);
		parametros.put("cdOrg", cdOrg);
		
		try {
			return Wso2Restful.JsonToHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"caixaPagamento/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
		return new HashMap<String, String>();
	}
	
	
	public ArrayList<HashMap<String, String>>  buscarCaixaPagamento(String parcela, String cdOrg,String oper){
		parametros = new HashMap<String, String>();
		parametros.put("parcelaRe", parcela);
		parametros.put("cdOrg", cdOrg);
		parametros.put("operacao", oper);
		
		try {
			return Wso2Restful.JsonArrayToArrayListOfHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"caixaPagamentoOperacao/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
		return new ArrayList<HashMap<String, String>>();
	}
	
	
	public HashMap<String,String> buscarOmPorCx(String cdCaixa){
		parametros = new HashMap<String, String>();
		parametros.put("cd_caixa", cdCaixa);
		
		try {
			return Wso2Restful.JsonToHashMap(Wso2Restful.doGet(GenericUtil.getHost() + baseURIConsig,
					"oMPorCxPagamento/", parametros));
		} catch (Wso2Exception e) {
			GenericUtil.logErroComException("DescontoWebClient", e);
		}
		return new HashMap<String, String>();
	}
	

	






}
