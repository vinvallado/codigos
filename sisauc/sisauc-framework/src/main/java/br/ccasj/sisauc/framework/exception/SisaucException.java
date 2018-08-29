package br.ccasj.sisauc.framework.exception;

public class SisaucException extends Exception {

	private static final long serialVersionUID = -688459126850643800L;

	public SisaucException(String string) {
		super(string);
	}
	
	public SisaucException() {
		super();
	}

}
