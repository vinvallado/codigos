package br.ccasj.sisauc.framework.faces;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;



public class FacesExceptionHandlerFactory extends
		ExceptionHandlerFactory {
	
	private ExceptionHandlerFactory exceptionHandlerFactory;

	public FacesExceptionHandlerFactory(
			ExceptionHandlerFactory exceptionHandlerFactory) {
		this.exceptionHandlerFactory = exceptionHandlerFactory;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler result = exceptionHandlerFactory.getExceptionHandler();
		result = new FacesExceptionHandler(result);
		return result;
	}
}
