package br.ccasj.sisauc.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.generic.SortTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.framework.utils.managedbean.FormatterBean;
import br.ccasj.sisauc.relatorio.domain.Relatorio;

@Component
public class GeradorRelatorio {

	@Autowired
	private VelocityEngine velocityEngine;

	public void gerar(Relatorio relatorio) {
		gerar(relatorio, false);
	}
	
	public void gerar(Relatorio relatorio, boolean paisagem){

		Template t = velocityEngine.getTemplate(relatorio.getTemplate());

		VelocityContext context = gerarContext(relatorio.getMapa());
		context.put("number",new NumberTool());
		context.put("date", new DateTool());
		context.put("esc", new EscapeTool());
		context.put("sorter", new SortTool());
		context.put("eComercialEsc", new EComercialEscapeTool());
		context.put("formatter", new FormatterBean());

		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		String html = writer.toString();
		if(paisagem){
			html = html.replace("21cm 29.7cm", "29.7cm 21cm");
		}
		gerarRespostaHttp(gerarOutputStreamPDF(html), relatorio.getFilename());

	}

	private VelocityContext gerarContext(Map<String, Object> mapa) {
		VelocityContext context = new VelocityContext();

		for (String key : mapa.keySet()) {
			context.put(key, mapa.get(key));
		}

		return context;
	}

	//Esse método é reponsável por criar o PDF a partir de um HTML
	private ByteArrayOutputStream gerarOutputStreamPDF(String html) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(html);
			renderer.layout();
			renderer.createPDF(os);
			os.close();
			return os;
		} catch (DocumentException | IOException e) {
			throw new SystemRuntimeException("Problema ao gerar o PDF " + e.getMessage());
		}
	}

	//Esse método é reponsável por enviar o PDF via download para o usuário 
	private final void gerarRespostaHttp(ByteArrayOutputStream out, String filename) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.setResponseHeader("Content-Type", "application/pdf");
			externalContext.setResponseHeader("Content-disposition", "attachment; filename=\"" + filename + ".pdf\"");
			externalContext.getResponseOutputStream().write(out.toByteArray());
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new SystemRuntimeException("Ocorreu um problema ao gerar um relatório: " + e.getLocalizedMessage());
		}

	}
}
