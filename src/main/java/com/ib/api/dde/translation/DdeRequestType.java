package com.ib.api.dde.translation;

public enum DdeRequestType {
	
	ERROR_ID("err"), ERROR_CODE("err"), ERROR_MESSAGE("err"), ERROR_ALL("err"), 
	RT_DATA("tik"), TICK("tik"), CANCEL_RT_DATA("cancel"), ALL("");
	
	private String topic;
	
	private DdeRequestType(String topic) {
		this.topic = topic;
	}
	
	public String getTopic() {
		return topic;
	}
}
