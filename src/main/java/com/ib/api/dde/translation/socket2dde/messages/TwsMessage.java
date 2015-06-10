package com.ib.api.dde.translation.socket2dde.messages;

import com.ib.api.dde.translation.DdeRequestType;

public abstract class TwsMessage {

	protected DdeRequestType responseType;

	public DdeRequestType getResponseType() {
		return responseType;
	}

	public void setResponseType(DdeRequestType responseType) {
		this.responseType = responseType;
	}
	
	
	
}
