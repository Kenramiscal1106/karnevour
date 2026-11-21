package application.controllers.components;

import database.inventory.Inventory;
import database.logs.SaleLog;
import database.logs.Transaction;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SalesHistoryEntryController implements EntryControllerBase<SaleLog> {
    @FXML
    public Text priceLabel;
    @FXML
    public Text secondaryText;
    @FXML
    public Text primaryText;
    @FXML
    public ImageView imageDisplay;


    @Override
    public void initData(SaleLog saleLog) {
        priceLabel.setText("₱%,.0f" + (saleLog.value));
        var holder = Inventory.getInstance().getItemHolderByUid(saleLog.productUid);
        primaryText.setText(holder.getItemDescription());
        secondaryText.setText(formatQty(saleLog.qty) + " " + holder.getUnit());
    }

    private String formatQty(double qty){
        return qty == Math.floor(qty) ? String.valueOf((int) qty) : String.valueOf(qty);
    }
}
