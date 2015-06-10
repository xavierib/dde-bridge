package com.ib.api.dde.translation.dde2socket.messages.error;

import java.util.List;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.DdeRequestType;
import com.ib.api.dde.translation.dde2socket.messages.DdeMultiMessage;
import com.ib.api.dde.translation.dde2socket.messages.DdeRequest;

public class ErrorAllRequest extends DdeMultiMessage {

	public ErrorAllRequest(List<DdeRequest> requests, int reqId, DdeRequestStatus status) {
		super(DdeRequestType.ERROR_ALL.getTopic(), reqId, status);
		this.ddeRequests = requests;
	}

	@Override
	public DdeRequestType getType() {
		return DdeRequestType.ERROR_ALL;
	}

	
}
