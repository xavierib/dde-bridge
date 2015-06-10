package com.ib.api.dde;

import java.util.List;

import org.apache.log4j.Logger;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.ib.api.dde.translation.DdeToSocketTranslator;
import com.ib.api.dde.translation.dde2socket.messages.DdeAnonymousMessage;
import com.ib.api.dde.translation.dde2socket.messages.DdeMessage;
import com.ib.api.dde.translation.dde2socket.messages.DdeMultiMessage;
import com.ib.api.dde.translation.dde2socket.messages.DdeRequest;
import com.ib.api.dde.translation.dde2socket.messages.IDdeMessage;
import com.ib.api.dde.translation.socket2dde.messages.TwsMessage;
import com.ib.api.dde.translation.translators.ErrorRequestTranslator;
import com.ib.api.dde.translation.translators.MarketDataRequestTranslator;
import com.pretty_tools.dde.DDEException;

public class SocketDdeBridge {
	
	private static final Logger LOG = Logger.getLogger(SocketDdeBridge.class);
	
	private TwsDdeServer ddeServer;
	private TwsService twsService;
	private DdeToSocketTranslator dde2SocketTranslator;
	
	public SocketDdeBridge(String ddeServiceName, String twsHost, int twsPort, int twsClientId) {
		ddeServer = new TwsDdeServer(ddeServiceName, this);
		twsService = new TwsService(twsHost, twsPort, twsClientId, this);
		dde2SocketTranslator = new DdeToSocketTranslator();
		dde2SocketTranslator.addTranslator(new MarketDataRequestTranslator());
		dde2SocketTranslator.addTranslator(new ErrorRequestTranslator());
	}
	
	public void start() throws DDEException {
		ddeServer.start();
		twsService.connect();
	}
	
	public String handleDdeRequest(String topic, String request)  {
		IDdeMessage ddeRequest = dde2SocketTranslator.convertDdeMessage(topic, request);
		 if(ddeRequest == null)
			 return DdeRequestStatus.UNKNOWN.name();
		 return sendDdeToTws(ddeRequest);
	}
	
	public void handleTwsMessage(TwsMessage twsMessage) {
		IDdeMessage ddeMessage = dde2SocketTranslator.convertTwsMessage(twsMessage);
		if(ddeMessage != null) {
			if(ddeMessage instanceof DdeMultiMessage) {
				DdeMultiMessage ddeMultiMessage = ((DdeMultiMessage)ddeMessage);
				List<DdeRequest> ddeRequests =  ddeMultiMessage.getDdeRequests();
				for(DdeRequest req : ddeRequests) {
					if(req != null)
						sendTwsToDde(new DdeAnonymousMessage(ddeMultiMessage.getTopic(), req.getRequestStr()));
				}
			} else {
				sendTwsToDde((DdeMessage)ddeMessage);
			}
		}
	}
	
	public void sendTwsToDde(DdeMessage message) {
		try {
			ddeServer.notifyClients(message.getTopic(), message.getRequestStr());
		}
		catch(DDEException ddeEx) {
			LOG.error("Failed to send to DDE: ", ddeEx);
		}
	}
	
	private String sendDdeToTws(IDdeMessage message) {
		return twsService.sendDdeToTws(message);
	}
		
}
