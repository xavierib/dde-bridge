package com.ib.api.dde.translation.dde2socket.messages.error;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.DdeRequestType;
import com.ib.api.dde.translation.dde2socket.messages.DdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.DdeRequest;

public class ErrorMessageRequest extends DdeMessage {

	public ErrorMessageRequest(String requestString, int reqId, DdeRequestStatus status) {
		super(DdeRequestType.ERROR_MESSAGE.getTopic(), reqId, status);
		this.ddeRequest = new DdeRequest(requestString, DdeRequestType.ERROR_MESSAGE);
	}

}
