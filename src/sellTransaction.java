public class sellTransaction extends Transaction {

	public sellTransaction(String ticker, String accountName, Float Price, int numShares){
		Float total = Price*numShares;
		this.Transaction = "Sold "+numShares+" shares of "+ticker+" for "+total+". Profits put in "+accountName;
		this.amount = ((Integer)numShares).toString();
		this.SharePrice =  Price.toString();
		this.to = ticker;
		this.from = accountName;
	}
	
	public String saveTransaction(){

		return ("T,S,"+to+","+from+","+SharePrice+","+amount);
	}

}

