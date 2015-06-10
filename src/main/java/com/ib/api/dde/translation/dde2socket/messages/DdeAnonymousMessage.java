package com.ib.api.dde.translation.dde2socket.messages;

//When broadcasting to multiple possible formulas, it is convenient to have an object with the absolute minimum
//information to deliver the data (i.e. see ErrorAllEvent).
public class DdeAnonymousMessage extends DdeMessage {

	public DdeAnonymousMessage(String topic, String requestString) {
		super(topic, -1, null);
		this.topic = topic;
		this.ddeRequest = new DdeRequest(requestString, null);
	}

}
