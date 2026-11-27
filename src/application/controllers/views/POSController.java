package application.controllers.views;

import application.controllers.components.POSAvailableSelectionEntryController;
import application.controllers.components.POSSelectionEntryController;
import application.models.CartLine;
import application.services.CheckoutService;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.items.Product;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;

public class POSController {
    @FXML
    public ListView<ItemHolder> choicesList;
    @FXML
    public ListView<CartLine> selectedList;

    private final ObservableList<CartLine> cart = FXCollections.observableArrayList();

    @FXML
    public Text totalText;

    @FXML
    public Button cancelButton;

    @FXML
    public Button checkoutButton;

    public final SimpleDoubleProperty tenderedProperty = new SimpleDoubleProperty(0);

    public final SimpleDoubleProperty netTotalProperty = new SimpleDoubleProperty(0);

    public final SimpleDoubleProperty changeProperty = new SimpleDoubleProperty(0);
    public TextField tenderedField;
    public Text change;
    public TextField soldTo;

    @FXML
    public void initialize(){
        choicesList.setItems(Inventory.getInstance().getItemHolderList().filtered(holder -> holder.getItem() instanceof Product));
        choicesList.setCellFactory(listView -> new ListCell<>(){
            private final Node row;
            private final POSAvailableSelectionEntryController controller;

            {
                try{
                    var loader = new FXMLLoader(getClass().getResource("/ui/components/POSAvailableSelectionEntry.fxml"));
                    row = loader.load();
                    controller = loader.getController();
                    controller.setOnAdd(holder -> addToCart(holder));
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void updateItem(ItemHolder itemHolder, boolean empty){
                super.updateItem(itemHolder, empty);
                if (empty || itemHolder == null){
                    setGraphic(null);
                } else {
                    controller.initData(itemHolder);
                    setGraphic(row);
                }
            }
        });

        selectedList.setItems(cart);
        selectedList.setCellFactory(listView -> new ListCell<>(){
            private final Node row;
            private final POSSelectionEntryController controller;

            {
                try{
                    var loader = new FXMLLoader(getClass().getResource("/ui/components/POSSelectionEntry.fxml"));
                    row = loader.load();
                    controller = loader.getController();
                    controller.setOnRemove( line -> cart.remove(line));
                    controller.setOnChange(() -> updateTotal());
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void updateItem(CartLine line, boolean empty){
                super.updateItem(line, empty);
                if (empty || line == null){
                    setGraphic(null);
                } else {
                    controller.initData(line);
                    setGraphic(row);
                }
            }
        });
        checkoutButton.disableProperty().bind(Bindings.isEmpty(cart));
        cancelButton.setOnAction(e -> close());
        checkoutButton.setOnAction(e -> confirm());

        totalText.textProperty().bind(Bindings.format("₱%,.2f", netTotalProperty));
        cart.addListener((ListChangeListener<CartLine>) change -> updateTotal());

        Bindings.bindBidirectional(tenderedField.textProperty(), tenderedProperty,  new NumberStringConverter());
        changeProperty.bind(tenderedProperty.subtract(netTotalProperty));
        change.textProperty().bind(Bindings.format("₱%,.2f", changeProperty));

        tenderedProperty.addListener((obs, oldValue, newValue) -> updateTenderedError());
        netTotalProperty.addListener((obs, oldValue, newValue) -> updateTenderedError());
    }

    private void addToCart(ItemHolder itemHolder){
        for(CartLine line:cart) {
            if (line.getItemHolder() == itemHolder) {
                return;
            }
        }
        cart.add(new CartLine(itemHolder));
    }

    private void updateTotal(){
        double total = 0;

        for (CartLine line: cart){
            total += line.getTotal();
        }
        netTotalProperty.set(total);
    }

    private void updateTenderedError(){
        double tendered = tenderedProperty.get();
        double total = netTotalProperty.get();
        boolean tooLow = tendered > 0 && tendered < total;

        if (tooLow) {
            if (!tenderedField.getStyleClass().contains("error")) {
                tenderedField.getStyleClass().add("error");
            }
        } else {
            tenderedField.getStyleClass().remove("error");
        }
    }

    private void confirm(){
        if (tenderedProperty.get() < netTotalProperty.get()) {
            showError(String.format("Tendered must be at least ₱%,.2f.", netTotalProperty.get()));
            return;
        }

        for (CartLine line: cart){
            var holder = line.getItemHolder();
            if (line.getQty() <= 0 || line.getQty() > holder.getItemInStock()){
                showError(holder.getItemDescription() + ": quantity must be more than 0 or at most " + holder.getItemInStock() + " " + holder.getUnit() + ".");
                return;
            }
        }

        var service = new CheckoutService(cart, soldTo.getText());
        service.setOnSucceeded(e -> close());
        service.setOnFailed(e -> showError(service.getException().getMessage()));
        service.start();
    }

    private void markError(TextField field, boolean hasError) {
        if (hasError) {
            if (!field.getStyleClass().contains("error")) {
                field.getStyleClass().add("error");
            }
        } else {
            field.getStyleClass().remove("error");
        }
    }

    private void showError(String message){
        if (tenderedProperty.get() < netTotalProperty.get()) {
            markError(tenderedField, true);
            return;
        }
    }

    private void close(){
        cancelButton.getScene().getWindow().hide();
    }

}
