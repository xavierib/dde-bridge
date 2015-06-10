package com.ib.api.dde;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.dde2socket.messages.DdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.IDdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.data.RealtimeDataCancel;
import com.ib.api.dde.translation.dde2socket.messages.data.RealtimeDataRequest;
import com.ib.api.dde.translation.dde2socket.messages.data.RealtimeTickRequest;
import com.ib.api.impl.EWrapperImpl;
import com.ib.api.impl.twsservice.ErrorMessage;
import com.ib.api.impl.twsservice.TickData;

public class TwsService {
	
	private static final Logger LOG = Logger.getLogger(TwsService.class);
	
	private String host;
	private int port;
	private int clientId;
	private SocketDdeBridge bridge;
	private EWrapperImpl wrapper;
	
	private Map<Integer, TickData> realtimeDataRequests = null;
	private Map<Integer, ErrorMessage> errorMessages = null;
	private ErrorMessage lastAvailableError = null;
	
	public TwsService(String host, int port, int clientId, SocketDdeBridge bridge) {
		this.host = host;
		this.port = port;
		this.clientId = clientId;
		this.bridge = bridge;
		realtimeDataRequests = new ConcurrentHashMap<Integer, TickData>();
		errorMessages = new ConcurrentHashMap<Integer, ErrorMessage>();
		wrapper = new EWrapperImpl(this, bridge);
	}
	
	public Map<Integer, TickData> getRealtimeDataRequests() {
		return realtimeDataRequests;
	}
	
	
	public void connect() {
		wrapper.clientSocket().eConnect(host, port, clientId);
	}
	
	public void handleErrorMessage(ErrorMessage errorMessage) {
		errorMessages.put(errorMessage.getRequesId(), errorMessage);
		lastAvailableError = errorMessage;
		TickData dataRequest = realtimeDataRequests.get(errorMessage.getRequesId());
		if(dataRequest != null) {
			DdeMessage ddeMessage = dataRequest.getDdeRequest();
			//CAUTION: only mark request as conflicted IF we are sure the error is from this same request.
			//Error 102 corresponds to "duplicate tickerId" and should not interfere with the active request for that id.
			if(errorMessage.getErrorCode() != 102 ) {
				ddeMessage.setStatus(DdeRequestStatus.ERROR);
				ddeMessage.setErrorMessage(errorMessage);
				bridge.sendTwsToDde(ddeMessage);
			}
		}
	}
	
	public String sendDdeToTws(IDdeMessage message) {
		switch(message.getType()) {
		case RT_DATA:
			return handleRealtimeDataRequest((RealtimeDataRequest)message);
		case TICK:
			return handleTickRequest((RealtimeTickRequest)message);
		case CANCEL_RT_DATA:
			return handleRealtimeDataCancel((RealtimeDataCancel)message);
		case ERROR_ALL:
		case ERROR_ID:
		case ERROR_CODE:
		case ERROR_MESSAGE:
			return handleErrorRequest(message);
		}
		
		return DdeRequestStatus.UNKNOWN.name();
	}
	
	private String handleErrorRequest(IDdeMessage request) {
		ErrorMessage error = null;
		if(request.getRequestId() > 0) {
			error = errorMessages.get(request.getRequestId());
		} else {
			error = lastAvailableError;
		}
		
		if(error != null) {
			switch(request.getType()) {
			case ERROR_ALL:
				return error.toString();
			case ERROR_ID: 
				return String.valueOf(error.getRequesId());
			case ERROR_CODE:
				return String.valueOf(error.getErrorCode());
			case ERROR_MESSAGE:
				return error.getErrorMessage();
			}
		}
		
		return DdeRequestStatus.UNAVAILABLE.name();
	}
	
	private String handleRealtimeDataRequest(RealtimeDataRequest request) {
		if(!isIdAvailable(request.getRequestId())) {
			//We can already know this requestId because...
			TickData data = realtimeDataRequests.get(request.getRequestId());
			if(data != null) {
				if(data.getDdeRequest().getStatus() == DdeRequestStatus.ERROR) {
					//1) There was an error coming from the TWS	
					realtimeDataRequests.remove(request.getRequestId());
					return DdeRequestStatus.ERROR.name().concat(": ").concat(data.getDdeRequest().getErrorMessage().getErrorMessage());
				} else if(data.getDdeRequest().getStatus() == DdeRequestStatus.FINISHED) {
					realtimeDataRequests.remove(request.getRequestId());
					//2) Client canceled this market data request
					return DdeRequestStatus.FINISHED.name();
				}
			}
			//3) Client accidentally used the same request id
			return DdeRequestStatus.DUPLICATE_ID.name();
		}
		wrapper.clientSocket().reqMktData(request.getRequestId(), request.getContract(), 
				request.getGenericTicks(), request.isSnapshot());
		realtimeDataRequests.put(request.getRequestId(), new TickData(request));
		return DdeRequestStatus.REQUESTED.name();
	}
	
	private String handleRealtimeDataCancel(RealtimeDataCancel request) {
		TickData data = realtimeDataRequests.get(request.getRequestId());
		if(data != null) {
			wrapper.clientSocket().cancelMktData(request.getRequestId());
			DdeMessage originalRequest = data.getDdeRequest();
			originalRequest.setStatus(DdeRequestStatus.FINISHED);
			bridge.sendTwsToDde(originalRequest);
			return DdeRequestStatus.OK.name();
		}
		else {
			return DdeRequestStatus.REQUEST_NOT_FOUND.name();
		}
	}
	
	private String handleTickRequest(RealtimeTickRequest tickRequest) {
		TickData tickData = realtimeDataRequests.get(tickRequest.getRequestId());
		if(tickData != null){
			Object value = tickData.get(tickRequest.getTickType());
			if(value != null) {
				return String.valueOf(value);
			}
		}
		return DdeRequestStatus.UNAVAILABLE.name();
	}
	
	private boolean isIdAvailable(int requestId) {
		return !realtimeDataRequests.containsKey(requestId);
	}
	
	
}
