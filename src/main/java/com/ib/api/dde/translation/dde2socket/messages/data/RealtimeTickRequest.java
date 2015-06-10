package com.ib.api.dde.translation.dde2socket.messages.data;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.DdeRequestType;
import com.ib.api.dde.translation.dde2socket.messages.DdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.DdeRequest;

public class RealtimeTickRequest extends DdeMessage {
	
	private int tickType;
	//The tick type string representation
	private String ddeType;

	public RealtimeTickRequest(String requestString, int reqId, DdeRequestStatus status, int tickType, String ddeType) {
		super(DdeRequestType.TICK.getTopic(), reqId, status);
		this.tickType = tickType;
		this.ddeType = ddeType;
		this.ddeRequest = new DdeRequest(requestString, DdeRequestType.TICK);
	}

	public int getTickType() {
		return tickType;
	}

	public void setTickType(int tickType) {
		this.tickType = tickType;
	}

	public String getDdeType() {
		return ddeType;
	}

	public void setDdeType(String ddeType) {
		this.ddeType = ddeType;
	}
	
	

}
