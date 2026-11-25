package database.items;

import database.Database;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class Item {

    protected final Database db = Database.getInstance();

    protected String itemName;
    protected String imagePath;
    protected String description;
    protected String alternativeName;

    Item(String itemName, String description, String alternativeName) {
        this.itemName = itemName;
        this.description = description;
        this.alternativeName = alternativeName.trim();

        // later u change picture path
        try{
            this.imagePath = db.loadImageURLPath(this.itemName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Item(String itemName, String description) {
        this.itemName = itemName;
        this.description = description;
        this.alternativeName = "";

        // later u change picture path
        try{
            this.imagePath = db.loadImageURLPath(this.itemName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // getters
    public String getItemName() {return this.itemName;}
    public String getDescription() {return this.description;}
    public String getAlternativeName() { return this.alternativeName; }
    public String getImagePath() {return this.imagePath;}
    // setters
    /*
    make sure user instance of admin before setting
     */
    public void setDescription(String desc) {this.description=description;}
    public void setAlternativeName(String altName) {this.alternativeName=altName;}
}
