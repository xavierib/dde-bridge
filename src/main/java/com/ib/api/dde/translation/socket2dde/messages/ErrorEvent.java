package com.ib.api.dde.translation.socket2dde.messages;

import com.ib.api.dde.translation.DdeRequestType;

public class ErrorEvent extends TwsMessage {

	private int requestId;
	private int errorCode;
	private String errorMessage;
	
	public ErrorEvent(int requestId, int errorCode, String errorMessage) {
		this.responseType = DdeRequestType.ERROR_ALL;
		this.requestId = requestId;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
