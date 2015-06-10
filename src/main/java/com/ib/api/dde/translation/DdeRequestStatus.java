package com.ib.api.dde.translation;

public enum DdeRequestStatus {

	PARSED, 
	MALFORMED, 
	DUPLICATE_ID,
	REQUEST_NOT_FOUND,
	UNAVAILABLE, 
	UNKNOWN, 
	REQUESTED, 
	RECEIVED, 
	FINISHED,
	OK,
	ERROR,
	EXCEPTION
}
