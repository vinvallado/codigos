package br.mil.fab.consigext.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;
import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.mil.fab.consigext.config.Message;
import br.mil.fab.consigext.entity.EnderecoOrganizacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.Localidade;
import br.mil.fab.consigext.enums.Restriction;
import br.mil.fab.consigext.enums.StatusAcesso;
import br.mil.fab.consigext.service.util.UsuarioUtilService;


public class GenericUtil {

	private GenericUtil() {
	};

	private static void sendMessage(FacesMessage.Severity severity, String msg) {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, ""));

	}
	public static String getHost() {
		return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("restServer");
	}

	public static String removerAcentos(String texto) {
		texto = texto.replace("'", "");
		return StringUtils.stripAccents(texto);
	}

	public static void mensagemErro(String msg) {
		sendMessage(FacesMessage.SEVERITY_ERROR, msg);
	}

	public static void mensagemInfo(String msg) {
		sendMessage(FacesMessage.SEVERITY_INFO, msg);
	}

	public static void mensagemAtencao(String msg) {
		sendMessage(FacesMessage.SEVERITY_WARN, msg);
	}

	public static void logErroComException(String classe, Exception e) {
		Logger.getLogger(classe)
				.error("LINHA: " + e.getStackTrace()[0].getLineNumber() + " ERRO: " + e.getLocalizedMessage());

	}
	public static void logErro(String classe, String msg) {
		Logger.getLogger(classe).error("ERRO: " + msg);

	}
	
	public static void logInfo(String classe, String msg) {
		Logger.getLogger(classe).info("INFO: " + msg);

	}

	/**
	 * A partir de um BigDecimal, retorna o valor em String formatado para
	 * localidade brasileira. Ex.: R$1.000,00
	 * 
	 * @param numero
	 *            no formato BigDecimal que deseja formatar
	 * @return Retorna o num�ro formatado com separa��o de casa decimal com v�rgula
	 *         e separa��o de grupos com ponto Ex.: R$1.000,00
	 */
	public static String formatacaoDecimalMoeda(BigDecimal numero) {
		if (numero != null) {
			DecimalFormat df = new DecimalFormat();
			df.setMinimumFractionDigits(2);
			DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
			symbols.setGroupingSeparator('.');
			return df.format(numero);
		}
		return "0,00";
	}

	/**
	 * Retorna mes e ano atual em String no formato MMYYYY. Ex: 022016
	 * 
	 * @return String formata com o valor MESANO. Ex: 012016.
	 */
	public static String getMMAAAtual() {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MMyy");
		return df.format(cal.getTime());
	}

	/**
	 * Retorna m�s e ano atual em String no formato MMYYYY. Ex: 022016
	 * 
	 * @return String formata com o valor MESANO. Ex: 012016.
	 */
	public static String getAAAAMMtual() {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(cal.getTime());
	}

	public static String getAAAAMMtual(Calendar cal) {
		DateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(cal.getTime());
	}
	
	public static String getDateTimeAtual() {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("EEEEE, dd MMMM yyyy - HH:mm:ss");
		return df.format(cal.getTime());
	}
	
	public static String getDateTimeAtualFormat() {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		return df.format(cal.getTime());
	}

	public static String getCpfFormatado(String cpf) {
		try {
			MaskFormatter mf = new MaskFormatter("###.###.###-##");
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(cpf);
		} catch (ParseException e) {
			return cpf;
		}

	}

	/**
	 * Retorna o valor de uma data no formato MESANO_HORAMINSEGUNDO. Ex:
	 * 012016_010110.
	 * 
	 * @return String formata com o valor MESANO_HORAMINSEGUNDO. Ex: 012016_010110.
	 */
	public static String getDataAtualDoisDigitos() {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MMyyyy_hms");
		return df.format(cal.getTime());
	}

	/**
	 * Formata valores de data para dois digitos caso seja menor que 10, colocando
	 * um 0 na frente.
	 * 
	 * @param O
	 *            valor em inteiro.
	 * @return Caso o valor seja menor que 10, o valor com um 0 na frente. Caso seja
	 *         seja maior, o mesmo n�mero. O retorno � em String.
	 */
	private static String formatarDoisDigitos(int valor) {
		return (valor < 10 ? ("0" + valor) : (valor + ""));
	}

	public static boolean isLongValid(String longStr) {
		try {
			Long.parseLong(longStr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean isDoubleValid(String doubleStr) {
		try {
			new Double(doubleStr.replace(",", "."));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * Retorna o valor de uma propriedade do arquivo resources.properties.
	 * 
	 * @param propriedade
	 *            em String.
	 * @return O valor da propriedade em String.
	 */
	public static String valorPropriedade(String propriedade) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle(facesContext.getApplication().getMessageBundle());
		try {
			return bundle.getString(propriedade);
		} catch (MissingResourceException e) {
			logErroComException("GenericUtil", e);
			return "";
		}
	}

	/**
	 * Lista de string contendo m�s e ano no formato 201601 em oredem decrescente
	 * onde o primeiro item � o m�s e ano atual e o �ltimo item � o primeiro m�s de
	 * seis anos atr�s no formato 201601.
	 * 
	 * @return Lista com todos os meses e anos em String do m�s atual at� seis anos
	 *         atr�s.
	 */
	public static List<String> comboAnoMes() {
		List<String> calendar = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		String ano = "";
		String mes = "";
		int mesAtual = cal.get(Calendar.MONTH);
		for (int i = 0; i <= 6; i++) {
			ano = "" + (cal.get(Calendar.YEAR) - i);
			for (int v = 12; v > 0; v--) {
				if (i == 0 && v == 12) {
					mes = "" + mesAtual;
					v = mesAtual + 1;
				}
				mes = formatarDoisDigitos(v);
				calendar.add(ano + mes);
			}
		}
		return calendar;
	}

	public static String dataParaString(Date date) {
		return dataParaString(date, "dd/MM/yyyy");
	}
	public static String dataParaString(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	public static Date stringParaData(String date) {
		try {
			if (!date.equals("9999999999"))
				return new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Diferen�a entre datas string no formato 201601
	 * 
	 * @param Duas
	 *            datas em string no formato 201601
	 * @return diferen�a de meses em inteiro
	 */
	public static int diferencaEntreDatas(String dateInicial, String dateFinal) {
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(dateInicial.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(dateInicial.substring(4, 6)));
		cal2.set(Calendar.YEAR, Integer.parseInt(dateFinal.substring(0, 4)));
		cal2.set(Calendar.MONTH, Integer.parseInt(dateFinal.substring(4, 6)));
		int diffYear = cal2.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		return diffYear * 12 + cal2.get(Calendar.MONTH) - cal.get(Calendar.MONTH);
	}
	
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true);
	}

	public static boolean validaFieldVazio(String field) {
		return StringUtils.isBlank(field);
	}

	public static Map<Long, String> convertStringToHash(String value) {
		value = value.substring(1, value.length() - 1);
		String[] keyValuePairs = value.split("(?<=\\, )(?=\\d)");
		Map<Long, String> map = new HashMap<>();

		for (String pair : keyValuePairs) {
			pair = pair.replace("{", "").replace(",", "").replace("}", "");
			String[] entry = pair.split("=");
			map.put(Long.parseLong(entry[0].trim()), entry[1].trim());
		}
		return map;
	}

	public static Date addDaysToDate(boolean flagDiasUteis, Date date, int ndias) {
		Calendar proxCal;
		if(flagDiasUteis == false) {
			proxCal = Calendar.getInstance();
			proxCal.setTime(date);
			proxCal.add(Calendar.DAY_OF_MONTH, ndias);
			return proxCal.getTime();
		}
		//a partir daqui são apenas os dias uteis
		proxCal = Calendar.getInstance();
		proxCal.setTime(date); 
		List<Calendar> holidays = getListOfHolidays();
		for(int i =0;i<ndias;) {
			proxCal.add(Calendar.DAY_OF_MONTH, 1);
			if(isUtilDay(proxCal, holidays)==false)
				continue;
			i++;
		}
		return proxCal.getTime();
	}
	public static String dgtVerificadorNrOrdem(String nrOrdem) {
		long[] n = IntStream.range(0, nrOrdem.length())
				.mapToLong(i -> Long.parseLong(String.valueOf(nrOrdem.toCharArray()[i]))).toArray();
		long vlDigito = 0;
		for (int pos = 1; pos < 7; pos++) {
			Double primeiraParte = (1 + pos + 7 * Math.floor(1 / 7));
			long segundaParte = n[n.length - pos] * primeiraParte.longValue();
			vlDigito = vlDigito + segundaParte;
		}
		return String.valueOf(Math.floorMod(Math.floorMod(vlDigito * 10, 11), 10));
	}

	public static BigDecimal formataMoeda(String valor) {
		valor = valor.replace(".", "").replace(",", ".");
		return new BigDecimal(valor);
	}
	
//	public static BigDecimal stringToBigDecimal(String value) {
//		
//		try {
//			
//			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//			symbols.setGroupingSeparator(',');
//			symbols.setDecimalSeparator('.');
//			String pattern = "#,##0.0#";
//			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
//			decimalFormat.setParseBigDecimal(true);
//		
//			return (BigDecimal) decimalFormat.parse(value);
//		
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//	}

	public static String cleanChr(String valor) {
		valor = valor.replace(".", "").replace(",", "").replace("-", "").replace("/", "");
		return valor;
	}
	
	public static String yyyyMmToMmYY(String str) {
		return str.substring(4).concat(str.substring(2, 4));
	}

	public static Calendar getCalendarFromAAAAMM(String AAAAMM) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(AAAAMM.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(AAAAMM.substring(4, 6))-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal;
	}

	public static boolean isPossibleAlterQuota(Long cdAnoMesParcela) {
		if(cdAnoMesParcela==null)
			return false;
		Calendar now = Calendar.getInstance();
		Calendar dataParcela = getCalendarFromAAAAMM(cdAnoMesParcela.toString());
		if(dataParcela.before(now))
			return false;		
		int nowYear = now.get(Calendar.YEAR);
		int parcelaYear = dataParcela.get(Calendar.YEAR);
		int nowMonth = now.get(Calendar.MONTH);
		int parcelaMonth = dataParcela.get(Calendar.MONTH);
		int nowDay = now.get(Calendar.DAY_OF_MONTH);
		if(nowYear == parcelaYear && nowMonth==parcelaMonth) {
			if(nowDay>=5)
				return false;
		}		
		return true;
	}
	
	public static String abreviarNomes(String str, int tamanho) {
		str = GenericUtil.removerAcentos(str);
		String[] strs = str.split(" ");
		String name = "";
		if (str.length() > tamanho) {
			for (int i = strs.length - 1; i >= 0; i--) {
				String strTem = "";
				if (!(i == strs.length - 1 || i == 0 || strs[i].length() < 3)) {
					strs[i] = WordUtils.initials(strs[i]);
				}

				for (int y = 0; y < strs.length; y++) {
					if (y != strs.length - 1) {
						strTem = strTem.concat(strs[y] + " ");
					} else {
						strTem = strTem.concat(strs[y]);
					}
				}
				if (strTem.length() <= tamanho) {
					name = strTem;
					break;
				}
			}
			return name;
		}
		return str;
	}
	
	public static void addRecoveredAttributes(Map<String, Object> body, RedirectAttributes redirectAttrs) {
		for (Map.Entry<String, Object> entry : body.entrySet()) {
			String key = entry.getKey().toString();
			String value = body.get(key).toString();
			redirectAttrs.addFlashAttribute(key+"Recovered", value);
		}
	}
	public static void addRecoveredAttributes(Map<String, Object> body, Model model) {
		for (Map.Entry<String, Object> entry : body.entrySet()) {
			String key = entry.getKey().toString();
			String value = body.get(key).toString();
			model.addAttribute(key+"Recovered", value);
		}
	}
	public static String printNumberTwoDigits(Object numObj) {
		if(numObj == null)
			return null;
		Double num=null;
		if(numObj.getClass()==BigDecimal.class)
			num = ((BigDecimal)numObj).doubleValue();
		if(num==null) {
			try{
				num = new Double(numObj.toString().replace(",", "."));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return (num==null)? null : String.format("%.2f", num).replace(",", ".");
	}
	public static Double getDoubleTwoDigits(Object numObj) {
		String num = printNumberTwoDigits(numObj);
		return num==null ? null : Double.parseDouble(num);
	}
	public static Long getLongInBody(Map<String, Object> body, String key) {
		try {
			return Long.parseLong(body.get(key).toString());
		} catch (Exception e) {
			return null;
		}
	}
	public static Integer getIntegerInBody(Map<String, Object> body, String key) {
		try {
			return Integer.parseInt(body.get(key).toString());
		} catch (Exception e) {
			return null;
		}
	}
	public static BigDecimal getBigDecimalInBody(Map<String, Object> body, String key) {
		BigDecimal b = new BigDecimal(getDoubleInBody(body, key));
		if(b!=null)
			b = b.setScale(2, RoundingMode.HALF_UP);
		return b;
	}
	public static Double getDoubleInBody(Map<String, Object> body, String key) {
		return getDoubleTwoDigits(body.get(key));
	}
	public static String getStringInBody(Map<String, Object> body, String key) {
		Object ob =  body.get(key);
		if(ob==null)return null;
		return ob.toString();
	}
	public static List<Calendar> getListOfHolidays() {
		List<Calendar> holidays = new ArrayList<Calendar>();
		return holidays;
	}
	public static Long getAttributeLongOfModel(Model model, String key) {
		String attributeOfModel = getAttributeOfModel(model, key);
		try {
			return Long.parseLong(attributeOfModel);
		} catch (Exception e) {
			return null;
		}
	}
	public static String getAttributeOfModel(Model model, String key) {
		try {
			return model.asMap().get(key).toString();
		} catch (Exception e) {
			return null;
		}
	}
	public static boolean isUtilDay(Calendar calDate, List<Calendar> holidays){
		if(calDate.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calDate.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
			return false;
		for(Calendar holiday: holidays)
			if(holiday.get(Calendar.DAY_OF_MONTH) == calDate.get(Calendar.DAY_OF_MONTH) &&
				holiday.get(Calendar.MONTH) == calDate.get(Calendar.MONTH) &&
				holiday.get(Calendar.YEAR) == calDate.get(Calendar.YEAR))
				return false;
		return true;
	}
	public static BigDecimal stringToBigDecimal(String bigDecStr) {
		try{
			return new BigDecimal(getDoubleTwoDigits(bigDecStr));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String removeLeftZeros(String str) {
		if(str==null)
			return null;
		int numZeros = 0;
		for(int i=0;i<str.length();i++)
			if(str.charAt(i) == '0')
				numZeros++;
			else
				break;
		return str.substring(numZeros);
	}
	public static Date getDateInBody(Map<String, Object> body, String string) {
		String dateStr = getStringInBody(body, string);
		Calendar cal = null;
		if(dateStr.isEmpty()==false)
			try{
				String[] split = dateStr.split("-");
				cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(split[2]));
				cal.set(Calendar.MONTH, Integer.parseInt(split[1])-1);
				cal.set(Calendar.YEAR, Integer.parseInt(split[0]));
			}
			catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		if(cal==null)
			return null;
		return cal.getTime();
	}

	public static String reloadPostPage(StatusAcesso acesso, Message msg, Model model, RedirectAttributes redirectAttrs,  String redirectString_sem_acesso, String redirectString_com_acesso) {
		if (acesso != StatusAcesso.PERMITIDO) {
			List<String> errors = new ArrayList<String>();
			errors.add(msg.get(acesso.getStatusAcesso()));
			redirectAttrs.addFlashAttribute("detailedErrors", errors);
			return redirectString_sem_acesso;
		}
		return redirectString_com_acesso;
	}
	
	/**
	 * validaCamposFormumlario : Atualiza valores vindos do formulário pelo map, body para 
	 * os seus respectivos objetos.
	 * @param body 
	 * @param classes
	 * @return Retorna a lista com os objetos com valores alterados vindos do map, em caso 
	 * 			de erro retorna uma lista nula;
	 */
	public static List<?> validaCamposFormumlario(Map<String, Object> body, List<?> classes) {
		
		body.entrySet().stream().forEach(x -> {
			classes.forEach(l -> {
				for (Field f : l.getClass().getDeclaredFields()) {
					if (f.getName().equals(x.getKey())) {
						try {
							f.setAccessible(true);
							if (f.getType().equals(Date.class)) {
								f.set(l, stringParaData((String) x.getValue()));
							} else {
								f.set(l, x.getValue());
							}
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			});
		});
		
		return classes;
	}

	
}
