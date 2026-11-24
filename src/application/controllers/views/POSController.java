package application.controllers.views;

import application.controllers.components.POSAvailableSelectionEntryController;
import application.controllers.components.POSSelectionEntryController;
import application.models.CartLine;
import application.services.SalesOperations;
import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.items.Product;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;

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

    private final SalesOperations operations = new SalesOperations();

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

        cart.addListener((ListChangeListener<CartLine>) change -> updateTotal());
        updateTotal();
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
        totalText.setText(String.format("₱%,.2f", total));
    }

    private void confirm(){
        for (CartLine line: cart){
            var holder = line.getItemHolder();
            if (line.getQty() <= 0 || line.getQty() > holder.getItemInStock()){
                showError(holder.getItemDescription() + ": quantity must be more than 0 or at most " + holder.getItemInStock() + " " + holder.getUnit() + ".");
                return;
            }
        }

        var service = operations.new checkoutService(cart);
        service.setOnSucceeded(e -> close());
        service.setOnFailed(e -> showError(service.getException().getMessage()));
        service.start();
    }

    private void showError(String message){
        new Alert(Alert.AlertType.WARNING, message).showAndWait();
    }

    private void close(){
        cancelButton.getScene().getWindow().hide();
    }

}
