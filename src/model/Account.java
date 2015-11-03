package model;

public class Account {

	private String name;

	private Float balance;

	private String dateCreated;

	public Account(String name, Float balance, String dateCreated) {
		this.name = name;
		this.setBalance(balance);
		this.setDateCreated(dateCreated);
	}

	public Account(String name, String dateCreated) {
		this.name = name;
		this.setBalance(0f);
		this.setDateCreated(dateCreated);
	}

	public String getName() {
		return this.name;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void addFunds(Float funds) {
		this.balance = balance + funds;
	}

	public int removeFunds(Float funds) {
		if (funds <= balance) {
			balance = balance - funds;
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj){
		Account a = (Account) obj;
		if(this.name.equals(a.getName()) && this.balance.equals(a.getBalance()) && this.dateCreated.equals(a.getDateCreated()) ){
			return true;
		}
		return false;
	}
}
