package com.ib.api.dde.translation.dde2socket.messages;

import java.util.List;

import com.ib.api.dde.translation.DdeRequestStatus;

//Often from a single TWS event we will need to dispatch multiple messages to each of the formulas extracting each piece of the event
public abstract class DdeMultiMessage extends DdeBaseMessage implements IDdeMessage {
	
	protected List<DdeRequest> ddeRequests;

	public DdeMultiMessage(String topic, int requestId, DdeRequestStatus status) {
		super(topic, requestId, status);
	}

	public List<DdeRequest> getDdeRequests() {
		return ddeRequests;
	}
	
	//Messages from the TWS to the DDE can be multiple
	//Messages from DDE to TWS cannot, hence do we need to do this?
	//public abstract DdeMessage toDdeMessage();

	
}
