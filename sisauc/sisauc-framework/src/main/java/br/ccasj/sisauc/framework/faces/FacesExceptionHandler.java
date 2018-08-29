package br.ccasj.sisauc.framework.faces;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.commons.lang.exception.ExceptionUtils;

import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

public class FacesExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler exceptionHandler;

	public FacesExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.exceptionHandler;
	}

	@Override
	public void handle() throws FacesException {

		Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			List<Throwable> causes = Arrays.asList(ExceptionUtils.getThrowables(context.getException()));

			for (Throwable cause : causes) {
				if (cause instanceof SystemRuntimeException) {
					SystemRuntimeException exception = (SystemRuntimeException) cause;
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, exception.getMessage(),
									exception.getCause() != null ? exception.getCause().toString() : ""));

					FacesContext.getCurrentInstance().renderResponse();
					i.remove();
				}
			}

		}
		getWrapped().handle();
	}

}
