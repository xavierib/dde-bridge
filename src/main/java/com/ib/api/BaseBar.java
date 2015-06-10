package com.ib.api;

public class BaseBar {

	//date, open, high, low,
    //close, volume, barCount, WAP,
    //Boolean.valueOf(hasGaps).booleanValue()
	
	private String date;
	private double open;
	private double high;
	private double low;
	private double close;
	private int volume;
	private double wap;
	
	public BaseBar(String date, double open, double high, double low, double close, int volume, double wap) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.wap = wap;
	}
	
	public String getDate() {
		return date;
	}
	public double getOpen() {
		return open;
	}
	public double getHigh() {
		return high;
	}
	public double getClose() {
		return close;
	}
	public int getVolume() {
		return volume;
	}
	public double getWap() {
		return wap;
	}
	public double getLow() {
		return low;
	}	
	
}
