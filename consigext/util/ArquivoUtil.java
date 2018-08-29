package br.mil.fab.consigext.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.Processamento;
import br.mil.fab.consigext.entity.ProcessamentoReto;
import br.mil.fab.consigext.entity.ServidorConsig;
import br.mil.fab.consigext.enums.CapacidadeServidor;
import br.mil.fab.consigext.enums.StatusEstabilizado;

public class ArquivoUtil {
	
	private static String montaLayoutString(String valor, int tamanho, String patterns) {
		if (valor.length() < tamanho) {
			StringBuffer sb = new StringBuffer(valor);
			for (int i = valor.length(); i < tamanho; i++) {
				sb.append(patterns);
			}
			return sb.toString();
		} else if (valor.length() > tamanho) {
			return valor.substring(0, tamanho);
		} else {
			return valor;
		}
	}

	/**
	 * Método que monta layout sendo numérico, com 0 (zero) a direita.
	 *
	 * @param valor
	 * @param tamanho
	 * @return
	 */
	private static String montaLayoutNumerico(long valor, int tamanho) {
		return String.format("%0" + tamanho + "d", valor);
	}

	public static File criarArquivo(List<Processamento> lista) throws IOException {
		String nomeArquivo = GenericUtil.getDataAtualDoisDigitos() + ".txt";
		File temp = File.createTempFile(nomeArquivo, ".txt");
		FileInputStream fis = null;
		BufferedWriter out = new BufferedWriter(new FileWriter(temp));
		StringBuffer sb;
		for (Processamento processo : lista) {
				sb = new StringBuffer();
				String valor = "0";
				BigDecimal valorCal = processo.getVlParcela();
				if (valorCal != null) {
					String valorS = valorCal.toString();
					valor = valorS.replace(".", "");
					sb.append(montaLayoutString(processo.getCdOrg(), 6, " ")); // UNIDADE PAGADORA
					//TODO: alterar tipo de operação
					sb.append(montaLayoutString(processo.getTpPessoa(),2," ")); // TIPO
					sb.append(montaLayoutString(processo.getCdPostoPgm(), 2, " ")); // POSTO PGM
					sb.append(montaLayoutString(processo.getNrOrdem(), 6, " ")); // Numero de Ordem
					sb.append(montaLayoutString(StringUtils.upperCase(processo.getCdCaixaAcantus()), 3, " ")); // Caixa acantus
					sb.append(montaLayoutNumerico(0, 9)); // Valor Atual
					sb.append(montaLayoutNumerico(Integer.parseInt(valor), 9)); // Valor novo
					sb.append(montaLayoutString(GenericUtil.yyyyMmToMmYY(processo.getCdAnoMesFim()), 4, " ")); // PRAZO
					sb.append(montaLayoutString(
							GenericUtil.abreviarNomes(
							StringUtils.upperCase(
							GenericUtil.removerAcentos(processo.getNmPessoa())),29), 29, " ")); // NOME
					sb.append(montaLayoutString("", 1, " ")); // STATUS
					sb.append(montaLayoutString(processo.getNrAde(), 9," ")); // NR ADE
					sb.append(montaLayoutString("", 1, " ")); // FILLER
					sb.append(montaLayoutNumerico(0, 1)); // OPERAÇÃO
					sb.append(montaLayoutString("", 3, " ")); // FILLER
					sb.append(montaLayoutString(processo.getNrIndice(), 2,"0")); // PLANO
					sb.append("\n");
					out.write(sb.toString());
				
			}
		}
		out.close();

		try {
			fis = new FileInputStream(temp);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;

	}
	
	public static List<ServidorConsig> processarArquivoPleno(byte[] arquivo) throws IOException {
		 InputStream is = new ByteArrayInputStream(arquivo);
		 return processarArquivoPleno(is);
	}
	
	public static List<ServidorConsig> processarArquivoPleno(InputStream file) throws IOException {
		  List<ServidorConsig> plenos = new ArrayList<>();
	        BufferedReader reader = new BufferedReader(
	        		new InputStreamReader(file,Charset.forName("UTF-8")) {
						});
				String line;
				try {
					   while ((line = reader.readLine()) != null) {
				if(line.length() == 186){
								ServidorConsig item  = gerarPlenoTripa(line);
								plenos.add(item);
						}else{
							break;
					}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return plenos;
	}
	
	
	
	public static List<ProcessamentoReto> processarArquivoReto(byte[] arquivo) throws IOException {
		 InputStream is = new ByteArrayInputStream(arquivo);
		 return processarArquivoReto(is);
	}
	
	
	public static List<ProcessamentoReto> processarArquivoReto(InputStream file) throws IOException {
		  List<ProcessamentoReto> retos = new ArrayList<>();
	        BufferedReader reader = new BufferedReader(
	        		new InputStreamReader(file,Charset.forName("UTF-8")) {
						});
				String line;
				try {
					   while ((line = reader.readLine()) != null) {
						if(line.length() == 97){
							ProcessamentoReto item  = gerarRetoTripa(line);
							retos.add(item);
						}else{
							break;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return retos;

	}
	

	private static ServidorConsig gerarPlenoTripa(String tripa) {
		ServidorConsig pleno = new ServidorConsig();
		String nrOrdem = tripa.substring(3,9).trim();
				nrOrdem = nrOrdem.concat(GenericUtil.dgtVerificadorNrOrdem(nrOrdem));
				pleno.setPesfis(new PesfisComgep(nrOrdem));
//	       		pleno.setVlMargem70(new BigDecimal(tripa.substring(84,95).trim()));
	       		pleno.setStCapacidade(CapacidadeServidor.valueOf(tripa.substring(96,97)));
	       		pleno.setStEstabilizado(tripa.substring(107,115).equals("SIM")? StatusEstabilizado.S : StatusEstabilizado.N);
	       		pleno.setDsCategoria(tripa.substring(115,165));
	       		pleno.setDtReenganjamento(GenericUtil.stringParaData(tripa.substring(165,175)));
//	       		pleno.setVlMargem30(new BigDecimal(tripa.substring(175,186).trim()));
	       	    return pleno;
		}

	
	private static ProcessamentoReto gerarRetoTripa(String tripa) {
		ProcessamentoReto reto = new ProcessamentoReto();
			reto.setCdOrg(tripa.substring(0,6));
			reto.setTipo(tripa.substring(6,7));
			reto.setPosto(tripa.substring(7,9));
			reto.setNrordem(tripa.substring(9,15));
			reto.setCaixa(tripa.substring(15,18));
			reto.setValorAtual(concatDecimals(tripa.substring(18,25),tripa.substring(25,27)));
			reto.setValornovo(concatDecimals(tripa.substring(27,34),tripa.substring(34,36)));
			reto.setPrazo(tripa.substring(36,40));
			reto.setNome(tripa.substring(40,69).trim());
			reto.setStatus(tripa.substring(69,70));
			reto.setAde(tripa.substring(70,79));
			reto.setTpOperacao(tripa.substring(80,81));
			reto.setPlano(tripa.substring(84,86));
			reto.setCpf(tripa.substring(86,97));
			return reto;
	}

	
	private static BigDecimal concatDecimals(String real, String decimal) {
		return new BigDecimal(real.concat(".").concat(decimal));
	}

}
