
public class transferTransaction extends Transaction {

	public transferTransaction(String accountNameF, String accountNameT, Float amount){
		this.Transaction = "Transfered $"+amount+" from "+accountNameF+" to "+accountNameT+".";
	}
}
