
public class Holding {

	private String tickerSymbol;
	
	private String name;
	
	private Float price;
	
	private String sectors;
	
	public Holding(String tS, String name, Float price, String sectors){
		this.tickerSymbol = tS;
		this.name = name;
		this.price = price;
		this.sectors = sectors;
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
	protected String getSectors(){
		return this.sectors;
	}
}
