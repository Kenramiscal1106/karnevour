package application.controllers.views;

import database.inventory.ItemHolder;

public class SubtractInventoryController extends OperationInventoryController{
    @Override
    protected String getActionType(){
        return "sell";
    }

    @Override
    protected String validate(ItemHolder itemHolder, double amount){
        if (amount > itemHolder.getItemInStock()){
            return "Only " + itemHolder.getItemInStock() + " " + itemHolder.getUnit() + " in stock.";
        }
        return null;
    }
}
