import java.io.Serializable;
import java.util.HashMap;

public class BuyersGoods extends BaseClass implements Serializable {

    public String goodName;
    public int quantity;

    public BuyersGoods(String goodName, int quantity){
        this.goodName = goodName;
        this.quantity = quantity;
    }

    public String getGood() {
        return goodName;
    }

    public int getQuantity() {
        return quantity;
    }
}
