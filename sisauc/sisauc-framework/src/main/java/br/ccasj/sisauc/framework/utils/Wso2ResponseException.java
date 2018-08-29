package br.ccasj.sisauc.framework.utils;

public class Wso2ResponseException extends Throwable {

	private static final long serialVersionUID = -748941603617243585L;

	public Integer responseErrorCode;
	
	public String responseErrorReaseon;

	public Wso2ResponseException(Integer responseErrorCode, String responseErrorReaseon) {
		super();
		this.responseErrorCode = responseErrorCode;
		this.responseErrorReaseon = responseErrorReaseon;
	}
	
	@Override
	public String getMessage() {
		return responseErrorReaseon;
	}
	
	@Override
	public String toString() {
		return "Wso2ResponseException [responseErrorCode=" + responseErrorCode + ", responseErrorReaseon="
				+ responseErrorReaseon + "]";
	}	
	
	
	public Integer getResponseErrorCode() {
		return responseErrorCode;
	}

	public void setResponseErrorCode(Integer responseErrorCode) {
		this.responseErrorCode = responseErrorCode;
	}

	public String getResponseErrorReaseon() {
		return responseErrorReaseon;
	}

	public void setResponseErrorReaseon(String responseErrorReaseon) {
		this.responseErrorReaseon = responseErrorReaseon;
	}

}
