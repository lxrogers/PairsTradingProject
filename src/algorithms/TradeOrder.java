package algorithms;

public class TradeOrder {
	
	String ticker;
	String aTradeType; // The trade type for be is always the opposite of A.
	double percentage;
	int OUValue;
	int currentOU;
	
	/*
	 * Constructor for the TradeOrder class.
	 */
	
	public TradeOrder(String ticker, String tradeType, double percentage, int OUValue) {
		currentOU = 0;
		this.ticker = ticker;
		this.aTradeType = tradeType;
		this.percentage = percentage;
		this.OUValue = OUValue;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getaTradeType() {
		return aTradeType;
	}

	public void setaTradeType(String aTradeType) {
		this.aTradeType = aTradeType;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public int getOUValue() {
		return OUValue;
	}

	public void setOUValue(int oUValue) {
		OUValue = oUValue;
	}

	public int getCurrentOU() {
		return currentOU;
	}

	public void setCurrentOU(int currentOU) {
		this.currentOU = currentOU;
	}
	
}
