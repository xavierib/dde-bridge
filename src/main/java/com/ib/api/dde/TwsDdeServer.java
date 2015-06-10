package com.ib.api.dde;

import org.apache.log4j.Logger;

import com.ib.api.dde.translation.DdeRequestStatus;
import com.pretty_tools.dde.ClipboardFormat;
import com.pretty_tools.dde.server.DDEServer;

public class TwsDdeServer extends DDEServer {
	
	private static final Logger LOG = Logger.getLogger(TwsDdeServer.class);

	private long startupTime;	
	
	private SocketDdeBridge socket2dde;
	
	
	public TwsDdeServer(String serviceName, SocketDdeBridge bridge) {
		super(serviceName);
		startupTime = System.currentTimeMillis();
		socket2dde = bridge;
	}
	
	 @Override
     protected boolean isTopicSupported(String topicName) {
     	LOG.debug("Checking if Topic is supported "+topicName);
     	//To mimic current DDE behaviour, we accept any topic and simply return an error
     	/*
     	for(int i=0; i< DdeRequestType.values().length - 1; i++){
     		if(topicName.equals(DdeRequestType.values()[i].getTopic()))
     			return true;
     	}
     	return false;
     	*/
     	return true;
     }

     @Override
     protected boolean isItemSupported(String topic, String item, int uFmt)
     {
    	 //Here is where the request from the Excel file reaches first.
    	 boolean isTopicSupported = isTopicSupported(topic);
    	 
    	 boolean res = isTopicSupported
                 && (uFmt == ClipboardFormat.CF_TEXT.getNativeCode() || uFmt == ClipboardFormat.CF_UNICODETEXT.getNativeCode());
    	
         return res;
     }
     
	 @Override
	 protected String onRequest(String topic, String item) {
		 LOG.debug("Received DDE request: Topic: ["+topic+"] Item: ["+item+"].");
		 try {
			 return socket2dde.handleDdeRequest(topic, item);
		 } catch(Exception ex) {
			 LOG.error("Failed to handle request: ", ex);
		 }
		 return DdeRequestStatus.EXCEPTION.name();
     }
	
	@Override
    protected byte[] onRequest(String topic, String item, int uFmt) {
        LOG.info("onRequest(" + topic + ", " + item + ", " + uFmt + ") not supported!");

        return null;
    }
	

}
