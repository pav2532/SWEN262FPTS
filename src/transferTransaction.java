
public class transferTransaction extends Transaction {

	public transferTransaction(String accountNameF, String accountNameT, Float amount) {
		this.Transaction = "Transfered $" + amount + " from " + accountNameF + " to " + accountNameT + ".";
		this.amount = amount.toString();
		this.to = accountNameT;
		this.from = accountNameF;
	}

	public String saveTransaction() {
		return ("T,T," + to + "," + from + "," + amount);
	}
}
