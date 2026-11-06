package database.inventory;

import database.products.GeneralProduct;
import database.products.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ProductHolder {
    /*
    is different from the database itself, holds all products from a specific type using general type,
    DO NOT mix in other types
     */
    private String holderName;
    private GeneralProduct generalProductHold;
    private Map<Product, Double> productMap;

    ProductHolder(String holderName, GeneralProduct generalProductHold){
        this.generalProductHold = Objects.requireNonNull(generalProductHold);
        this.holderName = holderName;
        this.productMap = new HashMap<>();
    }

    public void setProduct(Product product, double amount){
        Objects.requireNonNull(product, "Null passed");

        if(amount<0)
            return;

        if(productMap.containsKey(product))
            updateProductCount(product, amount,"add");
        else
            this.productMap.put(product, amount);
    }

    void updateProductCount(Product product, double amount, String operation){
        /*
        For business logic people, this func takes in the product, amount, and the operation needed to be done
         */
        double toAdd = operation.equalsIgnoreCase("subtract") ? -amount:amount;

        if(product.getGeneralProductType() != this.generalProductHold)
            return;

        if(!this.productMap.containsKey(product))
            return;

        if(0>toAdd)
            return;

        this.productMap.compute(product, (key,value) -> {
            double newVal = (value==null) ? toAdd:value+toAdd;

            return (newVal>=0) ? newVal : null;
        });
    }

    void removeProduct(Product product) {
        if (!this.productMap.containsKey(product)) {
            return;
        }
        this.productMap.remove(product);
    }

    // getters
    public String getHolderName()                   {  return this.holderName;  }
    public GeneralProduct getGeneralProductHold()   {  return this.generalProductHold;  }
    public Set<Product> getAllProducts()            {  return this.productMap.keySet(); }
    public double getProductInInventory(Product product)     {  return this.productMap.get(product); }
    public Product getProductByUid(String uid){
        for(Product product: this.productMap.keySet()){
            if(product.getUid().equals(uid)){
                return product;
            }
        }
        return null;
    }
}
