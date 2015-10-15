
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

	public Holding simulate(int percentage, int type, int intervals, Holding holding) {
		Holding tempHolding = new Holding(holding);
		Float price = tempHolding.getPrice() * ((100 + percentage) / 100);
		if (type == 1) {
			price = price / 365;
			price = price * intervals;

		} else if (type == 2) {
			price = price / 12;
			price = price * intervals;
		} else {
			for (int i = 1; i < intervals; i++)
				price = price * ((100 + percentage) / 100);
		}
		tempHolding.setPrice(price);
		return tempHolding;

	}
}
