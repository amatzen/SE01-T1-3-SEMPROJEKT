package worldofzuul;

public enum CommandWord
{
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    PICKUP("pickup"),
    INVENTORY("inventory"),
    DUMP("dump"),
    DROP("drop"),
    UNKNOWN("?");
    
    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
