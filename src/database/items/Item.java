package database.items;

import java.io.File;

public abstract class Item {
    protected SpecificItem specificItem;
    protected File imagePath;
    protected String description;
    protected String alternativeName;

    Item(SpecificItem specificItem, String description, String alternativeName) {
        this.specificItem = specificItem;
        this.description = description;
        this.alternativeName = alternativeName.trim();

        File altImage = new File("src/pictures/" + alternativeName + ".jpg");
        File specificImage = new File("/pictures/" + specificItem + ".jpg");

        if(altImage.exists())
            this.imagePath = altImage;
        else if(specificImage.exists())
            this.imagePath = specificImage;
        else
            this.imagePath = new File("src/pictures/DEFAULT_PICTURE.jpg");
    }

    Item(SpecificItem specificItem, String description) {
        this.specificItem = specificItem;
        this.description = description;
        this.alternativeName = "";

        File specificImage = new File("/pictures/" + specificItem + ".jpg");

        if (specificImage.exists())
            this.imagePath = specificImage;
        else
            this.imagePath = new File("src/pictures/DEFAULT_PICTURE.jpg");
    }

    // getters
    public SpecificItem getSpecificItemType() {return this.specificItem;}
    public GeneralItem getGeneralItem() {return this.specificItem.getGeneralProductType();}
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
