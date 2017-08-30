import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Goods extends BaseClass implements Serializable {
    List<Good> listOfGoods = null;

    public Goods(){
        super();
        listOfGoods = new ArrayList<Good>();
    }

    public void add(Good good){
        listOfGoods.add(good);
    }

    public void getGoodsInfo(String goodId, boolean existingOnly, Store store) {
        for (int j = 0; j < listOfGoods.size(); j++) {
            Good currGood = listOfGoods.get(j);
            if (existingOnly) {
                if ((Integer) store.map.get(currGood.getGoodId()) > 0) {
                    System.out.println("Good id is " + currGood.getGoodId() + "; good color is " + currGood.getColor() + "; good provider is " +
                            currGood.getProvider() + "; quantity in the store is " + store.map.get(currGood.getGoodId()));
                }
            } else if (currGood.getGoodId().equals(goodId)) {
                System.out.println("Good id is " + currGood.getGoodId() + "; good color is " + currGood.getColor() + "; good provider is " +
                        currGood.getProvider() + "; quantity in the store is " + store.map.get(currGood.getGoodId()));
                break;
            }
        }
    }
}