import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

public class Provider extends BaseClass implements Serializable {

    private String providerName;
    private String type;
    private String manager;

    public Provider(String providerName, String type, String manager){
        super();
        this.providerName = providerName;
        this.type = type;
        this.manager = manager;
    }

    public String getProviderName(){
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        if (providerName != null ? !providerName.equals(provider.providerName) : provider.providerName != null)
            return false;
        if (type != null ? !type.equals(provider.type) : provider.type != null) return false;
        return manager != null ? manager.equals(provider.manager) : provider.manager == null;
    }

    @Override
    public int hashCode() {
        int result = providerName != null ? providerName.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        return result;
    }
}
