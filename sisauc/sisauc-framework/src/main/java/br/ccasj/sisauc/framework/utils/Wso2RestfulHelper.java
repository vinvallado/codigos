package br.ccasj.sisauc.framework.utils;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Wso2RestfulHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(Wso2RestfulHelper.class);

    private static Client client;
    private static WebTarget target;
    public static String host = //FacesContext.getCurrentInstance().getExternalContext().getInitParameter("	");
            "http://10.52.141.138:9763";
    public static final String baseURIPortal = host + "/services/ServicePortal";
    public static final String baseURIBoletim = host + "/services/ServiceBoletim";
    public static final String baseURIAcesso = host + "/services/ServiceAcesso";
    public static final String baseURISIGPES = host + "/services/ServiceSIGPES";
    public static final String baseURIConsig = host + "/services/ServiceConsig";
    public static final String baseURISisauc = host + "/services/ServiceSisauc";
    private static Gson gson;

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        gson = gsonBuilder.create();
    }

    /* Configuracao do cliente para chamada do webservice
         * @param uri: caminho base do webservice
         * @param targetPath: metodo a ser executado
         */


    private static void setup(String uri, String targetPath) {
        client = ClientBuilder.newClient();
        target = client.target(uri);
        target = target.path(targetPath);
    }

    /*
         * @param response: resposta do servico
         * @param parent: primeiro nivel hierarquico na resposta Json do WSO2 - DSS (corresponde ao group namespace quando usado XML como outpu no WSO2 - DSS)
         * @param child: segundo nivel hierarquico na resposta Json do WSO2 - DSS (corresponde ao row namespace quando usado XML como outpu no WSO2 - DSS)
         * @return JsonArray: resposta formatada no formato JsonArray
         */
    private static JsonArray resultadoLista(String response, String parent, String child) {
        JsonArray array = new JsonArray();

        if (response != null && !response.isEmpty()) {
            JsonParser parser = new JsonParser();
            JsonObject obj = parser.parse(response).getAsJsonObject().get(parent).getAsJsonObject();
            if (obj.get(child) != null) {
                array = obj.get(child).getAsJsonArray();
            }
        }

        return array;
    }

    /*
         * @param method: metodo a ser executado no webservice
         * @param parameters: parametros do payload do Json, formatado em "key:value"
         * @return String com o Json formatado para o WSO2 - DSS
         */
    private static String gerarRequisicao(String method, Map<String, Object> parameters) {
        StringBuffer saida = new StringBuffer();
        method = method.toLowerCase(Locale.getDefault());
        saida.append("{" + "\"" + method + "\": {");
        int i = 0;
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
        	if (entry.getValue() instanceof Double){
        		saida.append(" \"" + entry.getKey() + "\" : " + entry.getValue() );
        	} else {
        		saida.append(" \"" + entry.getKey() + "\" : \"" + entry.getValue() + "\"");
        	}
            if ((i + 1) < parameters.keySet().size()) {
                saida.append(", ");
            }
            i++;
        }
        saida.append("}}");
        return saida.toString();
    }

    /*
         * @param target: WebTarget
         * @param inputJson: Json a ser enviado
         * @return resposta do webservice
         */
    private static Response putResponse(WebTarget target, String inputJson) {
        return target.request(MediaType.APPLICATION_JSON).put(Entity.entity(inputJson, MediaType.APPLICATION_JSON),
                Response.class);
    }

    /*
         * @param target: WebTarget
         * @param inputJson: Json a ser enviado
         * @return resposta do webservice
         */
    private static Response postResponse(WebTarget target, String inputJson) throws ConnectException {
        return target.request(MediaType.APPLICATION_JSON).post(Entity.entity(inputJson, MediaType.APPLICATION_JSON),
                Response.class);
    }


    /*
         * @param uri: caminho base do webservice
         * @param targetPath: nome do metodo a ser executado no webservice
         * @param params: parametros do payload do Json, formatado em "key:value"
         * @param parent: primeiro nivel hierarquico na resposta Json do WSO2 - DSS (corresponde ao group namespace quando usado XML como outpu no WSO2 - DSS)
         * @param child: segundo nivel hierarquico na resposta Json do WSO2 - DSS (corresponde ao row namespace quando usado XML como outpu no WSO2 - DSS)
         * @return JsonArray: resposta do webservice formatada no formato JsonArray
         */
    public static JsonArray doGet(String uri, String targetPath, Map<String, String> params, String parent,
                                  String child) {
        setup(uri, targetPath);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                target = target.queryParam(entry.getKey(), entry.getValue());
            }
        }

        Response response = target.request(MediaType.APPLICATION_JSON).get(Response.class);
        if (response != null) {
            return response.getStatus() == 200 ? resultadoLista(response.readEntity(String.class), parent, child) :
                   null;
        }
        return new JsonArray();

    }

    /*
        * @param uri: caminho base do webservice
        * @param targetPath: nome do metodo a ser executado no webservice
        * @param params: parametros do payload do Json, formatado em "key:value"
        * @param parent: primeiro nivel hierarquico na resposta Json do WSO2 - DSS
        * @param child: segundo nivel hierarquico na resposta Json do WSO2 - DSS
        * @return true se sucesso, false caso algum erro
         */
    public static boolean doPost(String uri, String targetPath, String method, Map<String, Object> params) throws ConnectException {
		return _doPost(uri, targetPath, method, params).getStatus() == 200;
    }
    
    public static Object doPost(String uri, String targetPath, String method, Map<String, Object> params, Class<?> clazz) throws ConnectException, Wso2ResponseException{
    	Response response = _doPost(uri, targetPath, method, params);
    	if (response != null ){
		    if (response.getStatus() == 200) {
		        final String entity = response.readEntity(String.class);
//		        
//		        DescontoList descontoList = new DescontoList();
//		        
//		        Desconto desconto = new Desconto();
//		        desconto.setIdDesconto(555);
//		        
//		        List<Desconto> descontos = new ArrayList<Desconto>();
//		        descontos.add(desconto);
//		        descontoList.setDesconto(descontos);
//		        
//		        System.out.println(gson.toJson(descontoList));
//		        
		        
		        
		        return gson.fromJson(entity, clazz);
		    } else {
		    	throw new Wso2ResponseException(response.getStatus(), ((StatusType)response.getStatusInfo()).getReasonPhrase());
		    }
    	}
	    return null;
    }
    
    private static Response _doPost(String uri, String targetPath, String method, Map<String, Object> params) throws ConnectException{
    	try{
	    	setup(uri, targetPath);
	    	String inputJson = gerarRequisicao(method, params);
	    	return postResponse(target, inputJson);
    	}catch (ConnectException e){
    		e.printStackTrace();
    		throw e;
    	} catch (Exception e){
    		e.printStackTrace();
    		throw e;
    	} 
    }
    

    /*
         * @param uri: caminho base do webservice
         * @param targetPath: nome do metodo a ser executado no webservice
         * @param params: parametros do payload do Json, formatado em "key:value"
         * @return true se sucesso, false caso algum erro
         */
    public static boolean doPut(String uri, String targetPath, String method, Map<String, Object> params) {
        setup(uri, targetPath);
        String inputJson = gerarRequisicao(method, params);
        Response response = putResponse(target, inputJson);
        return response.getStatus() == 202;
    }

    /*
         * @param jsonArray: resposta do webservice em formato Json
         * @return ArrayList de HashMap em que cada HashMap<String,String> representa uma instancia de uma entidade (1 linha na tabela)
         */
    public static ArrayList JsonArrayToArrayListOfHashMap(JsonArray jsonArray) {

        ArrayList output = new ArrayList();

        for (JsonElement e : jsonArray) {

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(e.toString());
            JsonObject jObj = element.getAsJsonObject();

            HashMap tempMap = new HashMap();

            for (Map.Entry<String, JsonElement> el : jObj.entrySet()) {
                String key = el.getKey();

                if (!el.getValue().isJsonNull()) {
                    String value = el.getValue().getAsString();
                    tempMap.put(key, value);
                }
            }

            output.add(tempMap);
        }

        return output;
    }

    public static Object doGet(final String uri, final String targetPath, final Map<String, Object> params, final Class<?> clazz) {
        LOGGER.info("Start Http GET to [{}] resource[{}]", uri, targetPath);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        client = ClientBuilder.newClient();
        WebTarget resourceTarget = client.target(uri).path(targetPath);

        if (params != null && !params.isEmpty()) {
            for (final Map.Entry<String, Object> entry : params.entrySet()) {
                resourceTarget = resourceTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        final Response response = resourceTarget.request(MediaType.APPLICATION_JSON).get(Response.class);

        stopWatch.stop();

        LOGGER.info("Finish Http GET status result [{}] in [{}]", response.getStatus(), stopWatch.getTime());

        if (response != null && response.getStatus() == 200) {
            final String entity = response.readEntity(String.class);
            return gson.fromJson(entity, clazz);
        }
        return null;
    }
}