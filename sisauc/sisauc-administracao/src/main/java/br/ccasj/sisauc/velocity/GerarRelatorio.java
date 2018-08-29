package br.ccasj.sisauc.velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.stereotype.Service;

@Service("gerarRelatorio")
public class GerarRelatorio {

		public void imprimir(String template, Object object) throws IOException {
			
		VelocityEngine ve = new VelocityEngine();
		
	    ve.setProperty("input.encoding", "UTF-8");
	    ve.setProperty("output.encoding", "UTF-8");
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		
		ve.init();
		
		Template t = ve.getTemplate(template);
		
		VelocityContext context = new VelocityContext();
		context.put("object", object);
		context.put("number", new NumberTool());
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		System.out.println(writer.toString());
		
		FacesContext ctext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) ctext.getExternalContext().getResponse();
		
		response.setContentType("text/html");
		
		response.getOutputStream().write(writer.toString().getBytes(Charset.forName("UTF-8")));
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		}
	
}
