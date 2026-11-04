package database.products;

import java.util.Objects;

public class Product {
    private String uid;
    private String nickName;
    private SpecificProduct specificProductType;
    private double price;
    private String unit;
    private String description;
    private String imagePath;

    Product(String uid, String nickName, SpecificProduct specificProductType, double price, String unit, String description){
        try{
            this.uid = Objects.requireNonNull(uid, "Null ");
            this.specificProductType = Objects.requireNonNull(specificProductType);
        } catch (Exception e){
            e.printStackTrace();
        }

        this.nickName = nickName;
        this.price = price;
        this.unit = unit;
        this.description = description;
        this.imagePath = null; // "/nickname_path".exists() ? "/nickname_path":"/default_specific_path"
    }

    //overrides for map
    @Override
    public boolean equals(Object o){
        if(this==o)
            return true;

        if(o==null || o.getClass()!=this.getClass())
            return false;

        Product product = (Product) o;

        // for now strict implementation
        return (
                Objects.equals(product.getUid(), this.getUid()) &&
                Objects.equals(product.getNickName(), this.getNickName()) &&
                product.getPrice() == this.getPrice() &&
                product.getSpecificProductType() == this.getSpecificProductType() &&
                Objects.equals(product.getUnit(), this.getUnit()) &&
                Objects.equals(product.getDescription(), this.getDescription())
                );
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.uid);
    }

    // getters
    public String getNickName()                     {   return this.nickName;   }
    public SpecificProduct getSpecificProductType() {   return this.specificProductType;    }
    public GeneralProduct getGeneralProductType()   {   return this.specificProductType.getGeneralProductType();    }
    public double getPrice()                        {   return this.price;  }
    public String getUnit()                         {   return this.unit;   }
    public String getDescription()                  {   return this.description;    }
    public String getImagePath()                    {   return this.imagePath;  }
    public String getUid()                          {   return this.uid; }

    // setters
    public void setPrice(int price){
        if(price<=0) {
            System.out.println("Price less than or equals to 0");
            return;
        }
        this.price=price;
    }

    public void setDescription(String desc)         {   this.description = desc;    }

}
