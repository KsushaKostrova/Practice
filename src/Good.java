import java.io.Serializable;

public class Good implements Serializable {

    private String goodId;
    private String color;
    private String provider;


    public Good(String goodId, String color, String provider){
        setGoodId(goodId);
        setColor(color);
        setProvider(provider);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Good good = (Good) o;

        if (goodId != null ? !goodId.equals(good.goodId) : good.goodId != null) return false;
        if (color != null ? !color.equals(good.color) : good.color != null) return false;
        return provider != null ? provider.equals(good.provider) : good.provider == null;
    }

    @Override
    public int hashCode() {
        int result = goodId != null ? goodId.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getGoodId(){
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
}
