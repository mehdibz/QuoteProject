package ca.quotemedia.services;


public class ResponseObject {

	private String bid;
	private String ask; 

	public static class Builder {

		private String bid;
		private String ask; 

		public Builder(String bid,String ask) {
			this.bid = bid;
			this.ask = ask;
		}

		public ResponseObject build() {
			return new ResponseObject(this);
		}
	}

	/**
	 * Default Constructor
	 */
	private ResponseObject(Builder builder) {
		bid = builder.bid;
		ask = builder.ask;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getBid() {
		return bid;
	}
	
	public void setAsk(String ask) {
		this.ask = ask;
	}

	public String getAsk() {
		return ask;
	}


	@Override
	public String toString() {
		return "[bid=" + bid + ", ask=" + ask + "]";
	}

}
