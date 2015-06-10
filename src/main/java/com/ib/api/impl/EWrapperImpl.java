package com.ib.api.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.ib.api.BaseBar;
import com.ib.api.CommissionReport;
import com.ib.api.Contract;
import com.ib.api.ContractDetails;
import com.ib.api.EClientSocket;
import com.ib.api.EWrapper;
import com.ib.api.Execution;
import com.ib.api.Order;
import com.ib.api.OrderState;
import com.ib.api.TickType;
import com.ib.api.UnderComp;
import com.ib.api.dde.SocketDdeBridge;
import com.ib.api.dde.TwsService;
import com.ib.api.dde.translation.socket2dde.messages.ErrorEvent;
import com.ib.api.dde.translation.socket2dde.messages.RealtimeDataEvent;
import com.ib.api.impl.twsservice.ErrorMessage;
import com.ib.api.impl.twsservice.TickData;

public class EWrapperImpl implements EWrapper {
	
	private static final Logger LOG = Logger.getLogger(EWrapperImpl.class);
	
	protected EClientSocket clientSocket = null;
	private TwsService twsService = null; 
	private SocketDdeBridge socket2dde = null;	
		
	public EWrapperImpl(TwsService twsService, SocketDdeBridge socket2dde) {
		this.twsService = twsService;
		this.socket2dde = socket2dde;		
		clientSocket = new EClientSocket(this);
	}
	
	public EClientSocket clientSocket() {
		return clientSocket;
	}
	
	@Override
	public void error(Exception e) {
		LOG.error("Error: ", e);
	}

	@Override
	public void error(String str) {
		
	}

	@Override
	public void error(int id, int errorCode, String errorMsg) {
		LOG.error("Error: Id["+id+"] ErrorCode ["+errorCode+"] ErrorMsg ["+errorMsg+"]");
		twsService.handleErrorMessage(new ErrorMessage(id, errorCode, errorMsg));
		socket2dde.handleTwsMessage(new ErrorEvent(id, errorCode, errorMsg));
	}

	@Override
	public void connectionClosed() {
		
	}

	@Override
	public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
		LOG.debug("tickPrice TickerId ["+tickerId+"] Field ["+TickType.getField(field)+"] Price ["+price+"] CanAutoExecute ["+canAutoExecute+"]");
		RealtimeDataEvent event = new RealtimeDataEvent(tickerId, field, price);		
		TickData data = twsService.getRealtimeDataRequests().get(tickerId);
		if(data != null) {
			data.set(field, price);
			socket2dde.handleTwsMessage(event);
		}
	}

	@Override
	public void tickSize(int tickerId, int field, int size) {
		LOG.debug("tickSize TickerId ["+tickerId+"] Field ["+TickType.getField(field)+"] Size ["+size+"]");
		RealtimeDataEvent event = new RealtimeDataEvent(tickerId, field, size);		
		TickData data = twsService.getRealtimeDataRequests().get(tickerId);
		if(data != null) {
			data.set(field, size);
			socket2dde.handleTwsMessage(event);
		}
	}

	@Override
	public void tickOptionComputation(int tickerId, int field,
			double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta,
			double undPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickGeneric(int tickerId, int tickType, double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickString(int tickerId, int tickType, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickEFP(int tickerId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureExpiry, double dividendImpact, double dividendsToExpiry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orderStatus(int orderId, String status, int filled,
			int remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openOrder(int orderId, Contract contract, Order order,
			OrderState orderState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openOrderEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAccountValue(String key, String value, String currency,
			String accountName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePortfolio(Contract contract, int position,
			double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAccountTime(String timeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accountDownloadEnd(String accountName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextValidId(int orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contractDetails(int reqId, ContractDetails contractDetails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contractDetailsEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execDetailsEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMktDepth(int tickerId, int position, int operation,
			int side, double price, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMktDepthL2(int tickerId, int position,
			String marketMaker, int operation, int side, double price, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNewsBulletin(int msgId, int msgType, String message,
			String origExchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void managedAccounts(String accountsList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveFA(int faDataType, String xml) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void historicalData(int reqId, List<BaseBar> bars) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scannerParameters(String xml) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scannerData(int reqId, int rank,
			ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scannerDataEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void realtimeBar(int reqId, long time, double open, double high,
			double low, double close, long volume, double wap, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void currentTime(long time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fundamentalData(int reqId, String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deltaNeutralValidation(int reqId, UnderComp underComp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickSnapshotEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void marketDataType(int reqId, int marketDataType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commissionReport(CommissionReport commissionReport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void position(String account, Contract contract, int pos,
			double avgCost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void positionEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accountSummary(int reqId, String account, String tag,
			String value, String currency) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accountSummaryEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}

}
