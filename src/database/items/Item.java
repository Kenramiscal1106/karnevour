package database.items;

import database.Database;
import database.ImageCache;

import java.io.File;
import java.io.FileNotFoundException;


public abstract class Item {

    private static final File PICTURE_CACHE_FILE = new File("src/database/data/picturecache");
    protected final Database db = Database.getInstance();

    protected String itemName;
    protected String description;
    protected String alternativeName;
    protected String imagePath;

    Item(String itemName, String description, String alternativeName) {
        this.itemName = itemName;
        this.description = description;
        this.alternativeName = alternativeName.trim();

        // later u change picture path

    }

    Item(String itemName, String description) {
        this.itemName = itemName;
        this.description = description;
        this.alternativeName = "";

        // later u change picture path

    }

    // getters
    public String getItemName() {return this.itemName;}
    public String getDescription() {return this.description;}
    public String getAlternativeName() { return this.alternativeName; }
    public String getImagePath() {
        try{
            return db.loadImageURLPath(itemName);
        } catch (FileNotFoundException e) {
            return "src/pictures/default-picture.png";
        }
    }
    // setters
    /*
    make sure user instance of admin before setting
     */
    public void setDescription(String desc) {this.description=desc;}
    public void setAlternativeName(String altName) {this.alternativeName=altName;}
}
