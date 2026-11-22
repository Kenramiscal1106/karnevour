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


    @Override
    public void initData(SaleLog saleLog) {
        priceLabel.setText(String.format("₱%,.0f", saleLog.value));
        var holder = Inventory.getInstance().getItemHolderByUid(saleLog.productUid);
        entryName.setText(holder.getItemDescription());
        entryQuantity.setText(formatQty(saleLog.qty) + " " + holder.getUnit());
        File file = new File(holder.getItem().getImagePath());
        imageDisplay.setImage(new Image(file.toURI().toString()));
    }

    private String formatQty(double qty){
        return qty == Math.floor(qty) ? String.valueOf((int) qty) : String.valueOf(qty);
    }
}
