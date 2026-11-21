package database;

import database.inventory.Inventory;
import database.inventory.ItemHolder;
import database.logs.*;
import database.items.*;
import database.user.Admin;
import database.user.Clerk;
import database.user.User;
import javafx.collections.ObservableList;

import java.io.*;

public class Database {

    private final static Database instance = new Database();

    private static final File ITEMHOLDER_LIST_DEFAULT_FILE = new File("src/database/data/itemholder.kar");
    private static final File USER_STAFF_LIST_DEFAULT_FILE = new File("src/database/data/user-list.kar");
    private static final File LOG_LIST_DEFAULT_FILE = new File("src/database/data/log-list.kar");
    private static final File CASH_REGISTER_DEFAULT_FILE = new File("src/database/data/cash-register.kar");

    public static Database getInstance() {return instance;}

    public void saveInventoryToDatabase(ObservableList<ItemHolder> itemHolderList) {
        if(itemHolderList==null)
            throw new IllegalArgumentException("No Inventory found");

        File parentFolder = ITEMHOLDER_LIST_DEFAULT_FILE.getParentFile();
        if(parentFolder != null && !parentFolder.exists())
            parentFolder.mkdirs();

        /*
        big line of code yup, cant believe this will be the same for users and logs
         */
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMHOLDER_LIST_DEFAULT_FILE, false))){
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

    public void saveLogsToDatabase(ObservableList<Loggable> activityList){
        if (activityList==null || activityList.isEmpty())
            throw new IllegalArgumentException("No logs found");

        File defaultFile = LOG_LIST_DEFAULT_FILE;
        File parentFolder = defaultFile.getParentFile();
        if(parentFolder != null && !parentFolder.exists())
            parentFolder.mkdirs();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(defaultFile, false))){
            writer.write("LogType,Name,Date,Description,Author,Uid,ItemUid,{Total},{Qty}");
            writer.newLine();
            activityList.forEach(log->{
                String saveLine = log.toCSVFormat();
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

    public void saveCashRegisterCredentials(CashRegister cashRegister){

        File parentFolder = CASH_REGISTER_DEFAULT_FILE.getParentFile();
        if(parentFolder != null && !parentFolder.exists())
            parentFolder.mkdirs();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(CASH_REGISTER_DEFAULT_FILE, false))) {
            writer.write("CurrentBalance,TotalSales");
            writer.newLine();
            writer.write(cashRegister.getCurrentBalance()+","+cashRegister.getTotalSales());
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    public boolean saveUsersToDatabase(User user){
        if (user==null)
            throw new IllegalArgumentException("No users found");

        File parentFolder = USER_STAFF_LIST_DEFAULT_FILE.getParentFile();
        boolean needHeader = !USER_STAFF_LIST_DEFAULT_FILE.exists() || USER_STAFF_LIST_DEFAULT_FILE.length()==0;
        if(parentFolder != null && !parentFolder.exists())
            parentFolder.mkdirs();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(USER_STAFF_LIST_DEFAULT_FILE, true))){

            if(needHeader){
                writer.write("Class,Name,Uid,Password");
                writer.newLine();
            }

            if(userExists(user.getUid())) {
                return false;
            }

            writer.write(user.toCSVFormat());
            writer.newLine();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User loadUserFromDatabase(String name, String password) {
        if(!USER_STAFF_LIST_DEFAULT_FILE.exists())
            return null;

        try(BufferedReader reader = new BufferedReader(new FileReader(USER_STAFF_LIST_DEFAULT_FILE))){
            String line;
            reader.readLine();
            while((line=reader.readLine())!=null){
                String[] rawText = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if(removeQuotes(rawText[1]).equals(name) && removeQuotes(rawText[3]).equals(password))
                    switch(rawText[0]){
                        case "Admin":
                            return new Admin(removeQuotes(rawText[1]), removeQuotes(rawText[2]), removeQuotes(rawText[3]));

                        case "Clerk":
                            return new Clerk(removeQuotes(rawText[1]), removeQuotes(rawText[2]), removeQuotes(rawText[3]));
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public void loadAllFromDatabase(Inventory inventory, CashRegister cashRegister, Logger logger) throws FileNotFoundException {
        if(ITEMHOLDER_LIST_DEFAULT_FILE.exists())
            loadItemHolder(inventory);

        if(LOG_LIST_DEFAULT_FILE.exists())
            loadLogs(logger);

        if(CASH_REGISTER_DEFAULT_FILE.exists())
            loadCashRegisterCredentials(cashRegister);

    }

    private void loadCashRegisterCredentials(CashRegister cashRegister){
        try (BufferedReader reader = new BufferedReader(new FileReader(CASH_REGISTER_DEFAULT_FILE))){
            reader.readLine();
            String rawLine = reader.readLine();
            if(rawLine!=null && !rawLine.isBlank()){
                String[] line = rawLine.split(",");
                cashRegister.setCurrentBalance(Double.parseDouble(line[0]));
                cashRegister.setTotalSales(Integer.parseInt(line[1]));
            }
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    private void loadLogs(Logger logger) {
        String line;

        try(BufferedReader reader = new BufferedReader(new FileReader(LOG_LIST_DEFAULT_FILE))){
            reader.readLine();
            while((line=reader.readLine())!=null){
                if(line.isBlank()) continue;
                String[] rawText = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                switch (rawText[0]){
                    case "DonationLog" -> logger.addLog(new DonationLog(
                            removeQuotes(rawText[1]),
                            rawText[2],
                            removeQuotes(rawText[3]),
                            removeQuotes(rawText[4]),
                            rawText[5],
                            Double.parseDouble(rawText[6])
                            ));
                    case "InventoryLog" -> logger.addLog(new InventoryLog(
                            removeQuotes(rawText[1]),
                            rawText[2],
                            removeQuotes(rawText[3]),
                            removeQuotes(rawText[4]),
                            rawText[5],
                            rawText[6]
                    ));
                    case "RefundLog" -> logger.addLog(new RefundLog(
                            removeQuotes(rawText[1]),
                            rawText[2],
                            removeQuotes(rawText[3]),
                            removeQuotes(rawText[4]),
                            rawText[5],
                            rawText[6],
                            Double.parseDouble(rawText[7]),
                            Double.parseDouble(rawText[8])
                    ));
                    case "SaleLog" -> logger.addLog(new SaleLog(
                            removeQuotes(rawText[1]),
                            rawText[2],
                            removeQuotes(rawText[3]),
                            removeQuotes(rawText[4]),
                            rawText[5],
                            rawText[6],
                            Double.parseDouble(rawText[7]),
                            Double.parseDouble(rawText[8])
                    ));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemHolder(Inventory inventory){
        try(BufferedReader reader = new BufferedReader(new FileReader(ITEMHOLDER_LIST_DEFAULT_FILE))){
            String line;
            reader.readLine();
            while((line=reader.readLine())!=null){
                if(line.isBlank())
                    continue;
                SpecificItem enumConst = SpecificItem.NOT_FOUND;
                String[] rawText = line.split((",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
                try{
                    enumConst = SpecificItem.valueOf(rawText[2]);
                } catch (IllegalArgumentException e){
//                    TODO: Implement error for this one
                }
                Item rawItem = getRawItem(rawText, enumConst);
                inventory.setItem(rawText[1], rawItem, rawText[3], Double.parseDouble(rawText[7]), Double.parseDouble(rawText[8]), this);
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
        if(!USER_STAFF_LIST_DEFAULT_FILE.exists())
           return false;

        try(BufferedReader reader = new BufferedReader(new FileReader(USER_STAFF_LIST_DEFAULT_FILE))) {
            String line;
            reader.readLine();
            while((line = reader.readLine())!=null){
                if(line.isEmpty())
                    continue;
                String[] rawText = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                if(rawText.length>=4 && removeQuotes(rawText[2]).equals(uid))
                    return true;
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static String removeQuotes(String rawText){
        return rawText.substring(1,rawText.length()-1);
    }
}
