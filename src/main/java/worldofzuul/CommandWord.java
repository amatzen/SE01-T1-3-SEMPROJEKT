package worldofzuul;

public enum CommandWord
{
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    PICKUP("pickup"),
    INVENTORY("inventory"),
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
