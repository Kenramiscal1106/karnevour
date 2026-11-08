package database.products;

public class Product extends Item {
    protected double price;

    public Product(SpecificItem specificProductType, String description, double price, String alternativeName) {
        super(specificProductType, description, alternativeName);
        this.price = price;
    }

    public Product(SpecificItem specificProductType, String description, double price) {
        super(specificProductType, description);
        this.price = price;
    }

    // getters
    public double getPrice() {return this.price;}

    // setters
    /*
    make sure user instance of admin
     */
    public void setPrice(double price) {
        if (price<=0)
            throw new IllegalArgumentException("Negative number passed as price");
        this.price = price;
    }


}
