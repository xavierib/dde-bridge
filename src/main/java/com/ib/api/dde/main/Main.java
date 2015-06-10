package com.ib.api.dde.main;

import org.apache.log4j.Logger;

import com.ib.api.dde.SocketDdeBridge;
import com.pretty_tools.dde.DDEException;

public class Main {
	
	private static final Logger LOG = Logger.getLogger(Main.class);
	
	private static final String DDE_SERVICE_NAME = "Stwsserver";
	private static final String TWS_HOST = "127.0.0.1";
	private static final int TWS_PORT = 7496;
	private static final int TWS_CLIENT_ID = 0;

	public static void main(String[] args) throws InterruptedException, DDEException {
				
		try {
			LOG.info("Starting DdeSocket bridge server ["+DDE_SERVICE_NAME+"]");
			SocketDdeBridge bridge = new SocketDdeBridge(DDE_SERVICE_NAME, TWS_HOST, TWS_PORT, TWS_CLIENT_ID);
			bridge.start();
			
			/*
			
			TwsDdeServer ddeServer = new TwsDdeServer("twsserver", 0);
			ddeServer.start();
			LOG.info("Connecting to TWS...");
			TwsService.Get().connect("127.0.0.1", 7496, 0);
			*/
		} catch (Exception e) {
			LOG.error("Failed! ", e);
		}		
		
		while(true) {
			Thread.sleep(100);
		}
	}
	
}
