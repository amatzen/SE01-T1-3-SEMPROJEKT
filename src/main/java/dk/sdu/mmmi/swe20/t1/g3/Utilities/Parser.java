package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import worldofzuul.Command;

import java.util.Scanner;

public class Parser extends worldofzuul.Parser {
    public Parser() {
        super();
    }

    public Command getCommandFromString(String cmdText) {

        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print(cmdText);
        System.out.println();

        inputLine = cmdText;

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }
}
