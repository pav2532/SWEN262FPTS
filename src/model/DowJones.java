package model;
/**
 * Created by Quang on 10/31/15.
 */
import java.util.*;

public class DowJones {
    private int sum;
    private double divisor = 0.149;

    public double caculation(HashMap<String, Integer> thirtyStock){
        for(String key : thirtyStock.keySet()){
            sum += thirtyStock.get(key);
        }
        return (sum/divisor);
    }
}
