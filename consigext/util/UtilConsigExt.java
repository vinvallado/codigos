package br.mil.fab.consigext.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class UtilConsigExt {
	
	/**
	 * Converts a timestamp to a formated date
	 * @param timestamp Date
	 * @param showTime boolean	
	 * @return String with formated date
	 */
	public static String timestampToDate(Date timestamp, boolean showTime) {
		String formatedDate = null;
		
		if(showTime)
			formatedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp);
		else
			formatedDate = new SimpleDateFormat("dd/MM/yyyy").format(timestamp);
			
		return formatedDate;
	}
	
	/**
	 * Convert Iterable numa List<T>
	 * @param it Iterable
	 * @return List<T>
	 */
	public static <T> List<T> toList(final Iterable<T> it) {
		return StreamSupport.stream(it.spliterator(), false)
                .collect(Collectors.toList());
	}

	/**
	 * Pega o caminho do arquivo e preenche os dados necessários para salvar no banco
	 * @param caminhoArquivo String contendo o caminho do arquivo
	 * @param nrOrdem String contendo o nrOrdem do usuário que esta cadastrando
	 * @return Arquivo
	 */
	
}
