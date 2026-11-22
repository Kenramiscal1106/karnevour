package database.logs;

import database.inventory.Inventory;
import database.inventory.ItemHolder;

public class InventoryLog extends Log {

    public final String name;
    public final String date;
    public final String description;
    public final String author;
    public final String uid;
    public final String itemUid;

    public InventoryLog(String name, String date, String description, String author, String uid, String itemUid) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.author = author;
        this.uid = uid;
        this.itemUid = itemUid;
    }
    public String toCSVFormat() {
        return  this.getClass().getSimpleName()+","+
                "\""+name+"\"" + "," +
                date +"," +
                "\""+description+"\"" + "," +
                "\""+author+"\"" + "," +
                uid + "," +
                itemUid;
    }
    @Override
    public String logMessage() {
        return description;
    }
}
