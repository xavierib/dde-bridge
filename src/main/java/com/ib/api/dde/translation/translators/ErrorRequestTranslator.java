package com.ib.api.dde.translation.translators;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.DdeRequestType;
import com.ib.api.dde.translation.dde2socket.messages.DdeRequest;
import com.ib.api.dde.translation.dde2socket.messages.IDdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.error.ErrorAllRequest;
import com.ib.api.dde.translation.dde2socket.messages.error.ErrorCodeRequest;
import com.ib.api.dde.translation.dde2socket.messages.error.ErrorIdRequest;
import com.ib.api.dde.translation.dde2socket.messages.error.ErrorMessageRequest;
import com.ib.api.dde.translation.socket2dde.messages.ErrorEvent;
import com.ib.api.dde.translation.socket2dde.messages.TwsMessage;

public class ErrorRequestTranslator extends BaseRequestTranslator {
	
	private static final Logger LOG = Logger.getLogger(ErrorRequestTranslator.class);
	
	public static final String ERROR_ID = "id";
	public static final String ERROR_CODE = "errorCode";
	public static final String ERROR_MSG = "errorMsg";
	public static final String ERROR_ALL = "all";
	

	@Override
	public IDdeMessage translate(String topic, String request) {
		//Since all error requests have the same topic we can simply use ERROR_ID to translate the message
		//We however need to have different error enums to handle the request cleanly
		if(topic.equals(DdeRequestType.ERROR_ID.getTopic())) {
			return translateErrorRequest(request);
		}
		return null;
	}

	@Override
	public IDdeMessage translate(TwsMessage response) {
		switch(response.getResponseType()) {
		case ERROR_ALL:
			ErrorEvent errorEvent = (ErrorEvent)response;
			
			List<DdeRequest> broadcastRequests = new ArrayList<DdeRequest>();
			broadcastRequests.add(getDdeRequest(DdeRequestType.ERROR_ALL, errorEvent.getRequestId(), false));
			broadcastRequests.add(getDdeRequest(DdeRequestType.ERROR_ID, errorEvent.getRequestId(), false));			
			broadcastRequests.add(getDdeRequest(DdeRequestType.ERROR_CODE, errorEvent.getRequestId(), false));						
			broadcastRequests.add(getDdeRequest(DdeRequestType.ERROR_MESSAGE, errorEvent.getRequestId(), false));
			
			if(errorEvent.getRequestId() > 0) {
				broadcastRequests.add(getDdeRequest(DdeRequestType.ERROR_ALL, errorEvent.getRequestId(), true));
				broadcastRequests.add(getDdeRequest(DdeRequestType.ERROR_ID, errorEvent.getRequestId(), true));
				broadcastRequests.add(getDdeRequest(DdeRequestType.ERROR_CODE, errorEvent.getRequestId(), true));
				broadcastRequests.add(getDdeRequest(DdeRequestType.ERROR_MESSAGE, errorEvent.getRequestId(), true));
			}
			
			return new ErrorAllRequest(broadcastRequests, errorEvent.getRequestId(), DdeRequestStatus.RECEIVED);
		}
		return null;
	}
		
	
	private DdeRequest getDdeRequest(DdeRequestType type, int reqId, boolean includeId) {
		String requestString = null;
		switch(type) {
		case ERROR_ALL:
			requestString = ERROR_ALL;
			break;
		case ERROR_ID:
			requestString = ERROR_ID;
			break;
		case ERROR_CODE:
			requestString = ERROR_CODE;
			break;
		case ERROR_MESSAGE:
			requestString = ERROR_MSG;
			break;
		}
		
		if(requestString != null) {
			if(includeId)
				 requestString += (DDE_REQUEST_SEPARATOR + ID + reqId);		
			return new DdeRequest(requestString, type);
		}
		LOG.warn("Could not translate to DdeRequest: ["+type+"]");
		return null;
	}
	
	private IDdeMessage translateErrorRequest(String request) {
		String errorFragment = null;
		int requestId = -1;
		if(request.contains(DDE_REQUEST_SEPARATOR)) {
			String[] messageTokens = request.split(DDE_REQUEST_SEPARATOR_PARSE);
			errorFragment = messageTokens[0];
			requestId = parseRequestId(messageTokens[1]);
		} else {
			errorFragment = request;
		}
		
		switch(errorFragment) {
		case ERROR_ID:
			return new ErrorIdRequest(request, requestId, DdeRequestStatus.PARSED);
		case ERROR_CODE:
			return new ErrorCodeRequest(request, requestId, DdeRequestStatus.PARSED);
		case ERROR_MSG:
			return new ErrorMessageRequest(request, requestId, DdeRequestStatus.PARSED);
		case ERROR_ALL:
			List<DdeRequest> requests = new ArrayList<DdeRequest>();
			requests.add(getDdeRequest(DdeRequestType.ERROR_ALL, requestId, requestId > 0 ? true : false));
			return new ErrorAllRequest(requests, requestId, DdeRequestStatus.PARSED);
		}
		return null;
	}

}
