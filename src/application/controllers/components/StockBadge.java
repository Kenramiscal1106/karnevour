package application.controllers.components;

import database.inventory.ItemHolder;
import javafx.scene.control.Label;

public final class StockBadge {
    private StockBadge(){}

    public static void apply(Label stockBadge, ItemHolder itemHolder) {
        stockBadge.getStyleClass().removeAll("stock-badge--low", "stock-badge--out");
        if (itemHolder.isOutOfStock()) {
            stockBadge.setText("Out of Stock");
            stockBadge.getStyleClass().add("stock-badge--out");
            showBadge(stockBadge, true);
        } else if (itemHolder.isLowInStock()) {
            stockBadge.setText("Low in Stock");
            stockBadge.getStyleClass().add("stock-badge--low");
            showBadge(stockBadge,true);
        } else {
            showBadge(stockBadge,false);
        }
    }

    private static void showBadge(Label stockBadge, boolean show){
        stockBadge.setVisible(show);
        stockBadge.setManaged(show);
    }
}
