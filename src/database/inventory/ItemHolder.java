package database.inventory;

import database.products.GeneralItem;
import database.products.Item;
import database.products.SpecificItem;

public class ItemHolder {
    private final Item item;
    private double itemInStock;
    private double lowStockLimit;

    ItemHolder(Item item, double itemInStock, double lowStockLimit){
        this.item = item;
        this.itemInStock = itemInStock;
        this.lowStockLimit = lowStockLimit;
    }

    // getters
    public Item getItem() {return this.item;}
    public double getItemInStock() {return this.itemInStock;}
    public double getLowStockLimit() {return this.lowStockLimit;}

    // shortcuts
    public String getItemUid() {return this.getItem().getUid();}
    public SpecificItem getItemSpecificType() {return this.getItem().getSpecificItemType();}
    public GeneralItem getItemGeneralType() {return this.getItemSpecificType().getGeneralProductType();}
    public String getItemDescription() {return this.getItem().getDescription();}
    public String getItemAlternativeName() { return this.getItem().getAlternativeName(); }

    // setters
    public void updateItemInStock(double stock, String operation){
        if (stock<0)
            throw new IllegalArgumentException("Negative stock passed");

        switch (operation.toLowerCase()){
            case "add":
                this.itemInStock+=stock;
                break;
            case "subtract":
                if (stock>this.getItemInStock())
                    throw new IllegalArgumentException("Item in stock results in negative");
                this.itemInStock-=stock;
                break;
            case "override":
                this.itemInStock = stock;
                break;
        }
    }

    public void setLowStockLimit(double stockLimit){
        if (stockLimit<0)
            throw new IllegalArgumentException("Limit cannot be in negative");
        this.lowStockLimit = stockLimit;
    }

    public boolean isLowInStock(){
        return this.getItemInStock() < this.getLowStockLimit();
    }
}
