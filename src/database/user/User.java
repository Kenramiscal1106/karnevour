package database.user;

import database.SimpleHash;

import java.security.NoSuchAlgorithmException;

public class User {
    private final String name;
    private final int id;
    private final String password;

    User(String name, int id, String password){
        this.name = name;
        this.password = password;
        this.id = id;
    }

    // getters
    public final String getName(){  return this.name;   }
    public String getPassword() {
        return this.password;
    }

    // refactor latur
    public final String toCSVFormat() throws NoSuchAlgorithmException {
        return this.getClass().getSimpleName()+","+
                "\""+this.getName()+"\""+","+
                SimpleHash.simpleHash(this.password);
    }

    public int getUid() {
        return id;
    }
}
