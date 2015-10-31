import java.util.*;

public interface Observer {
   
   public void update(ArrayList<Account> account, HashMap<String, Integer> holding, ArrayList<Transaction> allTransaction);
   
}
