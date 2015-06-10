package com.ib.api.impl.twsservice;

import java.util.HashMap;
import java.util.Map;

import com.ib.api.dde.translation.dde2socket.messages.DdeMessage;

public class TickData {

	//Original DDE request (contract, id, etc)
	private DdeMessage ddeRequest;
	//Each of the price requests
	private Map<Integer, Object> data = new HashMap<Integer, Object>();
	
	public TickData(DdeMessage request){
		this.ddeRequest = request;
	}
		
	public DdeMessage getDdeRequest() {
		return ddeRequest;
	}

	public Object get(int tickType){
		if(data.containsKey(tickType)){
			return data.get(tickType);
		}
		return -1;
	}
	
	public void set(int tickType, Object value) {
		data.put(tickType, value);
	}
	
}
