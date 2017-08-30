import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Providers extends BaseClass implements Serializable {

    public Providers(){
        super();
        map = new HashMap<Provider, Good>();
    }

    public void getProvidersInfo(){
        Set<Provider> tempKeySet = map.keySet();
        Iterator<Provider> it = tempKeySet.iterator();
        while (it.hasNext()) {
            Provider currProvider = it.next();
            System.out.println("Provider name is " + currProvider.getProviderName() + "; provider type is " + currProvider.getType() +
            "; provider manager is " + currProvider.getManager() + " provider produces good " + ((Good)map.get(currProvider)).getGoodId());
        }
    }

    public boolean goodCompliesWithProvider(Good good, String providerName){
        if (!providerExists(providerName)) {
            addNewProvider(good, providerName);
            return true;
        }
        else if (good.getGoodId().equals(getGoodNameByProvider(providerName))) {
            return true;
        }
       return false;
    }

    private String getGoodNameByProvider(String providerName){
        Set<Provider> tempKeySet = map.keySet();
        Iterator<Provider> it = tempKeySet.iterator();
        while (it.hasNext()) {
            Provider currProvider = it.next();
            if (currProvider.getProviderName().equals(providerName))
                return ((Good)map.get(currProvider)).getGoodId();
        }
        return "";
    }

    private boolean providerExists(String providerName){
        Set<Provider> tempKeySet = map.keySet();
        Iterator<Provider> it = tempKeySet.iterator();
        while (it.hasNext()) {
            Provider currProvider = it.next();
            if (currProvider.getProviderName().equals(providerName))
                return true;
        }
        return false;
    }

    private void addNewProvider(Good good, String providerName){
        String providerType = "";
        String providerManager = "";
        System.out.println("Enter the type of provider");
        BufferedReader brType = new BufferedReader(new InputStreamReader(System.in));
        try {
            providerType = brType.readLine();
            System.out.println("Enter the manager of provider");
            BufferedReader brManager = new BufferedReader(new InputStreamReader(System.in));
            providerManager = brManager.readLine();
            addToMap(new Provider(providerName, providerType, providerManager), good);
        } catch (IOException e) {
        }
    }
}
