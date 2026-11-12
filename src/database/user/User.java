package database.user;

public abstract class User {
    private String name;
    private String uid;
    private String password;

    User(String name, String uid, String password){
        this.name = name;
        this.uid = uid;
        this.password = password;
    }

    // getters
    public final String getName(){  return this.name;   }
    public final String getUid() {  return this.uid;    }
    public String getPassword() {
        return this.password;
    }

    public final String toCSVFormat() {
        return this.getClass().getSimpleName()+","+
                "\""+this.getName()+"\""+","+
                "\""+this.getUid()+"\""+","+
                "\""+this.getPassword()+"\"";
    }
}
