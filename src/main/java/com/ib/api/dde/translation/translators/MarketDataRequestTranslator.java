package com.ib.api.dde.translation.translators;

import com.ib.api.Contract;
import com.ib.api.TickType;
import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.DdeRequestType;
import com.ib.api.dde.translation.dde2socket.messages.DdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.IDdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.data.RealtimeDataCancel;
import com.ib.api.dde.translation.dde2socket.messages.data.RealtimeDataRequest;
import com.ib.api.dde.translation.dde2socket.messages.data.RealtimeTickRequest;
import com.ib.api.dde.translation.socket2dde.messages.RealtimeDataEvent;
import com.ib.api.dde.translation.socket2dde.messages.TwsMessage;

//This class is used to translate any market data requests: real time, historical, RT bars
public class MarketDataRequestTranslator extends BaseRequestTranslator {
	
	@Override
	public IDdeMessage translate(String topic, String request) {
		if(topic.equals(DdeRequestType.TICK.getTopic())){
			return translateRealtimeDataRequest(request);			
		}
		return null;
	}
	
	@Override
	public IDdeMessage translate(TwsMessage twsMessage) {
		switch(twsMessage.getResponseType()) {
		case TICK:
			RealtimeDataEvent dataEvent = (RealtimeDataEvent)twsMessage;
			String ddeRequestString = ID+dataEvent.getTickerId()+DDE_REQUEST_SEPARATOR+TickType.getField(dataEvent.getField());
			return new RealtimeTickRequest(ddeRequestString, dataEvent.getTickerId(), DdeRequestStatus.RECEIVED, 
					dataEvent.getField(), TickType.getField(dataEvent.getField()));
		}
		return null;
	}
	
	private DdeMessage translateRealtimeDataRequest(String request) {
		String[] messageTokens = request.split(DDE_REQUEST_SEPARATOR_PARSE);
		int requestId = parseRequestId(messageTokens[0]);		
		switch(messageTokens[1]){
		case DDE_REQUEST:
			Contract contract = parseContract(messageTokens[2], 1);
			return new RealtimeDataRequest(request, requestId, DdeRequestStatus.PARSED, contract);
		case DDE_CANCEL:
			return new RealtimeDataCancel(request, requestId, DdeRequestStatus.PARSED);
		default:
			int tickType = TickType.getType(messageTokens[1]);
			DdeRequestStatus status = null;
			if(tickType > 0)
				status = DdeRequestStatus.PARSED;
			else 
				status = DdeRequestStatus.UNAVAILABLE;
			return new RealtimeTickRequest(request, requestId, status, tickType, messageTokens[1]);
		}
	}

	
}
