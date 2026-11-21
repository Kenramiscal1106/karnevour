package application.services;

import database.CashRegister;
import database.Database;
import database.inventory.Inventory;
import database.logs.Logger;

import java.io.FileNotFoundException;

public class DataLoader {
    private static boolean loaded = false;

    public static void loadOnce(){
        if (loaded) return;
        try{
            Database.getInstance().loadAllFromDatabase(
                    Inventory.getInstance(), CashRegister.getInstance(), Logger.getInstance()
            );
            loaded = true;
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public static boolean isLoaded(){
        return loaded;
    }
}
