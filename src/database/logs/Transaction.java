package database.logs;

public class Transaction implements Logable {
    private String name;
    private String date;
    private String description;
    private String author;
    private String uid;

    public Transaction(String name, String date, String description, String author, String uid){
        this.name = name;
        this.date = date;
        this.description = description;
        this.author = author;
        this.uid = uid;
    }

    public String getName()            {   return this.name;    }
    public String getDate()            {   return this.date;   }
    public String getDescription()     {   return this.description;  }
    public String getAuthor()          {   return this.author;  }
    public String getUid()             {   return this.uid;     }
    public String toCSVFormat() {
        return  this.getName()+","+
                this.getDate()+","+
                "\""+this.getDescription()+"\""+","+
                this.getAuthor()+","+
                this.getUid();
    }

}
