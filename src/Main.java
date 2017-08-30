import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import jdk.nashorn.internal.scripts.JO;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.jmx.mbeanserver.Util.cast;

public class Main {

    public static void main(String[] args){

     //   Store store = new Store();
        Store store = (Store) BaseClass.readFromFile("src/files/Store.txt");

     //   Goods listOfGoods = new Goods();
        Goods listOfGoods = (Goods) BaseClass.readFromFile("src/files/Goods.txt");

     //   Providers providers = new Providers();
       Providers providers = (Providers) BaseClass.readFromFile("src/files/Providers.txt");

    //    Buyers buyers = new Buyers();
        Buyers buyers = (Buyers) BaseClass.readFromFile("src/files/Buyers.txt");

   //   Journal journal = new Journal();
         Journal journal = (Journal) BaseClass.readFromFile("src/files/Journal.txt");

        Loop:for(;;) {
            System.out.println("-----------------------------------------------------\n" +
                    "Menu:\nPress 1 to add a good to a store\n" +
                    "Press 2 to give good to a buyer\n" +
                    "Press 3 to get information about the good\n" +
                    "Press 4 to check if the good exists in the store\n" +
                    "Press 5 to get information about providers\n" +
                    "Press 6 to get information about buyers\n" +
                    "Press 7 to get information about available goods in the store\n" +
                    "Press 8 to view the journal\n" +
                    "Press 9 to exit\n");
            int option = 0;
            String opt = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                opt = br.readLine();
            } catch (IOException e) {
            }
            Pattern p = Pattern.compile("^[1-9]$");
            Matcher matcher = p.matcher(opt);
            if (matcher.matches())
                option = Integer.valueOf(opt);
            switch (option) {
                case 1:
                    String newGoodId;
                    BufferedReader brNewGoodId = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        System.out.println("Enter good id for a good you want to add");
                        newGoodId = brNewGoodId.readLine();
                        if (store.goodExists(newGoodId)){
                            String quantity;
                            BufferedReader brQuantity = new BufferedReader(new InputStreamReader(System.in));
                            try {
                                System.out.println("Enter how many instances of this good you want to add");
                                quantity = brQuantity.readLine();
                                store.map.replace(store.currentGoodKey, store.currentGoodValue, store.currentGoodValue +
                                        Integer.parseInt(quantity.trim()));
                                String recording = "Existing good " + newGoodId + " was added to the store in quantity of " + quantity;
                                journal.addToMap(recording.hashCode(), recording);
                            } catch (IOException e) {

                            } catch (NumberFormatException ex){
                                System.out.println("Enter a number please");
                            }
                        } else {
                            String quantity;
                            System.out.println("How many instances of this good you want to add?");
                            BufferedReader brNumber = new BufferedReader(new InputStreamReader(System.in));
                            quantity = brNumber.readLine();
                            String color;
                            BufferedReader brColor = new BufferedReader(new InputStreamReader(System.in));
                            System.out.println("What is the color of your good?");
                            color = brColor.readLine();
                            String provider;
                            System.out.println("What is the name of the provider company?");
                            BufferedReader brProvider = new BufferedReader(new InputStreamReader(System.in));
                            provider = brProvider.readLine();
                            if (providers.goodCompliesWithProvider(new Good(newGoodId, color, provider), provider)){
                                listOfGoods.add(new Good(newGoodId, color, provider));
                                store.addToMap(newGoodId, Integer.parseInt(quantity.trim()));
                                String recording = "New good " + newGoodId + " was added to the store in quantity of " + quantity;
                                journal.addToMap(recording.hashCode(), recording);
                            } else
                                System.out.println("This provider already produces another kind of goods");

                        }
                    } catch (IOException e) {
                    }
                    break;
                case 2:
                    boolean possible = false;
                    System.out.println("enter good id which you want to give to a buyer");
                    String goodIdAway;
                    String buyerName;
                    BufferedReader brGoodIdAway = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        goodIdAway = brGoodIdAway.readLine();
                        if (store.goodExists(goodIdAway)){
                            String quantity = "";
                            BufferedReader brQuantity = new BufferedReader(new InputStreamReader(System.in));
                            try {
                                System.out.println("Enter how many instances of this good you want to give to a buyer");
                                quantity = brQuantity.readLine();
                                int oldQuantity = store.currentGoodValue;
                                if (oldQuantity - Integer.parseInt(quantity.trim()) > 0) {
                                    store.map.replace(store.currentGoodKey, oldQuantity, (oldQuantity - Integer.parseInt(quantity.trim())));
                                    possible = true;
                                }
                            } catch (NumberFormatException ex){
                            }
                            if (possible) {
                                BufferedReader brBuyerName = new BufferedReader((new InputStreamReader(System.in)));
                                System.out.println("Enter the buyer name to whom you want to give this good");
                                buyerName = brBuyerName.readLine();
                                if (buyers.buyerExists(buyerName + goodIdAway, goodIdAway)) {
                                    BuyersGoods old = buyers.currentBuyersGoods;
                                    buyers.map.replace(buyers.currentBuyer, old, new BuyersGoods(goodIdAway, old.quantity + Integer.parseInt(quantity.trim())));
                                    String recording = "Good " + goodIdAway + " was given to a buyer " + buyerName + " in quantity of " + quantity;
                                    journal.addToMap(recording.hashCode(), recording);
                                } else {
                                    buyers.addToMap(buyerName + goodIdAway, new BuyersGoods(goodIdAway, Integer.parseInt(quantity.trim())));
                                    String recording = "Good " + goodIdAway + " was given to a buyer " + buyerName + " in quantity of " + quantity;
                                    journal.addToMap(recording.hashCode(), recording);
                                }
                            } else
                                System.out.println("Not enough instances in the store");
                        }
                    } catch (IOException e) {
                    }
                    break;
                case 3:
                    System.out.println("Enter good id you want to check");
                    String goodId;
                    BufferedReader brGoodId = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        goodId = brGoodId.readLine();
                        listOfGoods.getGoodsInfo(goodId, false, store);
                    } catch (IOException e) {
                    }
                    break;
                case 4:
                    System.out.println("Enter good id you want to check");
                    String goodIdToCheck;
                    BufferedReader brGoodIdToCheck = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        goodIdToCheck = brGoodIdToCheck.readLine();
                        if (store.goodExists(goodIdToCheck))
                            System.out.println("yes, it exists in the store");
                        else
                            System.out.println("this good doesn't exist in the store");
                    } catch (IOException e) {
                    }
                    break;
                case 5:
                    providers.getProvidersInfo();
                    break;
                case 6:
                    buyers.getBuyersInfo();
                    break;
                case 7:
                    listOfGoods.getGoodsInfo("",true, store);
                    break;
                case 8:
                    journal.viewJournal();
                    break;
                case 9:
                    store.writeToFile("src/files/Store.txt");
                    listOfGoods.writeToFile("src/files/Goods.txt");
                    buyers.writeToFile("src/files/Buyers.txt");
                    journal.writeToFile("src/files/Journal.txt");
                    providers.writeToFile("src/files/Providers.txt");
                    break Loop;
            }
        }
    }
}
