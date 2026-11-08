package database.products;

import java.io.File;

public abstract class Item {
    protected String uid;
    protected SpecificItem specificItem;
    protected File imagePath;
    protected String unit;
    protected String description;
    protected String alternativeName;

    Item(String uid, SpecificItem specificItem, String unit, String description, String alternativeName) {
        this.uid = uid;
        this.specificItem = specificItem;
        this.imagePath = new File("/pictures/" + alternativeName).exists() ? new File("/pictures/" + alternativeName) : new File("/pictures/" + specificItem);
        this.unit = unit;
        this.description = description;
        this.alternativeName = alternativeName;
    }

    Item(String uid, SpecificItem specificItem, String unit, String description) {
        this.uid = uid;
        this.specificItem = specificItem;
        this.imagePath = new File("/pictures/" + specificItem);
        this.unit = unit;
        this.description = description;
        this.alternativeName = "";
    }

    // getters
    public String getUid() {return this.uid;}
    public SpecificItem getSpecificItemType() {return this.specificItem;}
    public GeneralItem getGeneralItem() {return this.specificItem.getGeneralProductType();}
    public String getDescription() {return this.description;}
    public String getAlternativeName() { return this.alternativeName; }
    public String getImagePath() {return this.imagePath.toString();}
    public String getUnit() {return this.unit;}
    // setters
    /*
    make sure user instance of admin before setting
     */
    public void setDescription(String desc) {this.description=description;}
    public void setUnit(String unit) {this.unit=unit;}
    public void setAlternativeName(String altName) {this.alternativeName=altName;}
}
