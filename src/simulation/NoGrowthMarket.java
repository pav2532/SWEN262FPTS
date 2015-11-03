package simulation;

import java.util.HashMap;

import model.Holding;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PedroVega
 */
public class NoGrowthMarket implements Algorithim {

	@Override
	public Holding simulate(int percentage, int type, int intervals, Holding holding) {
		return holding;
	}

}
