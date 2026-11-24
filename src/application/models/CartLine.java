package application.models;

import database.inventory.ItemHolder;
import database.items.Product;
import javafx.fxml.FXML;

public class CartLine {

    private final ItemHolder itemHolder;
    private double qty;

    public CartLine(ItemHolder itemHolder){
        this.itemHolder = itemHolder;
        this.qty = Math.min(1, itemHolder.getItemInStock());
    }
    public ItemHolder getItemHolder() { return this.itemHolder; }
    public double getQty() { return this.qty; }
    public double getTotal() { return getPrice() * qty; }

    public void setQty(double qty) { this.qty = qty; }
    public double getPrice() { return ((Product) this.itemHolder.getItem()).getPrice(); }

}
