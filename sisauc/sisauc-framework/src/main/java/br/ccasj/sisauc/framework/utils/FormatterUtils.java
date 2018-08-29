package br.ccasj.sisauc.framework.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class FormatterUtils {
	
	public static String format(String pattern, Object value) {
		MaskFormatter mask;
		try {
			mask = new MaskFormatter(pattern);
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
