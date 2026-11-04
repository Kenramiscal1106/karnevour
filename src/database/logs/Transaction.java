package database.logs;

public class Transaction implements Log{
    private String transactionName;
    private String date;
    private String description;
    private String author;

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
}
