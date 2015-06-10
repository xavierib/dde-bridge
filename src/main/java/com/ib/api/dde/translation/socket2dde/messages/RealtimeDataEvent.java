package com.ib.api.dde.translation.socket2dde.messages;

import com.ib.api.dde.translation.DdeRequestType;

public class RealtimeDataEvent extends TwsMessage {

	private int tickerId;
	private int field;
	private Object value;
	
	public RealtimeDataEvent(int tickerId, int field, Object value) {
		this.responseType = DdeRequestType.TICK;
		this.tickerId = tickerId;
		this.field = field;
		this.value = value;
	}
	
	
	public int getTickerId() {
		return tickerId;
	}
	public void setTickerId(int tickerId) {
		this.tickerId = tickerId;
	}
	public int getField() {
		return field;
	}
	public void setField(int field) {
		this.field = field;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	
	
}
