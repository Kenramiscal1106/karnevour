package database.inventory;

import database.logs.*;
import database.products.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    /*
    holds the inventory of the product
     */

    private Map<GeneralProduct, ProductHolder> productHolderMap;
    private double cash;
    private List<Activity> inventoryActivityList;
    private List<Transaction> transactionList;

    public Inventory(){
        this.productHolderMap = new HashMap<GeneralProduct, ProductHolder>();
        this.cash = 0;
        this.inventoryActivityList = new ArrayList<Activity>();
        this.transactionList = new ArrayList<Transaction>();
    }

    public void setProductHolder(GeneralProduct generalProduct, ProductHolder specificProductHolder){
        if(productHolderMap.containsKey(generalProduct))
            return;

        this.productHolderMap.put(generalProduct,specificProductHolder);
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

}
