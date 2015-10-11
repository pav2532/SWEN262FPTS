
public class sellTransaction extends Transaction {

	public sellTransaction(String ticker, String accountName, Float Price, int numShares){
		Float total = Price*numShares;
		this.Transaction = "Sold "+numShares+" shares of "+ticker+" for "+total+". Profits put in "+accountName;
	}
	
}
