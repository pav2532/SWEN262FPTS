package model;

public class watchListHolding{
	private Float highTrigger;
	private Float lowTrigger;
	private boolean lowTriggerHit;
	private boolean highTriggerHit;
	private Holding holding;
	
	public watchListHolding(Float highTrigger, Float lowTrigger, Holding holding){
		this.holding = holding;
		this.highTrigger = highTrigger;
		this.lowTrigger = lowTrigger;
		this.highTriggerHit = false;
		this.lowTriggerHit = true;
	}
	
	public void checkTriggers(){
		if(holding.getPrice() > highTrigger){
			this.highTriggerHit = true;
		}
		
		if(holding.getPrice() < lowTrigger){
			this.lowTriggerHit = true;
		}
	}
	
	public boolean isAboveHighTrigger(){
		if(holding.getPrice() > highTrigger){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isBelowLowTrigger(){
		if(holding.getPrice() < lowTrigger){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean wasAboveTrigger(){
		return highTriggerHit;
	}
	
	public boolean wasBelowTrigger(){
		return lowTriggerHit;
	}
	
	public Holding getHolding(){
		return holding;
	}
	
	public Float getHighTrigger() {
		return highTrigger;
	}

	public Float getLowTrigger() {
		return lowTrigger;
	}

	public String save(){
		return "\""+highTrigger+"\",\""+lowTrigger+"\",\""+
				"\""+holding.getTickerSymbol()+"\",\""+holding.getName()+"\",\""+holding.getPrice()+"\",\""+holding.getSectors()+"\"";
	}
}
