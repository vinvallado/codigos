package br.ccasj.sisauc.framework.converter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "doubleConverter")
public class DoubleConverter implements Converter {

	private Locale locale = new Locale("pt", "BR");

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valorTela) throws ConverterException {

		if (valorTela == null || valorTela.toString().trim().equals("")) {
			return 0.0d;

		} else {
			try {
				valorTela = valorTela.replaceAll("[^0123456789,.-]", "");
				NumberFormat nf = NumberFormat.getInstance(locale);
				nf.setMaximumFractionDigits(2);
				nf.setMinimumFractionDigits(2);
				return nf.parse(valorTela).doubleValue();
			} catch (ParseException e) {
				e.printStackTrace();
				return 0.0d;
			}
		}
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object valorTela) throws ConverterException {

		if (valorTela == null || valorTela.toString().trim().equals("")) {
			return "0,00";

		} else {
			NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			return nf.format(Double.valueOf(valorTela.toString()));
		}
	}

}
