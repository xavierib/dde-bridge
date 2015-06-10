package com.ib.api.dde.translation.dde2socket.messages;

import com.ib.api.dde.translation.DdeRequestType;

public class DdeRequest {

	private String requestStr;
	private DdeRequestType type;
	
	public DdeRequest(String requestStr, DdeRequestType type) {
		this.requestStr = requestStr;
		this.type = type;
	}

	public String getRequestStr() {
		return requestStr;
	}

	public void setRequestStr(String requestStr) {
		this.requestStr = requestStr;
	}

	public DdeRequestType getType() {
		return type;
	}

	public void setType(DdeRequestType type) {
		this.type = type;
	}
	
	
}
