package worldofzuul;

public enum CommandWord
{
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    PICKUP("pickup"),
    DROP("drop"),
    INVENTORY("inventory"),
    INTERACT("interact"),
    DUMP("dump"),
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
