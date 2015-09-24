package core;

import parser.Parser;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    public Core() {
        Parser.parse("X^2 + 2X");
        Parser.parse("9(X^2 + 6X^3 + 12X^5) - X^2");
        Parser.parse("-11(-X^13(-X^3 - 4)) - (X^2 + 4X^2)");
    }

    public static void main(String[] args){
        new Core();
    }
}
