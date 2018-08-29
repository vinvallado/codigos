package br.ccasj.sisauc.framework.faces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

//**** REVERTE NONE > criacao_ws_beneficiarios (adição do LOGGER)
public class Mensagem {

    private static final Logger LOGGER = LoggerFactory.getLogger(Mensagem.class);

    public static void informacao(String msg) {
        LOGGER.info(msg);
        if (FacesContext.getCurrentInstance() == null)
            return;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    public static void erro(String msg) {
        LOGGER.info(msg);
        if (FacesContext.getCurrentInstance() == null)
            return;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }
}
