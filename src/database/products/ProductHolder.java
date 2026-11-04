package database.products;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProductHolder {
    /*
    is different from the database itself, holds all products from a specific type using general type,
    DO NOT mix in other types
     */
    private String holderName;
    private GeneralProduct generalProductHold;
    private Map<Product, Integer> productMap;

    ProductHolder(String holderName, GeneralProduct generalProductHold){
        this.holderName = holderName;
        this.generalProductHold = generalProductHold;
        this.productMap = new HashMap<>();
    }

    void updateProductCount(Product product, int amount, String operation){
        /*
        For business logic people, this func takes in the product, amount, and the operation needed to be done
         */
        if(product.getGeneralProductType() != this.generalProductHold)
            return;

        int toAdd = operation.equalsIgnoreCase("subtract") ? -amount:amount;
        this.productMap.compute(product, (key,value) -> {
            int newVal = (value==null) ? toAdd:value+toAdd;

            return (newVal<0) ? null : newVal;
        });
    }

    void removeProduct(Product product){
        if(!containsProduct(product)){
            return;
        }
        this.productMap.remove(product);
    }

    // validation
    public boolean containsProduct(Product product) {   return this.productMap.containsKey(product);    }

    // getters
    public String getHolderName()                   {  return this.holderName;  }
    public GeneralProduct getGeneralProductHold()   {  return this.generalProductHold;  }
    public Set<Product> getAllProducts()            {  return this.productMap.keySet(); }
    public int getProductCount(Product product)     {  return this.productMap.get(product); }
    public Product getProductByUid(String uid){
        for(Product product: this.productMap.keySet()){
            if(product.getUid().equals(uid)){
                return product;
            }
        }
        return null;
    }
}
