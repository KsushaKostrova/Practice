import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;

public class Store extends BaseClass implements Serializable {

    public String currentGoodKey;
    public Integer currentGoodValue;

    public Store(){
        super();
        map = new HashMap<String, Integer>();
    }

 /*   public void addGood(String goodId, int quantity) {
        map.put(goodId, quantity);
    }*/

    public boolean goodExists(String goodId){
        Set<String> tempKeySet = map.keySet();
        Iterator it = tempKeySet.iterator();
        while (it.hasNext()) {
            if (it.next().equals(goodId) && (Integer) map.get(goodId) > 0) {
                currentGoodKey = goodId;
                currentGoodValue = (Integer) map.get(goodId);
                return true;
            }
        }
        return false;
    }



}
