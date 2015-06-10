package com.ib.api.dde.translation.dde2socket.messages;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.DdeRequestType;

public abstract class DdeMessage extends DdeBaseMessage implements IDdeMessage {	
	
	protected DdeRequest ddeRequest;
	
	public DdeMessage(String topic, int reqId, DdeRequestStatus status) {
		super(topic, reqId, status);
		this.requestId = reqId;
		this.status = status;
	}

	public String getRequestStr() {
		return ddeRequest.getRequestStr();
	}

	public DdeRequestType getType() {
		return ddeRequest.getType();
	}
	
}
