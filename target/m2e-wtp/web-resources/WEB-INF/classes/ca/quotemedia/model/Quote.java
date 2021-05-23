package ca.quotemedia.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Quote implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min=2,max=6)
	private String symbol;
	
	@NotNull
	private String day;
	
	private String bid;
	private String ask;

	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}
	
	public String getAsk() {
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

	@Override
	public String toString() {
		return "Quote [symbol=" + symbol + ", day=" + day + ", bid="	+ bid + ", ask="+ ask +"]";
	}
}
