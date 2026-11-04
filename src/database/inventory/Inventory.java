package database.inventory;

import database.logs.*;
import database.products.*;

import java.util.List;
import java.util.Map;

public class Inventory {
    /*
    holds the inventory of the product
     */

    private Map<String, ProductHolder> productHolderMap;
    private double cash;
    private List<Activity> inventoryActivityList;
    private List<Transaction> transactionList;


}
