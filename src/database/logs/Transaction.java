package database.logs;

public class Transaction implements Logable {
    private String transactionName;
    private String date;
    private String description;
    private String author;
    private String uid;

    Transaction(String transactionName, String date, String description, String author){
        this.transactionName = transactionName;
        this.date = date;
        this.description = description;
        this.author = author;
    }

    public String getTransactionName() {   return this.transactionName;    }
    public String getDate()            {   return this.date;   }
    public String getDescription()     {   return this.description;  }
    public String getAuthor()          {   return this.author;  }
    public String getUid()             {   return this.uid;     }

}
