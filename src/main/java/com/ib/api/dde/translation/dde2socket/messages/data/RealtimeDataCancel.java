package com.ib.api.dde.translation.dde2socket.messages.data;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.DdeRequestType;
import com.ib.api.dde.translation.dde2socket.messages.DdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.DdeRequest;

public class RealtimeDataCancel extends DdeMessage {

	public RealtimeDataCancel(String requestString, int reqId, DdeRequestStatus status) {
		super(DdeRequestType.CANCEL_RT_DATA.getTopic(), reqId, status);
		this.ddeRequest = new DdeRequest(requestString, DdeRequestType.CANCEL_RT_DATA);
	}

}
