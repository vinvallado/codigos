package br.mil.fab.consigext.util;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class CalculoCet {
	public float confereCet(int nParcelas, float valorLiberado, float valorPrestacao, float cetConferir, Date dataPrimeiraParcela, Date dataLiberacao) {
		float calculo = 0;
		for(int i=1;i<=nParcelas;i++) {
			int nDias = daysAfterLiberation(dataLiberacao, dataPrimeiraParcela, i);
			float parcelaCalculo = (float)(valorPrestacao/Math.pow(1+cetConferir,  nDias/365.0));
			calculo+=parcelaCalculo;
		}
		return calculo-valorLiberado;
	}
	private int daysAfterLiberation(Date dataLiberacao, Date dataPrimeiraParcela, int nParcela) {
		LocalDate localDate = dataPrimeiraParcela.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		int month = localDate.getMonthValue()+nParcela-1;
		while(month>12) {
			month-=12;
			year++;
		}
		Date payment = getDate(localDate.getDayOfMonth(), month, year);
		return (int)((payment.getTime() - dataLiberacao.getTime()) / (1000 * 60 * 60 * 24));
	}
	public Date getDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
	//para manutenção do processamento, trocar os cets iniciais e o passo h
	public float calculaCet_Methods(int nParcelas, float valorLiberado, float valorPrestacao, Date dataPrimeiraParcela, Date dataLiberacao, Method method, float passo) throws Exception {
		float cet0 = 0.3f;
		float cetMinus1 = 0.4f;
		float cet = (float) method.invoke(this, new Object[] {cet0, cetMinus1, nParcelas, valorLiberado, valorPrestacao, dataPrimeiraParcela, dataLiberacao});
		while(cet==-1) {
			cetMinus1-=passo;
			cet0-=passo;
			if(cet0<=0) 
				break;
			cet = (float) method.invoke(this, new Object[] {cet0, cetMinus1, nParcelas, valorLiberado, valorPrestacao, dataPrimeiraParcela, dataLiberacao});
		}
		if(cet0<=0) {
			cet0=0.5f;
			cetMinus1=0.6f;
			cet = (float) method.invoke(this, new Object[] {cet0, cetMinus1, nParcelas, valorLiberado, valorPrestacao, dataPrimeiraParcela, dataLiberacao});
			while(cet==-1) {
				cet0+=passo;
				cetMinus1+=passo;
				if(cet0>10) 
					break;
				cet = (float) method.invoke(this, new Object[] {cet0, cetMinus1, nParcelas, valorLiberado, valorPrestacao, dataPrimeiraParcela, dataLiberacao});
			}
		}
		return cet;
	}
	public float calculaCet_Newton_Raphson(Float cet0, Float cetMinus1, Integer nParcelas, Float valorLiberado, Float valorPrestacao, Date dataPrimeiraParcela, Date dataLiberacao) {		
		float cet=cet0;
		boolean flag = Math.abs(confereCet(nParcelas, valorLiberado, valorPrestacao, cet, dataPrimeiraParcela, dataLiberacao))<0.0001;	
		int nTimes=0;
		while (flag==false) {
			nTimes++;
			cet = nextCet_Newton_Raphson(cet, nParcelas, valorLiberado, valorPrestacao, dataPrimeiraParcela, dataLiberacao);			
			flag = Math.abs(confereCet(nParcelas, valorLiberado, valorPrestacao, cet, dataPrimeiraParcela, dataLiberacao))<0.0001;
			if(nTimes>20)
				return -1;
		}
		return cet;
	}
	public float cetMensal(int n, float fc0, float fcj, Date d0, Date dj0) {
        float cet = 0f;

        while(true) {

            float total = 0;

            for(int j = 0; j < n; j++) {
                total += fcj / Math.pow(1.0 + cet, j+1);
            }

            cet += 0.00001;

            if(cet >= 10000) {
                return -1;
            }
            if(total - fc0 <= 0) {
                break;
            }
            else {
                cet *= total / fc0;
            }
        }
        return cet;
    }
	public float calculaCet_Secante(Float cet0,  Float cetMinus1, Integer nParcelas, Float valorLiberado, Float valorPrestacao, Date dataPrimeiraParcela, Date dataLiberacao) {				
		boolean flag = Math.abs(confereCet(nParcelas, valorLiberado, valorPrestacao, cet0, dataPrimeiraParcela, dataLiberacao))<0.0001;	
		int nTimes=0;
		while (flag==false) {
			nTimes++;
			float auxMinus1= cetMinus1;
			cetMinus1 = cet0;
			cet0 = nextCet_Secante(cet0, auxMinus1, nParcelas, valorLiberado, valorPrestacao, dataPrimeiraParcela, dataLiberacao);			
			flag = Math.abs(confereCet(nParcelas, valorLiberado, valorPrestacao, cet0, dataPrimeiraParcela, dataLiberacao))<0.0001;
			if(nTimes>20)
				return -1;
		}
		return cet0;
	}
	private float nextCet_Secante(float cet0, float cetMinus1, int nParcelas, float valorLiberado, float valorPrestacao, Date dataPrimeiraParcela, Date dataLiberacao) {
		float fx0= confereCet(nParcelas, valorLiberado, valorPrestacao, cet0, dataPrimeiraParcela, dataLiberacao);
		float fxMinus1= confereCet(nParcelas, valorLiberado, valorPrestacao, cetMinus1, dataPrimeiraParcela, dataLiberacao);		
		return cet0 - fx0*(cet0 - cetMinus1)/(fx0-fxMinus1);
	}
	private float nextCet_Newton_Raphson(float cet, int nParcelas, float valorLiberado, float valorPrestacao, Date dataPrimeiraParcela, Date dataLiberacao) {
		float fx= confereCet(nParcelas, valorLiberado, valorPrestacao, cet, dataPrimeiraParcela, dataLiberacao);
		float der_fx= getDerivativeValue(cet, nParcelas, valorLiberado, valorPrestacao, dataPrimeiraParcela, dataLiberacao);
		return cet - fx/der_fx;
	}
	private float getDerivativeValue(float cet, int nParcelas, float valorLiberado, float valorPrestacao, Date dataPrimeiraParcela, Date dataLiberacao) {
		float h = 0.0001f;
		float f_x_h=confereCet(nParcelas, valorLiberado, valorPrestacao, cet+h, dataPrimeiraParcela, dataLiberacao);
		float f_x = confereCet(nParcelas, valorLiberado, valorPrestacao, cet, dataPrimeiraParcela, dataLiberacao);
		return (f_x_h-f_x)/h;
	}

}
