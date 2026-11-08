package database.inventory;

import database.products.GeneralProduct;
import database.products.Item;
import database.products.SpecificProduct;

public class ItemHolder {
    private final Item item;
    private double itemInStock;
    private double lowStockLimit;
    protected String unit;
    protected String uid;

    ItemHolder(String uid, Item item, String unit, double itemInStock, double lowStockLimit){
        this.uid = uid;
        this.item = item;
        this.unit = unit;
        this.itemInStock = itemInStock;
        this.lowStockLimit = lowStockLimit;
    }

    // getters
    public Item getItem() {return this.item;}
    public double getItemInStock() {return this.itemInStock;}
    public double getLowStockLimit() {return this.lowStockLimit;}

    // shortcuts
    public String getUid() {return this.uid;}
    public SpecificProduct getItemSpecificType() {return this.getItem().getSpecificItemType();}
    public GeneralProduct getItemGeneralType() {return this.getItemSpecificType().getGeneralProductType();}
    public String getItemDescription() {return this.getItem().getDescription();}
    public String getItemAlternativeName() { return this.getItem().getAlternativeName(); }

    public String getUnit() {return this.unit;}

    public void setUnit(String unit) {this.unit=unit;}

    // setters
    void updateItemInStock(double stock, String operation){
        if (stock<0)
            throw new IllegalArgumentException("Negative stock passed");

        switch (operation.toLowerCase()){
            case "add":
                this.itemInStock+=stock;
                break;
            case "subtract":
                this.itemInStock = stock>this.itemInStock ? 0:itemInStock-stock;
                break;
            case "override":
                if (stock>this.itemInStock)
                    throw new IllegalArgumentException("Item in stock results in negative");
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
