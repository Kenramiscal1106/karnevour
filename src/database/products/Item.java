package database.products;

import java.io.File;

public abstract class Item {
    protected SpecificItem specificItem;
    protected File imagePath;
    protected String unit;
    protected String description;
    protected String alternativeName;

    Item(SpecificItem specificItem, String unit, String description, String alternativeName) {
        this.specificItem = specificItem;
        this.imagePath = new File("/pictures/" + alternativeName +".jpg").exists() ? new File("/pictures/" + alternativeName +".jpg") : new File("/pictures/" + specificItem +".jpg");
        this.unit = unit;
        this.description = description;
        this.alternativeName = alternativeName;
    }

    Item(SpecificItem specificItem, String unit, String description) {
        this.specificItem = specificItem;
        this.imagePath = new File("/pictures/" + specificItem +".jpg");
        this.unit = unit;
        this.description = description;
        this.alternativeName = "";
    }

    // getters
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
