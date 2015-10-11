
public class buyTransaction extends Transaction {


	public buyTransaction(String ticker, String accountName, Float Price, int numShares){
		Float total = Price*numShares;
		this.Transaction = "Bought "+numShares+" shares of "+ticker+" for "+total+". Bought with money from "+accountName;
	}
	
	
}
