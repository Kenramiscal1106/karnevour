package database.products;

import java.io.File;

public abstract class Item {
    protected SpecificProduct specificProduct;
    protected File imagePath;
    protected String description;
    protected String alternativeName;

    Item(SpecificProduct specificProduct, String description, String alternativeName) {
        this.specificProduct = specificProduct;
        this.imagePath = new File("/pictures/" + alternativeName +".jpg").exists() ? new File("/pictures/" + alternativeName +".jpg") : new File("/pictures/" + specificProduct +".jpg");
        this.description = description;
        this.alternativeName = alternativeName.trim();
    }

    Item(SpecificProduct specificProduct, String description) {
        this.specificProduct = specificProduct;
        this.imagePath = new File("/pictures/" + specificProduct +".jpg");
        this.description = description;
        this.alternativeName = "";
    }

    // getters
    public SpecificProduct getSpecificItemType() {return this.specificProduct;}
    public GeneralProduct getGeneralItem() {return this.specificProduct.getGeneralProductType();}
    public String getDescription() {return this.description;}
    public String getAlternativeName() { return this.alternativeName; }
    public String getImagePath() {return this.imagePath.toString();}
    // setters
    /*
    make sure user instance of admin before setting
     */
    public void setDescription(String desc) {this.description=description;}
    public void setAlternativeName(String altName) {this.alternativeName=altName;}
}
