package com.ib.api.dde.translation;

import java.util.ArrayList;
import java.util.List;

import com.ib.api.dde.translation.dde2socket.messages.IDdeMessage;
import com.ib.api.dde.translation.socket2dde.messages.TwsMessage;


public class DdeToSocketTranslator {
	
	private List<IDdeRequestTranslator> requestTranslators;
	
	public DdeToSocketTranslator() {
		requestTranslators = new ArrayList<IDdeRequestTranslator>();
	}
	
	public void addTranslator(IDdeRequestTranslator translator) {
		this.requestTranslators.add(translator);
	}
	
	public IDdeMessage convertDdeMessage(String topic, String item) {
		IDdeMessage ddeMessage = null;
		for(IDdeRequestTranslator reqTrans : requestTranslators) {
			ddeMessage = reqTrans.translate(topic, item);
			if(ddeMessage != null )
				return ddeMessage;
		}
				
		return ddeMessage;
	}
	
	public IDdeMessage convertTwsMessage(TwsMessage twsMessage){
		IDdeMessage ddeMessage = null;
		for(IDdeRequestTranslator reqTrans : requestTranslators) {
			ddeMessage = reqTrans.translate(twsMessage);
			if(ddeMessage != null)
				return ddeMessage;
		}
		return ddeMessage;
	}

}
