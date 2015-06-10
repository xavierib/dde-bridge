package com.ib.api.dde.translation;

import com.ib.api.dde.translation.dde2socket.messages.IDdeMessage;
import com.ib.api.dde.translation.socket2dde.messages.TwsMessage;

public interface IDdeRequestTranslator {
	
	//Return null if you cannot handle this request
	IDdeMessage translate(String topic, String request);
	
	IDdeMessage translate(TwsMessage response); 
}
