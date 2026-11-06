package database;

import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.logs.Activity;
import database.logs.Logable;
import database.logs.Transaction;
import database.products.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Database {

    private final File ITEMHOLDER_MAP_DEFAULT_FILE = new File("data/itemholder.kar");
    private final File ACTIVTIY_LIST_DEFAULT_FILE = new File("data/activitylist.kar");
    private final File TRANSACTION_LIST_DEFAULT_FILE = new File("data/activitylist.kar");

    public void saveToDatabase(Map<String, ItemHolder> itemHolderMap) {
        File parentFolder = ITEMHOLDER_MAP_DEFAULT_FILE.getParentFile();
        if(parentFolder != null && !parentFolder.exists())
            parentFolder.mkdirs();

        /*
        big line of code yup, cant believe this will be the same for users and logs
         */
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMHOLDER_MAP_DEFAULT_FILE, false))){
            writer.write("Class,Uid,SpecificItemType,Unit,Description,{Price},AlternativeName,ItemInStock,LowerLimitStock");
            itemHolderMap.forEach((id, itemHolder)->{
                Item rawItem = itemHolder.getItem();
                String isProduct = "-1";
                if (rawItem instanceof Product product){
                    isProduct = String.valueOf(product.getPrice());
                }
                String saveLine = rawItem.getClass().getName()+","+
                        rawItem.getUid()+","+
                        rawItem.getSpecificItemType()+","+
                        rawItem.getUnit()+","+
                        rawItem.getDescription()+","+
                        isProduct+","+
                        rawItem.getAlternativeName()+","+
                        itemHolder.getItemInStock()+","+
                        itemHolder.getLowStockLimit();
                try {
                    writer.write(saveLine);
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    public void saveToDatabase(List<Logable> activityList){
        File defaultFile = activityList.getFirst().getClass().getName().equals("Activity")? ACTIVTIY_LIST_DEFAULT_FILE:TRANSACTION_LIST_DEFAULT_FILE;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(defaultFile, false))){
            writer.write("Name,Date,Description,Author,Uid");
            activityList.forEach(log->{
                String saveLine = log.getName()+","+
                        log.getDate()+","+
                        log.getDescription()+","+
                        log.getAuthor()+","+
                        log.getUid();
                try {
                    writer.write(saveLine);
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFromDatabase(Inventory inventory) throws FileNotFoundException {
        /*
        should work for now >w<
         */

        loadItemHolder(inventory);
        loadLogs(inventory);

    }

   private void loadLogs(Inventory inventory) {
        String[] logs = {"Activity", "Transaction"};
        String line;
        for (String log: logs){
            File defaultFile = log.equals("Activity")? ACTIVTIY_LIST_DEFAULT_FILE:TRANSACTION_LIST_DEFAULT_FILE;
            try(BufferedReader reader = new BufferedReader(new FileReader(defaultFile))){
                reader.readLine();
                while((line=reader.readLine())!=null){
                    String[] rawText = line.split(",");
                    Logable rawLog = log.equals("Activity")? new Activity(rawText[0],rawText[1],rawText[2],rawText[3],rawText[4]): new Transaction(rawText[0],rawText[1],rawText[2],rawText[3],rawText[4]);
                    inventory.addToLogList(rawLog);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

   }

    private void loadItemHolder(Inventory inventory){
        try(BufferedReader reader = new BufferedReader(new FileReader(ITEMHOLDER_MAP_DEFAULT_FILE))){
            String line;
            reader.readLine();
            while((line=reader.readLine())!=null){
                if(line.isBlank())
                    break;
                SpecificItem enumConst = null;
                String[] rawText = line.split(",");
                enumConst = SpecificItem.valueOf(rawText[2]);
                if (rawText[0].equals("Product")){
                    Item rawItem = new Product(
                            rawText[1],
                            enumConst,
                            rawText[3],
                            rawText[4],
                            Double.parseDouble(rawText[5]),
                            rawText[6]
                    );
                } else {
                    Item rawItem = new Consumable(
                            rawText[1],
                            enumConst,
                            rawText[3],
                            rawText[4],
                            rawText[6]
                    );
                    inventory.addItemToInventory(rawItem,Double.parseDouble(rawText[7]), Double.parseDouble(rawText[8]));
                }
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
