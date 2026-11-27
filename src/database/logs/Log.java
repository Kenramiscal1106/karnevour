package database.logs;

/**
 * An abstract Class that holds basic functionalities for logging
 */
public abstract class Log implements Loggable{
    protected boolean isVoid = false;
    @Override
    public String toCSVFormat() {
        return "";
    }

    @Override
    public void voidLog() {
        isVoid = true;
    }

    @Override
    public boolean isLogVoid() {
        return isVoid;
    }

}
