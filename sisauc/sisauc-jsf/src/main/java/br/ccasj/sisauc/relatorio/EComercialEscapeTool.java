package br.ccasj.sisauc.relatorio;

public class EComercialEscapeTool {

	public String escape(String html){
		return html.replace("&", "&#38;");
	}
}
