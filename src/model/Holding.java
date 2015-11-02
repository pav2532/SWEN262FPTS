package model;
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
	
	public Float getPrice(){
		return this.price;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getTickerSymbol(){
		return this.tickerSymbol;
	}
	
	public void setPrice(Float p){
		this.price = p;
	}
	public String getSectors(){
		return this.sectors;
	}
}
