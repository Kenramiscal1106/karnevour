package database.user;

public abstract class User {
    private String name;
    private String uid;

    User(String name, String uid){
        this.name = name;
        this.uid = uid;
    }

    // getters
    public final String getName(){  return this.name;   }
    public final String getUid() {  return this.uid;    }
}
