package com.ib.api.dde.translation.dde2socket.messages.data;

import com.ib.api.Contract;
import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.DdeRequestType;
import com.ib.api.dde.translation.dde2socket.messages.DdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.DdeRequest;

public class RealtimeDataRequest extends DdeMessage {

	private Contract contract;
	private String genericTicks;
	private boolean snapshot;
	
	public RealtimeDataRequest(String requestStr, int requestId, DdeRequestStatus status, Contract contract) {
		super(DdeRequestType.RT_DATA.getTopic(), requestId, status);
		this.contract = contract;
		this.ddeRequest = new DdeRequest(requestStr, DdeRequestType.RT_DATA);
	}
	
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public String getGenericTicks() {
		return genericTicks;
	}
	public void setGenericTicks(String genericTicks) {
		this.genericTicks = genericTicks;
	}
	public boolean isSnapshot() {
		return snapshot;
	}
	public void setSnapshot(boolean snapshot) {
		this.snapshot = snapshot;
	}
	
	
	
}
