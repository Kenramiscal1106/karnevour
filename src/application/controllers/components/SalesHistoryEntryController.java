package application.controllers.components;

import database.inventory.Inventory;
import database.logs.SaleLog;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;

public class SalesHistoryEntryController implements EntryControllerBase<SaleLog> {
    @FXML
    public Text priceLabel;
    @FXML
    public Text entryQuantity;
    @FXML
    public Text entryName;
    @FXML
    public ImageView imageDisplay;

    public String textContent;


    @Override
    public void initData(SaleLog saleLog) {
        priceLabel.setText(String.format("₱%,.0f", saleLog.value));
        var holder = Inventory.getInstance().getItemHolderByUid(saleLog.productUid);
        textContent = holder.getItemAlternativeName().isEmpty() ? holder.getItem().getItemName() : holder.getItemAlternativeName();
        entryName.setText(textContent);
        entryQuantity.setText(formatQty(saleLog.qty) + " " + holder.getUnit());
        imageDisplay.setImage(new Image(holder.getItem().getImagePath(), true));
    }

    private String formatQty(double qty){
        return qty == Math.floor(qty) ? String.valueOf((int) qty) : String.valueOf(qty);
    }
}
