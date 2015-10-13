/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author PedroVega
 */
public class BullMarket implements Algorithim {
    
    public Holding simulate(int percentage, int type, int intervals,Holding holding){
    	Holding tempHolding = new Holding(holding);
		Float price = tempHolding.getPrice()*((100+percentage)/100);
		tempHolding.setPrice(price);
        return tempHolding;
        

    }
}
