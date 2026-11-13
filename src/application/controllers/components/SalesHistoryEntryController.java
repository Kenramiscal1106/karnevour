package application.controllers.components;

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

        priceLabel.setText("₱" + (saleLog.value));
//        secondaryText.setText();
    }
}
