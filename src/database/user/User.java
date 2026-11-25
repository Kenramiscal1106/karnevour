package database.user;

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

    public final String toCSVFormat() {
        return this.getClass().getSimpleName()+","+
                "\""+this.getName()+"\""+","+
                "\""+this.getPassword()+"\"";
    }

    public int getUid() {
        return id;
    }
}
