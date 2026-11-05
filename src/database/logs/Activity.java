package database.logs;

public class Activity implements Logable {
    private String logName;
    private String date;
    private String description;
    private String author;
    private String uid;

    Activity(String logName, String date, String description, String author){
        this.logName = logName;
        this.date = date;
        this.description = description;
        this.author = author;
    }

    // getters
    public String getLogName()      {   return this.logName;    }
    public String getDate()         {   return this.date;   }
    public String getDescription()  {   return this.description;    }
    public String getAuthor()       {   return this.author;  }
}
