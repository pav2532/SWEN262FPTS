
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Deacrease 
 *
 * @author PedroVega
 */
public class BearMarket implements Algorithim {

    @Override
    public Holding simulate(int percentage, int type, int intervals,Holding holding) {
    	Holding tempHolding = new Holding(holding);
    	if(percentage >= 100){
    		tempHolding.setPrice((float) 0.0);
    		return tempHolding;
    	}
		Float price = tempHolding.getPrice()*(100-percentage)/100;
		if(type == 1){
			price = price/365;
			price = price*intervals;
			
		}else if (type == 2){
			price = price/12;
			price = price * intervals;
		}else{
			for(int i = 1; i < intervals; i++)
				price = price*((100-percentage)/100);
		}
		tempHolding.setPrice(price);
        return tempHolding;
    }
}
