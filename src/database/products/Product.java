package database.products;

public class Product extends Item {
    protected double price;

    public Product(String uid, SpecificItem specificProductType, String unit, String description, double price, String alternativeName) {
        super(uid, specificProductType, unit, description, alternativeName);
        this.price = price;
    }

    public Product(String uid, SpecificItem specificProductType, String unit, String description, double price) {
        super(uid, specificProductType, unit, description);
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
