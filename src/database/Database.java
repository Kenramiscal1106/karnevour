package database;

import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.logs.Activity;
import database.logs.Logable;
import database.logs.Transaction;
import database.products.*;
import database.user.Admin;
import database.user.Clerk;
import database.user.User;
import javafx.collections.ObservableList;

import java.io.*;

public class Database {

    private static final File ITEMHOLDER_MAP_DEFAULT_FILE = new File("data/itemholder.kar");
    private static final File ACTIVTIY_LIST_DEFAULT_FILE = new File("data/activitylist.kar");
    private static final File TRANSACTION_LIST_DEFAULT_FILE = new File("data/activitylist.kar");
    private static final File USER_STAFF_LIST_DEFAULT_FILE = new File("data/stafflist.kar");

    public synchronized void saveInventoryToDatabase(ObservableList<ItemHolder> itemHolderList) {
        if(itemHolderList==null)
            throw new IllegalArgumentException("No Inventory found");

        File parentFolder = ITEMHOLDER_MAP_DEFAULT_FILE.getParentFile();
        if(parentFolder != null && !parentFolder.exists())
            parentFolder.mkdirs();

        /*
        big line of code yup, cant believe this will be the same for users and logs
         */
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMHOLDER_MAP_DEFAULT_FILE, false))){
            writer.write("Class,Uid,SpecificItemType,Unit,Description,{Price},AlternativeName,ItemInStock,LowerLimitStock");
            writer.newLine();
            itemHolderList.forEach(( itemHolder)->{
                Item rawItem = itemHolder.getItem();
                String isProduct = "-1";
                if (rawItem instanceof Product product){
                    isProduct = String.valueOf(product.getPrice());
                }
                String saveLine = rawItem.getClass().getSimpleName()+","+
                        itemHolder.getUid()+","+
                        rawItem.getSpecificItemType()+","+
                        itemHolder.getUnit()+","+
                        "\""+rawItem.getDescription()+"\""+","+
                        isProduct+","+
                        "\""+rawItem.getAlternativeName()+"\""+","+
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

    public synchronized void saveLogsToDatabase(ObservableList<Logable> activityList){
        if (activityList==null)
            throw new IllegalArgumentException("No logs found");

        File defaultFile = activityList.getFirst().getClass().getName().equals("Activity")? ACTIVTIY_LIST_DEFAULT_FILE:TRANSACTION_LIST_DEFAULT_FILE;
        File parentFolder = defaultFile.getParentFile();
        if(parentFolder != null && !parentFolder.exists())
            parentFolder.mkdirs();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(defaultFile, false))){
            writer.write("Name,Date,Description,Author,Uid");
            writer.newLine();
            activityList.forEach(log->{
                String saveLine = log.getName()+","+
                        log.getDate()+","+
                        "\""+log.getDescription()+"\""+","+
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

    public synchronized boolean saveUsersToDatabase(User user, String operation){
        if (user==null)
            throw new IllegalArgumentException("No users found");

        File parentFolder = USER_STAFF_LIST_DEFAULT_FILE.getParentFile();
        boolean needHeader = USER_STAFF_LIST_DEFAULT_FILE.exists() || USER_STAFF_LIST_DEFAULT_FILE.length()==0;
        if(parentFolder != null && !parentFolder.exists())
            parentFolder.mkdirs();

        String session = "offline";
        switch (operation.trim().toLowerCase()){
            case "signin" -> session = "online";
            case "logout" -> session = "offline";
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(USER_STAFF_LIST_DEFAULT_FILE, true))){

            if(needHeader){
                writer.write("Class,Name,Uid,Password,Login_Status");
                writer.newLine();
            }

            if(userExists(user.getUid())) {
                return false;
            }

            writer.write(
                    user.getClass().getSimpleName()+","+
                            user.getName()+","+
                            user.getUid()+","+
                            user.getPassword()+
                            session
            );
            writer.newLine();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized User loadUserFromDatabase(String name, String password) {
        if(!USER_STAFF_LIST_DEFAULT_FILE.exists())
            return null;

        try(BufferedReader reader = new BufferedReader(new FileReader(USER_STAFF_LIST_DEFAULT_FILE))){
            String line;
            reader.readLine();
            while((line=reader.readLine())!=null){
                String[] rawText = line.split(",");
                if(rawText[1].equals(name) && rawText[3].equals(password) && rawText[4].equals("offline"))
                    switch(rawText[0]){
                        case "Admin" -> {
                            return new Admin(rawText[1], rawText[2], rawText[3]);
                        }
                        case "Clerk" -> {
                            return new Clerk(rawText[1], rawText[2], rawText[3]);
                        }
                    }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public synchronized void loadFromDatabase(Inventory inventory) throws FileNotFoundException {
        /*
        should work for now >w<
         */

        loadItemHolder(inventory);
        loadLogs(inventory);

    }

    private synchronized void loadLogs(Inventory inventory) {
        String[] logs = {"Activity", "Transaction"};
        String line;
        for (String log: logs){
            File defaultFile = log.equals("Activity")? ACTIVTIY_LIST_DEFAULT_FILE:TRANSACTION_LIST_DEFAULT_FILE;
            try(BufferedReader reader = new BufferedReader(new FileReader(defaultFile))){
                reader.readLine();
                while((line=reader.readLine())!=null){
                    String[] rawText = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    Logable rawLog = log.equals("Activity")? new Activity(rawText[0],rawText[1],rawText[2],rawText[3],rawText[4]): new Transaction(rawText[0],rawText[1],rawText[2],rawText[3],rawText[4]);
                    inventory.addToLogList(rawLog);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

   }

    private synchronized void loadItemHolder(Inventory inventory){
        try(BufferedReader reader = new BufferedReader(new FileReader(ITEMHOLDER_MAP_DEFAULT_FILE))){
            String line;
            reader.readLine();
            while((line=reader.readLine())!=null){
                if(line.isBlank())
                    continue;
                SpecificItem enumConst = null;
                String[] rawText = line.split((",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
                enumConst = SpecificItem.valueOf(rawText[2]);
                Item rawItem = getRawItem(rawText, enumConst);
                inventory.addItem(rawText[1], rawItem, rawText[3], Double.parseDouble(rawText[7]), Double.parseDouble(rawText[8]));
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Item getRawItem(String[] rawText, SpecificItem enumConst) {
        Item rawItem = null;
        if (rawText[0].equals("Product")){
            rawItem = new Product(
                    enumConst,
                    rawText[4].substring(1, rawText[4].length()-1),
                    Double.parseDouble(rawText[5]),
                    rawText[6].substring(1, rawText[6].length()-1)
            );
        } else {
            rawItem = new FarmItem(
                    enumConst,
                    rawText[4].substring(1, rawText[4].length()-1),
                    rawText[6].substring(1, rawText[6].length()-1)
            );
        }
        return rawItem;
    }

    private static boolean userExists(String uid) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(USER_STAFF_LIST_DEFAULT_FILE))) {
            String line;
            reader.readLine();
            while((line = reader.readLine())!=null){
                if(line.isEmpty())
                    continue;
                String[] rawText = line.split(",");
                if(rawText.length==3 && rawText[2].equals(uid.trim()))
                    return true;
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
