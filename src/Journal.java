import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Journal extends BaseClass implements Serializable {

    public Journal(){
        super();
        map = new HashMap<Integer, String>();
    }

    public void viewJournal(){
        Set<Integer> tempKeySet = map.keySet();
        Iterator<Integer> it = tempKeySet.iterator();
        while (it.hasNext()) {
            int currLine = it.next();
            System.out.println(map.get(currLine));
        }
    }
}
