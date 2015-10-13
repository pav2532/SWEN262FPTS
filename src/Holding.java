
public class Holding {

	private String tickerSymbol;
	
	private String name;
	
	private Float price;
	
	public Holding(String tS, String name, Float price){
		this.tickerSymbol = tS;
		this.name = name;
		this.price = price;
	}
	
	public Holding(Holding holding){
		this.tickerSymbol = holding.tickerSymbol;
		this.name = holding.name;
		this.price = holding.price;
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
	
	protected void setPrice(Float p){
		this.price = p;
	}
}
