package database;

import database.inventory.Inventory;
import database.inventory.ProductHolder;
import database.products.GeneralProduct;
import database.products.Product;
import database.products.SpecificProduct;

import java.io.*;
import java.util.Map;

public class Database {

    private final File INVENTORY_DEFAULT_PATH = new File("data/inventory.kar");

    public void saveToDatabase(Inventory inventory){
        File parentFolder = INVENTORY_DEFAULT_PATH.getParentFile();
        if(parentFolder != null && !parentFolder.exists())
            parentFolder.mkdirs();

        /*
        big line of code yup, cant believe this will be the same for users and logs
         */
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_DEFAULT_PATH, false))){
           for (Map.Entry<GeneralProduct, ProductHolder> productHolderEntry: inventory.getProductHolderMap().entrySet()){
               String productHolderInfo = productHolderEntry.getKey().toString()+"|"+
                                          productHolderEntry.getValue().getHolderName();
               writer.write(productHolderInfo);
               writer.newLine();
               productHolderEntry.getValue().getAllProducts().forEach(product -> {
                   // dont save image for now as how it is implemented
                   String productInfo = product.getUid()+"|"+
                                        product.getNickName()+"|"+
                                        product.getSpecificProductType().toString()+"|"+
                                        product.getPrice()+"|"+
                                        product.getUnit()+"|"+
                                        product.getDescription()+"|"+
                                        productHolderEntry.getValue().getProductInInventory(product);
                   try {
                       writer.write(productInfo);
                       writer.newLine();
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               });
           }


        } catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    public void loadFromDatabase(Inventory inventory){
        /*
        should work for now >w< --reabas
         */
        try(BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_DEFAULT_PATH))){
            String line;
            GeneralProduct currType = null;
            while((line=reader.readLine())!=null){
                if(line.isBlank())
                    break;

                String[] rawText = line.split("\\|");
                if(rawText.length==2){
                    String enumConst = rawText[0].trim();
                    currType = GeneralProduct.valueOf(enumConst);
                    inventory.setProductHolder(GeneralProduct.valueOf(enumConst),rawText[1]);
                } else if(rawText.length==7){
                    Product rawProduct = new Product(
                            rawText[0],
                            rawText[1],
                            SpecificProduct.valueOf(rawText[2].trim()),
                            Double.parseDouble(rawText[3]),
                            rawText[4],
                            rawText[5]
                    );
                    inventory.getProductHolderMap().get(currType).setProduct(rawProduct, Double.parseDouble(rawText[6]));
                }
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
