package com.ib.api.dde.translation.dde2socket.messages;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.impl.twsservice.ErrorMessage;

public abstract class DdeBaseMessage {

	protected int requestId;
	protected String topic;
	//What is immediately sent back to requesting client.
	protected DdeRequestStatus status;
	protected ErrorMessage errorMessage;
	
	public DdeBaseMessage(String topic, int requestId, DdeRequestStatus status) {
		this.topic = topic;
		this.requestId = requestId;
		this.status = status;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public DdeRequestStatus getStatus() {
		return status;
	}

	public void setStatus(DdeRequestStatus status) {
		this.status = status;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
