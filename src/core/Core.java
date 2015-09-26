package core;

import gui.GUICore;
import parser.Parser;
import polynomial.Polynomial;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    public Core() {
        //start gui
        GUICore.main(new String[]{});

        Parser.parse("4*X^2 + 2X");
        Parser.parse("4*Z^2 + 2Y");
        Parser.parse("4*X^2 + 2");
        Parser.parse("x^2 + 2*x");
        Parser.parse("9(X^2 + 6X^3 + 12X^5) - X^2");
        Parser.parse("-11(-X^13(-X^3 - 4)) - (X^2 + 4X^2)");
        Parser.parse("9(X^2 + X - 1)(X - X^5 + 4X^2)");
        Parser.parse("9(X^2 + X - 1)-(X - X^5 + 4X^2)");

        System.out.println();
        System.out.println();

        new Polynomial(10, "4*X^2 + 2X");
        new Polynomial(10, "9(X^2 + X - 1)-(X - X^5 + 4X^2)");
        new Polynomial(10, "10(X^2 + X - 1)-(X - X^5 + 4X^2)");
        new Polynomial(10, "0");

        new GUICore();
    }

    public static void main(String[] args){
        new Core();
    }
}
