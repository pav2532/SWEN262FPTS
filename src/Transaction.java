
public abstract class Transaction {

	String Transaction;
	String amount;
	String to;
	String from;
	String SharePrice;
	
	public String getTransaction(){
		return this.Transaction;
	}
	
	public Transaction(){
		this.Transaction = "Default Transaction class. This should never display";
	}
	
	public abstract String saveTransaction();
}
