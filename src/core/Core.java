package core;

import polynomial.Polynomial;

/**
 * Created by Melroy van Nijnatten - 0849740.
 */
public class Core {
    public Core() {
        Polynomial p = new Polynomial("2X^2 + 4X + 1");
        System.out.println(p.toString());

        p = new Polynomial("2X^2 + 4X + 1 + 5X - X^2");
        System.out.println(p.toString());

        p = new Polynomial("2X^2 + 4X + 1 + 5X - 3X^2 + X^3");
        System.out.println(p.toString());
    }

    public static void main(String[] args){
        new Core();
    }
}
