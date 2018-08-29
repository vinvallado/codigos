package br.mil.fab.consigext.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.mil.fab.consigext.util.GenericUtil;

@Component
public class MoneyConverter implements Converter<String, BigDecimal>{

	@Override
	public BigDecimal convert(final String money) {
		if (money.indexOf(",") != -1) {
			return GenericUtil.formataMoeda(money);
		} else {
			return new BigDecimal(money);
		}
	}

}
