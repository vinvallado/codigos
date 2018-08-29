package br.ccasj.sisauc.framework.utils;

import java.util.Random;

public class EntidadeGenericaUtils {

	// Gambiarra para poder excluir entidades de collections sem precisar
	// sobrescrever os métodos hashcode e equals de entidades
	// IMPORTANTE: validar antes de salvar entidades com id negativo
	public static final int gerarIdNegativo() {
		int random = new Random().nextInt(1000000);
		return random * -1;
	}

}
