package com.ib.api.dde.translation.translators;

import com.ib.api.Contract;
import com.ib.api.dde.translation.IDdeRequestTranslator;

public abstract class BaseRequestTranslator implements IDdeRequestTranslator {

	public static final String DDE_REQUEST_SEPARATOR = "?";
	//We need these or it will be taken as a regexp when spliting the string.
	public static final String DDE_REQUEST_SEPARATOR_PARSE = "\\" + DDE_REQUEST_SEPARATOR;
	public static final String DDE_MESSAGE_SEPARATOR = "/";
	public static final String PARAM_SEPARATOR = "_";
	public static final String EMPTY_PARAM = "~";
	public static final String ID = "id";
	
	public static final String DDE_REQUEST = "req";
	public static final String DDE_CANCEL = "cancel";
	
	protected int parseRequestId(String token) {
		return Integer.valueOf(token.substring(2));
	}
	
	protected Contract parseContract(String contractString, int msgVersion) {
		
		switch(msgVersion) {
		case 1:
			return parseSimpleContract(contractString);
		}
		
		return null;
	}
	
	private Contract parseSimpleContract(String contractString) {
		String[] tokens = contractString.split(PARAM_SEPARATOR);
		Contract contract = new Contract();
		
		contract.m_symbol = tokens[0];
		contract.m_secType = tokens[1];
		
		switch(contract.m_secType) {
		case "CASH":
		case "STK":
		case "CFD":
		case "CMDTY":
		case "IND":
			contract.m_exchange = tokens[2];
			contract.m_currency = tokens[3];
			contract.m_primaryExch = tokens[4].contains(EMPTY_PARAM) ? null : tokens[4];
			break;
		case "OPT":
		case "FOP":
			contract.m_expiry = tokens[2];
			contract.m_strike = Double.valueOf(tokens[3]);
			contract.m_right = tokens[4];
			contract.m_multiplier = tokens[5];
			contract.m_exchange = tokens[6];
			contract.m_currency = tokens[7];
			contract.m_primaryExch = tokens[8].contains(EMPTY_PARAM) ? null : tokens[8];
			contract.m_tradingClass = tokens[9].contains(EMPTY_PARAM) ? null : tokens[9];
			break;
		case "FUT":
			contract.m_expiry = tokens[2];
			contract.m_multiplier = tokens[3];
			contract.m_exchange = tokens[4];
			contract.m_currency = tokens[5];
			contract.m_primaryExch = tokens[6].contains(EMPTY_PARAM) ? null : tokens[6];
			contract.m_tradingClass = tokens[7].contains(EMPTY_PARAM) ? null : tokens[7];
			break;
		}
		
		return contract;
	}
}
