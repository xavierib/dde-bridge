package com.ib.api.impl.twsservice;

public class ErrorMessage {

	private int requesId;
	private int errorCode;
	private String errorMessage;
	
	public ErrorMessage(int requesId, int errorCode, String errorMessage) {
		this.requesId = requesId;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public int getRequesId() {
		return requesId;
	}
	public void setRequesId(int requesId) {
		this.requesId = requesId;
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
	
	public String toString() {
		return requesId + ";" + errorCode + ";" + errorMessage;
	}
}
