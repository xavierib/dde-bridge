package com.ib.api.dde.translation.dde2socket.messages;

import com.ib.api.dde.translation.DdeRequestType;

public interface IDdeMessage {

	DdeRequestType getType();
	
	int getRequestId();
}
