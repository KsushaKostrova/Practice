import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Buyers extends BaseClass implements Serializable{

    public String currentBuyer;
    public  BuyersGoods currentBuyersGoods;

    public Buyers(){
        super();
        map = new HashMap<String, BuyersGoods>();//key is a buyer name, value is the number of goods he has
    }

    public void getBuyersInfo(){
        Set<String> tempKeySet = map.keySet();
        Iterator<String> it = tempKeySet.iterator();
        while (it.hasNext()) {
            currentBuyer = it.next();
            System.out.println("Buyer name is " + currentBuyer + "; the good name is " + ((BuyersGoods)map.get(currentBuyer)).getGood()
                    + "; number of goods he has is " + ((BuyersGoods)map.get(currentBuyer)).getQuantity());
        }
    }

    public boolean buyerExists(String buyerName, String goodId){
        Set<String> tempKeySet = map.keySet();
        Iterator<String> it = tempKeySet.iterator();
        while (it.hasNext()) {
            currentBuyer = it.next();
            currentBuyersGoods = (BuyersGoods)map.get(currentBuyer);
            if (currentBuyer.equals(buyerName) && currentBuyersGoods.getGood().equals(goodId)) {
                return true;
            }
        }
        return false;
    }
}
