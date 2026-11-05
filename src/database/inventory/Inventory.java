package database.inventory;

import database.logs.*;
import database.products.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    /*
    holds the inventory of the company
     */

    private Map<GeneralProduct, ProductHolder> productHolderMap;
    private List<Activity> inventoryActivityList;
    private List<Transaction> transactionList;

    public Inventory(){
        this.productHolderMap = new HashMap<GeneralProduct, ProductHolder>();
        this.inventoryActivityList = new ArrayList<Activity>();
        this.transactionList = new ArrayList<Transaction>();
    }

    // map functions
    public void setProductHolder(GeneralProduct generalProduct, String holderName){
        if(productHolderMap.containsKey(generalProduct))
            return;

        this.productHolderMap.put(generalProduct, new ProductHolder(holderName, generalProduct));
    }

    public void replaceProductHolder(GeneralProduct key, ProductHolder updateHolder){
        if(!productHolderMap.containsKey(key))
            return;

        productHolderMap.replace(key, updateHolder);
    }

    public void removeProductHolder(GeneralProduct key){
        if(!productHolderMap.containsKey(key))
            return;

        productHolderMap.remove(key);
    }

    // getters
    public Activity getInventoryActivityByUid(String uid){
        for(Activity activity: this.inventoryActivityList){
            if(activity.getUid().equals(uid))
                return activity;
        }
        return null;
    }

    public List<Activity> getAllInventoryActivityList(){
        return this.inventoryActivityList;
    }

    public Transaction getTransactionByUid(String uid){
        for(Transaction transaction: this.transactionList){
            if(transaction.getUid().equals(uid))
                return transaction;
        }
        return null;
    }

    public List<Transaction> getAllTransactionList(){
        return this.transactionList;
    }

    public Map<GeneralProduct, ProductHolder> getProductHolderMap(){    return this.productHolderMap;   }
}
