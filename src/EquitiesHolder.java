import java.util.HashMap;

public class EquitiesHolder {

	protected HashMap<String,HashMap<String,Holding>> Indices;
	
	public void addEquity(Holding newEquity, String[] Index){
		for(int i = 0; i < Index.length; i++ ){
			if(Indices.containsKey(Index[i])){
				
				Indices.get(Index[i]).put(newEquity.getTickerSymbol(), newEquity);
				
			}else{
				
				HashMap<String,Holding> newIndex = new HashMap<String, Holding>();
				Indices.put(Index[i], newIndex);
				Indices.get(Index[i]).put(newEquity.getTickerSymbol(), newEquity);
				
			}
		}
	}//end addEquity
	
	//ADD SEARCH FUNCTIONALITY HERE
}
