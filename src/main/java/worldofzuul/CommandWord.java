package worldofzuul;

public enum CommandWord
{
    GO("go"), // GO til at gå rundt mellem rum
    TAKE("take"), // TAKE til at tage ting op
    INTERACT("interact"), // INTERACT for at interagere med objekter
    QUIT("quit"), // QUIT for at afslutte spillet
    HELP("help"), // HELP for hjælp
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
