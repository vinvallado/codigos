package br.mil.fab.consigext.config;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ObjectArrays;

import br.mil.fab.consigext.entity.UsuarioPerfilOM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@Component
@Scope(value = "session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ServicosSigpes {

    @Autowired
    String enderecoSigpesFoto;

    @Autowired
    String enderecoSigpesOmUsuario;

    @Autowired
    String enderecoPermissaoOm;

    RestTemplate restTemplate = new RestTemplate();


    /**
     * Busca o cod da om do usuário logado
     *
     * @param nmOm String com nome da om
     * @return String cdOrg
     * @throws JSONException
     */
    public String buscarCodOmUsuarioLogado(String nmOm) throws JSONException {
        String strOm = restTemplate.getForObject(enderecoSigpesOmUsuario + nmOm, String.class);

        JSONObject xmlJSONObj = XML.toJSONObject(strOm);
        ObjectMapper mapper = new ObjectMapper();
        JSONObject root = xmlJSONObj.getJSONObject("OrganizacaoList");
        JSONObject dadosOm = root.getJSONObject("Organizacao");

        return dadosOm.getString("cdOrg");
    }

    /**
     *
     * @param codPerfil
     * @param codOm
     * @param nrCpf
     * @return True/False
     */
    public boolean buscarUsrComPerfilPelaOm(String codPerfil, String codOm, String nrCpf) {
        ResponseEntity<UsuarioPerfilOM[]> responseEntity = restTemplate.getForEntity(enderecoPermissaoOm + codPerfil + "/" + codOm, UsuarioPerfilOM[].class);
        List<UsuarioPerfilOM> result = Arrays.asList(responseEntity.getBody());
        boolean retorno = false;

        for (UsuarioPerfilOM u: result) {
            retorno = u.getNR_CPF().equalsIgnoreCase(nrCpf);

            if(retorno)
                break;
        }

        return retorno;
    }
}
