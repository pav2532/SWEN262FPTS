
public class Holding {

	private String tickerSymbol;
	
	private String name;
	
	private Float price;
	
	public Holding(String tS, String name, Float price){
		this.tickerSymbol = tS;
		this.name = name;
		this.price = price;
	}
	
	protected Float getPrice(){
		return this.price;
	}
	
	protected String getName(){
		return this.name;
	}
	
	protected String getTickerSymbol(){
		return this.tickerSymbol;
	}
}
