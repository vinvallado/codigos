package br.mil.fab.consigext.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class CalculoUtil {
	public static double square12(double n) {
		n=Math.cbrt(n);
		n=Math.sqrt(n);
		return Math.sqrt(n);
	}
	
	public static BigDecimal cetMensalToAnual(double cetMen) {
		cetMen=cetMen/100;
		return new BigDecimal((Math.pow(1+cetMen,12)-1)*100).setScale(2, BigDecimal.ROUND_HALF_EVEN);	
	}
	
	public static BigDecimal cetAnualToMensal(double cetAn) {
		cetAn=cetAn/100;
		return new BigDecimal((square12(1+cetAn)-1)*100);
	}
	
	
	public static BigDecimal valorParcela(BigDecimal taxa, long nrParcelas, BigDecimal vlSolicitado) {
		BigDecimal taxaPorCento = taxa
				.divide(new BigDecimal(100));
		BigDecimal primeiraparte =	new BigDecimal(1)
				.subtract(new BigDecimal(
						Math.pow(taxaPorCento.doubleValue() + 1,(nrParcelas * -1))));
		BigDecimal segundaParte = new BigDecimal(0);
		if(primeiraparte.equals(new BigDecimal(0))==false)
			segundaParte = taxaPorCento
				.divide(primeiraparte, 4, RoundingMode.HALF_EVEN);
		return segundaParte
				.multiply(vlSolicitado)
				.setScale(2,RoundingMode.HALF_EVEN);

	}
	public static float calculaCetMensal(int nParcelas, float valorLiberado, float valorPrestacao, Date dataPrimeiraParcela, Date dataLiberacao) throws Exception {
		Class[] parameterTypes = new Class[] {Float.class, Float.class, Integer.class, Float.class, Float.class, Date.class, Date.class};
		CalculoCet calcCet = new CalculoCet();	
		Method[] methods = new Method[2];
		Method method1 = CalculoCet.class.getMethod("calculaCet_Newton_Raphson", parameterTypes);
		methods[0]=method1;
		Method method2 = CalculoCet.class.getMethod("calculaCet_Secante", parameterTypes);				
		methods[1]=method2;	
		float cet=-1;
		float passo = 0.05f;
		for(Method m: methods) {
			cet = calcCet.calculaCet_Methods(nParcelas, valorLiberado, valorPrestacao, dataPrimeiraParcela, dataLiberacao, m, passo);
			if(cet!=-1) {
				cet = (float)(square12(1+cet)-1);
				break;
			}
		}
		float cetMensal = calcCet.cetMensal(nParcelas, valorLiberado, valorPrestacao, dataLiberacao, dataPrimeiraParcela);
		if(cetMensal==-1)
			return cet;
		if(cet==-1)
			return cetMensal;
		float confereMensal =  Math.abs(calcCet.confereCet(nParcelas, valorLiberado, valorPrestacao, cetMensal ,dataPrimeiraParcela, dataLiberacao));
		float confereMethod = Math.abs(calcCet.confereCet(nParcelas, valorLiberado, valorPrestacao, cet ,dataPrimeiraParcela, dataLiberacao));
		if(confereMensal < confereMethod)
			cet = cetMensal;
		return cet*100;
	}
	public static boolean isDoubleEquals(Double d1, Double d2) {
		if (Math.abs(d1 - d2) < 0.02)
			return true;
		return false;
	}
}
