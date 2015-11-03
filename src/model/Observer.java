package model;
import java.util.*;

import transactions.Transaction;

public interface Observer {
   
   public void update(ArrayList<Account> account, HashMap<String, Integer> holding, ArrayList<Transaction> allTransaction);
   
}
