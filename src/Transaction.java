
public abstract class Transaction {

	String Transaction;
	
	public String getTransaction(){
		return this.Transaction;
	}
	
	public Transaction(){
		this.Transaction = "Default Transaction class. This should never display";
	}
}
