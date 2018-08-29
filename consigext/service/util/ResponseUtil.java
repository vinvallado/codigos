package br.mil.fab.consigext.service.util;

public class ResponseUtil {
	
	private String message;
	private Object data;

	public ResponseUtil() {
		super();
		this.message = null;
		this.data = null;
	}
	
	public ResponseUtil(Object data) {
		super();
		this.data = data;
	}
	
	public ResponseUtil(String message) {
		super();
		this.message = message;
	}

	public ResponseUtil(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
