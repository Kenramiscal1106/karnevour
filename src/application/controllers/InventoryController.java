package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.services.*;
import database.products.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

public class InventoryController implements Initializable {
	private final InventoryService service = new InventoryService();
	
	@FXML
	private TilePane grid;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		refresh();
	}
	
	private void refresh() {
		grid.getChildren().clear();
		
		// load the inventory

	}
	
	// for adding items
	private HBox makeCard(Item item) {
		Label name = new Label(item.getSpecificItemType().toString());
		Label quantity = new Label(item.getUnit().toString());
		
		// add other elements
		
		HBox card = new HBox();
		return card;
		
	}
	
	// fetching image for the item
	private Image loadImage(Item item) {
		String path = item.getImagePath();
		var url = getClass().getResource(path);
		
		if (url == null) {
			url = getClass().getResource("database/pictures/default.png");
		}
		return new Image(url.toExternalForm());
	}
	

	// (TO BE DONE): addItem, removeItem, restockItem
}
